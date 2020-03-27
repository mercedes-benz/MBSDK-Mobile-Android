package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiCircle(
    @SerializedName("center") val center: ApiGeoCoordinates,
    @SerializedName("radius") val radius: Double
)
