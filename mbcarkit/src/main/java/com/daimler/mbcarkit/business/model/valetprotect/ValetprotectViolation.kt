package com.daimler.mbcarkit.business.model.valetprotect

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates

data class ValetprotectViolation(
    val id: Int,
    val violationtype: ValetprotectViolationType,
    val time: Long,
    val coordinate: GeoCoordinates,
    val snapshot: ValetprotectItem
)
