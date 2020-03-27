package com.daimler.mbcarkit.proto.tmp.service

import com.daimler.mbcarkit.socket.ReceivedServiceMessageCallback
import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.dto.service.ServiceActivationStatusUpdates

class ReceivedServiceMessageCallbackMapper(
    private val receivedServiceMessageCallback: ReceivedServiceMessageCallback
) : com.daimler.mbprotokit.ReceivedServiceMessageCallback {
    override fun onError(socketMessage: DataSocketMessage, cause: String) {
        receivedServiceMessageCallback.onError(socketMessage, cause)
    }

    override fun onServiceStatusUpdate(statusUpdates: ServiceActivationStatusUpdates) {
        receivedServiceMessageCallback.onServiceStatusUpdate(statusUpdates.map())
    }
}
