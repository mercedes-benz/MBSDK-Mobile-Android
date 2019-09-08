package com.daimler.mbmobilesdk.send2car

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

class SendLocationToCarActivity : MBBaseViewModelActivity<SendLocationToCarViewModel>() {

    override fun createViewModel(): SendLocationToCarViewModel {
        return ViewModelProviders.of(this).get(SendLocationToCarViewModel::class.java)
    }

    override fun getLayoutRes() = R.layout.activity_send_location_to_car

    override fun getModelId() = BR.model

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        super.onCreate(savedInstanceState)
        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.let {
            viewModel.processNewIntent(it)
        }
    }

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        viewModel.screenFinishedEvent.observe(this, LiveEventObserver {
            finish()
        })
    }
}