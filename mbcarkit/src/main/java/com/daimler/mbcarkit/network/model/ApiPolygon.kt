package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiPolygon(
    @SerializedName("coordinates") val coordinates: List<ApiGeoCoordinates>
)
