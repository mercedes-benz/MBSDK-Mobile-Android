package com.daimler.mbmobilesdk.notificationcenter.presentation

import android.content.Context
import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.notificationcenter.presentation.messagedetail.MessageDetailFragment
import com.daimler.mbmobilesdk.notificationcenter.presentation.messageoverview.MessageOverviewFragment
import com.daimler.mbmobilesdk.notificationcenter.model.Message
import com.daimler.mbuikit.components.activities.MBBaseToolbarActivity
import com.daimler.mbuikit.utils.extensions.replaceFragment

class InboxActivity : MBBaseToolbarActivity<ReachMeViewModel>(), Navigation {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, InboxActivity::class.java))
        }
    }

    override val buttonMode: Int
        get() = BUTTON_BACK

    override val toolbarTitle: String by lazy { inboxTitle() }

    override fun onBackPressed() {
        if (viewModel.screen().value is ReachMeScreen.MessageDetails) {
            showMessagesScreen()
        } else {
            onClose()
        }
    }

    override fun createViewModel(): ReachMeViewModel {
        return ViewModelProviders.of(this).get(ReachMeViewModel::class.java)
    }

    override fun getContentLayoutRes(): Int {
        return R.layout.activity_reachme
    }

    override fun getContentModelId(): Int {
        return BR.viewModel
    }

    override fun onContentBindingCreated(binding: ViewDataBinding) {
        bindMessageScreen()
    }

    override fun onClose() {
        finish()
    }

    override fun onShowMessage(message: Message) {
        viewModel.apply {
            adjustTitle(inboxTitle()) // Maybe it should show some message info as title
            showMessage(message)
        }
    }

    private fun showMessagesScreen() {
        viewModel.apply {
            adjustTitle(inboxTitle())
            showMessages()
        }
    }

    private fun bindMessageScreen() {
        viewModel.screen().observe(this, Observer {
            when (it) {
                is ReachMeScreen.Messages -> showMessagesFragment(it)
                is ReachMeScreen.MessageDetails -> showMessageFragment(it)
            }
        })
    }

    private fun inboxTitle(): String = getString(R.string.notification_center_title)

    private fun showMessageFragment(messageScreen: ReachMeScreen.MessageDetails) {
        replaceFragment(R.id.reachme_container, MessageDetailFragment.instance(messageScreen.message.key), null, true)
    }

    private fun showMessagesFragment(messagesScreen: ReachMeScreen.Messages) {
        if (messagesScreen.wasBackNavigation) {
            super.onBackPressed()
        } else {
            replaceFragment(R.id.reachme_container, MessageOverviewFragment.instance(), null, true)
        }
    }
}