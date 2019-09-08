package com.daimler.mbmobilesdk.pin

import android.content.Context
import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.biometric.SimpleBiometricCallback
import com.daimler.mbmobilesdk.biometric.auth.BiometricDialogConfig
import com.daimler.mbmobilesdk.biometric.biometricsPreconditionsFulfilled
import com.daimler.mbmobilesdk.databinding.ActivityUserPinBinding
import com.daimler.mbmobilesdk.utils.extensions.showOkayDialog
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.utils.extensions.hideKeyboard
import kotlinx.android.synthetic.main.activity_user_pin.*

class UserPinActivity : MBBaseViewModelActivity<UserPinViewModel>(), MBDialogFragment.DialogListener {

    override fun getLayoutRes(): Int = R.layout.activity_user_pin

    override fun getModelId(): Int = BR.model

    override fun createViewModel(): UserPinViewModel {
        if (!intent.hasExtra(ARG_IS_INITIAL_PIN)) {
            throw IllegalArgumentException("No information about the current pin state given.")
        }
        val isInitialPin = intent.getBooleanExtra(ARG_IS_INITIAL_PIN, true)
        val factory = UserPinViewModelFactory(application, isInitialPin)
        return ViewModelProviders.of(this, factory).get(UserPinViewModel::class.java)
    }

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        val activityUserPinBinding = binding as ActivityUserPinBinding

        setSupportActionBar(activityUserPinBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel.pinSetEvent.observe(this, onPinSet())
        viewModel.pinChangedEvent.observe(this, onPinChanged())
        viewModel.errorEvent.observe(this, onError())
        viewModel.loadingEvent.observe(this, onHideKeyboard())

        viewModel.oldPin.observe(this, onOldPinChanged(activityUserPinBinding))

        activityUserPinBinding.editPinNew.deletePressedWhileEmptyListener = {
            activityUserPinBinding.editPinOld.deleteAndFocusLast()
        }
    }

    private fun onOldPinChanged(binding: ActivityUserPinBinding) = object : Observer<String> {
        private var oldLength = 0

        override fun onChanged(s: String?) {
            s?.let {
                if (oldLength != it.length && it.length == binding.editPinOld.inputLength) {
                    binding.editPinNew.requestFocus()
                }
                oldLength = it.length
            }
        }
    }

    private fun onPinSet() = LiveEventObserver<String> {
        if (biometricsPreconditionsFulfilled(this) &&
            !MBMobileSDK.userSettings().promptedForBiometrics.get()) {
            promptForBiometrics()
        } else {
            showOkayDialog(ID_DIALOG_SUCCESS, getString(R.string.pin_popup_save_success), this)
        }
    }

    private fun onPinChanged() = LiveEventObserver<String> {
        if (biometricsPreconditionsFulfilled(this) &&
            !MBMobileSDK.userSettings().promptedForBiometrics.get()) {
            promptForBiometrics()
        } else {
            showOkayDialog(ID_DIALOG_SUCCESS, getString(R.string.pin_popup_save_success), this)
        }
    }

    private fun onError() = LiveEventObserver<String> {
        showOkayDialog(ID_DIALOG_ERROR, it, this)
    }

    private fun onHideKeyboard() = LiveEventObserver<Unit> {
        hideKeyboard()
    }

    private fun promptForBiometrics() {
        MBDialogFragment.Builder(ID_DIALOG_BIOMETRICS_PROMPT).apply {
            withTitle(getString(R.string.biometrics_id_prompt_title))
            withMessage(getString(R.string.biometrics_activation_prompt_message))
            withPositiveButtonText(getString(R.string.general_ok))
            withNegativeButtonText(getString(R.string.general_cancel))
            withListener(this@UserPinActivity)
        }.build().show(supportFragmentManager, null)
    }

    override fun onPositiveAction(id: Int) {
        when (id) {
            ID_DIALOG_SUCCESS -> finishWithResultOk()
            ID_DIALOG_BIOMETRICS_PROMPT -> registerBiometrics()
            ID_DIALOG_ERROR -> {
                edit_pin_old.clear()
                edit_pin_new.clear()
                edit_pin_old.requestFocus()
            }
        }
    }

    override fun onNegativeAction(id: Int) {
        if (id == ID_DIALOG_BIOMETRICS_PROMPT) {
            finishWithResultOk()
        }
    }

    private fun registerBiometrics() {
        val result = MBMobileSDK.biometrics()?.startBiometricAuthentication(
            this,
            BiometricDialogConfig.Builder(getString(R.string.biometrics_id_prompt_title),
                getString(R.string.general_cancel)).build(),
            object : SimpleBiometricCallback() {
                override fun onAuthenticationSucceeded() {
                    super.onAuthenticationSucceeded()
                    viewModel.onRegisterBiometrics()
                    finishWithResultOk()
                }

                override fun onAuthenticationError(errString: String) {
                    super.onAuthenticationError(errString)
                    finishWithResultOk()
                }
            }
        )
        if (result != true) finishWithResultOk()
    }

    private fun finishWithResultOk() {
        setResult(RESULT_OK)
        finish()
    }

    companion object {

        private const val ARG_IS_INITIAL_PIN = "arg.pin.is.initial"

        private const val ID_DIALOG_SUCCESS = 1
        private const val ID_DIALOG_ERROR = 2
        private const val ID_DIALOG_BIOMETRICS_PROMPT = 3

        fun getStartIntent(context: Context, isInitialPin: Boolean): Intent {
            val intent = Intent(context, UserPinActivity::class.java)
            intent.putExtra(ARG_IS_INITIAL_PIN, isInitialPin)
            return intent
        }
    }
}