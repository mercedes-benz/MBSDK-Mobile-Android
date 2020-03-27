package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.SendableMessage
import com.daimler.mbprotokit.generated.Client
import com.daimler.mbprotokit.generated.VehicleEvents

class VehicleStatusByVinAcknowledgement(private val sequenceNumber: Int) : SendableMessage {

    override fun parse(): DataSocketMessage {
        val clientMessage = Client.ClientMessage.newBuilder()
            .setAcknowledgeVepUpdatesByVin(
                VehicleEvents.AcknowledgeVEPUpdatesByVIN.newBuilder()
                    .setSequenceNumber(sequenceNumber)
            )
            .build()
        return DataSocketMessage.ByteSocketMessage(System.currentTimeMillis(), clientMessage.toByteArray())
    }
}
