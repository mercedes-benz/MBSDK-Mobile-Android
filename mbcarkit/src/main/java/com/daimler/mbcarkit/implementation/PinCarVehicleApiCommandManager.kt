package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.CarVehicleApiCommandManager
import com.daimler.mbcarkit.business.PinProvider
import com.daimler.mbcarkit.business.PinRequest
import com.daimler.mbcarkit.business.model.command.CarVehicleApiCommand
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiService
import com.daimler.mbcarkit.business.model.command.GenericCommandError
import com.daimler.mbcarkit.business.model.command.VehicleCommand
import com.daimler.mbcarkit.business.model.command.VehicleCommandCallback
import com.daimler.mbcarkit.business.model.command.VehicleCommandError
import com.daimler.mbcarkit.implementation.exceptions.MissingPinException
import com.daimler.mbcarkit.socket.CommandParsingError
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.socket.ConnectionState
import com.daimler.mbnetworkkit.socket.SocketService

class PinCarVehicleApiCommandManager(
    private val commandService: CommandVehicleApiService,
    private val pinProvider: PinProvider?
) : CarVehicleApiCommandManager {

    override fun <T : VehicleCommandError> sendCommand(commandRequest: VehicleCommand<T>, commandCallback: VehicleCommandCallback<T>) {
        MBLoggerKit.d("Send CarCommand called: ${commandRequest::class.java.simpleName} -> requestId=${commandRequest.id}, vin=${commandRequest.vin}")
        requestPinIfRequiredAndSendCommand(CarVehicleApiCommand(commandRequest), commandCallback)
    }

    private fun <T : VehicleCommandError> requestPinIfRequiredAndSendCommand(command: CarVehicleApiCommand<T>, commandCallback: VehicleCommandCallback<T>) {
        if (command.requiresPin()) {
            requestPin(command, commandCallback)
        } else {
            sendCommandIfConnected(command, commandCallback)
        }
    }

    private fun <T : VehicleCommandError> sendCommandIfConnected(command: CarVehicleApiCommand<T>, commandCallback: VehicleCommandCallback<T>) {
        commandService.commandRequestCreated(command, commandCallback)
        if (SocketService.connectionState() is ConnectionState.Connected) {
            parseAndSendCommand(command)
        } else {
            failCommand(command, GenericCommandError.NoInternetConnection("NO_INTERNET_CONNECTION"))
        }
    }

    private fun <T : VehicleCommandError> requestPin(carCommand: CarVehicleApiCommand<T>, commandCallback: VehicleCommandCallback<T>) {
        val pinProvider = carCommand.pinProvider() ?: this.pinProvider
        pinProvider?.requestPin(
            object : PinRequest {
                override fun confirmPin(pin: String) {
                    carCommand.clearPinProvider()
                    carCommand.pin = pin
                    sendCommandIfConnected(carCommand, commandCallback)
                }

                override fun cancel(cause: String?) {
                    carCommand.clearPinProvider()
                    failCommandCallback(carCommand, commandCallback, GenericCommandError.PinInputCancelled("PIN_INPUT_CANCELLED"))
                }
            }
        ) ?: failCommandCallback(carCommand, commandCallback, GenericCommandError.PinProviderMissing("PIN_PROVIDER_MISSING"))
    }

    private fun parseAndSendCommand(command: CarVehicleApiCommand<*>) {
        try {
            commandAboutToSend(command)
            if (SocketService.sendMessage(command)) {
                MBLoggerKit.d("CarCommand successfully send on socket")
            } else {
                val error = "Failed to send comment on Socket"
                failCommand(command, GenericCommandError.CouldNotSendCommand("RIS_COULD_NOT_SEND_COMMAND"))
            }
        } catch (e: MissingPinException) {
            failCommand(command, GenericCommandError.PinRequired("RIS_PIN_REQUIRED"))
        } catch (e: CommandParsingError) {
            failCommand(command, GenericCommandError.UnknownError("RIS_UNKNOWN_ERROR", e.toString()))
        }
    }

    private fun commandAboutToSend(command: CarVehicleApiCommand<*>) {
        commandService.commandAboutToSend(command)
    }

    private fun failCommand(carCommand: CarVehicleApiCommand<*>, cause: GenericCommandError) {
        MBLoggerKit.e("Execution of a command failed: " + cause.toString())
        commandService.onSendCommandFailed(carCommand, cause)
    }

    private fun <T : VehicleCommandError> failCommandCallback(carCommand: CarVehicleApiCommand<T>, commandCallback: VehicleCommandCallback<T>, cause: GenericCommandError) {
        MBLoggerKit.e("Creating of command failed: " + cause.toString())
        commandCallback.onError(null, listOf(carCommand.createGenericError(cause)))
    }
}
