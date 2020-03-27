package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.SendableMessage
import com.daimler.mbprotokit.generated.Client
import com.daimler.mbprotokit.generated.Vehicleapi

class PendingCommandRequestAcknowledgement(private val pendingCommands: List<PendingCommand>) : SendableMessage {

    override fun parse(): DataSocketMessage {
        val clientMessage = Client.ClientMessage.newBuilder()
            .setApptwinPendingCommandsResponse(
                Vehicleapi.AppTwinPendingCommandsResponse.newBuilder().apply {
                    pendingCommands.forEach { pendingCommand ->
                        addPendingCommands(
                            Vehicleapi.PendingCommand.newBuilder().apply {
                                pendingCommand.pID.toLongOrNull()?.let { setProcessId(it) }
                                requestId = pendingCommand.requestId
                                vin = pendingCommand.finOrVin
                                typeValue = pendingCommand.type
                            }.build()
                        )
                    }
                }.build()
            ).build()
        return DataSocketMessage.ByteSocketMessage(System.currentTimeMillis(), clientMessage.toByteArray())
    }
}
