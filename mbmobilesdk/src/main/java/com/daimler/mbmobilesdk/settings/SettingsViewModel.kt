package com.daimler.mbmobilesdk.settings

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.biometric.auth.BiometricAuthenticator
import com.daimler.mbmobilesdk.logic.UserTask
import com.daimler.mbmobilesdk.push.areNotificationsEnabled
import com.daimler.mbmobilesdk.push.areNotificationsEnabledOnSystemSettings
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.UserPinStatus
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.getString
import com.daimler.mbuikit.utils.extensions.invert
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf

internal class SettingsViewModel(
    app: Application,
    biometricsSupported: Boolean
) : AndroidViewModel(app) {

    val progressVisible = mutableLiveDataOf(false)
    val loading = mutableLiveDataOf(false)
    val showBiometrics = biometricsSupported && MBMobileSDK.biometrics() != null

    val pushEnabled = mutableLiveDataOf(areNotificationsEnabled(app))
    val biometricsEnabled = mutableLiveDataOf(MBMobileSDK.userSettings().pinBiometricsEnabled.get())
    val trackingEnabled = mutableLiveDataOf(MBMobileSDK.trackingSettings().trackingEnabled.get())

    val unitsClickEvent = MutableLiveUnitEvent()
    val onRegisterBiometricsEvent = MutableLiveUnitEvent()
    val onShowUserPinEvent = MutableLiveEvent<Boolean>()
    val onErrorEvent = MutableLiveEvent<String>()
    val onValidPinEvent = MutableLiveEvent<RegisterBiometricsWithPinEvent>()
    val onInvalidPinEvent = MutableLiveEvent<String>()
    val onShowPushSettingsEvent = MutableLiveUnitEvent()

    fun onPushEnabledChanged(enabled: Boolean) {
        pushEnabled.value = enabled

        // Check if notifications are enabled in system settings if the user wants
        // to enabled push notifications here.
        if (!areNotificationsEnabledOnSystemSettings(getApplication()) && enabled) {
            onShowPushSettingsEvent.sendEvent()
            pushEnabled.invert()
            return
        }

        // Check if the state did not change.
        if (MBMobileSDK.pushSettings().pushEnabled.get() == enabled) return

        onLoadingStarted()
        val fcmToken = MBMobileSDK.pushSettings().fcmToken.get()

        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                val handler = SettingsManager.pushSettingsHandler()
                val notificationsTask = if (enabled) {
                    handler.enablePushNotifications(jwt, fcmToken)
                } else {
                    handler.disablePushNotifications(jwt)
                }
                notificationsTask
                    .onComplete { pushEnabled.postValue(enabled) }
                    .onFailure { pushEnabled.postValue(!enabled) }
                    .onAlways { _, _, _ -> onLoadingFinished() }
            }.onFailure {
                MBLoggerKit.e("Could not refresh token.", throwable = it)
                onLoadingFinished()
                pushEnabled.invert()
            }
    }

    fun onTrackingEnabledChanged(enabled: Boolean) {
        trackingEnabled.value = enabled
        MBMobileSDK.trackingSettings().trackingEnabled.set(enabled)
    }

    fun onUnitsClick() {
        unitsClickEvent.sendEvent()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onBiometricsChanged(enabled: Boolean) {
        if (enabled == MBMobileSDK.userSettings().pinBiometricsEnabled.get()) return
        if (!enabled) {
            clearBiometrics()
        } else {
            enableBiometrics()
        }
    }

    fun onUserPinClicked() {
        onLoadingStarted()
        UserTask().fetchUser()
            .onComplete { onShowUserPinEvent.sendEvent(it.user.pinStatus != UserPinStatus.SET) }
            .onFailure { onErrorEvent.sendEvent(defaultErrorMessage(it)) }
            .onAlways { _, _, _ -> onLoadingFinished() }
    }

    fun validatePinForBiometrics(pin: String) {
        onLoadingStarted()
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                SettingsManager.biometricsSettingsHandler().validatePinInput(
                    token.jwtToken.plainToken,
                    pin
                ).onComplete { success ->
                    if (success) {
                        MBMobileSDK.biometrics()?.let {
                            onValidPinEvent.sendEvent(RegisterBiometricsWithPinEvent(it, pin))
                        }
                    } else {
                        onInvalidPinEvent.sendEvent(getString(R.string.pin_popup_wrong_pin_msg))
                    }
                }.onFailure {
                    onErrorEvent.sendEvent(defaultErrorMessage(it))
                    biometricsEnabled.postValue(false)
                }.onAlways { _, _, _ -> onLoadingFinished() }
            }.onFailure {
                onLoadingFinished()
                biometricsEnabled.postValue(false)
                onErrorEvent.sendEvent(defaultErrorMessage(it))
            }
    }

    fun onBiometricsRegistered(pin: String) {
        biometricsEnabled.postValue(true)
        SettingsManager.biometricsSettingsHandler().biometricsEnabled(pin)
    }

    fun onBiometricsCancelled() {
        clearBiometrics()
    }

    fun onUserPinSet() {
        biometricsEnabled.postValue(MBMobileSDK.userSettings().pinBiometricsEnabled.get())
    }

    private fun enableBiometrics() {
        onLoadingStarted()
        UserTask().fetchUser()
            .onComplete { result ->
                if (result.user.pinStatus == UserPinStatus.SET) {
                    onRegisterBiometricsEvent.sendEvent()
                } else {
                    onShowUserPinEvent.sendEvent(true)
                }
            }.onFailure {
                onErrorEvent.sendEvent(defaultErrorMessage(it))
            }.onAlways { _, _, _ -> onLoadingFinished() }
    }

    private fun clearBiometrics() {
        biometricsEnabled.postValue(false)
        val handler = SettingsManager.biometricsSettingsHandler()
        handler.biometricsDisabled()
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                handler.disableBiometrics(token.jwtToken.plainToken)
                    .onComplete { MBLoggerKit.d("Disabled biometrics.") }
                    .onFailure { MBLoggerKit.re("Failed to disable biometrics.", it) }
            }
    }

    private fun onLoadingStarted() {
        progressVisible.postValue(true)
        loading.postValue(true)
    }

    private fun onLoadingFinished() {
        progressVisible.postValue(false)
        loading.postValue(false)
    }

    internal data class RegisterBiometricsWithPinEvent(
        val biometrics: BiometricAuthenticator,
        val pin: String
    )
}