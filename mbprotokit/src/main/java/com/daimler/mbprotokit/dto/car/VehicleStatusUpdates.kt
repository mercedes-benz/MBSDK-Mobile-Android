package com.daimler.mbprotokit.dto.car

data class VehicleStatusUpdates(
    val vehiclesByVin: Map<String, VehicleUpdate>,
    val sequenceNumber: Int
)
