package com.daimler.mbprotokit.messagehandler.handler.car

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.ReceivedCarMessageCallback
import com.daimler.mbprotokit.generated.VehicleEvents

internal interface CarPushMessageHandler {
    fun handle(
        socketMessage: DataSocketMessage.ByteSocketMessage,
        pushMessage: VehicleEvents.PushMessage,
        messageCallback: ReceivedCarMessageCallback
    ): Boolean
}
