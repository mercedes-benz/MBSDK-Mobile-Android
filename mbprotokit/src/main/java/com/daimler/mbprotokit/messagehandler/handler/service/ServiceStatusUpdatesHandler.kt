package com.daimler.mbprotokit.messagehandler.handler.service

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.ReceivedServiceMessageCallback
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.service.map

internal class ServiceStatusUpdatesHandler : ServicePushMessageHandler {
    override fun handle(
        socketMessage: DataSocketMessage.ByteSocketMessage,
        pushMessage: VehicleEvents.PushMessage,
        messageCallback: ReceivedServiceMessageCallback
    ): Boolean {
        pushMessage.serviceStatusUpdates?.let {
            messageCallback.onServiceStatusUpdate(it.map())
        } ?: messageCallback.onError(socketMessage, "ServiceActivation.ServiceStatusUpdatesByVIN is null.")
        return true
    }
}
