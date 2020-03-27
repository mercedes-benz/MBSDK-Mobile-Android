package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiValetprotectRadius(
    @SerializedName("value") val value: Double,
    @SerializedName("unit") val unit: ApiDistanceUnit?
)
