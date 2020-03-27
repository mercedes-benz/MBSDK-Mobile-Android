package com.daimler.mbprotokit.messagehandler.handler.car

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.ReceivedCarMessageCallback
import com.daimler.mbprotokit.dto.car.DebugMessage
import com.daimler.mbprotokit.generated.VehicleEvents

internal class DebugHandler : CarPushMessageHandler {
    override fun handle(
        socketMessage: DataSocketMessage.ByteSocketMessage,
        pushMessage: VehicleEvents.PushMessage,
        messageCallback: ReceivedCarMessageCallback
    ): Boolean {
        messageCallback.onDebugMessageReceived(
            DebugMessage(
                socketMessage.timestamp,
                pushMessage.debugMessage?.message ?: "-"
            )
        )
        return true
    }
}
