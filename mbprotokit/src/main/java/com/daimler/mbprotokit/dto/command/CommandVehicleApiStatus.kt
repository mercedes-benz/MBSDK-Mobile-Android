package com.daimler.mbprotokit.dto.command

data class CommandVehicleApiStatus(
    val errors: List<CommandVehicleApiError>,
    val pid: String,
    var commandState: VehicleCommandStatus,
    val requestId: String?,
    val timestamp: Long,
    val type: Int
)
