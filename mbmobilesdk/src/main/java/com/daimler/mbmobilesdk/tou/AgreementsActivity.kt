package com.daimler.mbmobilesdk.tou

import android.content.Context
import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.tou.natcon.NatconLegalFragment
import com.daimler.mbmobilesdk.tou.soe.SoeAgreementsFragment
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.utils.extensions.replaceFragment
import kotlinx.android.synthetic.main.activity_agreements.*

class AgreementsActivity : MBBaseViewModelActivity<AgreementsViewModel>(), MBAgreementsCallback {

    override fun createViewModel(): AgreementsViewModel {
        val factory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        return ViewModelProviders.of(this, factory).get(AgreementsViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.activity_agreements

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        when (intent.getSerializableExtra(ARG_TYPE) as Type?) {
            Type.SOE ->
                showFragment(SoeAgreementsFragment.newInstance())
            Type.NATCON ->
                showFragment(NatconLegalFragment.newInstance(MBMobileSDK.userLocale().countryCode))
            null -> throw IllegalArgumentException("You need to provide a type.")
        }
    }

    override fun onSoeUpdated() {
        finishOkay()
    }

    override fun onSoeCancelled() {
        finishCancelled()
    }

    override fun onNatconUpdated() {
        finishOkay()
    }

    override fun onNatconCancelled() {
        finishCancelled()
    }

    override fun onUpdateAgreementsTitle(title: String) {
        viewModel.updateToolbarTitle(title)
    }

    private fun showFragment(fragment: BaseAgreementsFragment<*>) {
        replaceFragment(R.id.content_container, fragment)
    }

    private fun finishOkay() {
        finishWithResult(RESULT_OK)
    }

    private fun finishCancelled() {
        finishWithResult(RESULT_CANCELED)
    }

    private fun finishWithResult(resultCode: Int, data: Intent? = null) {
        setResult(resultCode, data)
        finish()
    }

    enum class Type { SOE, NATCON }

    companion object {

        private const val ARG_TYPE = "arg.agreements.subsystem"

        fun getStartIntent(context: Context, type: Type): Intent {
            val intent = Intent(context, AgreementsActivity::class.java)
            intent.putExtra(ARG_TYPE, type)
            return intent
        }
    }
}