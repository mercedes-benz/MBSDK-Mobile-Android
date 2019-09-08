package com.daimler.mbmobilesdk.settings

import android.annotation.SuppressLint
import android.app.admin.DevicePolicyManager
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.biometric.BiometricPreconditionError
import com.daimler.mbmobilesdk.biometric.SimpleBiometricCallback
import com.daimler.mbmobilesdk.biometric.auth.BiometricDialogConfig
import com.daimler.mbmobilesdk.biometric.biometricsPreconditionsError
import com.daimler.mbmobilesdk.pin.UserPinActivity
import com.daimler.mbmobilesdk.push.getPushSettingsIntent
import com.daimler.mbmobilesdk.units.UnitsSettingsActivity
import com.daimler.mbmobilesdk.utils.UserValuePolicy
import com.daimler.mbmobilesdk.utils.extensions.canShowDialog
import com.daimler.mbmobilesdk.utils.extensions.isResolvable
import com.daimler.mbmobilesdk.utils.extensions.simpleToastObserver
import com.daimler.mbmobilesdk.utils.extensions.withPositiveAction
import com.daimler.mbmobilesdk.utils.ifNotNull
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.components.dialogfragments.MBPinDialogFragment
import com.daimler.mbuikit.components.fragments.MBBaseMenuFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

