package com.daimler.mbcarkit.business.model.geofencing

data class Fence(
    val id: Int?,
    val isActive: Boolean?,
    val name: String,
    val activeTimes: ActiveTimes,
    val violationType: GeofenceViolationType?,
    val shape: Shape
)
