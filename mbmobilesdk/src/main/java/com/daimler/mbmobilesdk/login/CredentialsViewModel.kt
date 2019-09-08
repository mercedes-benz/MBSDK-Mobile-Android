package com.daimler.mbmobilesdk.login

import android.app.Application
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.view.Surface
import android.view.TextureView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.registration.LoginUser
import com.daimler.mbmobilesdk.tou.custom.CustomAgreementsService
import com.daimler.mbmobilesdk.tou.custom.CustomUserAgreements
import com.daimler.mbmobilesdk.utils.UserValuePolicy
import com.daimler.mbmobilesdk.utils.extensions.*
import com.daimler.mbmobilesdk.utils.postToMainThread
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.CustomUserAgreement
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.getString
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf
import java.util.*

internal class CredentialsViewModel(
    app: Application,
    userId: String?
) : AndroidViewModel(app) {

    val progressVisible = mutableLiveDataOf(false)

    val currentUser = mutableLiveDataOf(userId)

    var hasCredentilasText = mutableLiveDataOf(false)

    internal val pinRequestStarted = MutableLiveUnitEvent()
    internal val navigateToLocaleSelection = MutableLiveEvent<LoginUser>()
    internal val navigateToPinVerification = MutableLiveEvent<LoginUser>()
    internal val pinRequestError = MutableLiveEvent<String>()
    internal val showLegalEvent = MutableLiveUnitEvent()
    internal val showPdfAgreementsEvent = MutableLiveEvent<String>()
    internal val showWebAgreementsEvent = MutableLiveEvent<String>()
    internal val errorEvent = MutableLiveEvent<String>()
    internal val showMmeIdInfoEvent = MutableLiveUnitEvent()

    private val customService = CustomAgreementsService(MBIngressKit.userService())

    private var mediaPlayer: MediaPlayer? = null

    private val agreements = MutableLiveData<AgreementsLoadingResult>()
    private val loading: Boolean
        get() = progressVisible.value == true

    init {
        loadCustomTermsOfUse()
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.let {
            it.tryStop()
            it.release()
        }
        mediaPlayer = null
    }

    fun getSurfaceListener() = object : TextureView.SurfaceTextureListener {

        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) = Unit

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) = Unit

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean = true

        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(getApplication(), R.raw.login_movie_new)?.apply {
                    setSurface(Surface(surface))
                    isLooping = true
                    mute()
                    setOnPreparedListener { tryResume() }
                }
            } else {
                mediaPlayer?.tryAction { setSurface(Surface(surface)) }
            }
        }
    }

    fun onCredentialsChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isNotBlank()) hasCredentilasText.postValue(true) else hasCredentilasText.postValue(false)
    }

    fun onNextClicked() {
        if (!loading) {
            login(currentUser.value?.replace("\\s".toRegex(), ""))
        }
    }

    fun onLegalClicked() {
        showLegalEvent.sendEvent()
    }

    fun onMmeIdClicked() {
        showMmeIdInfoEvent.sendEvent()
    }

    internal fun resumeVideo() = mediaPlayer?.tryResume()

    internal fun pauseVideo() = mediaPlayer?.tryPause()

    internal fun onEulaSelected() {
        showAgreementsIfPossibleOrWait { accessConditions }
    }

    internal fun onDataProtectionSelected() {
        showAgreementsIfPossibleOrWait { dataPrivacy }
    }

    internal fun onBetaTermsSelected() {
        showAgreementsIfPossibleOrWait { betaContent }
    }

    private fun login(user: String?) {
        if (isInputValid(user)) {
            pinRequestStarted()
            postToMainThread { startPinRequest(user!!) }
        } else {
            pinRequestError.sendEvent(getString(R.string.login_invalid_input))
        }
    }

    private fun isInputValid(user: String?) =
        UserValuePolicy.Mail.isValid(user) || UserValuePolicy.Phone.isValid(user)

    private fun pinRequestStarted() {
        onLoading()
        pinRequestStarted.sendEvent()
    }

    private fun startPinRequest(user: String) {
        MBLoggerKit.d("Request pin for $user")
        val locale = Locale.getDefault()
        MBIngressKit.userService().sendPin(user, locale.country.toUpperCase())
            .onComplete {
                MBLoggerKit.d("Pin sent for $it")
                if (isLogin(it)) {
                    navigateToPinVerification.sendEvent(
                        LoginUser(it.userName, it.isMail, locale.toUserLocale())
                    )
                } else {
                    // We cannot use the returned user here, since it is empty in case
                    // of registration.
                    navigateToLocaleSelection.sendEvent(LoginUser(user, it.isMail,
                        locale.toUserLocale()))
                }
            }.onFailure {
                MBLoggerKit.re("Failed to send the pin request.", it)
                pinRequestError.sendEvent(userInputErrorMessage(it))
            }.onAlways { _, _, _ ->
                onLoadingFinished()
            }
    }

    private fun isLogin(user: com.daimler.mbingresskit.common.LoginUser): Boolean {
        return user.userName.isNotEmpty()
    }

    private fun loadCustomTermsOfUse() {
        val locale = Locale.getDefault()
        customService.fetchAgreements(locale.format(), locale.country.toUpperCase(), false)
            .onComplete {
                agreements.postValue(AgreementsLoadingResult(it, null))
            }.onFailure {
                agreements.postValue(AgreementsLoadingResult(null, it))
            }
    }

    /**
     * This method handles 3 cases:
     *  1. Agreements are already loaded -> just show them.
     *  2. Requesting of agreements failed -> show error.
     *  3. Wait for the loading to finish.
     */
    private fun showAgreementsIfPossibleOrWait(accessor: CustomUserAgreements.() -> CustomUserAgreement?) {
        if (!handleAgreementsResponseIfAvailable(accessor)) waitForAgreements(accessor)
    }

    private fun handleAgreementsResponseIfAvailable(accessor: CustomUserAgreements.() -> CustomUserAgreement?): Boolean {
        val result = agreements.value
        return result?.let { r ->
            when {
                r.error != null -> {
                    errorEvent.sendEvent(defaultErrorMessage(r.error))
                    true
                }
                r.agreements != null -> {
                    val customAgreement = r.agreements.accessor()
                    customAgreement?.let { agreement ->
                        if (agreement.hasPdf()) {
                            agreement.filePath?.let {
                                showPdfAgreementsEvent.sendEvent(it)
                            } ?: errorEvent.sendEvent(generalError())
                        } else {
                            showWebAgreementsEvent.sendEvent(agreement.originalUrl)
                        }
                    } ?: errorEvent.sendEvent(generalError())
                    true
                }
                else -> {
                    errorEvent.sendEvent(generalError())
                    true
                }
            }
        } ?: false
    }

    private fun waitForAgreements(accessor: CustomUserAgreements.() -> CustomUserAgreement?) {
        val observer = object : Observer<AgreementsLoadingResult> {
            override fun onChanged(t: AgreementsLoadingResult?) {
                agreements.removeObserver(this)
                onLoadingFinished()
                handleAgreementsResponseIfAvailable(accessor)
            }
        }
        agreements.observeForever(observer)
        onLoading()
    }

    private fun onLoading() {
        progressVisible.postValue(true)
    }

    private fun onLoadingFinished() {
        progressVisible.postValue(false)
    }

    private fun MediaPlayer.tryResume() {
        try {
            if (!isPlaying) start()
        } catch (e: IllegalStateException) {
            MBLoggerKit.e("Error while resuming media player.", throwable = e)
        }
    }

    private fun MediaPlayer.tryPause() {
        try {
            if (isPlaying) pause()
        } catch (e: IllegalStateException) {
            MBLoggerKit.e("Error while pausing media player.", throwable = e)
        }
    }

    private fun MediaPlayer.tryStop() {
        try {
            if (isPlaying) stop()
        } catch (e: IllegalStateException) {
            MBLoggerKit.e("Error while stopping media player.", throwable = e)
        }
    }

    private fun MediaPlayer.tryAction(action: MediaPlayer.() -> Unit) {
        try {
            this.action()
        } catch (e: IllegalStateException) {
            MBLoggerKit.e("Error while performing action on media player.", throwable = e)
        }
    }

    private fun MediaPlayer.mute() {
        setVolume(0f, 0f)
    }

    private data class AgreementsLoadingResult(
        val agreements: CustomUserAgreements?,
        val error: ResponseError<out RequestError>?
    )
}