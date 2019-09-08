package com.daimler.mbmobilesdk.tou.natcon

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.login.BaseLoginFragment
import com.daimler.mbmobilesdk.registration.LoginUser
import com.daimler.mbmobilesdk.utils.checkParameterNotNull
import com.daimler.mbmobilesdk.utils.extensions.*
import com.daimler.mbingresskit.util.IngressFileProvider
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

internal class NatconLoginFragment : BaseLoginFragment<NatconLoginViewModel>() {

    override fun createViewModel(): NatconLoginViewModel {
        val user = arguments?.getParcelable<LoginUser>(ARG_USER)
        checkParameterNotNull("user", user)

        val factory = NatconLoginViewModelFactory(application, user!!)
        return ViewModelProviders.of(this, factory).get(NatconLoginViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_natcon

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        notifyEndpointVisibilityChanged(false)
        notifyToolbarTitle(getString(R.string.natcon_title))

        viewModel.errorEvent.observe(this, onError())
        viewModel.showPdfEvent.observe(this, onShowPdf())
        viewModel.showWebEvent.observe(this, onShowWeb())
        viewModel.natconConfirmedEvent.observe(this, onNatconConfirmed())
    }

    private fun onError() = simpleTextObserver()

    private fun onShowPdf() = LiveEventObserver<String> {
        context?.let { ctx ->
            val pdfIntent = IngressFileProvider.getPdfIntent(ctx, it)
            pdfIntent?.let { startActivity(it) } ?: showNoPdfReaderError()
        }
    }

    private fun onShowWeb() = LiveEventObserver<String> {
        context?.let { ctx ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(it)
            if (intent.isResolvable(ctx)) {
                startActivity(intent)
            }
        }
    }

    private fun showNoPdfReaderError() {
        if (canShowDialog()) {
            activity?.showOkayDialog(getString(R.string.agreements_error_no_pdf_reader))
        }
    }

    private fun onNatconConfirmed() = LiveEventObserver<LoginUser> {
        notifyShowRegistration(it)
    }

    companion object {
        private const val ARG_USER = "arg.registration.natcon.user"

        fun newInstance(user: LoginUser): NatconLoginFragment {
            val fragment = NatconLoginFragment()
            val args = Bundle().apply {
                putParcelable(ARG_USER, user)
            }
            fragment.arguments = args
            return fragment
        }
    }
}