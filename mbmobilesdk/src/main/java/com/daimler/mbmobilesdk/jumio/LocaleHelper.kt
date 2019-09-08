package com.daimler.mbmobilesdk.jumio

import java.util.*

object LocaleHelper {
    private val localeMap: MutableMap<String, Locale> = Locale.getISOCountries()
        .associateBy(
            { Locale("", it).isO3Country.toUpperCase() },
            { Locale("", it) })
        .toMutableMap()

    fun getLocalizedCountryNameFromISO3(iso3: String): String? {
        return localeMap[iso3]?.displayCountry
    }
}
