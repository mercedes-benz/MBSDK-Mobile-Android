package com.daimler.mbmobilesdk.vehiclestage

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.utils.extensions.simpleTextObserver

internal class StageAssignFragment : StageDescriptionFragment<StageAssignViewModel>() {

    override fun createViewModel(): StageAssignViewModel {
        val factory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
        return ViewModelProviders.of(this, factory).get(StageAssignViewModel::class.java)
    }

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        viewModel.errorEvent.observe(this, onErrorEvent())
    }

    override fun onAction() {
        callback?.onAssignVehicle()
    }

    override fun onCancel() {
        callback?.onAssignmentCancelled()
    }

    private fun onErrorEvent() = simpleTextObserver()

    companion object {
        fun newInstance() = StageAssignFragment()
    }
}