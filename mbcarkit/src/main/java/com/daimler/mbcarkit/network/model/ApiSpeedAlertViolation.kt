package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.daimler.mbcarkit.business.model.speedalert.SpeedAlertViolation
import com.google.gson.annotations.SerializedName

internal data class ApiSpeedAlertViolation(
    @SerializedName("id") val id: String,
    @SerializedName("time") val time: Long,
    @SerializedName("speedalertTreshold") val speedAlertTreshold: Int,
    @SerializedName("speedalertEndtime") val speedAlertEndTime: Long,
    @SerializedName("coordinates") val coordinate: ApiGeoCoordinates
)

internal fun ApiSpeedAlertViolation.toSpeedAlertViolation() =
    SpeedAlertViolation(
        id,
        time,
        speedAlertTreshold,
        speedAlertEndTime,
        GeoCoordinates(
            coordinate.latitude,
            coordinate.longitude
        )
    )

internal fun List<ApiSpeedAlertViolation>.toSpeedAlertViolations() =
    map { it.toSpeedAlertViolation() }
