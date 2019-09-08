package com.daimler.mbmobilesdk.legal.license

import android.content.Context
import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.checkParameterNotNull
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

class LicenseDetailActivity : MBBaseViewModelActivity<LicenseDetailViewModel>() {

    override fun createViewModel(): LicenseDetailViewModel {
        val license = intent.getParcelableExtra<LicenseDetail>(ARG_LICENSE)
        checkParameterNotNull("license", license)
        val factory = LicenseDetailViewModelFactory(application, license.libraryTitle, license.licenseFileName)
        return ViewModelProviders.of(this, factory).get(LicenseDetailViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.activity_license_detail

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        viewModel.onCloseEvent.observe(this, onClose())
    }

    private fun onClose() = LiveEventObserver<Unit> { finish() }

    companion object {
        private const val ARG_LICENSE = "arg.license.detail"

        fun getStartIntent(context: Context, license: LicenseDetail): Intent {
            val intent = Intent(context, LicenseDetailActivity::class.java)
            intent.putExtra(ARG_LICENSE, license)
            return intent
        }
    }
}