package com.daimler.mbmobilesdk.jumio.identityScanner

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.jumio.JumioSelectedConfig
import com.daimler.mbmobilesdk.jumio.identitycheck.NetverifyController
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbuikit.components.activities.MBBaseToolbarActivity
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.utils.extensions.toast
import com.jumio.nv.custom.NetverifyCancelReason
import com.jumio.nv.custom.NetverifyCustomScanView
import kotlinx.android.synthetic.main.activity_jumio_identity_scanner.*

class IdentityScannerActivity : MBBaseToolbarActivity<IdentityScannerViewModel>(), NetverifyController.ScanEvents {

    override val buttonMode: Int = BUTTON_BACK

    override var toolbarTitle: String = ""

    override fun getContentLayoutRes(): Int = R.layout.activity_jumio_identity_scanner

    override fun getContentModelId(): Int = BR.viewModel

    private lateinit var config: JumioSelectedConfig

    override fun createViewModel(): IdentityScannerViewModel {
        return ViewModelProviders.of(this).get(IdentityScannerViewModel::class.java)
    }

    override fun onContentBindingCreated(binding: ViewDataBinding) {
        super.onContentBindingCreated(binding)

        (intent.getSerializableExtra(EXTRA_CONFIG) as JumioSelectedConfig?)?.let { config ->
            this.config = config
            NetverifyController.setConfig(config)
            tryScanNext()
        }

        NetverifyController.scanFinishedCommand.observe(this, Observer { scanReference ->
            viewModel.hasBackButton.postValue(false)
            viewModel.title.postValue(getString(R.string.identity_check_upload))
            viewModel.uploading.value = false
            viewModel.uploaded.value = true
            MBIngressKit.refreshTokenIfRequired()
                .onComplete { token ->
                    MBIngressKit.userService().verifyUser(token.jwtToken.plainToken, scanReference)
                        .onComplete {
                            setResult(Activity.RESULT_OK, Intent())
                            MBDialogFragment.Builder()
                                .withTitle(getString(R.string.successful_identity_check_title))
                                .withMessage(getString(R.string.successful_identity_check_description))
                                .withPositiveButton(getString(R.string.general_ok))
                                .build().show(supportFragmentManager, null)
                        }
                        .onFailure {
                            setResult(Activity.RESULT_CANCELED, Intent())
                            MBDialogFragment.Builder()
                                .withTitle(getString(R.string.unsuccessful_identity_check_title))
                                .withMessage(getString(R.string.unsuccessful_identity_check_description))
                                .withPositiveButton(getString(R.string.bam_error_view_button_try_again))
                                .addNeutralButton(getString(R.string.assign_action_search_retailer))
                                .withNegativeButton(getString(R.string.general_cancel))
                                .build().show(supportFragmentManager, null)
                        }
                }
                .onFailure {
                    setResult(Activity.RESULT_CANCELED, Intent())
                }
        })

        viewModel.title.observe(this, Observer { viewModel.adjustTitle(it) })
    }

    private fun tryScanNext() {
        if (NetverifyController.hasNextSide()) {
            NetverifyController.scanNextSide(scan_view, confirm_view, this)?.let {
                viewModel.presenter = it
                viewModel.stepText.postValue(getString(
                    R.string.identity_check_steps,
                    NetverifyController.currentStep,
                    NetverifyController.totalCount
                ))
                when (scan_view.mode) {
                    NetverifyCustomScanView.MODE_FACE -> {
                        viewModel.title.postValue(getString(R.string.verification_face_title))
                        viewModel.hintTitleText.postValue(getString(R.string.verification_capture_face))
                    }
                    NetverifyCustomScanView.MODE_ID -> {
                        viewModel.title.postValue(getString(R.string.netverify_scanview_title))
                        viewModel.hintTitleText.postValue(config.selectedType.getLocalizedName(this))
                    }
                }
            }
            viewModel.scanState.value = true
        } else {
            NetverifyController.finish()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun clickReadable(v: View) {
        viewModel.scanState.value = true
        viewModel.presenter?.confirmScan()
        viewModel.presenter?.destroy()
        tryScanNext()
    }

    override fun onResume() {
        super.onResume()
        NetverifyController.resume()
    }

    override fun onPause() {
        super.onPause()
        NetverifyController.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.presenter?.destroy()
    }

    @Suppress("UNUSED_PARAMETER")
    fun next(v: View) {
        finish()
    }

    override fun onConfirm() {
        viewModel.scanState.value = false
        viewModel.title.postValue(getString(R.string.netverify_scanview_title_check))
    }

    override fun onScanNext() {
        tryScanNext()
    }

    override fun onUploading() {
        viewModel.uploading.value = true
    }

    override fun onCanceled(cancelReason: NetverifyCancelReason?) {
        toast(getString(R.string.general_error_msg))
        finish()
    }

    companion object {
        private const val EXTRA_CONFIG = "extra.config"

        fun Activity.getIdentityScannerIntent(config: JumioSelectedConfig): Intent {
            val intent = Intent(this, IdentityScannerActivity::class.java)
            return intent.putExtra(EXTRA_CONFIG, config)
        }
    }
}
