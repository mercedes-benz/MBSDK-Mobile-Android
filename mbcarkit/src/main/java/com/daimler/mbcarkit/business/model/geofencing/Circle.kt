package com.daimler.mbcarkit.business.model.geofencing

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates

data class Circle(
    val center: GeoCoordinates,
    val radius: Double
)
