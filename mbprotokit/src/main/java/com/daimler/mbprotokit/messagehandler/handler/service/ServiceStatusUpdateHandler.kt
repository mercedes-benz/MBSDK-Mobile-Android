package com.daimler.mbprotokit.messagehandler.handler.service

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.ReceivedServiceMessageCallback
import com.daimler.mbprotokit.dto.service.ServiceActivationStatusUpdates
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.service.map

internal class ServiceStatusUpdateHandler : ServicePushMessageHandler {
    override fun handle(
        socketMessage: DataSocketMessage.ByteSocketMessage,
        pushMessage: VehicleEvents.PushMessage,
        messageCallback: ReceivedServiceMessageCallback
    ): Boolean {
        pushMessage.serviceStatusUpdate?.let {
            messageCallback.onServiceStatusUpdate(
                ServiceActivationStatusUpdates(
                    updatesByVin = mapOf(it.vin to it.map()),
                    sequenceNumber = it.sequenceNumber
                )
            )
        } ?: messageCallback.onError(socketMessage, "ServiceActivation.ServiceStatusUpdate is null.")
        return true
    }
}
