package com.daimler.mbprotokit.messagehandler.handler.service

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.ReceivedServiceMessageCallback
import com.daimler.mbprotokit.generated.VehicleEvents

internal interface ServicePushMessageHandler {
    fun handle(
        socketMessage: DataSocketMessage.ByteSocketMessage,
        pushMessage: VehicleEvents.PushMessage,
        messageCallback: ReceivedServiceMessageCallback
    ): Boolean
}
