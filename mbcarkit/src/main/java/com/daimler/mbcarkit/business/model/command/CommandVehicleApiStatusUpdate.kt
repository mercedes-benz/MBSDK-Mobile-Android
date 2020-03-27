package com.daimler.mbcarkit.business.model.command

data class CommandVehicleApiStatusUpdate(
    val commandVehicleApiStatusModels: List<CommandVehicleApiStatus>,
    val sequenceNumber: Int,
    val vin: String
)
