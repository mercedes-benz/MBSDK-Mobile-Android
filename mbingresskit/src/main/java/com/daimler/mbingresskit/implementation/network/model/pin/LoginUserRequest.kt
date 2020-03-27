package com.daimler.mbingresskit.implementation.network.model.pin

import com.google.gson.annotations.SerializedName

internal data class LoginUserRequest(
    @SerializedName("emailOrPhoneNumber") val emailOrPhoneNumber: String,
    @SerializedName("countryCode") val countryCode: String,
    @SerializedName("locale") val locale: String,
    @SerializedName("nonce") val nonce: String
)
