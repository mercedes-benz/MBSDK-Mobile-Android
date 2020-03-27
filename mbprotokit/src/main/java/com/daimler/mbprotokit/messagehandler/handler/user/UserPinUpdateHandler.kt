package com.daimler.mbprotokit.messagehandler.handler.user

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.ReceivedUserMessageCallback
import com.daimler.mbprotokit.dto.user.UserPinUpdate
import com.daimler.mbprotokit.generated.VehicleEvents

internal class UserPinUpdateHandler : UserPushMessageHandler {

    override fun handle(
        socketMessage: DataSocketMessage,
        pushMessage: VehicleEvents.PushMessage,
        callback: ReceivedUserMessageCallback
    ): Boolean {
        pushMessage.userPinUpdate?.let {
            callback.onUserPinUpdated(UserPinUpdate(it.sequenceNumber))
        } ?: callback.onUserMessageError(socketMessage, "No pin update within socket message.")

        return true
    }
}
