package com.daimler.mbingresskit.common

data class Country(
    val countryCode: String,
    val countryName: String,
    val instance: CountryInstance,
    val legalRegion: String,
    val defaultLocale: String?,
    val natconCountry: Boolean,
    val connectCountry: Boolean,
    val locales: List<CountryLocale>?,
    val availability: Boolean = true
)
