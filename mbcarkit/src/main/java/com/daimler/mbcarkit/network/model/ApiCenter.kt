package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.google.gson.annotations.SerializedName

internal data class ApiCenter(
    @SerializedName("latitude") val latitude: String?,
    @SerializedName("longitude") val longitude: String?
) {
    companion object {
        fun fromGeoCoordinates(geoCoordinates: GeoCoordinates) =
            ApiCenter(
                geoCoordinates.latitude.toString(),
                geoCoordinates.longitude.toString()
            )
    }
}
