package com.daimler.mbmobilesdk.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.databinding.FragmentCredentialsBinding
import com.daimler.mbmobilesdk.featuretoggling.FLAG_BETA_PHASE_CONTENT
import com.daimler.mbmobilesdk.featuretoggling.isFeatureToggleEnabled
import com.daimler.mbmobilesdk.registration.LoginUser
import com.daimler.mbmobilesdk.utils.extensions.*
import com.daimler.mbmobilesdk.utils.extensions.application
import com.daimler.mbmobilesdk.utils.extensions.canShowDialog
import com.daimler.mbmobilesdk.utils.extensions.getPdfIntent
import com.daimler.mbmobilesdk.utils.extensions.simpleTextObserver
import com.daimler.mbingresskit.util.IngressFileProvider
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

internal class CredentialsFragment : BaseLoginFragment<CredentialsViewModel>() {

    override fun createViewModel(): CredentialsViewModel {
        val userId = arguments?.getString(EXTRA_ME_ID, null)
        val factory = CredentialsViewModelFactory(application, userId)
        return ViewModelProviders.of(this, factory).get(CredentialsViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_credentials

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        val credentialsBinding: FragmentCredentialsBinding = binding as FragmentCredentialsBinding

        viewModel.navigateToLocaleSelection.observe(this, navigateToLocaleSelection())
        viewModel.navigateToPinVerification.observe(this, navigateToPinVerification())
        viewModel.pinRequestError.observe(this, requestPinFailed())
        viewModel.pinRequestStarted.observe(this, pinRequestStarted())
        viewModel.showLegalEvent.observe(this, showLegalButton())
        viewModel.showPdfAgreementsEvent.observe(this, showPdfAgreement())
        viewModel.showWebAgreementsEvent.observe(this, showWebAgreement())
        viewModel.errorEvent.observe(this, errorEvent())
        viewModel.showMmeIdInfoEvent.observe(this, showMmeIdInfo())

        applyClickableSpans(credentialsBinding)

        notifyEndpointVisibilityChanged(true)
        notifyHideToolbar()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeVideo()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseVideo()
    }

    private fun applyClickableSpans(binding: FragmentCredentialsBinding) {
        val text = if (isFeatureToggleEnabled(FLAG_BETA_PHASE_CONTENT)) {
            getString(R.string.login_disclaimer_gtc_beta)
        } else {
            getString(R.string.login_disclaimer_gtc)
        }
        val partEula = getString(R.string.login_disclaimer_part_eula)
        val partData = getString(R.string.login_disclaimer_part_data_protection)
        val partBeta = getString(R.string.login_disclaimer_part_beta)
        val clickActions = listOf(
            ClickAction(partEula) { viewModel.onEulaSelected() },
            ClickAction(partData) { viewModel.onDataProtectionSelected() },
            ClickAction(partBeta) { viewModel.onBetaTermsSelected() }
        )
        setCustomSpans(binding.tvDisclaimer, text, clickActions)
    }

    private fun setCustomSpans(textView: TextView, text: String, clickActions: List<ClickAction>) {
        val context = context ?: return
        val spannableString = SpannableString(text)
        clickActions.forEach {
            val clickSpan = object : ClickableSpan() {
                override fun updateDrawState(ds: TextPaint) {
                    ds.color = ContextCompat.getColor(context, R.color.mb_accent_primary)
                    ds.isUnderlineText = false
                }

                override fun onClick(widget: View) {
                    it.handler.invoke()
                }
            }
            val clickableText = it.text
            val index = text.indexOf(clickableText)
            if (index >= 0) {
                spannableString.setSpan(clickSpan, index, index + clickableText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun pinRequestStarted() = LiveEventObserver<Unit> {
        notifyLoginStarted()
    }

    private fun requestPinFailed() = LiveEventObserver<String> {
        notifyPinError(it)
    }

    private fun navigateToLocaleSelection() = LiveEventObserver<LoginUser> {
        notifyShowLocaleSelection(it)
    }

    private fun navigateToPinVerification() = LiveEventObserver<LoginUser> {
        notifyShowPinVerification(it, true)
    }

    private fun showLegalButton() = LiveEventObserver<Unit> {
        notifyShowLegal()
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

    private fun errorEvent() = simpleTextObserver()

    private fun showMmeIdInfo() = LiveEventObserver<Unit> {
        if (canShowDialog()) {
            activity?.let { activity ->
                MBDialogFragment.Builder(0).apply {
                    withTitle(getString(R.string.login_info_alert_title))
                    withMessage(getString(R.string.login_info_alert_message))
                    withPositiveButtonText(getString(R.string.general_ok))
                }.build().show(activity.supportFragmentManager, null)
            }
        }
    }

    private fun showNoPdfReaderError() {
        if (canShowDialog()) {
            activity?.showOkayDialog(getString(R.string.agreements_error_no_pdf_reader))
        }
    }

    companion object {

        private const val EXTRA_ME_ID = "extra.credentials.me.id"

        fun newInstance(userId: String? = null): CredentialsFragment {
            val fragment = CredentialsFragment()
            val bundle = Bundle().apply {
                putString(EXTRA_ME_ID, userId)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}