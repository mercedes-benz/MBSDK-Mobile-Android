package com.daimler.mbmobilesdk.support

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.featuretoggling.FLAG_SUPPORT_BETA_NUMBER
import com.daimler.mbmobilesdk.logic.UserTask
import com.daimler.mbmobilesdk.utils.extensions.formatWithSeparator
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.common.User
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.utils.extensions.getString
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf
import java.util.*

class SupportViewModel(app: Application) : AndroidViewModel(app) {

    val progressVisible = mutableLiveDataOf(false)

    val phoneEvent = MutableLiveEvent<String>()
    val mailEvent = MutableLiveEvent<SupportRequest>()

    val phoneNumber = MutableLiveData<String>()

    private var supportData: SupportModel? = null

    init {
        supportData = findSupportData()
        phoneNumber.postValue(phoneNumber() ?: "-")
    }

    fun onPhoneClicked() {
        phoneNumber()?.let {
            phoneEvent.sendEvent(it)
        } ?: MBLoggerKit.e("No phone number found.")
    }

    fun onSendMessageClicked() {
        supportData?.email?.let { mail ->
            onLoadingStarted()
            UserTask().fetchUser()
                .onAlways { _, result, _ ->
                    onLoadingFinished()
                    mailEvent.sendEvent(prepareMail(mail, result?.user))
                }
        } ?: MBLoggerKit.e("No mail address found.")
    }

    private fun shouldUseBetaPhoneNumber() = MBMobileSDK.featureToggleService().isToggleEnabled(FLAG_SUPPORT_BETA_NUMBER)

    private fun findSupportData(): SupportModel? {
        return JsonMBSupportKit.loadSupportForLocale(getApplication(), Locale.getDefault().formatWithSeparator("_"))
    }

    private fun phoneNumber() = supportData?.phoneNumber(shouldUseBetaPhoneNumber())

    private fun onLoadingStarted() {
        progressVisible.postValue(true)
    }

    private fun onLoadingFinished() {
        progressVisible.postValue(false)
    }

    private fun prepareMail(mail: String, user: User?): SupportRequest {
        val subject = getString(R.string.support_subject)
        val message = SupportMailBuilder().apply {
            repeat(MAIL_TOP_EMPTY_LINES) { newLine() }
            appendUserAndAppData(getApplication(), user)
        }.toString()

        return SupportRequest(mail, subject, message)
    }

    data class SupportRequest(val mail: String, val subject: String, val message: String)

    private companion object {
        const val MAIL_TOP_EMPTY_LINES = 3
    }
}