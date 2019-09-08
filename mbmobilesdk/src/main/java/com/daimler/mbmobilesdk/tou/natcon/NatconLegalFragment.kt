package com.daimler.mbmobilesdk.tou.natcon

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.tou.BaseAgreementsFragment
import com.daimler.mbmobilesdk.utils.checkParameterNotNull
import com.daimler.mbmobilesdk.utils.extensions.*
import com.daimler.mbingresskit.util.IngressFileProvider
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

internal class NatconLegalFragment : BaseAgreementsFragment<NatconLegalViewModel>() {

    override fun createViewModel(): NatconLegalViewModel {
        val countryCode: String? = arguments?.getString(EXTRA_COUNTRY_CODE)
        checkParameterNotNull("countryCode", countryCode)
        val factory = NatconLegalViewModelFactory(application, countryCode!!)
        return ViewModelProviders.of(this, factory).get(NatconLegalViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_natcon

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        notifyAgreementsTitle(getString(R.string.natcon_title))

        viewModel.errorEvent.observe(this, onError())
        viewModel.showPdfEvent.observe(this, onShowPdf())
        viewModel.showWebEvent.observe(this, onShowWeb())
        viewModel.natconUpdatedEvent.observe(this, onNatconUpdated())
    }

    private fun onError() = simpleTextObserver()

    private fun onShowPdf() = LiveEventObserver<String> {
        context?.let { ctx ->
            val pdfIntent = IngressFileProvider.getPdfIntent(ctx, it)
            pdfIntent?.let { startActivity(it) } ?: showNoPdfReaderError()
        }
    }

    private fun onShowWeb() = LiveEventObserver<String> {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(it)
        startActivity(intent)
    }

    private fun showNoPdfReaderError() {
        if (canShowDialog()) {
            activity?.showOkayDialog(getString(R.string.agreements_error_no_pdf_reader))
        }
    }

    private fun onNatconUpdated() = LiveEventObserver<Unit> {
        notifyNatconUpdated()
    }

    companion object {

        private const val EXTRA_COUNTRY_CODE = "extra.country.code"

        fun newInstance(countryCode: String): NatconLegalFragment {
            val fragment = NatconLegalFragment()
            Bundle().apply {
                putString(EXTRA_COUNTRY_CODE, countryCode)
                fragment.arguments = this
            }
            return fragment
        }
    }
}