package com.daimler.mbcarkit.business.model.services

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.SendableMessage
import com.daimler.mbprotokit.generated.Client
import com.daimler.mbprotokit.generated.ServiceActivation

data class ServicesActivationAcknowledgement(
    val sequenceNumber: Int
) : SendableMessage {

    override fun parse(): DataSocketMessage {
        val acknowledgement = ServiceActivation.AcknowledgeServiceStatusUpdatesByVIN.newBuilder().setSequenceNumber(sequenceNumber)
        val message = Client.ClientMessage.newBuilder().setAcknowledgeServiceStatusUpdatesByVin(acknowledgement).build()
        return DataSocketMessage.ByteSocketMessage(System.currentTimeMillis(), message.toByteArray())
    }
}
