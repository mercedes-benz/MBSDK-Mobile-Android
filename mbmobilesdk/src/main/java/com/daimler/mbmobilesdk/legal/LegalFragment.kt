package com.daimler.mbmobilesdk.legal

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.legal.license.LicensesActivity
import com.daimler.mbmobilesdk.menu.NavigationCallback
import com.daimler.mbmobilesdk.tou.AgreementsActivity
import com.daimler.mbmobilesdk.tou.HtmlUserAgreementActivity
import com.daimler.mbmobilesdk.tou.custom.HtmlUserAgreementContent
import com.daimler.mbmobilesdk.utils.extensions.createAndroidViewModel
import com.daimler.mbmobilesdk.utils.extensions.simpleTextObserver
import com.daimler.mbuikit.components.fragments.MBBaseMenuFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

internal open class LegalFragment : MBBaseMenuFragment<LegalViewModel>() {

    private var navigationCallback: NavigationCallback? = null

    override fun createViewModel(): LegalViewModel =
        createAndroidViewModel(LegalViewModel::class.java)

    override fun getLayoutRes(): Int = R.layout.fragment_legal

    override fun getModelId(): Int = BR.model

    override fun getToolbarTitleRes(): Int = R.string.menu_legal

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        viewModel.showNatconEvent.observe(this, onShowNatcon())
        viewModel.showSoeEvent.observe(this, onShowSoe())
        viewModel.showHtmlEvent.observe(this, onShowHtml())
        viewModel.showLicensesEvent.observe(this, onShowLicenses())
        viewModel.showSupportEvent.observe(this, onShowSupport())
        viewModel.errorEvent.observe(this, onErrorEvent())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationCallback = context as? NavigationCallback
    }

    override fun onDetach() {
        super.onDetach()
        navigationCallback = null
    }

    private fun onShowNatcon() = LiveEventObserver<Unit> {
        context?.let {
            startActivity(AgreementsActivity.getStartIntent(it, AgreementsActivity.Type.NATCON))
        }
    }

    private fun onShowSoe() = LiveEventObserver<Unit> {
        context?.let {
            startActivity(AgreementsActivity.getStartIntent(it, AgreementsActivity.Type.SOE))
        }
    }

    private fun onShowHtml() =
        LiveEventObserver<HtmlUserAgreementContent> {
            context?.let { ctx ->
                startActivity(HtmlUserAgreementActivity.getStartIntent(
                    ctx, it.displayName.orEmpty(), it.htmlContent
                ))
            }
        }

    private fun onShowLicenses() = LiveEventObserver<Unit> {
        context?.let {
            startActivity(LicensesActivity.getStartIntent(it))
        }
    }

    private fun onShowSupport() = LiveEventObserver<Unit> {
            navigationCallback?.onShowSupport()
    }

    private fun onErrorEvent() = simpleTextObserver()

    companion object {

        fun newInstance() = LegalFragment()
    }
}