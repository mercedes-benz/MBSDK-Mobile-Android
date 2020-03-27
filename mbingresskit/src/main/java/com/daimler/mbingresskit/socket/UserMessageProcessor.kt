package com.daimler.mbingresskit.socket

import com.daimler.mbingresskit.socket.observable.toProfileDataUpdate
import com.daimler.mbingresskit.socket.observable.toProfilePictureUpdate
import com.daimler.mbingresskit.socket.observable.toProfilePinUpdate
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.socket.message.BaseChainableMessageProcessor
import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.MessageProcessor
import com.daimler.mbnetworkkit.socket.message.Notifyable
import com.daimler.mbnetworkkit.socket.message.SendMessageService
import com.daimler.mbnetworkkit.socket.message.SendableMessage
import com.daimler.mbnetworkkit.socket.message.notifyChange
import com.daimler.mbprotokit.ReceivedUserMessageCallback
import com.daimler.mbprotokit.UserMessageParser
import com.daimler.mbprotokit.dto.user.UserDataUpdate
import com.daimler.mbprotokit.dto.user.UserPictureUpdate
import com.daimler.mbprotokit.dto.user.UserPinUpdate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class UserMessageProcessor(
    private val messageParser: UserMessageParser,
    nextProcessor: MessageProcessor? = null,
    private val scope: CoroutineScope = SocketProcessingCoroutineScope()
) : BaseChainableMessageProcessor(nextProcessor), ReceivedUserMessageCallback {

    private var notifyable: Notifyable? = null
    private var sendService: SendMessageService? = null

    override fun doHandleReceivedMessage(
        notifyable: Notifyable,
        sendService: SendMessageService,
        dataSocketMessage: DataSocketMessage
    ): Boolean {
        this.notifyable = notifyable
        this.sendService = sendService
        return messageParser.parseReceivedMessage(dataSocketMessage, this)
    }

    override fun onUserDataUpdated(update: UserDataUpdate) {
        scope.launch {
            sendAcknowledgement(messageParser.parseUserUpdateAcknowledgement(update))

            notifyable?.notifyChange(update.toProfileDataUpdate())
        }
    }

    override fun onUserPictureUpdated(update: UserPictureUpdate) {
        scope.launch {
            sendAcknowledgement(messageParser.parsePictureUpdateAcknowledgement(update))

            notifyable?.notifyChange(update.toProfilePictureUpdate())
        }
    }

    override fun onUserPinUpdated(update: UserPinUpdate) {
        scope.launch {
            sendAcknowledgement(messageParser.parsePinUpdateAcknowledgement(update))

            notifyable?.notifyChange(update.toProfilePinUpdate())
        }
    }

    override fun onUserMessageError(socketMessage: DataSocketMessage, cause: String) {
        MBLoggerKit.e("Error while parsing received User message: $cause")
    }

    private fun sendAcknowledgement(message: SendableMessage) {
        sendService?.let {
            if (it.sendMessage(message)) {
                MBLoggerKit.d("ACK - Sent acknowledgement: $message")
            } else {
                MBLoggerKit.e("ACK - Failed to send acknowledgement: $message")
            }
        } ?: MBLoggerKit.e("ACK - Could not send acknowledgement since no SendService is available.")
    }
}
