package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.daimler.mbcarkit.business.model.speedfence.SpeedFenceViolation
import com.google.gson.annotations.SerializedName

internal data class ApiSpeedFenceViolation(
    @SerializedName("violationid") val violationId: Int,
    @SerializedName("time") val time: Long,
    @SerializedName("coordinates") val coordinates: ApiGeoCoordinates?,
    @SerializedName("speedfence") val speedFence: ApiSpeedFence?
)

internal fun ApiSpeedFenceViolation.toSpeedFenceViolation() = SpeedFenceViolation(
    violationId,
    time,
    GeoCoordinates(coordinates?.latitude, coordinates?.longitude),
    speedFence?.toSpeedFence()
)
