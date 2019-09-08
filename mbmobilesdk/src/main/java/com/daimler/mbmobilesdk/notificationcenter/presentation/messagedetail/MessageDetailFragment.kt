package com.daimler.mbmobilesdk.notificationcenter.presentation.messagedetail

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.databinding.FragmentReachmeMessagedetailsBinding
import com.daimler.mbmobilesdk.notificationcenter.model.MessageDetail
import com.daimler.mbmobilesdk.notificationcenter.presentation.ReachMeFragment
import com.daimler.mbmobilesdk.utils.extensions.createAndroidViewModel
import com.daimler.mbuikit.utils.extensions.toast

class MessageDetailFragment : ReachMeFragment<MessageDetailsViewModel>() {

    companion object {
        const val MIME_TYPE_HTML = "text/html"
        const val ENCODING_WEB_CONTENT = "utf-8"

        fun instance(messageKey: String): MessageDetailFragment {
            val arguments = Bundle().apply {
                putString(EXTRA_MESSAGE_KEY, messageKey)
            }
            return MessageDetailFragment().apply {
                setArguments(arguments)
            }
        }

        private const val EXTRA_MESSAGE_KEY = "extra.message.key"
    }

    override fun createViewModel(): MessageDetailsViewModel {
        return createAndroidViewModel(MessageDetailsViewModel::class.java)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_reachme_messagedetails
    }

    override fun getModelId(): Int {
        return BR.item
    }

    override fun onBindingCreated(binding: ViewDataBinding) {
        arguments?.getString(EXTRA_MESSAGE_KEY)?.let {
            viewModel.showDetails(it)
        }
        observeMessageDetails(binding as FragmentReachmeMessagedetailsBinding)
    }

    private fun observeMessageDetails(binding: FragmentReachmeMessagedetailsBinding) {
        viewModel.messageResult().observe(this, Observer { result ->
            binding.wvContent.apply {
                val client = WebViewClient()
                webViewClient = client
            }
            handleResult(binding, result)
        })
    }

    private fun handleResult(binding: FragmentReachmeMessagedetailsBinding, result: MessageDetailsViewModel.DetailsResult) {
        when (result) {
            is MessageDetailsViewModel.DetailsResult.Success -> showDetailsContent(binding, result.details)
            is MessageDetailsViewModel.DetailsResult.Error -> showError(result)
        }
    }

    private fun showDetailsContent(binding: FragmentReachmeMessagedetailsBinding, details: MessageDetail) {
        binding.wvContent.loadData(details.htmlContent, MIME_TYPE_HTML, ENCODING_WEB_CONTENT)
    }

    private fun showError(error: MessageDetailsViewModel.DetailsResult.Error) {
        toast(getString(R.string.general_error_msg))
    }
}