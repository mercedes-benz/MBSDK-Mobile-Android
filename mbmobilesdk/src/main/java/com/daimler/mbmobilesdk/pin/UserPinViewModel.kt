package com.daimler.mbmobilesdk.pin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.logic.UserTask
import com.daimler.mbmobilesdk.settings.SettingsManager
import com.daimler.mbmobilesdk.utils.UserValuePolicy
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbmobilesdk.utils.extensions.getHttpError
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbmobilesdk.utils.extensions.userInputErrorMessage
import com.daimler.mbmobilesdk.utils.ifNotNull
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbnetworkkit.networking.HttpError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.getString

class UserPinViewModel(app: Application, val isInitialPin: Boolean) : AndroidViewModel(app) {

    val oldPin = MutableLiveData<String>()
    val newPin = MutableLiveData<String>()

    val progressVisible = MutableLiveData<Boolean>()

    val pinDigits: Int = UserValuePolicy.USER_PIN_DIGITS

    val loadingEvent = MutableLiveUnitEvent()
    val pinSetEvent = MutableLiveEvent<String>()
    val pinChangedEvent = MutableLiveEvent<String>()
    val errorEvent = MutableLiveEvent<String>()

    fun onSaveClicked() {
        if (isInitialPin) {
            if (UserValuePolicy.Pin.isValid(newPin.value)) {
                newPin.value?.let { setPin(it) }
            } else {
                notifyInputError(oldPinInvalid = false, newPinInvalid = true)
            }
        } else {
            when {
                !UserValuePolicy.Pin.isValid(oldPin.value) ->
                    notifyInputError(oldPinInvalid = true, newPinInvalid = false)
                !UserValuePolicy.Pin.isValid(newPin.value) ->
                    notifyInputError(oldPinInvalid = false, newPinInvalid = true)
                else -> ifNotNull(oldPin.value, newPin.value) { oldPin, newPin ->
                    changePin(oldPin, newPin)
                }
            }
        }
    }

    fun onOldPinInputChanged(s: String) {
        oldPin.postValue(s)
    }

    fun onNewPinInputChanged(s: String) {
        newPin.postValue(s)
    }

    internal fun onRegisterBiometrics() {
        val pin = newPin.value
        if (pin.isNullOrBlank()) return

        MBMobileSDK.userSettings().promptedForBiometrics.set(true)

        val handler = SettingsManager.biometricsSettingsHandler()
        MBIngressKit.refreshTokenIfRequired()
            .onComplete {
                // We already knew that the pin was valid, so we can safely ignore the result.
                handler.validatePinInput(it.jwtToken.plainToken, pin)
            }
        handler.biometricsEnabled(pin)
    }

    private fun setPin(pin: String) {
        onLoading()
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                MBIngressKit.userService().setPin(jwt, pin)
                    .onComplete { fetchUserAndNotifyPinSet(pin) }
                    .onFailure {
                        MBLoggerKit.re("Error while setting pin.", it)
                        onLoadingFinished()
                        notifyUserInputError(it)
                    }
            }.onFailure {
                MBLoggerKit.e("Error while refreshing token.", throwable = it)
                onLoadingFinished()
                notifyDefaultError(it)
            }
    }

    private fun changePin(oldPin: String, newPin: String) {
        onLoading()
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                MBIngressKit.userService().changePin(jwt, oldPin, newPin)
                    .onComplete { notifyPinChanged(newPin) }
                    .onFailure {
                        MBLoggerKit.re("Error while changing pin.", it)
                        handleChangePinError(it)
                    }.onAlways { _, _, _ -> onLoadingFinished() }
            }.onFailure {
                MBLoggerKit.e("Error while refreshing token.", throwable = it)
                onLoadingFinished()
                notifyDefaultError(it)
            }
    }

    private fun fetchUserAndNotifyPinSet(pin: String) {
        UserTask().fetchUser(true)
            .onAlways { _, _, _ ->
                // we notify no matter the result
                onLoadingFinished()
                notifyPinSet(pin)
            }
    }

    private fun handleChangePinError(error: ResponseError<out RequestError>?) {
        error?.getHttpError<HttpError.Forbidden>()?.let {
            notifyWrongPinError()
        } ?: notifyUserInputError(error)
    }

    private fun notifyPinChanged(newPin: String) {
        pinChangedEvent.sendEvent(newPin)
    }

    private fun notifyPinSet(newPin: String) {
        pinSetEvent.sendEvent(newPin)
    }

    private fun notifyWrongPinError() {
        errorEvent.sendEvent(getString(R.string.pin_popup_wrong_pin_msg))
    }

    private fun notifyUserInputError(error: ResponseError<out RequestError>?) {
        errorEvent.sendEvent(userInputErrorMessage(error))
    }

    private fun notifyDefaultError(error: Throwable?) {
        errorEvent.sendEvent(defaultErrorMessage(error))
    }

    private fun notifyInputError(oldPinInvalid: Boolean, newPinInvalid: Boolean) {
        errorEvent.sendEvent(generateInputErrorMessage(oldPinInvalid, newPinInvalid))
    }

    private fun generateInputErrorMessage(oldPinInvalid: Boolean, newPinInvalid: Boolean): String =
        when {
            oldPinInvalid -> getString(R.string.custom_pin_old_not_filled)
            newPinInvalid -> getString(R.string.custom_pin_not_filled)
            else -> getString(R.string.custom_pin_not_filled)
        }

    private fun onLoading() {
        loadingEvent.sendEvent()
        progressVisible.postValue(true)
    }

    private fun onLoadingFinished() {
        progressVisible.postValue(false)
    }
}