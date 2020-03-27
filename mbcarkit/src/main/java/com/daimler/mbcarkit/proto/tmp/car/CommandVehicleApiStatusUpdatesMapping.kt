package com.daimler.mbcarkit.proto.tmp.car

import com.daimler.mbcarkit.business.model.command.CommandVehicleApiError
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatus
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatusUpdate
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatusUpdates
import com.daimler.mbcarkit.business.model.command.VehicleCommandStatus

fun com.daimler.mbprotokit.dto.command.CommandVehicleApiStatusUpdates.map() = CommandVehicleApiStatusUpdates(
    commandsByVin.mapValues {
        it.value.map()
    }
)

fun com.daimler.mbprotokit.dto.command.CommandVehicleApiStatusUpdate.map() = CommandVehicleApiStatusUpdate(
    commandVehicleApiStatusModels = commandVehicleApiStatusModels.map {
        it.map()
    },
    sequenceNumber = sequenceNumber,
    vin = vin
)

fun com.daimler.mbprotokit.dto.command.CommandVehicleApiStatus.map() = CommandVehicleApiStatus(
    errors = errors.map { it.map() },
    pid = pid,
    commandState = commandState.map(),
    requestId = requestId,
    timestamp = timestamp,
    type = type
)

fun com.daimler.mbprotokit.dto.command.VehicleCommandStatus.map() = when (this) {
    com.daimler.mbprotokit.dto.command.VehicleCommandStatus.UNKNOWN -> VehicleCommandStatus.UNKNOWN
    com.daimler.mbprotokit.dto.command.VehicleCommandStatus.ACCEPTED -> VehicleCommandStatus.ACCEPTED
    com.daimler.mbprotokit.dto.command.VehicleCommandStatus.ENQUEUED -> VehicleCommandStatus.ENQUEUED
    com.daimler.mbprotokit.dto.command.VehicleCommandStatus.PROCESSING -> VehicleCommandStatus.PROCESSING
    com.daimler.mbprotokit.dto.command.VehicleCommandStatus.WAITING -> VehicleCommandStatus.WAITING
    com.daimler.mbprotokit.dto.command.VehicleCommandStatus.FINISHED -> VehicleCommandStatus.FINISHED
    com.daimler.mbprotokit.dto.command.VehicleCommandStatus.FAILED -> VehicleCommandStatus.FAILED
    com.daimler.mbprotokit.dto.command.VehicleCommandStatus.ABOUT_TO_SEND -> VehicleCommandStatus.ABOUT_TO_SEND
}

fun com.daimler.mbprotokit.dto.command.CommandVehicleApiError.map() = CommandVehicleApiError(
    code = code,
    message = message,
    subErrors = subErrors.map(),
    attributes = attributes
)

fun List<com.daimler.mbprotokit.dto.command.CommandVehicleApiError>.map(): List<CommandVehicleApiError> = this.map {
    it.map()
}
