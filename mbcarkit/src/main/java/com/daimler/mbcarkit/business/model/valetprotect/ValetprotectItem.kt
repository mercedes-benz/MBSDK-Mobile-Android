package com.daimler.mbcarkit.business.model.valetprotect

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates

data class ValetprotectItem(
    val name: String,
    val violationtypes: List<ValetprotectViolationType>,
    val center: GeoCoordinates,
    val radius: ValetprotectRadius
)
