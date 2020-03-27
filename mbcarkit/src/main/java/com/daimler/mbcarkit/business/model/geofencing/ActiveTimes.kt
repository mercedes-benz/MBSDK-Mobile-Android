package com.daimler.mbcarkit.business.model.geofencing

data class ActiveTimes(
    val days: List<Int>,
    val begin: Int,
    val end: Int
)
