package com.daimler.mbcarkit.business.model.command

data class CommandVehicleApiStatus(
    val errors: List<CommandVehicleApiError>,
    val pid: String,
    var commandState: VehicleCommandStatus,
    val requestId: String?,
    val timestamp: Long,
    val type: Int
)
