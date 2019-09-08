package com.daimler.mbmobilesdk.profile.locale

import android.app.Application
import com.daimler.mbmobilesdk.app.UserLocale
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent

internal class UserLocaleViewModel(
    app: Application,
    private val userLocale: UserLocale
) : BaseUserLocaleViewModel(app, userLocale.countryCode, userLocale.languageCode) {

    val localeSelectedEvent = MutableLiveEvent<UserLocaleSelectionEvent>()
    val cancelEvent = MutableLiveUnitEvent()

    override fun onUserLocaleSelected(userLocale: UserLocale) {
        if (this.userLocale != userLocale) {
            localeSelectedEvent.sendEvent(UserLocaleSelectionEvent(userLocale,
                shouldShowNatcon(userLocale.countryCode)))
        } else {
            cancelEvent.sendEvent()
        }
    }

    override fun onShowLegal() = Unit

    data class UserLocaleSelectionEvent(val userLocale: UserLocale, val shouldShowNatcon: Boolean)
}