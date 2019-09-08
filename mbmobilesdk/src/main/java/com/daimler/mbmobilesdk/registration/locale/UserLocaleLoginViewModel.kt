package com.daimler.mbmobilesdk.registration.locale

import android.app.Application
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.app.UserLocale
import com.daimler.mbmobilesdk.app.format
import com.daimler.mbmobilesdk.profile.locale.BaseUserLocaleViewModel
import com.daimler.mbmobilesdk.registration.LoginUser
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent

internal class UserLocaleLoginViewModel(
    app: Application,
    private val user: String,
    private val isMail: Boolean
) : BaseUserLocaleViewModel(app, null, null, true) {

    val showRegistrationEvent = MutableLiveEvent<LoginUser>()
    val showNatconEvent = MutableLiveEvent<LoginUser>()
    val legalShowEvent = MutableLiveUnitEvent()

    override fun onUserLocaleSelected(userLocale: UserLocale) {
        val loginUser = LoginUser(user, isMail, userLocale)
        MBMobileSDK.headerService().updateNetworkLocale(userLocale.format())
        if (shouldShowNatcon(userLocale.countryCode)) {
            showNatconEvent.sendEvent(loginUser)
        } else {
            showRegistrationEvent.sendEvent(loginUser)
        }
    }

    override fun onShowLegal() {
        legalShowEvent.sendEvent()
    }
}