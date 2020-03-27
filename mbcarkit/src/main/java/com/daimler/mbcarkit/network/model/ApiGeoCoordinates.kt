package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiGeoCoordinates(
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?
)
