package com.daimler.mbprotokit.messagehandler.handler.service

import com.daimler.mbprotokit.generated.VehicleEvents

internal object ServiceMessageHandlerFactory {

    fun handlerForMessage(protoMessage: VehicleEvents.PushMessage): ServicePushMessageHandler? = when (protoMessage.msgCase) {
        VehicleEvents.PushMessage.MsgCase.SERVICE_STATUS_UPDATES -> ServiceStatusUpdatesHandler()
        VehicleEvents.PushMessage.MsgCase.SERVICE_STATUS_UPDATE -> ServiceStatusUpdateHandler()
        else -> null
    }
}
