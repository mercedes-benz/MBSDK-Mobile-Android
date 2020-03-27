package com.daimler.mbprotokit.messagehandler.handler.user

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.ReceivedUserMessageCallback
import com.daimler.mbprotokit.generated.VehicleEvents

internal interface UserPushMessageHandler {

    fun handle(
        socketMessage: DataSocketMessage,
        pushMessage: VehicleEvents.PushMessage,
        callback: ReceivedUserMessageCallback
    ): Boolean
}
