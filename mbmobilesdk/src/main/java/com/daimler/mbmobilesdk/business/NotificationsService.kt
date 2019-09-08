package com.daimler.mbmobilesdk.business

import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

internal interface NotificationsService {

    fun registerForNotifications(
        token: String,
        deviceToken: String
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    fun unregisterFromNotifications(
        token: String
    ): FutureTask<Unit, ResponseError<out RequestError>?>
}