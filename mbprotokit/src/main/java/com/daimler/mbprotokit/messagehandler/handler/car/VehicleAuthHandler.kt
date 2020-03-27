package com.daimler.mbprotokit.messagehandler.handler.car

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.ReceivedCarMessageCallback
import com.daimler.mbprotokit.dto.car.VehicleAuthUpdate
import com.daimler.mbprotokit.generated.VehicleEvents

internal class VehicleAuthHandler : CarPushMessageHandler {
    override fun handle(
        socketMessage: DataSocketMessage.ByteSocketMessage,
        pushMessage: VehicleEvents.PushMessage,
        messageCallback: ReceivedCarMessageCallback
    ): Boolean {
        pushMessage.userVehicleAuthChangedUpdate?.let {
            messageCallback.onVehicleAuthUpdate(
                VehicleAuthUpdate(
                    it.emitTimestampInMs,
                    it.sequenceNumber
                )
            )
        } ?: messageCallback.onError(
            socketMessage,
            "UserEvents.UserVehicleAuthChangedUpdate was null."
        )
        return true
    }
}
