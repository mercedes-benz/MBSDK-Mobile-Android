package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiOpeningHours(
    @SerializedName("MONDAY") val monday: ApiDay?,
    @SerializedName("TUESDAY") val tuesday: ApiDay?,
    @SerializedName("WEDNESDAY") val wednesday: ApiDay?,
    @SerializedName("THURSDAY") val thursday: ApiDay?,
    @SerializedName("FRIDAY") val friday: ApiDay?,
    @SerializedName("SATURDAY") val saturday: ApiDay?,
    @SerializedName("SUNDAY") val sunday: ApiDay?
)
