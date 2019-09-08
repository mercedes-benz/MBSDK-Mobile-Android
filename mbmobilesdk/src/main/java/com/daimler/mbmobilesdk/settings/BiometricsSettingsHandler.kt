package com.daimler.mbmobilesdk.settings

import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

internal interface BiometricsSettingsHandler {

    /**
     * Sends the activation state ENABLED together with the given pin to the BFF.
     *
     * @return a [FutureTask] with a result of true if the pin is correct
     */
    fun validatePinInput(token: String, pin: String):
        FutureTask<Boolean, ResponseError<out RequestError>?>

    /**
     * Sends the activation state DISABLED to the BFF.
     */
    fun disableBiometrics(token: String):
        FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Enables biometrics on client side and caches the pin.
     */
    fun biometricsEnabled(pin: String)

    /**
     * Disables biometrics on client side and deletes the cached pin.
     */
    fun biometricsDisabled()
}