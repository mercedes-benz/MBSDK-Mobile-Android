package com.daimler.mbmobilesdk.login.pin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.registration.LoginUser
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.UserCredentials
import com.daimler.mbingresskit.login.LoginFailure
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.getString
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf
import java.util.*

internal class PinVerificationViewModel(
    app: Application,
    private val isRegistration: Boolean,
    private val user: LoginUser
) : AndroidViewModel(app) {

    val isMail = user.isMail
    val validationProgressVisible = mutableLiveDataOf(false)
    val tanProgressVisible = mutableLiveDataOf(false)
    val processing = mutableLiveDataOf(false)
    val onPinError = MutableLiveEvent<String>()
    val onPinVerified = MutableLiveEvent<PinVerificationEvent>()
    val onPinRequested = MutableLiveUnitEvent()
    val onPinRequestError = MutableLiveEvent<String>()
    val onShowLegalEvent = MutableLiveUnitEvent()
    val captionText = String.format(getString(R.string.verification_login_msg_user), PIN_DIGITS, user.user)

    private var pin: String? = null

    fun onPinInputChanged(newPin: String) {
        pin = newPin
    }

    fun onContinueClicked() {
        pin?.takeIf {
            isPinValid(it)
        }?.let {
            MBLoggerKit.d("Valid pin: $it.")
            startLogin(it)
        } ?: MBLoggerKit.e("Invalid pin: $pin.")
    }

    fun onRetryClicked() {
        MBLoggerKit.d("Request pin for $user")
        val locale = Locale.getDefault()
        onSendNewTan()
        MBIngressKit.userService().sendPin(user.user, locale.country.toUpperCase())
            .onComplete {
                notifyPinRequestRetried()
            }.onFailure {
                MBLoggerKit.re("Error while trying to request a pin.", it)
                handlePinRequestRetryError(it)
            }.onAlways { _, _, _ ->
                onSendNewTanFinished()
            }
    }

    fun onShowLegal() {
        onShowLegalEvent.sendEvent()
    }

    /**
     * Should only be called if pin was already check for validity.
     */
    private fun startLogin(pin: String) {
        onLogin()
        MBIngressKit.loginWithCredentials(UserCredentials(user.user, pin))
            .onComplete {
                MBLoggerKit.d("Login for $user success")
                notifyPinVerified(user, isRegistration)
            }.onFailure {
                MBLoggerKit.e("Login for $user failed: $it")
                handleLoginError(it)
            }.onAlways { _, _, _ ->
                onLoginFinished()
            }
    }

    private fun handleLoginError(error: ResponseError<out RequestError>?) {
        notifyPinError(
            defaultErrorMessage<LoginFailure>(error) {
                when (it) {
                    LoginFailure.UNABLE_TO_EXCHANGE_TOKEN -> getString(R.string.general_error_msg)
                    LoginFailure.WRONG_CREDENTIALS -> getString(R.string.login_error_wrong_tan)
                    LoginFailure.AUTHORIZATION_FAILED -> getString(R.string.login_error_authentication_failed)
                    else -> getString(R.string.general_error_msg)
                }
            }
        )
    }

    private fun handlePinRequestRetryError(error: ResponseError<out RequestError>?) {
        notifyPinRequestRetryError(defaultErrorMessage(error))
    }

    private fun isPinValid(pin: String): Boolean =
        pin.length == PIN_DIGITS

    private fun notifyPinVerified(user: LoginUser, isRegistration: Boolean) {
        onPinVerified.sendEvent(PinVerificationEvent(user, isRegistration))
    }

    private fun notifyPinError(error: String) {
        onPinError.sendEvent(error)
    }

    private fun notifyPinRequestRetried() {
        onPinRequested.sendEvent()
    }

    private fun notifyPinRequestRetryError(error: String) {
        onPinRequestError.sendEvent(error)
    }

    private fun onLogin() {
        validationProgressVisible.postValue(true)
        onLoading()
    }

    private fun onLoginFinished() {
        validationProgressVisible.postValue(false)
        onLoadingFinished()
    }

    private fun onSendNewTan() {
        tanProgressVisible.postValue(true)
        onLoading()
    }

    private fun onSendNewTanFinished() {
        tanProgressVisible.postValue(false)
        onLoadingFinished()
    }

    private fun onLoading() {
        processing.postValue(true)
    }

    private fun onLoadingFinished() {
        processing.postValue(false)
    }

    internal data class PinVerificationEvent(val user: LoginUser, val isRegistration: Boolean)

    companion object {
        const val PIN_DIGITS = 6
    }
}