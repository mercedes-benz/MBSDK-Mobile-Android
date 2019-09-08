package com.daimler.mbmobilesdk.vehiclestage

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

internal class StageServicesFragment : StageDescriptionFragment<StageServicesViewModel>() {

    override fun createViewModel(): StageServicesViewModel {
        val factory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
        return ViewModelProviders.of(this, factory).get(StageServicesViewModel::class.java)
    }

    override fun onAction() {
        callback?.onActivateServices()
    }

    override fun onCancel() {
        callback?.onServiceActivationCancelled()
    }

    companion object {

        fun newInstance() = StageServicesFragment()
    }
}