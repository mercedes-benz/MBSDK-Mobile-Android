package com.daimler.mbmobilesdk.settings

import com.daimler.mbmobilesdk.biometric.auth.BiometricAuthenticator
import com.daimler.mbmobilesdk.biometric.pincache.PinCache
import com.daimler.mbmobilesdk.preferences.user.UserSettings
import com.daimler.mbmobilesdk.utils.extensions.format
import com.daimler.mbingresskit.common.UserBiometricState
import com.daimler.mbingresskit.login.UserService
import com.daimler.mbnetworkkit.networking.HttpError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject
import java.util.*

internal class BiometricsSettingsHandlerImpl(
    private val userService: UserService,
    private val userPinCache: PinCache,
    private val userSettings: UserSettings,
    private val biometrics: BiometricAuthenticator?
) : BiometricsSettingsHandler {

    override fun validatePinInput(token: String, pin: String): FutureTask<Boolean, ResponseError<out RequestError>?> {
        val task = TaskObject<Boolean, ResponseError<out RequestError>?>()
        userService.sendBiometricActivation(token, Locale.getDefault().format(),
            UserBiometricState.ENABLED, pin)
            .onComplete { task.complete(true) }
            .onFailure {
                if (it?.requestError is HttpError.Forbidden) {
                    task.complete(false)
                } else {
                    task.fail(it)
                }
            }
        return task.futureTask()
    }

    override fun disableBiometrics(token: String): FutureTask<Unit, ResponseError<out RequestError>?> {
        return userService.sendBiometricActivation(
            token, Locale.getDefault().format(), UserBiometricState.DISABLED, null
        )
    }

    override fun biometricsEnabled(pin: String) {
        userPinCache.pin = pin
        userSettings.pinBiometricsEnabled.set(true)
    }

    override fun biometricsDisabled() {
        userPinCache.clear()
        userSettings.pinBiometricsEnabled.set(false)
        biometrics?.deleteBiometricKey()
    }
}