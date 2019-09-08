package com.daimler.mbmobilesdk.profile.locale

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daimler.mbmobilesdk.app.UserLocale
import com.daimler.mbmobilesdk.app.format
import com.daimler.mbmobilesdk.utils.emptyAddress
import com.daimler.mbingresskit.common.User
import com.daimler.mbnetworkkit.MBNetworkKit
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent

internal class UserLocaleChangeViewModel(
    user: User,
    locale: UserLocale
) : ViewModel() {

    val toolbarTitle = MutableLiveData<String>()

    val backEvent = MutableLiveUnitEvent()

    var currentUser: User = user
        private set

    var currentLocale: UserLocale = locale
        private set

    var natconShown = false
        private set

    fun onBackClicked() {
        notifyBack()
    }

    fun updateToolbarTitle(title: String) {
        toolbarTitle.postValue(title)
    }

    fun localeSelected(userLocale: UserLocale) {
        natconShown = false
        updateUserByLocale(userLocale)
    }

    fun natconShown() {
        natconShown = true
    }

    private fun updateUserByLocale(userLocale: UserLocale) {
        currentLocale = userLocale
        MBNetworkKit.headerService().updateNetworkLocale(currentLocale.format()) // update header locale
        val address = currentUser.address ?: emptyAddress()
        currentUser = currentUser.copy(
            countryCode = userLocale.countryCode,
            address = address.copy(countryCode = userLocale.countryCode),
            languageCode = userLocale.format()
        )
    }

    private fun notifyBack() {
        backEvent.sendEvent()
    }
}