package com.daimler.mbmobilesdk.notificationcenter.presentation.messageoverview

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.databinding.FragmentReachmeInboxBinding
import com.daimler.mbmobilesdk.notificationcenter.presentation.ReachMeFragment
import com.daimler.mbmobilesdk.utils.extensions.createAndroidViewModel
import com.daimler.mbuikit.utils.extensions.toast

class MessageOverviewFragment : ReachMeFragment<MessageOverviewViewModel>() {

    companion object {
        fun instance(): MessageOverviewFragment {
            return MessageOverviewFragment()
        }
    }

    override fun createViewModel(): MessageOverviewViewModel {
        return createAndroidViewModel(MessageOverviewViewModel::class.java)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_reachme_inbox
    }

    override fun getModelId(): Int {
        return BR.viewModel
    }

    override fun onBindingCreated(binding: ViewDataBinding) {
        val fragmentReachmeInboxBinding = binding as FragmentReachmeInboxBinding
        setupPullToRefresh(binding.srMessages)
        observeItems(fragmentReachmeInboxBinding)
    }

    private fun setupPullToRefresh(swipeRefreshLayout: SwipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeResources(R.color.mb_accent_primary)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshMessages()
        }
    }

    private fun observeItems(binding: FragmentReachmeInboxBinding) {
        viewModel.inboxData().observe(this, Observer {
            when (it) {
                is MessageOverviewViewModel.InboxResult.Success -> updateMessageItems(binding, it.messages)
                is MessageOverviewViewModel.InboxResult.Error -> showError(it)
            }
        })
        viewModel.messageSelected().observe(this, Observer {
            it.getContentIfNotHandled()?.let { message ->
                navigation().onShowMessage(message)
            }
        })
        viewModel.refreshing().observe(this, Observer {
            if (it.not()) {
                binding.srMessages.isRefreshing = false
            }
        })
    }

    private fun updateMessageItems(binding: FragmentReachmeInboxBinding, messages: List<InboxMessageItem>) {
        val messageRecyclerView = binding.rvMessages
        if (messageRecyclerView.adapter == null) {
            messageRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@MessageOverviewFragment.context)
                this.adapter = InboxRecyclerAdapter()
            }
        }
        messageRecyclerView.apply {
            (adapter as InboxRecyclerAdapter).updateItems(messages)
        }
    }

    private fun showError(error: MessageOverviewViewModel.InboxResult.Error) {
        toast(getString(R.string.general_error_msg))
    }
}