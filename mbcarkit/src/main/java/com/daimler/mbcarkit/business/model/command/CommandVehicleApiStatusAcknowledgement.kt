package com.daimler.mbcarkit.business.model.command

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.SendableMessage
import com.daimler.mbprotokit.generated.Client
import com.daimler.mbprotokit.generated.Vehicleapi

data class CommandVehicleApiStatusAcknowledgement(val sequenceNumber: Int) : SendableMessage {
    override fun parse(): DataSocketMessage {
        val clientMessage = Client.ClientMessage.newBuilder()
            .setAcknowledgeApptwinCommandStatusUpdateByVin(
                Vehicleapi.AcknowledgeAppTwinCommandStatusUpdatesByVIN.newBuilder()
                    .setSequenceNumber(sequenceNumber)
            )
            .build()
        return DataSocketMessage.ByteSocketMessage(System.currentTimeMillis(), clientMessage.toByteArray())
    }
}
