package com.daimler.mbmobilesdk.support

import com.google.gson.annotations.SerializedName

internal data class SupportLocationsModel(
    @SerializedName("support_locations") val supportLocations: List<SupportModel>,
    @SerializedName("fallback_number") val fallbackNumber: String,
    @SerializedName("fallback_mail") val fallbackMail: String
)