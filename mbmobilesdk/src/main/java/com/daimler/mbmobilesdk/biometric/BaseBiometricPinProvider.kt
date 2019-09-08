package com.daimler.mbmobilesdk.biometric

import com.daimler.mbmobilesdk.app.BasePinProvider
import com.daimler.mbmobilesdk.biometric.auth.BiometricAuthenticator
import com.daimler.mbmobilesdk.biometric.pincache.PinCache
import com.daimler.mbmobilesdk.preferences.user.UserSettings
import com.daimler.mbcarkit.business.PinRequest
import com.daimler.mbcarkit.business.model.command.CommandStatus
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatus

internal abstract class BaseBiometricPinProvider(
    protected val authenticator: BiometricAuthenticator,
    private val userSettings: UserSettings,
    private val pinCache: PinCache,
    next: BasePinProvider?
) : BasePinProvider(next) {

    final override fun doRequestPin(pinRequest: PinRequest) {
        val pin = pinCache.pin
        if (pin.isBlank()) {
            showProvidePinPrompt(pinRequest)
        } else {
            doBiometricAuthentication(pinRequest, pin)
        }
    }

    final override fun onPinAccepted(commandStatus: CommandStatus, pin: String) {
        handlePinAccepted(pin)
    }

    final override fun onPinInvalid(commandStatus: CommandStatus, pin: String) {
        handlePinInvalid()
    }

    override fun onPinAccepted(commandStatus: CommandVehicleApiStatus, pin: String) {
        handlePinAccepted(pin)
    }

    override fun onPinInvalid(commandStatus: CommandVehicleApiStatus, pin: String) {
        handlePinInvalid()
    }

    private fun handlePinAccepted(pin: String) {
        if (!userSettings.pinBiometricsEnabled.get() &&
            !userSettings.promptedForBiometrics.get() &&
            canProvideBiometrics()) {
            promptForBiometrics(pin)
            userSettings.promptedForBiometrics.set(true)
        }
        postHandleAcceptedPin(pin)
    }

    private fun handlePinInvalid() {
        if (userSettings.pinBiometricsEnabled.get()) {
            promptForInvalidPin()
        }
    }

    final override fun isEligible(): Boolean =
        userSettings.pinBiometricsEnabled.get() && canProvideBiometrics()

    /**
     * Returns true if the device can provide a biometric id.
     */
    protected abstract fun canProvideBiometrics(): Boolean

    /**
     * Authenticates through biometrics using the given [pin]. Call [PinRequest.confirmPin] when
     * the user is authenticated through the biometrics or [PinRequest.cancel] if something went
     * wrong.
     */
    protected abstract fun doBiometricAuthentication(pinRequest: PinRequest, pin: String)

    /**
     * Shows a dialog that lets the user input the pin code. This is needed in case that no
     * cached pin is available. Follow up with the authentication logic and call
     * [biometricsEnabled] when the authentication is confirmed.
     */
    protected abstract fun showProvidePinPrompt(pinRequest: PinRequest)

    /**
     * Shows a dialog that offers the user to use biometrics as pin authentication in the future.
     * Follow up with the registration of a biometric id if the user decided so. Call
     * [biometricsEnabled] when the registration is done.
     */
    protected abstract fun promptForBiometrics(pin: String)

    /**
     * Shows a dialog to inform the user that the pin code is invalid now.
     * Let the user input a new pin and follow up with the registration of the biometric id.
     * Call [biometricsEnabled] if the user went through the registration successfully.
     */
    protected abstract fun promptForInvalidPin()

    /**
     * Called when the given [pin] was accepted by the [PinRequest].
     */
    protected open fun postHandleAcceptedPin(pin: String) = Unit

    /**
     * Disables biometric authentication.
     */
    protected fun biometricsDisabled() {
        pinCache.clear()
        userSettings.pinBiometricsEnabled.set(false)
        authenticator.deleteBiometricKey()
    }

    /**
     * Enables biometric authentication with the given [pin].
     */
    protected fun biometricsEnabled(pin: String) {
        pinCache.pin = pin
        userSettings.pinBiometricsEnabled.set(true)
    }
}