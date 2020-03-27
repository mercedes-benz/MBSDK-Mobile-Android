package com.daimler.mbprotokit.messagehandler.handler.car

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.ReceivedCarMessageCallback
import com.daimler.mbprotokit.generated.VehicleEvents

internal class AppTwinRequestHandler : CarPushMessageHandler {
    override fun handle(
        socketMessage: DataSocketMessage.ByteSocketMessage,
        pushMessage: VehicleEvents.PushMessage,
        messageCallback: ReceivedCarMessageCallback
    ): Boolean {
        pushMessage.apptwinPendingCommandRequest?.let {
            messageCallback.onPendingCommandRequest()
        } ?: messageCallback.onError(socketMessage, "Vehicleapi.AppTwinPendingCommandsRequest was null.")
        return true
    }
}
