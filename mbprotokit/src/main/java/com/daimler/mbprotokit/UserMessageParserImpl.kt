package com.daimler.mbprotokit

import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.SendableMessage
import com.daimler.mbprotokit.dto.user.UserDataUpdate
import com.daimler.mbprotokit.dto.user.UserPictureUpdate
import com.daimler.mbprotokit.dto.user.UserPinUpdate
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.messagehandler.handler.user.UserMessageHandlerFactory
import com.daimler.mbprotokit.send.user.UserDataUpdateAcknowledgement
import com.daimler.mbprotokit.send.user.UserPictureUpdateAcknowledgement
import com.daimler.mbprotokit.send.user.UserPinUpdateAcknowledgement

internal class UserMessageParserImpl : UserMessageParser {

    override fun parseReceivedMessage(
        socketMessage: DataSocketMessage,
        callback: ReceivedUserMessageCallback
    ): Boolean {
        if (socketMessage !is DataSocketMessage.ByteSocketMessage) {
            MBLoggerKit.e("SocketMessage is not a ByteSocketMessage.")
            return false
        }

        return try {
            val message = VehicleEvents.PushMessage.parseFrom(socketMessage.bytes)
            val handler = UserMessageHandlerFactory.handlerForMessage(message)
            handler?.handle(socketMessage, message, callback) ?: false
        } catch (e: Exception) {
            MBLoggerKit.e("Failed to handle socket message.", throwable = e)
            callback.onUserMessageError(socketMessage, e.message.orEmpty())
            false
        }
    }

    override fun parseUserUpdateAcknowledgement(update: UserDataUpdate): SendableMessage =
        UserDataUpdateAcknowledgement(update)

    override fun parsePictureUpdateAcknowledgement(update: UserPictureUpdate): SendableMessage =
        UserPictureUpdateAcknowledgement(update)

    override fun parsePinUpdateAcknowledgement(update: UserPinUpdate): SendableMessage =
        UserPinUpdateAcknowledgement(update)
}
