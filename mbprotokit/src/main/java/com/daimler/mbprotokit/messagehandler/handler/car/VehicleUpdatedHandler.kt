package com.daimler.mbprotokit.messagehandler.handler.car

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.ReceivedCarMessageCallback
import com.daimler.mbprotokit.dto.car.VehiclesUpdated
import com.daimler.mbprotokit.generated.VehicleEvents

internal class VehicleUpdatedHandler : CarPushMessageHandler {
    override fun handle(
        socketMessage: DataSocketMessage.ByteSocketMessage,
        pushMessage: VehicleEvents.PushMessage,
        messageCallback: ReceivedCarMessageCallback
    ): Boolean {
        pushMessage.vehicleUpdated?.let {
            messageCallback.onVehiclesUpdate(
                VehiclesUpdated(
                    it.emitTimestampInMs,
                    it.sequenceNumber
                )
            )
        } ?: messageCallback.onError(socketMessage, "VehicleEvents.VehicleUpdated was null.")
        return true
    }
}
