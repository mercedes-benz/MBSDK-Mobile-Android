package com.daimler.mbmobilesdk.settings

import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

internal interface PushSettingsHandler {

    fun enablePushNotifications(
        token: String,
        fcmToken: String
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    fun disablePushNotifications(
        token: String
    ): FutureTask<Unit, ResponseError<out RequestError>?>
}