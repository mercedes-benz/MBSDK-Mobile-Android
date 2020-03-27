package com.daimler.mbprotokit.dto.command

import com.google.protobuf.Value

data class CommandVehicleApiError(
    val code: String,
    val message: String,
    val subErrors: List<CommandVehicleApiError>,
    val attributes: Map<String, Value>
)
