package com.daimler.mbmobilesdk.utils.extensions

import com.daimler.mbmobilesdk.app.UserLocale
import com.daimler.mbingresskit.MBIngressKit

private const val LOCALE_DELIMITER = "-"

internal fun MBIngressKit.userLocale(default: UserLocale): UserLocale {
    return cachedUser()?.let {
        val country = it.countryCode
        val languageLocale = it.languageCode
        userLocaleFromString(languageLocale)
            ?: UserLocale(country, getLanguageCodeFromLocale(languageLocale))
    } ?: default
}

private fun getLanguageCodeFromLocale(locale: String): String {
    return getLanguageCodeFromLocale(locale, LOCALE_DELIMITER) ?: locale
}

private fun getLanguageCodeFromLocale(locale: String, delimiter: String): String? {
    val split = locale.split(delimiter)
    return if (split.size > 1) split.first() else null
}

internal fun userLocaleFromString(locale: String): UserLocale? {
    val split = locale.split(LOCALE_DELIMITER)
    return if (split.size == 2) {
        UserLocale(split[1], split[0])
    } else {
        null
    }
}