package com.daimler.mbmobilesdk.vehiclestage

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbuikit.components.fragments.MBBaseViewModelFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

internal abstract class StageDescriptionFragment<T : StageDescriptionViewModel> : MBBaseViewModelFragment<T>() {

    protected var callback: VehicleStageCallback? = null
        private set

    override fun getLayoutRes(): Int = R.layout.fragment_stage_description

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        viewModel.actionEvent.observe(this, onActionEvent())
        viewModel.cancelEvent.observe(this, onCancelEvent())
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        MBLoggerKit.d("onAttach")
        callback = context as? VehicleStageCallback
    }

    override fun onDetach() {
        super.onDetach()
        MBLoggerKit.d("onDetach")
        callback = null
    }

    protected abstract fun onAction()

    protected abstract fun onCancel()

    private fun onActionEvent() = LiveEventObserver<Unit> {
        onAction()
    }

    private fun onCancelEvent() = LiveEventObserver<Unit> {
        onCancel()
    }
}