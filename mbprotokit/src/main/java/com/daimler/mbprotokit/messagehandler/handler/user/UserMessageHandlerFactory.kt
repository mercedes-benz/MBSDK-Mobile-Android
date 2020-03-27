package com.daimler.mbprotokit.messagehandler.handler.user

import com.daimler.mbprotokit.generated.VehicleEvents

internal object UserMessageHandlerFactory {

    fun handlerForMessage(protoMessage: VehicleEvents.PushMessage): UserPushMessageHandler? =
        when (protoMessage.msgCase) {
            VehicleEvents.PushMessage.MsgCase.USER_DATA_UPDATE -> UserDataUpdateHandler()
            VehicleEvents.PushMessage.MsgCase.USER_PICTURE_UPDATE -> UserPictureUpdateHandler()
            VehicleEvents.PushMessage.MsgCase.USER_PIN_UPDATE -> UserPinUpdateHandler()
            else -> null
        }
}
