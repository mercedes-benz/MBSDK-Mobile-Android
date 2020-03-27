package com.daimler.mbprotokit

import android.util.Log
import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.messagehandler.handler.service.ServiceMessageHandlerFactory
import com.google.protobuf.InvalidProtocolBufferException

class ServiceKitMessageParser : ServiceMessageParser {
    override fun parseReceivedMessage(
        socketMessage: DataSocketMessage,
        callback: ReceivedServiceMessageCallback
    ): Boolean {
        if (socketMessage !is DataSocketMessage.ByteSocketMessage) {
            Log.d("ServiceProtoMessage", "socketMessage is not a ByteSocketMessage")
            return false
        }

        return try {
            val pushMessage = VehicleEvents.PushMessage.parseFrom(socketMessage.bytes)
            val pushMessageHandler = ServiceMessageHandlerFactory.handlerForMessage(pushMessage)
            pushMessageHandler?.handle(socketMessage, pushMessage, callback) ?: false
        } catch (e: InvalidProtocolBufferException) {
            callback.onError(socketMessage, e.message ?: "UNKNOWN")
            Log.e("ServiceKitMessageParser", "InvalidProtocolBufferException - ${e.message}")
            false
        }
    }
}
