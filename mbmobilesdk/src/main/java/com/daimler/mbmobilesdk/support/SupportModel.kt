package com.daimler.mbmobilesdk.support

import com.google.gson.annotations.SerializedName

internal data class SupportModel(
    @SerializedName("locale") val locale: String,
    @SerializedName("beta_number") val betaNumber: String?,
    @SerializedName("fallback_number") val fallbackNumber: String,
    @SerializedName("preferred_number") val preferredNumber: String?,
    @SerializedName("e-mail") val email: String?
)

internal fun SupportModel.phoneNumber(useBetaNumber: Boolean) =
    if (useBetaNumber) betaNumber ?: standardPhoneNumber else standardPhoneNumber

private val SupportModel.standardPhoneNumber: String
    get() = preferredNumber ?: fallbackNumber
