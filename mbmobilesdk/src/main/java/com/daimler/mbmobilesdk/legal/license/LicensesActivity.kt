package com.daimler.mbmobilesdk.legal.license

import android.content.Context
import android.content.Intent
import androidx.databinding.ViewDataBinding
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.extensions.createAndroidViewModel
import com.daimler.mbuikit.components.activities.MBBaseToolbarActivity
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

class LicensesActivity : MBBaseToolbarActivity<LicensesViewModel>() {

    override val toolbarTitle: String
        get() = getString(R.string.legal_licences)

    override val buttonMode: Int = BUTTON_BACK

    override fun createViewModel(): LicensesViewModel {
        return createAndroidViewModel()
    }

    override fun getContentLayoutRes(): Int = R.layout.activity_licenses

    override fun getContentModelId(): Int = BR.model

    override fun onContentBindingCreated(binding: ViewDataBinding) {
        super.onContentBindingCreated(binding)
        viewModel.licenseSelectedEvent.observe(this, onLicenseSelected())
    }

    override fun onToolbarButtonClicked() {
        finish()
    }

    private fun onLicenseSelected() = LiveEventObserver<LicenseDetail> {
        startActivity(LicenseDetailActivity.getStartIntent(this, it))
    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, LicensesActivity::class.java)
        }
    }
}