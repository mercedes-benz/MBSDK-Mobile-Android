package com.daimler.mbcarkit.business.model.command

/**
 * Marker interface for errors occuring while executing VehicleCommands.
 */
abstract class VehicleCommandError(val rawErrorCode: String) {
    abstract fun getErrorCode(): InternalVehicleCommandError
}
