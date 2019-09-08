package com.daimler.mbmobilesdk.tou.soe

import androidx.databinding.ViewDataBinding
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.tou.BaseAgreementsFragment
import com.daimler.mbmobilesdk.utils.extensions.*
import com.daimler.mbingresskit.util.IngressFileProvider
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

internal class SoeAgreementsFragment : BaseAgreementsFragment<SoeAgreementsViewModel>() {

    override fun createViewModel(): SoeAgreementsViewModel {
        return createAndroidViewModel()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_soe_agreements

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        notifyAgreementsTitle(getString(R.string.agreement_title))

        viewModel.readSoeEvent.observe(this, onReadSoeEvent())
        viewModel.updateEvent.observe(this, onUpdateEvent())
        viewModel.cancelEvent.observe(this, onCancelEvent())
        viewModel.rejectEvent.observe(this, onRejectedEvent())
        viewModel.errorEvent.observe(this, onErrorEvent())
    }

    private fun onReadSoeEvent() = LiveEventObserver<String> { path ->
        context?.let { ctx ->
            val pdfIntent = IngressFileProvider.getPdfIntent(ctx, path)
            pdfIntent?.let { startActivity(it) } ?: showNoPdfReaderError()
        }
    }

    private fun onUpdateEvent() = LiveEventObserver<String> { msg ->
        activity?.let {
            if (canShowDialog()) {
                it.showOkayDialog(msg) {
                    notifySoeUpdated()
                }
            }
        }
    }

    private fun onCancelEvent() = LiveEventObserver<Unit> {
        notifySoeCancelled()
    }

    private fun onRejectedEvent() = LiveEventObserver<String> { msg ->
        activity?.let { activity ->
            if (canShowDialog()) {
                MBDialogFragment.Builder(0).apply {
                    withMessage(msg)
                    withPositiveButtonText(getString(R.string.general_yes))
                    withNegativeButtonText(getString(R.string.general_no))
                    withPositiveAction { viewModel.rejectTermsAndConditions() }
                }.build().show(activity.supportFragmentManager, null)
            }
        }
    }

    private fun showNoPdfReaderError() {
        if (canShowDialog()) {
            activity?.showOkayDialog(getString(R.string.agreements_error_no_pdf_reader))
        }
    }

    private fun onErrorEvent() = simpleTextObserver()

    companion object {

        fun newInstance(): SoeAgreementsFragment = SoeAgreementsFragment()
    }
}