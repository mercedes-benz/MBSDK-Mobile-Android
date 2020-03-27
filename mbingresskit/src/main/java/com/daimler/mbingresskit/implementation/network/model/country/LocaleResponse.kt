package com.daimler.mbingresskit.implementation.network.model.country

import com.daimler.mbingresskit.common.CountryLocale
import com.google.gson.annotations.SerializedName

internal data class LocaleResponse(
    @SerializedName("localeCode") val localeCode: String?,
    @SerializedName("localeName") val localeName: String?
)

internal fun LocaleResponse.toCountryLocale() = CountryLocale(
    localeCode = localeCode,
    localeName = localeName
)
