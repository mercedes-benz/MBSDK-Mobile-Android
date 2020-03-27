package com.daimler.mbprotokit

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.SendableMessage
import com.daimler.mbprotokit.dto.user.UserDataUpdate
import com.daimler.mbprotokit.dto.user.UserPictureUpdate
import com.daimler.mbprotokit.dto.user.UserPinUpdate

interface UserMessageParser {

    fun parseReceivedMessage(
        socketMessage: DataSocketMessage,
        callback: ReceivedUserMessageCallback
    ): Boolean

    fun parseUserUpdateAcknowledgement(update: UserDataUpdate): SendableMessage

    fun parsePictureUpdateAcknowledgement(update: UserPictureUpdate): SendableMessage

    fun parsePinUpdateAcknowledgement(update: UserPinUpdate): SendableMessage
}
