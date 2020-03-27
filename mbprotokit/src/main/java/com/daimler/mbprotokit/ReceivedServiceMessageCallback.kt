package com.daimler.mbprotokit

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.dto.service.ServiceActivationStatusUpdates

interface ReceivedServiceMessageCallback {

    fun onServiceStatusUpdate(statusUpdates: ServiceActivationStatusUpdates)

    fun onError(socketMessage: DataSocketMessage, cause: String)
}
