package com.daimler.mbmobilesdk.example

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

/**
 * Use this class with the Kotlin REPL to generate the json for available locales.
 */
@Suppress("UNUSED")
private class LocaleHelper {

    fun createJson(): String {
        val locales = initializeSupportedLocales()
        val model = JsonLocales(locales)
        return Gson().toJson(model)
    }

    private fun initializeSupportedLocales(): Map<String, Array<String>> {
        val supportedLocales = mutableMapOf<String, Array<String>>()
        supportedLocales["AE"] = arrayOf("en")
        supportedLocales["AE-AZ"] = arrayOf("en")
        supportedLocales["AE-DU"] = arrayOf("en")
        supportedLocales["AT"] = arrayOf("de")
        supportedLocales["BE"] = arrayOf("fr", "nl")
        supportedLocales["BG"] = arrayOf("bg")
        supportedLocales["CA"] = arrayOf("en", "fr")
        supportedLocales["CH"] = arrayOf("de", "fr", "it")
        supportedLocales["CN"] = arrayOf("zh", "en")
        supportedLocales["CY"] = arrayOf("el", "en")
        supportedLocales["CZ"] = arrayOf("cs")
        supportedLocales["DE"] = arrayOf("de")
        supportedLocales["DK"] = arrayOf("da")
        supportedLocales["EE"] = arrayOf("et")
        supportedLocales["ES"] = arrayOf("es")
        supportedLocales["FI"] = arrayOf("fi")
        supportedLocales["FR"] = arrayOf("fr")
        supportedLocales["GB"] = arrayOf("en")
        supportedLocales["GR"] = arrayOf("el")
        supportedLocales["HR"] = arrayOf("hr")
        supportedLocales["HU"] = arrayOf("hu")
        supportedLocales["IE"] = arrayOf("en")
        supportedLocales["IT"] = arrayOf("it")
        supportedLocales["JP"] = arrayOf("ja", "en")
        supportedLocales["KR"] = arrayOf("ko", "en")
        supportedLocales["LI"] = arrayOf("de")
        supportedLocales["LT"] = arrayOf("lt")
        supportedLocales["LU"] = arrayOf("de", "fr")
        supportedLocales["LV"] = arrayOf("lv")
        supportedLocales["MT"] = arrayOf("mt", "en")
        supportedLocales["NL"] = arrayOf("nl")
        supportedLocales["NO"] = arrayOf("no")
        supportedLocales["PL"] = arrayOf("pl")
        supportedLocales["PT"] = arrayOf("pt")
        supportedLocales["RO"] = arrayOf("ro")
        supportedLocales["RU"] = arrayOf("ru", "en")
        supportedLocales["SE"] = arrayOf("sv")
        supportedLocales["SI"] = arrayOf("sl")
        supportedLocales["SK"] = arrayOf("sk")
        supportedLocales["TW"] = arrayOf("zh", "en")
        supportedLocales["US"] = arrayOf("en")
        supportedLocales["ZA"] = arrayOf("af", "en")
        supportedLocales["AU"] = arrayOf("en")
        supportedLocales["NZ"] = arrayOf("en")
        supportedLocales["TH"] = arrayOf("th", "en")
        supportedLocales["MY"] = arrayOf("en")
        supportedLocales["MX"] = arrayOf("en", "es")
        supportedLocales["IN"] = arrayOf("en")
        return supportedLocales
    }

    data class JsonLocales(
        @SerializedName("locales") val map: Map<String, Array<String>>
    )
}