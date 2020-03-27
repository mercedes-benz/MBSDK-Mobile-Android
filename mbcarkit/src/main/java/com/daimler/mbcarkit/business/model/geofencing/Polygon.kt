package com.daimler.mbcarkit.business.model.geofencing

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates

data class Polygon(
    val coordinates: List<GeoCoordinates>
)
