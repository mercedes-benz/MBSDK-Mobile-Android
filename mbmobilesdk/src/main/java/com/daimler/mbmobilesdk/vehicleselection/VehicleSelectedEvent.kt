package com.daimler.mbmobilesdk.vehicleselection

import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo

internal data class VehicleSelectedEvent(
    val vehicleInfo: VehicleInfo,
    val autoSelected: Boolean = false
)