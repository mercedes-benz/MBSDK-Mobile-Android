package com.daimler.mbmobilesdk.jumio.identitycheck

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.widget.AdapterView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.BuildConfig.JUMIO_API_SECRET
import com.daimler.mbmobilesdk.BuildConfig.JUMIO_API_TOKEN
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.databinding.ActivityJumioIdentityCheckBinding
import com.daimler.mbmobilesdk.jumio.JumioSelectedConfig
import com.daimler.mbmobilesdk.jumio.dialogs.JumioTermsDialog
import com.daimler.mbmobilesdk.jumio.identityScanner.IdentityScannerActivity.Companion.getIdentityScannerIntent
import com.daimler.mbuikit.components.activities.MBBaseToolbarActivity
import com.jumio.core.enums.JumioDataCenter
import com.jumio.nv.NetverifySDK

class IdentityCheckActivity : MBBaseToolbarActivity<IdentityCheckViewModel>() {

    override val buttonMode: Int = BUTTON_BACK

    override val toolbarTitle: String by lazy { getString(R.string.service_summary_identity_check) }

    override fun getContentLayoutRes(): Int = R.layout.activity_jumio_identity_check

    override fun getContentModelId(): Int = BR.viewModel

    private var termsAccepted = MBMobileSDK.jumioSettings().jumioTermsAccepted.get()

    override fun createViewModel(): IdentityCheckViewModel {
        return ViewModelProviders.of(this).get(IdentityCheckViewModel::class.java)
    }

    override fun onContentBindingCreated(binding: ViewDataBinding) {
        super.onContentBindingCreated(binding)

        (binding as ActivityJumioIdentityCheckBinding).countries.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.let {
                    viewModel.onSelectItem(it.context, position)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        viewModel.documentSelectedCommand.observe(this, onDocumentSelected())

        if (checkIfAlreadyHavePermission()) {
            showTermsAndDataPolicyDialog()
            initJumio()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.CAMERA
                ), REQUEST_CODE
            )
        }
    }

    private fun initJumio() {
        val netverifySDK = NetverifySDK.create(this, JUMIO_API_TOKEN, JUMIO_API_SECRET, JumioDataCenter.EU)
        NetverifyController.start(netverifySDK) {
            viewModel.setConfig(it)
        }
    }

    private fun checkIfAlreadyHavePermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        return result == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showTermsAndDataPolicyDialog()
                    initJumio()
                } else {
                    onBackPressed()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun onDocumentSelected() = Observer<JumioSelectedConfig> {
        startActivity(getIdentityScannerIntent(it).apply { addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT) })
    }

    private fun showTermsAndDataPolicyDialog() {
        if (!termsAccepted) {
            JumioTermsDialog.Builder(0)
                .withListener(object : JumioTermsDialog.DialogListener {
                override fun onNegativeAction(id: Int) {
                    finish()
                }

                override fun onPositiveAction(id: Int) {
                    MBMobileSDK.jumioSettings().jumioTermsAccepted.set(true)
                }
            }).build().show(supportFragmentManager, null)

            /*JumioTermsDialogFragment.Builder()
                .withTitle(getString(R.string.verification_terms_popup_title))
                .withMessage(getString(R.string.verification_terms_popup_description))
                .withPositiveButton(getString(R.string.agreement_accept)) {}
                .withNegativeButton(getString(R.string.general_cancel)) { finish() }
                .build().show(supportFragmentManager, null)*/
        }
    }

    companion object {
        const val REQUEST_CODE = 101
    }
}