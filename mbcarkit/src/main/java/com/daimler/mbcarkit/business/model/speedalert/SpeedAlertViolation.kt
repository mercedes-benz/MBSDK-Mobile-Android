package com.daimler.mbcarkit.business.model.speedalert

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates

data class SpeedAlertViolation(
    val id: String,
    val time: Long,
    val speedAlertTreshold: Int,
    val speedAlertEndTime: Long,
    val coordinate: GeoCoordinates
)
