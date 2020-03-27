package com.daimler.mbprotokit.messagehandler.handler.car

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.ReceivedCarMessageCallback
import com.daimler.mbprotokit.dto.command.CommandVehicleApiError
import com.daimler.mbprotokit.dto.command.CommandVehicleApiStatus
import com.daimler.mbprotokit.dto.command.CommandVehicleApiStatusUpdate
import com.daimler.mbprotokit.dto.command.CommandVehicleApiStatusUpdates
import com.daimler.mbprotokit.dto.command.VehicleCommandStatus
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.generated.Vehicleapi

internal class AppTwinUpdateHandler : CarPushMessageHandler {
    override fun handle(
        socketMessage: DataSocketMessage.ByteSocketMessage,
        pushMessage: VehicleEvents.PushMessage,
        messageCallback: ReceivedCarMessageCallback
    ): Boolean {
        pushMessage.apptwinCommandStatusUpdatesByVin?.let {
            messageCallback.onCommandVehicleApiStatusUpdates(
                CommandVehicleApiStatusUpdates(
                    it.updatesByVinMap.map { entry ->
                        entry.key to commandApiVehicleStatusFromCommandStatusUpdate(entry.value, it.sequenceNumber)
                    }.toMap()
                )
            )
        } ?: messageCallback.onError(socketMessage, "VehicleEvents.AppTwinCommandStatusUpdatesByVIN was null")
        return true
    }

    private fun commandApiVehicleStatusFromCommandStatusUpdate(
        commandUpdates: Vehicleapi.AppTwinCommandStatusUpdatesByPID,
        sequenceNumber: Int
    ): CommandVehicleApiStatusUpdate {
        val commandStatusList = commandUpdates.updatesByPidMap?.values?.map {
            it.map()
        } ?: emptyList()
        return CommandVehicleApiStatusUpdate(commandStatusList, sequenceNumber, commandUpdates.vin)
    }

    private fun Vehicleapi.AppTwinCommandStatus.map(): CommandVehicleApiStatus = CommandVehicleApiStatus(
        errorsList.map {
            mapCommandVehicleApiErrorRecursive(it)
        },
        processId.toString(),
        VehicleCommandStatus.map(stateValue),
        requestId,
        timestampInMs,
        typeValue
    )

    private fun mapCommandVehicleApiErrorRecursive(commandError: Vehicleapi.VehicleAPIError): CommandVehicleApiError = CommandVehicleApiError(
        commandError.code,
        commandError.message,
        commandError.subErrorsList.map {
            mapCommandVehicleApiErrorRecursive(it)
        },
        commandError.attributesMap
    )
}