internal class SettingsFragment : MBBaseMenuFragment<SettingsViewModel>(),
    MBPinDialogFragment.PinDialogListener, MBDialogFragment.DialogListener {

    override fun createViewModel(): SettingsViewModel {
        val biometricsSupported = arguments?.getBoolean(ARG_BIOMETRICS_SUPPORTED) == true
        val factory = SettingsViewModelFactory(activity!!.application, biometricsSupported)
        return ViewModelProviders.of(this, factory).get(SettingsViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_settings

    override fun getModelId(): Int = BR.model

    override fun getToolbarTitleRes(): Int = R.string.menu_settings

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        viewModel.onRegisterBiometricsEvent.observe(this, onRegisterBiometrics())
        viewModel.onShowUserPinEvent.observe(this, onShowUserPin())
        viewModel.onErrorEvent.observe(this, onErrorEvent())
        viewModel.unitsClickEvent.observe(this, onUnitsClickEvent())
        viewModel.onValidPinEvent.observe(this, onValidPin())
        viewModel.onInvalidPinEvent.observe(this, onInvalidPinEvent())
        viewModel.onShowPushSettingsEvent.observe(this, onShowPushSettings())
    }

    override fun onInputCancelled() {
        viewModel.onBiometricsCancelled()
    }

    override fun onPinConfirmed(pin: String) {
        viewModel.validatePinForBiometrics(pin)
    }

    @SuppressLint("InlinedApi")
    override fun onPositiveAction(id: Int) {
        if (isAdded && isVisible) {
            when (id) {
                DIALOG_ID_LOCK_SCREEN_NOT_SECURED ->
                    handleSettingsIntent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD, REQ_CODE_LOCK_SCREEN)
                DIALOG_ID_FINGERPRINT_NOT_CONFIGURED ->
                    handleSettingsIntent(Settings.ACTION_FINGERPRINT_ENROLL, REQ_CODE_FINGERPRINT)
                DIALOG_ID_INVALID_PIN ->
                    startRegisteringBiometrics()
                else -> return
            }
        }
    }

    override fun onNegativeAction(id: Int) {
        viewModel.onBiometricsCancelled()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_CODE_FINGERPRINT -> checkAndShowBiometricsErrorOrElse { showPinDialog() }
            REQ_CODE_LOCK_SCREEN -> checkAndShowBiometricsErrorOrElse { showPinDialog() }
            REQ_CODE_USER_PIN -> viewModel.onUserPinSet()
            REQ_CODE_PUSH_SETTINGS -> Unit
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleSettingsIntent(action: String?, reqCode: Int) {
        ifNotNull(activity, action) { activity, intentAction ->
            val intent = Intent(intentAction)
            if (intent.isResolvable(activity)) {
                startActivityForResult(intent, reqCode)
            } else {
                intent.action = Settings.ACTION_SETTINGS
                if (intent.isResolvable(activity)) startActivityForResult(intent, reqCode)
            }
        }
    }

    private fun onRegisterBiometrics() =
        LiveEventObserver<Unit> { startRegisteringBiometrics() }

    private fun startRegisteringBiometrics() {
        checkAndShowBiometricsErrorOrElse { showPinDialog() }
    }

    private fun checkAndShowBiometricsErrorOrElse(onNoError: (() -> Unit)?) {
        val activity = activity
        if (isAdded && isVisible && activity != null) {
            val error = biometricsPreconditionsError(activity)
            error?.let { e -> showBiometricsError(e) } ?: onNoError?.invoke()
        }
    }

    private fun showPinDialog() {
        activity?.let {
            MBPinDialogFragment.Builder().apply {
                withTitle(getString(R.string.biometrics_confirm_pin_title))
                withMessage(String.format(getString(R.string.pin_popup_description), UserValuePolicy.USER_PIN_DIGITS))
                withPositiveButtonText(getString(R.string.general_continue))
                withNegativeButtonText(getString(R.string.general_cancel))
                withListener(this@SettingsFragment)
            }.build().show(it.supportFragmentManager, null)
        }
    }

    private fun showBiometricsError(error: BiometricPreconditionError) {
        val (id: Int, msg: String) = when (error) {
            BiometricPreconditionError.LOCK_SCREEN_NOT_SECURED ->
                DIALOG_ID_LOCK_SCREEN_NOT_SECURED to getString(R.string.biometrics_error_lockscreen_not_secured)
            BiometricPreconditionError.NO_FINGERPRINT_CONFIGURED ->
                DIALOG_ID_FINGERPRINT_NOT_CONFIGURED to getString(R.string.biometrics_error_fingerprint_not_configured)
            else ->
                DIALOG_ID_BIOMETRICS_GENERAL_ERROR to getString(R.string.biometrics_error_general)
        }
        activity?.let {
            MBDialogFragment.Builder(id).apply {
                withTitle(getString(R.string.general_hint))
                withMessage(msg)
                withPositiveButtonText(getString(R.string.general_ok))
                withNegativeButtonText(getString(R.string.general_cancel))
                withListener(this@SettingsFragment)
            }.build().show(it.supportFragmentManager, null)
        }
    }

    private fun onShowUserPin() = LiveEventObserver<Boolean> {
        if (isAdded && isVisible) {
            context?.let { context ->
                startActivityForResult(UserPinActivity.getStartIntent(context, it),
                    REQ_CODE_USER_PIN)
            }
        }
    }

    private fun onErrorEvent() = simpleToastObserver()

    private fun onUnitsClickEvent() = LiveEventObserver<Unit> {
        startActivity(UnitsSettingsActivity.getStartIntent(context!!))
    }

    private fun onValidPin() =
        LiveEventObserver<SettingsViewModel.RegisterBiometricsWithPinEvent> { event ->
            activity?.let {
                event.biometrics.startBiometricAuthentication(
                    it,
                    BiometricDialogConfig.Builder(getString(R.string.biometrics_id_prompt_title),
                        getString(R.string.general_cancel)).build(),
                    object : SimpleBiometricCallback() {
                        override fun onAuthenticationSucceeded() {
                            viewModel.onBiometricsRegistered(event.pin)
                        }

                        override fun onAuthenticationError(errString: String) {
                            viewModel.onBiometricsCancelled()
                        }
                    }
                )
            }
        }

    private fun onInvalidPinEvent() = LiveEventObserver<String> {
        if (canShowDialog()) {
            activity?.let { activity ->
                MBDialogFragment.Builder(DIALOG_ID_INVALID_PIN).apply {
                    withMessage(it)
                    withPositiveButtonText(getString(R.string.general_ok))
                    withNegativeButtonText(getString(R.string.general_cancel))
                    withListener(this@SettingsFragment)
                }.build().show(activity.supportFragmentManager, null)
            }
        }
    }

    private fun onShowPushSettings() = LiveEventObserver<Unit> {
        activity?.let { activity ->
            if (canShowDialog()) {
                MBDialogFragment.Builder(0).apply {
                    withMessage(getString(R.string.settings_message_push_permission))
                    withPositiveButtonText(getString(R.string.general_ok))
                    withNegativeButtonText(getString(R.string.general_cancel))
                    withPositiveAction {
                        getPushSettingsIntent(activity)?.let {
                            startActivityForResult(it, REQ_CODE_PUSH_SETTINGS)
                        }
                    }
                }.build().show(activity.supportFragmentManager, null)
            }
        }
    }

    companion object {

        private const val ARG_BIOMETRICS_SUPPORTED = "arg.settings.biometrics.supported"

        private const val DIALOG_ID_LOCK_SCREEN_NOT_SECURED = 1
        private const val DIALOG_ID_FINGERPRINT_NOT_CONFIGURED = 2
        private const val DIALOG_ID_BIOMETRICS_GENERAL_ERROR = 3
        private const val DIALOG_ID_INVALID_PIN = 4

        private const val REQ_CODE_LOCK_SCREEN = 121
        private const val REQ_CODE_FINGERPRINT = 122
        private const val REQ_CODE_USER_PIN = 123
        private const val REQ_CODE_PUSH_SETTINGS = 124

        fun newInstance(biometricsSupported: Boolean): SettingsFragment {
            val fragment = SettingsFragment()
            val args = Bundle()
            args.putBoolean(ARG_BIOMETRICS_SUPPORTED, biometricsSupported)
            fragment.arguments = args
            return fragment
        }
    }
}