package com.daimler.mbprotokit.send.user

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.SendableMessage
import com.daimler.mbprotokit.dto.user.UserDataUpdate
import com.daimler.mbprotokit.generated.Client
import com.daimler.mbprotokit.generated.UserEvents

internal class UserDataUpdateAcknowledgement(
    private val update: UserDataUpdate
) : SendableMessage {

    override fun parse(): DataSocketMessage {
        val message = Client.ClientMessage.newBuilder()
            .setAcknowledgeUserDataUpdate(
                UserEvents.AcknowledgeUserDataUpdate.newBuilder()
                    .setSequenceNumber(update.sequenceNumber)
                    .build()
            ).build()
        return DataSocketMessage.ByteSocketMessage(System.currentTimeMillis(), message.toByteArray())
    }
}
