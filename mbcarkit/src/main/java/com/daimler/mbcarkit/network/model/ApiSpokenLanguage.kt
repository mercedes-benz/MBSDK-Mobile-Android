package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiSpokenLanguage(
    @SerializedName("languageIsoCode") val languageIsoCode: String?
)
