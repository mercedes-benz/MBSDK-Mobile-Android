package com.daimler.mbmobilesdk.jumio.dialogs

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbingresskit.util.IngressFileProvider
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.extensions.ClickAction
import com.daimler.mbmobilesdk.utils.extensions.getPdfIntent
import com.daimler.mbmobilesdk.utils.extensions.setClickableSpans
import com.daimler.mbuikit.components.dialogfragments.MBGenericDialogFragment
import com.daimler.mbuikit.components.dialogfragments.buttons.ClickablePurpose
import com.daimler.mbuikit.components.dialogfragments.buttons.DialogButtonOrientation
import com.daimler.mbuikit.components.dialogfragments.buttons.DialogButtonStyle
import com.daimler.mbuikit.components.dialogfragments.buttons.DialogClickable
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.widgets.checkboxes.MBCheckBox

class JumioTermsDialogFragment : MBGenericDialogFragment<JumioTermsDialogViewModel>() {

    var checkBoxAccept: MBCheckBox? = null

    override fun <T> onButtonClicked(button: DialogClickable<T>) {
        when (button.purpose) {
            ClickablePurpose.POSITIVE -> button.invokeClickAction(Unit)
            ClickablePurpose.NEGATIVE -> button.invokeClickAction(Unit)
            ClickablePurpose.NEUTRAL -> button.invokeClickAction(Unit)
        }
    }

    override fun onInflateBelowMessage(root: ViewGroup) {
        super.onInflateBelowMessage(root)

        activity?.applicationContext?.let {
            val layout = LayoutInflater.from(it).inflate(R.layout.dialog_jumio_terms_check, null)
            root.addView(layout)

            checkBoxAccept = root.findViewById(R.id.checkbox_accept)

            applyClickableSpans(it)

            checkBoxAccept?.apply {
                isChecked = false
                setOnClickListener { checkBox ->
                    (checkBox as MBCheckBox).apply {
                        // TODO: disable/enable button
                    }
                }
            }
        }
    }

    override fun createViewModel(): JumioTermsDialogViewModel {
        val factory = JumioTermsDialogViewModelFactory(
            title,
            msg,
            positive,
            negative
        )
        return ViewModelProviders.of(this, factory).get(JumioTermsDialogViewModel::class.java)
    }

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        viewModel.showPdfAgreementsEvent.observe(this, showPdfAgreement())
        viewModel.showWebAgreementsEvent.observe(this, showWebAgreement())
    }

    override fun onPositiveAction() = Unit

    override fun onNegativeAction() = Unit

    private fun applyClickableSpans(context: Context) {
        val text = getString(R.string.verification_terms_popup_chekbox_title)
        val partTermsOfUse = getString(R.string.verification_terms_popup_chekbox_terms_url)
        val privacyStatement = getString(R.string.verification_terms_popup_chekbox_privacy_url)
        val clickActions = listOf(
            ClickAction(partTermsOfUse) { viewModel.onTermsSelected() },
            ClickAction(privacyStatement) { viewModel.onPrivacyStatementSelected() }
        )
        checkBoxAccept?.apply {
            setClickableSpans(text, clickActions,
                ContextCompat.getColor(context, R.color.mb_accent_primary))
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

    class Builder : BaseDialogBuilder<Unit, Unit>() {

        fun withTitle(title: String?): Builder {
            this.title = title
            return this
        }

        fun withMessage(message: String?): Builder {
            this.message = message
            return this
        }

        fun withOrientation(orientation: DialogButtonOrientation): Builder {
            this.orientation = orientation
            return this
        }

        fun addNeutralButton(title: String, clickAction: ((Unit) -> Unit)? = null): Builder {
            neutralButtons.add(JumioDialogButton(title, ClickablePurpose.NEUTRAL, DialogButtonStyle.SECONDARY, true, clickAction))
            return this
        }

        fun withPositiveButton(title: String, clickAction: ((Unit) -> Unit)? = null): Builder {
            positiveButton = JumioDialogButton(title, ClickablePurpose.POSITIVE, DialogButtonStyle.PRIMARY, true, clickAction)
            return this
        }

        fun withNegativeButton(title: String, clickAction: ((Unit) -> Unit)? = null): Builder {
            negativeButton = JumioDialogButton(title, ClickablePurpose.NEGATIVE, DialogButtonStyle.SECONDARY, true, clickAction)
            return this
        }

        fun build(): JumioTermsDialogFragment {
            return JumioTermsDialogFragment().apply {
                applyBundleToFragment(this)
                applyButtonsToFragment(this)
            }
        }
    }

    internal data class JumioDialogButton<T>(
        override val title: String,
        override val purpose: ClickablePurpose,
        override val style: DialogButtonStyle,
        override val dismissAfterClick: Boolean,
        override val clickAction: ((T) -> Unit)?
    ) : DialogClickable<T>
}