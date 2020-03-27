package com.daimler.mbprotokit.messagehandler.handler.user

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.ReceivedUserMessageCallback
import com.daimler.mbprotokit.dto.user.UserPictureUpdate
import com.daimler.mbprotokit.generated.VehicleEvents

internal class UserPictureUpdateHandler : UserPushMessageHandler {

    override fun handle(
        socketMessage: DataSocketMessage,
        pushMessage: VehicleEvents.PushMessage,
        callback: ReceivedUserMessageCallback
    ): Boolean {
        pushMessage.userPictureUpdate?.let {
            callback.onUserPictureUpdated(UserPictureUpdate(it.sequenceNumber))
        } ?: callback.onUserMessageError(socketMessage, "No picture update within socket message.")

        return true
    }
}
