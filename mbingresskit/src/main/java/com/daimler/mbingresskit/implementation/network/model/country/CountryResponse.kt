package com.daimler.mbingresskit.implementation.network.model.country

import com.daimler.mbingresskit.common.Countries
import com.daimler.mbingresskit.common.Country
import com.daimler.mbingresskit.common.CountryInstance
import com.google.gson.annotations.SerializedName

internal data class CountryResponse(
    @SerializedName("connectCountry") val connectCountry: Boolean,
    @SerializedName("countryCode") val countryCode: String,
    @SerializedName("countryName") val countryName: String,
    @SerializedName("instance") val instance: ApiCountryInstance?,
    @SerializedName("legalRegion") val legalRegion: String,
    @SerializedName("defaultLocale") val defaultLocale: String?,
    @SerializedName("locales") val locales: List<LocaleResponse>?,
    @SerializedName("natconCountry") val natconCountry: Boolean,
    @SerializedName("availability") val availability: Boolean
)

internal fun List<CountryResponse>.toCountries() = Countries(map(CountryResponse::toCountry))

internal fun CountryResponse.toCountry() = Country(
    countryCode = countryCode,
    countryName = countryName,
    instance = instance?.let { CountryInstance.forName(it.name) } ?: CountryInstance.UNKNOWN,
    legalRegion = legalRegion,
    defaultLocale = defaultLocale,
    natconCountry = natconCountry,
    connectCountry = connectCountry,
    locales = locales?.map { locale -> locale.toCountryLocale() },
    availability = availability
)
