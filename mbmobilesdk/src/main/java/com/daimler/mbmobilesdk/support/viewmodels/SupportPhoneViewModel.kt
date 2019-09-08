package com.daimler.mbmobilesdk.support.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.app.Stage
import com.daimler.mbmobilesdk.app.format
import com.daimler.mbmobilesdk.featuretoggling.FLAG_SUPPORT_TRANSFER_DATA_WITH_CALL
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.User
import com.daimler.mbsupportkit.MBSupportKit
import com.daimler.mbsupportkit.common.CacCallData
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent

class SupportPhoneViewModel(application: Application) : AndroidViewModel(application) {
    val clickEvent = MutableLiveEvent<Int>()

    val switchStatus = MutableLiveData<Boolean>()
    val additionalDataVisible = MutableLiveData<Boolean>()
    val cacAdditionalDataEnabled = MutableLiveData<Boolean>()
    val callButtonEnabled = MutableLiveData<Boolean>()
    val ownPhoneNumber = MutableLiveData<String>()
    val cacPhoneNumber = MutableLiveData<String>()

    var user: User? = null

    init {
        val cacCallDataEnabled = MBMobileSDK.featureToggleService().isToggleEnabled(FLAG_SUPPORT_TRANSFER_DATA_WITH_CALL)
        cacAdditionalDataEnabled.postValue(cacCallDataEnabled)
        callButtonEnabled.postValue(false)

        setStoredState()
        userLoad()
    }

    private fun userLoad() {
        MBIngressKit.refreshTokenIfRequired()
                .onComplete { token ->
                    MBIngressKit.userService().loadUser(token.jwtToken.plainToken)
                            .onComplete { user ->
                                this.user = user
                                cacNumberLoad()
                            }
                            .onFailure { cacNumberLoad() }
                }
                .onFailure { cacNumberLoad() }
    }

    private fun cacNumberLoad() {
        MBIngressKit.refreshTokenIfRequired()
                .onComplete { token ->
                    MBSupportKit.cacPhoneService().fetchCacPhoneNumber(token.jwtToken.plainToken, MBMobileSDK.userLocale().format())
                            .onComplete { phoneNumber -> onCacNumberLoaded(phoneNumber.number) }
                            .onFailure { onCacNumberLoaded(FALLBACK_CAC_NUMBER) }
                }
                .onFailure { onCacNumberLoaded(FALLBACK_CAC_NUMBER) }
    }

    private fun onCacNumberLoaded(number: String) {
        when (MBMobileSDK.selectedStage()) {
            Stage.PROD -> cacPhoneNumber.postValue(number)
            else -> cacPhoneNumber.postValue("0800")
        }
        setCallButtonStatus()
    }

    // ----- Databinding Methods
    fun callButtonClick() = clickEvent.sendEvent(CALL_CAC)

    fun dataInformationClick() = clickEvent.sendEvent(DATA_INFORMATION)

    fun switchStatusChanged(status: Boolean) {
        switchStatus.value = status
        additionalDataVisible.value = status
        setCallButtonStatus()
    }

    fun onPhoneNumberChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        ownPhoneNumber.value = s.toString()
        setCallButtonStatus()
    }

    private fun setCallButtonStatus() {
        if (cacPhoneNumber.value == null) {
            callButtonEnabled.postValue(false)
            return
        }
        if (switchStatus.value!! && !isValidPhoneNumber(ownPhoneNumber.value)) {
            callButtonEnabled.postValue(false)
            return
        }
        callButtonEnabled.postValue(true)
    }

    /**
     * Sends additional Data to CAC Compass if Feature is enabled and
     * switch button is enabled
     *
     * This data will be changed in future
     *
     * TODO implement correct Data
     */
    fun sendAdditionalCallData() {
        if (!switchStatus.value!!) return
        val cacCallData = CacCallData(user?.countryCode ?: "",
                "",
                MBMobileSDK.userLocale().format(),
                "",
                "",
                "")
        MBIngressKit.refreshTokenIfRequired()
                .onComplete { token ->
                    MBSupportKit.cacPhoneService().sendCacCallData(token.jwtToken.plainToken, cacCallData)
                            .onComplete { sendSuccess ->
                                if (sendSuccess) MBLoggerKit.d("Additional call data was send")
                                else MBLoggerKit.e("Error while sending additional call data")
                            }
                            .onFailure { MBLoggerKit.e("Error while sending additional call data") }
                }
                .onFailure { MBLoggerKit.e("Error while getting token") }
    }

    // ----- Preferences
    fun storeState() {
        val storeOwnPhoneNumber = ownPhoneNumber.value ?: ""
        val storeCacPhoneNumber = cacPhoneNumber.value ?: ""
        val storeCallDataSwitchState = switchStatus.value ?: false
        MBMobileSDK.supportSettings().ownPhoneNumber.set(storeOwnPhoneNumber)
        MBMobileSDK.supportSettings().cacPhoneNumber.set(storeCacPhoneNumber)
        MBMobileSDK.supportSettings().cacCallDataSwitchEnabled.set(storeCallDataSwitchState)
    }

    private fun setStoredState() {
        additionalDataVisible.postValue(MBMobileSDK.supportSettings().cacCallDataSwitchEnabled.get())
        cacPhoneNumber.postValue(MBMobileSDK.supportSettings().cacPhoneNumber.get())
        ownPhoneNumber.postValue(MBMobileSDK.supportSettings().ownPhoneNumber.get())
        switchStatus.value = MBMobileSDK.supportSettings().cacCallDataSwitchEnabled.get()
        setCallButtonStatus()
    }

    // ----- Helper functions
    private fun isValidPhoneNumber(number: String?): Boolean {
        return number?.let {
            it.matches(REGEX_PHONE_NUMBER) && (!it.matches(REGEX_PHONE_END_WITH_ZERO))
        } ?: false
    }

    companion object {
        val REGEX_PHONE_NUMBER = Regex("^\\+?[0-9]{8,20}$")
        val REGEX_PHONE_END_WITH_ZERO = Regex("^.*[0]{6}$")
        const val FALLBACK_CAC_NUMBER = "+442076600440"
        const val DATA_INFORMATION = 1
        const val CALL_CAC = 2
    }
}
