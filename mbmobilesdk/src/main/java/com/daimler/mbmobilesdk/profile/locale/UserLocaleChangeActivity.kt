package com.daimler.mbmobilesdk.profile.locale

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.UserLocale
import com.daimler.mbmobilesdk.databinding.ActivityUserLocaleChangeBinding
import com.daimler.mbmobilesdk.profile.completion.ProfileCompletionCallback
import com.daimler.mbmobilesdk.profile.completion.ProfileCompletionFragment
import com.daimler.mbmobilesdk.tou.MBAgreementsCallback
import com.daimler.mbmobilesdk.tou.natcon.NatconLegalFragment
import com.daimler.mbmobilesdk.utils.checkParameterNotNull
import com.daimler.mbingresskit.common.User
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.utils.extensions.replaceFragment

internal class UserLocaleChangeActivity :
    MBBaseViewModelActivity<UserLocaleChangeViewModel>(),
    LocaleChangeCallback,
    MBAgreementsCallback,
    ProfileCompletionCallback {

    override fun createViewModel(): UserLocaleChangeViewModel {
        val user: User? = intent.getParcelableExtra(ARG_USER)
        val userLocale: UserLocale? = intent.getParcelableExtra(ARG_USER_LOCALE)
        checkParameterNotNull("user", user)
        checkParameterNotNull("userLocale", userLocale)
        val factory = UserLocaleChangeViewModelFactory(user!!, userLocale!!)
        return ViewModelProviders.of(this, factory).get(UserLocaleChangeViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.activity_user_locale_change

    override fun getModelId(): Int = BR.model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            replaceFragment(R.id.content_container, UserLocaleFragment.newInstance(viewModel.currentLocale))
        }
    }

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        binding as ActivityUserLocaleChangeBinding

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel.apply {
            backEvent.observe(this@UserLocaleChangeActivity, onBackEvent())
        }
    }

    /* UserLocaleCallback */

    override fun onLocaleSelected(userLocale: UserLocale) {
        viewModel.localeSelected(userLocale)
        replaceFragment(R.id.content_container, ProfileCompletionFragment.newInstance(viewModel.currentUser),
            addToBackStack = true)
    }

    override fun onShowNatcon(userLocale: UserLocale) {
        viewModel.localeSelected(userLocale)
        replaceFragment(R.id.content_container, NatconLegalFragment.newInstance(userLocale.countryCode),
            addToBackStack = true)
    }

    override fun onCancel() {
        cancelLocaleChange()
    }

    override fun onUpdateTitle(title: String) {
        viewModel.updateToolbarTitle(title)
    }

    /* MBAgreementsCallback */

    override fun onSoeUpdated() = Unit

    override fun onSoeCancelled() = Unit

    override fun onNatconUpdated() {
        viewModel.natconShown()
        replaceFragment(R.id.content_container, ProfileCompletionFragment.newInstance(viewModel.currentUser),
            addToBackStack = true)
    }

    override fun onNatconCancelled() {
        finishWithResult(viewModel.currentUser, false)
    }

    override fun onUpdateAgreementsTitle(title: String) {
        viewModel.updateToolbarTitle(title)
    }

    /* ProfileCompletionCallback */

    override fun onUserUpdated(user: User) {
        finishWithResult(user, viewModel.natconShown)
    }

    override fun onUpdateProfileTitle(title: String) {
        viewModel.updateToolbarTitle(title)
    }

    /* --- */

    private fun onBackEvent() = LiveEventObserver<Unit> {
        if (supportFragmentManager.backStackEntryCount != 0) {
            super.onBackPressed()
        } else {
            cancelLocaleChange()
        }
    }

    private fun finishWithResult(user: User, natconUpdated: Boolean) {
        val intent = Intent().apply {
            putExtra(ARG_UPDATED_USER, user)
            putExtra(ARG_NATCON_UPDATED, natconUpdated)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun cancelLocaleChange() {
        setResult(RESULT_CANCELED)
        finish()
    }

    companion object {

        private const val ARG_USER = "arg.user"
        private const val ARG_USER_LOCALE = "arg.user.locale"

        const val ARG_UPDATED_USER = "arg.user.locale.change.updated"
        const val ARG_NATCON_UPDATED = "arg.user.locale.natcon.updated"

        fun getStartIntent(context: Context, user: User, userLocale: UserLocale): Intent {
            return Intent(context, UserLocaleChangeActivity::class.java).apply {
                putExtra(ARG_USER, user)
                putExtra(ARG_USER_LOCALE, userLocale)
            }
        }
    }
}