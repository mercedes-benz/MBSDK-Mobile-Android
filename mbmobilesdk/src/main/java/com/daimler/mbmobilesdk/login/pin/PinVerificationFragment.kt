package com.daimler.mbmobilesdk.login.pin

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.databinding.FragmentPinVerificationBinding
import com.daimler.mbmobilesdk.login.BaseLoginFragment
import com.daimler.mbmobilesdk.registration.LoginUser
import com.daimler.mbmobilesdk.utils.checkParameterNotNull
import com.daimler.mbmobilesdk.utils.extensions.application
import com.daimler.mbmobilesdk.utils.extensions.canShowDialog
import com.daimler.mbmobilesdk.utils.extensions.showOkayDialog
import com.daimler.mbmobilesdk.utils.extensions.simpleTextObserver
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.utils.extensions.hideKeyboard

internal class PinVerificationFragment : BaseLoginFragment<PinVerificationViewModel>() {

    override fun createViewModel(): PinVerificationViewModel {
        var user: LoginUser? = null
        var isRegistration: Boolean? = null
        arguments?.let {
            isRegistration = it.getBoolean(ARG_IS_REGISTRATION)
            user = it.getParcelable(ARG_USER)
        }
        checkParameterNotNull("mode", isRegistration)
        checkParameterNotNull("user", user)
        return ViewModelProviders.of(this, PinVerificationViewModelFactory(
            application, isRegistration!!, user!!)
        ).get(PinVerificationViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_pin_verification

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        viewModel.processing.observe(this, progressChanged())
        viewModel.onPinError.observe(this, onPinError())
        viewModel.onPinVerified.observe(this, onPinVerified())
        viewModel.onPinRequested.observe(this, onPinRequested())
        viewModel.onPinRequestError.observe(this, onPinRequestError())
        viewModel.onShowLegalEvent.observe(this, onShowLegal())

        notifyEndpointVisibilityChanged(false)
        notifyToolbarTitle(getString(R.string.verification_title))
    }

    private fun onPinError() = LiveEventObserver<String> {
        notifyPinError(it)
    }

    private fun progressChanged() = Observer<Boolean> {
        val progress = it == true
        if (progress) activity?.hideKeyboard()
    }

    private fun onPinRequested() = LiveEventObserver<Unit> {
        (binding as? FragmentPinVerificationBinding)?.editPin?.clear()
        if (canShowDialog()) {
            activity?.showOkayDialog(getString(R.string.verification_send_again_msg))
        }
    }

    private fun onShowLegal() = LiveEventObserver<Unit> {
        notifyShowLegal()
    }

    private fun onPinRequestError() = simpleTextObserver()

    private fun onPinVerified() =
        LiveEventObserver<PinVerificationViewModel.PinVerificationEvent> {
            notifyPinVerified(it.user, it.isRegistration)
        }

    companion object {

        private const val ARG_USER = "arg.pin.user"
        private const val ARG_IS_REGISTRATION = "arg.pin.is.registration"

        fun getInstance(user: LoginUser, isRegistration: Boolean): PinVerificationFragment {
            val pinFragment = PinVerificationFragment()
            val arguments = Bundle()
            arguments.apply {
                putParcelable(ARG_USER, user)
                putBoolean(ARG_IS_REGISTRATION, isRegistration)
            }
            pinFragment.arguments = arguments
            return pinFragment
        }
    }
}