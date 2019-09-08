package com.daimler.mbmobilesdk.registration.locale

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.login.BaseLoginFragment
import com.daimler.mbmobilesdk.registration.LoginUser
import com.daimler.mbmobilesdk.utils.checkParameterNotBlank
import com.daimler.mbmobilesdk.utils.checkParameterNotNull
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

internal class UserLocaleLoginFragment : BaseLoginFragment<UserLocaleLoginViewModel>() {

    override fun createViewModel(): UserLocaleLoginViewModel {
        val user = arguments?.getString(ARG_USER)
        val isMail = arguments?.getBoolean(ARG_IS_MAIL)
        checkParameterNotBlank("user", user)
        checkParameterNotNull("isMail", isMail)
        val factory = UserLocaleLoginViewModelFactory(activity!!.application, user!!, isMail!!)
        return ViewModelProviders.of(this, factory).get(UserLocaleLoginViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_user_locale

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        notifyEndpointVisibilityChanged(false)
        notifyToolbarTitle(getString(R.string.location_title))

        viewModel.showRegistrationEvent.observe(this, showRegistration())
        viewModel.showNatconEvent.observe(this, showNatcon())
        viewModel.legalShowEvent.observe(this, showLegal())
    }

    private fun showRegistration() = LiveEventObserver<LoginUser> {
        notifyShowRegistration(it)
    }

    private fun showNatcon() = LiveEventObserver<LoginUser> {
        notifyShowNatcon(it)
    }

    private fun showLegal() = LiveEventObserver<Unit> {
        notifyShowLegal()
    }

    companion object {

        private const val ARG_USER = "arg.registration.user"
        private const val ARG_IS_MAIL = "arg.registration.is.mail"

        fun newInstance(user: String, isMail: Boolean): UserLocaleLoginFragment {
            val fragment = UserLocaleLoginFragment()
            val bundle = Bundle().apply {
                putString(ARG_USER, user)
                putBoolean(ARG_IS_MAIL, isMail)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}