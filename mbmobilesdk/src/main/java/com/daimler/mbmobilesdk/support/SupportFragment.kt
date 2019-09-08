package com.daimler.mbmobilesdk.support

import android.content.Intent
import android.net.Uri
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.extensions.isResolvable
import com.daimler.mbuikit.components.fragments.MBBaseMenuFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

class SupportFragment : MBBaseMenuFragment<SupportViewModel>() {

    override fun createViewModel(): SupportViewModel {
        val factory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
        return ViewModelProviders.of(this, factory).get(SupportViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_support

    override fun getModelId(): Int = BR.model

    override fun getToolbarTitleRes(): Int = R.string.support_title

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        viewModel.phoneEvent.observe(this, onPhone())
        viewModel.mailEvent.observe(this, onMail())
    }

    private fun onPhone() = LiveEventObserver<String> { phoneNumber ->
        createPhoneIntent(phoneNumber)?.let { startActivity(it) }
    }

    private fun onMail() =
        LiveEventObserver<SupportViewModel.SupportRequest> { request ->
            createMailIntent(request.subject, request.message, request.mail)?.let {
                startActivity(it)
            }
        }

    private fun createPhoneIntent(phoneNumber: String): Intent? {
        return context?.let {
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null))
            if (intent.isResolvable(it)) intent else null
        }
    }

    private fun createMailIntent(subject: String, message: String, mail: String): Intent? {
        return context?.let {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse(
                    "mailto:${Uri.encode(mail)}?subject=${Uri.encode(subject)}&body=${Uri.encode(message)}"
                )
            }
            if (intent.isResolvable(it)) intent else null
        }
    }

    companion object {
        fun newInstance() = SupportFragment()
    }
}