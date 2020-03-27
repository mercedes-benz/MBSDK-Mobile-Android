package com.daimler.mbprotokit.send.user

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.SendableMessage
import com.daimler.mbprotokit.dto.user.UserPinUpdate
import com.daimler.mbprotokit.generated.Client
import com.daimler.mbprotokit.generated.UserEvents

internal class UserPinUpdateAcknowledgement(
    private val update: UserPinUpdate
) : SendableMessage {

    override fun parse(): DataSocketMessage {
        val message = Client.ClientMessage.newBuilder()
            .setAcknowledgeUserPinUpdate(
                UserEvents.AcknowledgeUserPINUpdate.newBuilder()
                    .setSequenceNumber(update.sequenceNumber)
                    .build()
            ).build()
        return DataSocketMessage.ByteSocketMessage(System.currentTimeMillis(), message.toByteArray())
    }
}
