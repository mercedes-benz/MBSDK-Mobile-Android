package com.daimler.mbcarkit.business.model.geofencing

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates

data class GeofenceViolation(
    val id: Int,
    val type: GeofenceViolationType?,
    val fenceId: Int,
    val time: Int,
    val coordinate: GeoCoordinates,
    val snapshot: Fence
)
