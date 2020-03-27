package com.daimler.mbprotokit

import android.util.Log
import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.messagehandler.handler.car.CarMessageHandlerFactory
import com.google.protobuf.InvalidProtocolBufferException

class CarKitMessageParser : CarMessageParser {

    override fun parseReceivedMessage(
        socketMessage: DataSocketMessage,
        messageCallback: ReceivedCarMessageCallback
    ): Boolean {
        if (socketMessage !is DataSocketMessage.ByteSocketMessage) {
            Log.d("CarKitMessageParser", "socketMessage is not a ByteSocketMessage")
            return false
        }

        return try {
            val pushMessage = VehicleEvents.PushMessage.parseFrom(socketMessage.bytes)
            val pushMessageHandler = CarMessageHandlerFactory.handlerForMessage(pushMessage)
            pushMessageHandler?.handle(socketMessage, pushMessage, messageCallback) ?: false
        } catch (e: InvalidProtocolBufferException) {
            messageCallback.onError(socketMessage, e.message ?: "UNKNOWN")
            Log.e("CarKitMessageParser", "InvalidProtocolBufferException - ${e.message}")
            false
        }
    }
}
