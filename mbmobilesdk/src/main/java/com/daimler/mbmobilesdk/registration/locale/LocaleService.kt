package com.daimler.mbmobilesdk.registration.locale

internal interface LocaleService {

    fun loadCountries(): List<LocaleModel>

    fun loadLanguagesForCountry(countryCode: String): List<LocaleModel>
}