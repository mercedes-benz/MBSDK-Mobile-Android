package com.daimler.mbcarkit.business.model.speedfence

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates

data class SpeedFenceViolation(
    val violationId: Int,
    val time: Long,
    val coordinates: GeoCoordinates?,
    val speedFence: SpeedFence?
)
