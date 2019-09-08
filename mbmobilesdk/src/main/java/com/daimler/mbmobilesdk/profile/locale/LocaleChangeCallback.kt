package com.daimler.mbmobilesdk.profile.locale

import com.daimler.mbmobilesdk.app.UserLocale

internal interface LocaleChangeCallback {

    fun onLocaleSelected(userLocale: UserLocale)

    fun onShowNatcon(userLocale: UserLocale)

    fun onCancel()

    fun onUpdateTitle(title: String)
}