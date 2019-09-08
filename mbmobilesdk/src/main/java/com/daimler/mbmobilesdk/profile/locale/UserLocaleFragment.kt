package com.daimler.mbmobilesdk.profile.locale

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.UserLocale
import com.daimler.mbmobilesdk.utils.checkParameterNotNull
import com.daimler.mbmobilesdk.utils.extensions.application
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

internal class UserLocaleFragment : BaseLocaleChangeFragment<UserLocaleViewModel>() {

    override fun createViewModel(): UserLocaleViewModel {
        val userLocale: UserLocale? = arguments?.getParcelable(EXTRA_USER_LOCALE)
        checkParameterNotNull("userLocale", userLocale)
        val factory = UserLocaleViewModelFactory(application, userLocale!!)
        return ViewModelProviders.of(this, factory).get(UserLocaleViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_user_locale

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        notifyUpdateToolbarTitle(getString(R.string.location_title))

        viewModel.apply {
            localeSelectedEvent.observe(this@UserLocaleFragment, onLocaleSelectedEvent())
            cancelEvent.observe(this@UserLocaleFragment, onCancelEvent())
        }
    }

    private fun onLocaleSelectedEvent() =
        LiveEventObserver<UserLocaleViewModel.UserLocaleSelectionEvent> {
            if (it.shouldShowNatcon) {
                notifyShowNatcon(it.userLocale)
            } else {
                notifyLocaleSelected(it.userLocale)
            }
        }

    private fun onCancelEvent() = LiveEventObserver<Unit> {
        notifyCancel()
    }

    companion object {

        private const val EXTRA_USER_LOCALE = "extra.user.locale"

        fun newInstance(userLocale: UserLocale): UserLocaleFragment {
            val fragment = UserLocaleFragment()
            val bundle = Bundle().apply {
                putParcelable(EXTRA_USER_LOCALE, userLocale)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}