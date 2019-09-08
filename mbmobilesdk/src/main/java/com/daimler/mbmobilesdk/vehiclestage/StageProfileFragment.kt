package com.daimler.mbmobilesdk.vehiclestage

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.utils.extensions.simpleTextObserver
import com.daimler.mbingresskit.common.User
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

@Deprecated("Unused. Will be removed.")
internal class StageProfileFragment : StageDescriptionFragment<StageProfileViewModel>() {

    override fun createViewModel(): StageProfileViewModel {
        val factory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
        return ViewModelProviders.of(this, factory).get(StageProfileViewModel::class.java)
    }

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        viewModel.userLoadedEvent.observe(this, onUserLoaded())
        viewModel.errorEvent.observe(this, onErrorEvent())
    }

    override fun onAction() {
        viewModel.loadUser()
    }

    override fun onCancel() {
        // --
    }

    private fun onUserLoaded() = LiveEventObserver<User> {
        // --
    }

    private fun onErrorEvent() = simpleTextObserver()

    companion object {

        fun newInstance() = StageProfileFragment()
    }
}