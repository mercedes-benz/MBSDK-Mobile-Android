package com.daimler.mbmobilesdk.biometric

import android.app.Activity
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.BasePinProvider
import com.daimler.mbmobilesdk.biometric.auth.BiometricAuthenticator
import com.daimler.mbmobilesdk.biometric.auth.BiometricDialogConfig
import com.daimler.mbmobilesdk.biometric.pincache.PinCache
import com.daimler.mbmobilesdk.preferences.user.UserSettings
import com.daimler.mbmobilesdk.utils.UserValuePolicy
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbcarkit.business.PinRequest
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.components.dialogfragments.MBPinDialogFragment

@RequiresApi(Build.VERSION_CODES.M)
internal class BiometricPinProviderM(
    app: Application,
    authenticator: BiometricAuthenticator,
    pinCache: PinCache,
    userSettings: UserSettings,
    next: BasePinProvider?
) : BaseBiometricPinProvider(
    authenticator,
    userSettings,
    pinCache,
    next
) {

    private var pinRequest: PinRequest? = null

    init {
        initCallbacks(app)
    }

    override fun canProvideBiometrics(): Boolean {
        if (!activitySet) return false
        return biometricsPreconditionsError(activity()) == null
    }

    override fun doBiometricAuthentication(pinRequest: PinRequest, pin: String) {
        this.pinRequest = pinRequest
        authenticate(pin, false)
    }

    override fun showProvidePinPrompt(pinRequest: PinRequest) {
        this.pinRequest = pinRequest
        showPinDialog(activity() as AppCompatActivity)
    }

    override fun promptForBiometrics(pin: String) {
        if (!activitySet) return
        val activity = activity() as AppCompatActivity
        MBDialogFragment.Builder(DIALOG_ID_BIOMETRICS_ENABLING).apply {
            withTitle(activity.getString(R.string.biometrics_id_prompt_title))
            withMessage(activity.getString(R.string.biometrics_activation_prompt_message))
            withPositiveButtonText(activity.getString(R.string.general_ok))
            withNegativeButtonText(activity.getString(R.string.general_cancel))
            withListener(BiometricsPromptListener(pin))
        }.build().show(activity.supportFragmentManager, null)
    }

    override fun promptForInvalidPin() {
        if (!activitySet) return
        val activity = activity() as AppCompatActivity
        MBDialogFragment.Builder(DIALOG_ID_PIN_INVALID).apply {
            withTitle(activity.getString(R.string.biometrics_id_prompt_title))
            withMessage(activity.getString(R.string.biometrics_pin_invalid))
            withPositiveButtonText(activity.getString(R.string.general_ok))
            withNegativeButtonText(activity.getString(R.string.general_cancel))
            withListener(InvalidPinPromptListener())
        }.build().show(activity.supportFragmentManager, null)
    }

    override fun postHandleAcceptedPin(pin: String) {
        super.postHandleAcceptedPin(pin)
        pinRequest = null
    }

    private fun authenticate(pin: String, persistPinOnSucceed: Boolean) {
        val activity = activity() as FragmentActivity
        val result = authenticator.startBiometricAuthentication(
            activity,
            biometricDialogConfig(activity),
            BiometricCallback(pin, persistPinOnSucceed)
        )
        if (!result) {
            MBDialogFragment.Builder(DIALOG_ID_INVALID_CRYPTO).apply {
                withTitle(activity.getString(R.string.biometrics_id_prompt_title))
                withMessage(activity.getString(R.string.biometrics_security_settings_changed))
                withPositiveButtonText(activity.getString(R.string.general_ok))
                withNegativeButtonText(activity.getString(R.string.general_cancel))
                withListener(InvalidCryptoPromptListener(pin, persistPinOnSucceed))
            }.build().show(activity.supportFragmentManager, null)
        }
    }

    private fun showPinDialog(activity: AppCompatActivity) {
        MBPinDialogFragment.Builder().apply {
            withTitle(activity.getString(R.string.pin_popup_confirm_title))
            withMessage(String.format(activity.getString(R.string.pin_popup_description), UserValuePolicy.USER_PIN_DIGITS))
            withPositiveButtonText(activity.getString(R.string.pin_popup_send))
            withNegativeButtonText(activity.getString(R.string.pin_popup_cancel))
            withListener(PinDialogListener())
        }.build().show(activity.supportFragmentManager, null)
    }

    private fun biometricDialogConfig(activity: Activity) =
        BiometricDialogConfig.Builder(activity.getString(R.string.biometrics_id_prompt_title),
            activity.getString(R.string.general_cancel)).build()

    private inner class BiometricCallback(
        private val pin: String,
        private val persistPinOnSucceed: Boolean
    ) : SimpleBiometricCallback() {

        override fun onAuthenticationError(errString: String) {
            MBLoggerKit.e("onAuthenticationError: $errString")
            pinRequest?.cancel(errString)
            pinRequest = null
        }

        override fun onAuthenticationSucceeded() {
            MBLoggerKit.d("onAuthenticationSucceeded")
            if (persistPinOnSucceed) biometricsEnabled(pin)
            pinRequest?.confirmPin(pin)
        }
    }

    private inner class PinDialogListener : MBPinDialogFragment.PinDialogListener {

        override fun onPinConfirmed(pin: String) {
            authenticate(pin, true)
        }

        override fun onInputCancelled() {
            MBLoggerKit.d("Cancelled pin input.")
            pinRequest = null
        }
    }

    private inner class BiometricsPromptListener(
        private val pin: String
    ) : MBDialogFragment.DialogListener {

        override fun onPositiveAction(id: Int) {
            authenticate(pin, true)
        }

        override fun onNegativeAction(id: Int) {
            biometricsDisabled()
        }
    }

    private inner class InvalidPinPromptListener : MBDialogFragment.DialogListener {

        override fun onPositiveAction(id: Int) {
            if (!activitySet) return
            showPinDialog(activity() as AppCompatActivity)
        }

        override fun onNegativeAction(id: Int) {
            pinRequest = null
            biometricsDisabled()
        }
    }

    private inner class InvalidCryptoPromptListener(
        private val pin: String,
        private val persistPinOnSucceed: Boolean
    ) : MBDialogFragment.DialogListener {

        override fun onPositiveAction(id: Int) {
            if (!activitySet) return
            authenticate(pin, persistPinOnSucceed)
        }

        override fun onNegativeAction(id: Int) {
            pinRequest = null
            biometricsDisabled()
        }
    }

    private companion object {
        private const val DIALOG_ID_BIOMETRICS_ENABLING = 1
        private const val DIALOG_ID_PIN_INVALID = 2
        private const val DIALOG_ID_INVALID_CRYPTO = 3
    }
}