package com.daimler.mbmobilesdk.jumio.dialogs

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.databinding.DialogJumioTermsDataPolicyBinding
import com.daimler.mbmobilesdk.utils.extensions.ClickAction
import com.daimler.mbmobilesdk.utils.extensions.getPdfIntent
import com.daimler.mbmobilesdk.utils.extensions.setClickableSpans
import com.daimler.mbingresskit.util.IngressFileProvider
import com.daimler.mbuikit.components.dialogfragments.MBBaseDialogFragment
import com.daimler.mbuikit.components.viewmodels.MBGenericDialogViewModel
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.widgets.buttons.MBPrimaryTextButton

class JumioTermsDialog : MBBaseDialogFragment<MBGenericDialogViewModel>() {

    private var dialogId = DEFAULT_ID
    private var listener: DialogListener? = null

    override fun getLayoutRes(): Int {
        return R.layout.dialog_jumio_terms_data_policy
    }

    override fun getModelId(): Int {
        return BR.model
    }

    override fun createViewModel(): MBGenericDialogViewModel {
        val factory = JumioTermsDialogViewModelFactory(
            getString(R.string.verification_terms_popup_title),
            getString(R.string.verification_terms_popup_description),
            getString(R.string.agreement_accept),
            getString(R.string.general_cancel)
        )
        return ViewModelProviders.of(this, factory).get(JumioTermsDialogViewModel::class.java)
    }

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        isCancelable = false

        (viewModel as JumioTermsDialogViewModel).apply {
            positiveClickEvent.observe(this@JumioTermsDialog, onPositiveClickEvent())
            negativeClickEvent.observe(this@JumioTermsDialog, onNegativeClickEvent())
            termsAcceptedLiveData.observe(this@JumioTermsDialog, onAcceptTermsAndConditions())
            showPdfAgreementsEvent.observe(this@JumioTermsDialog, showPdfAgreement())
            showWebAgreementsEvent.observe(this@JumioTermsDialog, showWebAgreement())
        }

        applyClickableSpans()
    }

    private fun applyClickableSpans() {
        val text = getString(R.string.verification_terms_popup_chekbox_title)
        val partTermsOfUse = getString(R.string.verification_terms_popup_chekbox_terms_url)
        val privacyStatement = getString(R.string.verification_terms_popup_chekbox_privacy_url)
        val clickActions = listOf(
            ClickAction(partTermsOfUse) { (viewModel as JumioTermsDialogViewModel).onTermsSelected() },
            ClickAction(privacyStatement) { (viewModel as JumioTermsDialogViewModel).onPrivacyStatementSelected() }
        )
        context?.let {
            (binding as DialogJumioTermsDataPolicyBinding).acceptDescription.setClickableSpans(text, clickActions,
                ContextCompat.getColor(it, R.color.mb_accent_primary))
        }
    }

    class Builder(private val dialogId: Int) {

        private var dialogListener: DialogListener? = null

        fun withListener(dialogListener: DialogListener?): Builder {
            this.dialogListener = dialogListener
            return this
        }

        fun build(): JumioTermsDialog {
            return JumioTermsDialog().apply {
                listener = dialogListener
            }
        }
    }

    private fun showPdfAgreement() = LiveEventObserver<String> {
        context?.let { ctx ->
            val pdfIntent = IngressFileProvider.getPdfIntent(ctx, it)
            pdfIntent?.let { startActivity(it) } ?: showNoPdfReaderError()
        }
    }

    private fun showWebAgreement() = LiveEventObserver<String> {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(it)
        startActivity(intent)
    }

    private fun showNoPdfReaderError() {
        context?.let {
            Toast.makeText(it, getString(R.string.agreements_error_no_pdf_reader), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun onNegativeAction() {
        listener?.onNegativeAction(dialogId)
    }

    private fun onPositiveAction() {
        listener?.onPositiveAction(dialogId)
    }

    private fun onPositiveClickEvent() = LiveEventObserver<Unit> {
        onPositiveAction()
        dismiss()
    }

    private fun onNegativeClickEvent() = LiveEventObserver<Unit> {
        onNegativeAction()
        dismiss()
    }

    private fun onAcceptTermsAndConditions() = Observer<Boolean> {
        binding.root.findViewById<MBPrimaryTextButton>(R.id.accept_button).isClickable = it
    }

    interface DialogListener {

        /**
         * Called when the user taps the positive button.
         */
        fun onPositiveAction(id: Int)

        /**
         * Called when the user taps the negative button.
         */
        fun onNegativeAction(id: Int)
    }

    companion object {
        const val DEFAULT_ID = -1
    }
}