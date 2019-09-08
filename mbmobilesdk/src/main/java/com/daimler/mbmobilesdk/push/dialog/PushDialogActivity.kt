package com.daimler.mbmobilesdk.push.dialog

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.push.PushData
import com.daimler.mbmobilesdk.utils.checkParameterNotNull
import com.daimler.mbmobilesdk.utils.extensions.isResolvable
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

internal class PushDialogActivity : MBBaseViewModelActivity<PushDialogViewModel>() {

    override fun createViewModel(): PushDialogViewModel {
        val pushData: PushData? = intent.getParcelableExtra(ARG_PUSH_DATA)
        checkParameterNotNull("pushData", pushData)

        val factory = PushDialogViewModelFactory(pushData!!)
        return ViewModelProviders.of(this, factory).get(PushDialogViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.activity_push_dialog

    override fun getModelId(): Int = BR.model

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
    }

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        viewModel.apply {
            confirmEvent.observe(this@PushDialogActivity, onConfirmEvent())
            cancelEvent.observe(this@PushDialogActivity, onCancelEvent())
            showUrlEvent.observe(this@PushDialogActivity, onShowUrlEvent())
            redirectToDeepLinkEvent.observe(this@PushDialogActivity, onRedirectToDeepLinkEvent())
        }
    }

    private fun onConfirmEvent() = LiveEventObserver<Unit> {
        finish()
    }

    private fun onCancelEvent() = LiveEventObserver<Unit> {
        finish()
    }

    private fun onShowUrlEvent() = LiveEventObserver<String> {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(it)
        }
        if (intent.isResolvable(this)) {
            startActivity(intent)
        }
        finish()
    }

    private fun onRedirectToDeepLinkEvent() = LiveEventObserver<String> {
        MBLoggerKit.w("Deep link handling is not supported yet!")
        finish()
    }

    companion object {
        private const val ARG_PUSH_DATA = "arg.push.dialog.data"

        fun getStartIntent(context: Context, pushData: PushData): Intent {
            return Intent(context, PushDialogActivity::class.java).apply {
                putExtra(ARG_PUSH_DATA, pushData)
            }
        }
    }
}