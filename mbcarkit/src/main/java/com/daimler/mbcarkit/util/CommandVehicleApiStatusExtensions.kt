package com.daimler.mbcarkit.util

import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatus
import com.daimler.mbcarkit.business.model.command.GenericCommandError
import com.daimler.mbcarkit.business.model.command.GenericVehicleCommandError
import com.daimler.mbcarkit.business.model.command.InternalVehicleCommandError
import com.daimler.mbcarkit.business.model.command.VehicleCommand

/**
 * Searches for a [com.daimler.mbcarkit.business.model.command.GenericCommandError] of type [T] within
 * this [CommandVehicleApiStatus] and returns the first one found.
 * Returns null if none could be found.
 */
internal inline fun <reified T : GenericCommandError> CommandVehicleApiStatus.findGenericError(command: VehicleCommand<*>): T? =
    errors.asSequence()
        .map { command.convertToSpecificError(it) }
        .filterIsInstance<GenericVehicleCommandError>()
        .find { it.genericError is T }?.genericError as? T

internal fun CommandVehicleApiStatus.containsUserBlockedError() =
    errors.any { InternalVehicleCommandError.UserBlocked.errorCodes.contains(it.code) }

internal fun CommandVehicleApiStatus.containsPinInvalidError() =
    errors.any { InternalVehicleCommandError.PinInvalid.errorCodes.contains(it.code) }
