package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiAddress(
    @SerializedName("street") val street: String?,
    @SerializedName("addressAddition") val addressAddition: String?,
    @SerializedName("zipCode") val zipCode: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("district") val district: String?,
    @SerializedName("countryIsoCode") val countryIsoCode: String?
)
