package com.daimler.mbcarkit.socket

import com.daimler.mbcarkit.business.model.services.ServiceActivationStatusUpdates
import com.daimler.mbnetworkkit.socket.message.DataSocketMessage

interface ReceivedServiceMessageCallback {

    fun onServiceStatusUpdate(statusUpdates: ServiceActivationStatusUpdates)

    fun onError(socketMessage: DataSocketMessage, cause: String)
}
