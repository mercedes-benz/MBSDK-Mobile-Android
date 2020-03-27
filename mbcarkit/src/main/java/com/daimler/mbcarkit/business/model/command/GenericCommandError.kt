// THIS FILE IS GENERATED! DO NOT EDIT!
// The generator can be found at https://git.daimler.com/RisingStars/commons-go-lib/tree/master/gen
package com.daimler.mbcarkit.business.model.command

import com.google.protobuf.Value

/*
 * Errors that can happen during the execution of every command.
 */
sealed class GenericCommandError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    /**
     * Command failed. Normally, there should be additional business errors detailing what exactly went wrong
     */
    class CommandFailed(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.CommandFailed)

    /**
     * The command is not available for the specified vehicle
     */
    class CommandUnavailable(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.CommandUnavailable)

    /**
     * a general error and the `message` field of the VehicleAPIError struct should be checked for more information
     */
    class CouldNotSendCommand(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.CouldNotSendCommand)

    /**
     * returned if the service(s) for the requested command are not active
     * - serviceId: The id of the service which needs to be activated.
     */
    class InactiveServices(rawErrorCode: String, val serviceId: Int) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.InactiveServices)

    /**
     * Received an invalid condition
     */
    class InvalidCondition(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.InvalidCondition)

    /**
     * Should never happen due to migration guide
     */
    class InvalidStatus(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.InvalidStatus)

    /**
     * The client does not have an internet connection and therefore the command could not be sent.
     */
    class NoInternetConnection(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.NoInternetConnection)

    /**
     * No vehicle was selected
     */
    class NoVehicleSelected(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.NoVehicleSelected)

    /**
     * Command was overwritten in queue
     */
    class Overwritten(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.Overwritten)

    /**
     * The user cancelled the PIN input. The command is therefore not transmitted and not executed.
     */
    class PinInputCancelled(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.PinInputCancelled)

    /**
     * returned if the given PIN does not match the one saved in CPD
     * - attempt: Count of the attempt to enter the valid user PIN. Will be set to zero if the user has provided a valid PIN.
     */
    class PinInvalid(rawErrorCode: String, val attempt: Int) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.PinInvalid)

    /**
     * The pin provider was not configured, but a PIN is needed for this command.
     */
    class PinProviderMissing(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.PinProviderMissing)

    /**
     * returned if the user tried to send a sensitive command that requires a PIN but didn't provide one
     */
    class PinRequired(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.PinRequired)

    /**
     * Command was rejected due to a blocked command queue. This can happen if another user is executing a similar command.
     */
    class RejectedByQueue(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.RejectedByQueue)

    /**
     * Command was forcefully terminated
     */
    class Terminated(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.Terminated)

    /**
     * Failed due to timeout
     */
    class Timeout(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.Timeout)

    /**
     * returned if an unknown error has occurred. Should never happen, so let us know if you see this error
     * - message: A message which might have more details
     */
    class UnknownError(rawErrorCode: String, val message: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.UnknownError)

    /**
     * The status of the command is unknown. returned if the state of a given command could not be polled. When polling for the state of a command only the last running or currently running command status is returned. If the app is interested in the status of a previous command for any reason and the state cannot be determined this error is returned
     */
    class UnknownStatus(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.UnknownStatus)

    /**
     * is returned if there was an error in polling the command state. E.g. 4xx/5xx response codes from the vehicleAPI
     */
    class UnknownStatusDueToPollError(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.UnknownStatusDueToPollError)

    /**
     * returned if the command request contains a command type that is not yet supported by the AppTwin
     */
    class UnsupportedCommand(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.UnsupportedCommand)

    /**
     * command is not supported by the currently selected environment
     */
    class UnsupportedStage(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.UnsupportedStage)

    /**
     * returned if the CIAM ID is currently blocked from sending sensitive commands e.g. Doors Unlock due to too many PIN attempts
     * - attempt: Count of the attempt to enter the valid user PIN. Will be set to zero if the user has provided a valid PIN.
     * - blockedUntil: Unix timestamp in seconds indicating the moment in time from when the user is allowed to try another PIN.
     */
    class UserBlocked(rawErrorCode: String, val attempt: Int, val blockedUntil: Int) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.UserBlocked)

    /**
     * Returned if the input parameters of the command did not pass validation. The payload should indicate what went wrong
     * - fields: A map from fields that did not pass validation
     */
    class ValidationFailed(rawErrorCode: String, val fields: Map<String, String>) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.ValidationFailed)

    /**
     * returned if a command request is received for a VIN that is not assigned to the ciam id of the current user
     */
    class VehicleNotAssigned(rawErrorCode: String) : GenericCommandError(rawErrorCode, InternalVehicleCommandError.VehicleNotAssigned)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): GenericCommandError {
            return when (code) {
                "CMD_FAILED" -> CommandFailed(code)
                "CMD_INVALID_CONDITION" -> InvalidCondition(code)
                "CMD_INVALID_STATUS" -> InvalidStatus(code)
                "CMD_OVERWRITTEN" -> Overwritten(code)
                "CMD_REJECTED_BY_QUEUE" -> RejectedByQueue(code)
                "CMD_TERMINATED" -> Terminated(code)
                "CMD_TIMEOUT" -> Timeout(code)
                "COMMAND_UNAVAILABLE" -> CommandUnavailable(code)
                "NO_INTERNET_CONNECTION" -> NoInternetConnection(code)
                "NO_VEHICLE_SELECTED" -> NoVehicleSelected(code)
                "PIN_INPUT_CANCELLED" -> PinInputCancelled(code)
                "PIN_PROVIDER_MISSING" -> PinProviderMissing(code)
                "RIS_CIAM_ID_BLOCKED" -> UserBlocked(code, attempt = attributes["attempt"]?.numberValue?.toInt() ?: 0, blockedUntil = attributes["blocked_until"]?.numberValue?.toInt() ?: 0)
                "RIS_COULD_NOT_SEND_COMMAND" -> CouldNotSendCommand(code)
                "RIS_EMPTY_VEHICLE_API_QUEUE" -> UnknownStatus(code)
                "RIS_FORBIDDEN_VIN" -> VehicleNotAssigned(code)
                "RIS_INACTIVE_SERVICES" -> InactiveServices(code, serviceId = attributes["serviceId"]?.numberValue?.toInt() ?: 0)
                "RIS_PIN_INVALID" -> PinInvalid(code, attempt = attributes["attempt"]?.numberValue?.toInt() ?: 0)
                "RIS_PIN_REQUIRED" -> PinRequired(code)
                "RIS_UNKNOWN_ERROR" -> UnknownError(code, message = attributes["message"]?.stringValue ?: "")
                "RIS_UNSUPPORTED_COMMAND" -> UnsupportedCommand(code)
                "RIS_UNSUPPORTED_ENVIRONMENT" -> UnsupportedStage(code)
                "RIS_VALIDATION_FAILED" -> ValidationFailed(code, fields = attributes.mapValues { it.toString() })
                "RIS_VEHICLE_API_POLLING" -> UnknownStatusDueToPollError(code)
                else -> UnknownError(code, "Unknown error code \"${code}\"")
            }
        }
    }
}
