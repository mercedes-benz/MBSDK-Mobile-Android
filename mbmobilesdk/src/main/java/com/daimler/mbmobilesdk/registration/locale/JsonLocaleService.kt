package com.daimler.mbmobilesdk.registration.locale

import android.content.Context
import com.daimler.mbmobilesdk.assets.MBMobileSDKAssetManager
import com.daimler.mbmobilesdk.assets.AssetSubType
import java.util.*

internal class JsonLocaleService(context: Context) : LocaleService {

    private val locales: JsonLocales by lazy { readCountries(context) }

    override fun loadCountries(): List<LocaleModel> = mapCountries()

    override fun loadLanguagesForCountry(countryCode: String): List<LocaleModel> {
        val languages = locales.locales[countryCode]
        return languages?.let { language ->
            language.mapNotNull { localeFromLanguage(it) }.sortedBy { it.name }
        } ?: emptyList()
    }

    private fun readCountries(context: Context): JsonLocales =
        MBMobileSDKAssetManager.createAssetService(context, AssetSubType.LOCALES)
            .parseFromJson(LOCALES_JSON, JsonLocales::class.java)

    private fun mapCountries(): List<LocaleModel> =
        locales.locales.mapNotNull { localeFromCountryCode(it.key) }.sortedBy { it.name }

    private fun localeFromCountryCode(countryCode: String): LocaleModel? {
        val locale = Locale.getAvailableLocales().firstOrNull {
            it.country == countryCode && !it.displayCountry.isNullOrBlank()
        }
        return locale?.let { LocaleModel(it.displayCountry, it.country, LocaleType.COUNTRY) }
    }

    private fun localeFromLanguage(languageCode: String): LocaleModel? {
        val locale = Locale.getAvailableLocales().firstOrNull {
            it.language == languageCode && !it.displayLanguage.isNullOrBlank()
        }
        return locale?.let { LocaleModel(it.displayLanguage, it.language, LocaleType.LANGUAGE) }
    }

    private data class JsonLocales(val locales: Map<String, Array<String>>)

    private companion object {
        private const val LOCALES_JSON = "locales.json"
    }
}