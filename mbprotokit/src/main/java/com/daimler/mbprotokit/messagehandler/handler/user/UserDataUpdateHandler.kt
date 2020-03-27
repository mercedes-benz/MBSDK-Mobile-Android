package com.daimler.mbprotokit.messagehandler.handler.user

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.ReceivedUserMessageCallback
import com.daimler.mbprotokit.dto.user.UserDataUpdate
import com.daimler.mbprotokit.generated.VehicleEvents

internal class UserDataUpdateHandler : UserPushMessageHandler {

    override fun handle(
        socketMessage: DataSocketMessage,
        pushMessage: VehicleEvents.PushMessage,
        callback: ReceivedUserMessageCallback
    ): Boolean {
        pushMessage.userDataUpdate?.let {
            callback.onUserDataUpdated(UserDataUpdate(it.sequenceNumber))
        } ?: callback.onUserMessageError(socketMessage, "No user update within socket message.")

        return true
    }
}
