package com.daimler.mbmobilesdk.utils.extensions

import com.daimler.mbmobilesdk.app.UserLocale
import java.util.*

fun getCountryByCountryCode(iso: String?): String? = iso?.let { code ->
    val locales = Locale.getAvailableLocales()
    return locales.firstOrNull { it.country == code }?.displayCountry
}

fun getLanguageByLanguageCode(code: String?): String? =
    Locale.getAvailableLocales().firstOrNull { it.formatWithSeparator("-") == code }?.formatLanguage()

fun Locale.format() = formatWithSeparator("-")

fun Locale.formatWithSeparator(separator: String) = "$language$separator$country"

fun Locale.formatLanguage() = "$displayLanguage ($displayCountry)"

internal fun Locale.toUserLocale() = UserLocale(country, language)