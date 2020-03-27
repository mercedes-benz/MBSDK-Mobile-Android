package com.daimler.mbprotokit.dto.command

data class CommandVehicleApiStatusUpdate(
    val commandVehicleApiStatusModels: List<CommandVehicleApiStatus>,
    val sequenceNumber: Int,
    val vin: String
)
