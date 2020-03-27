package com.daimler.mbprotokit.messagehandler.handler.car

import com.daimler.mbprotokit.generated.VehicleEvents

internal object CarMessageHandlerFactory {

    fun handlerForMessage(protoMessage: VehicleEvents.PushMessage): CarPushMessageHandler? =
        when (protoMessage.msgCase) {
            VehicleEvents.PushMessage.MsgCase.DEBUGMESSAGE -> DebugHandler()
            VehicleEvents.PushMessage.MsgCase.VEPUPDATES -> VepUpdatesHandler()
            VehicleEvents.PushMessage.MsgCase.APPTWIN_COMMAND_STATUS_UPDATES_BY_VIN -> AppTwinUpdateHandler()
            VehicleEvents.PushMessage.MsgCase.VEHICLE_UPDATED -> VehicleUpdatedHandler()
            VehicleEvents.PushMessage.MsgCase.USER_VEHICLE_AUTH_CHANGED_UPDATE -> VehicleAuthHandler()
            VehicleEvents.PushMessage.MsgCase.APPTWIN_PENDING_COMMAND_REQUEST -> AppTwinRequestHandler()
            else -> null
        }
}
