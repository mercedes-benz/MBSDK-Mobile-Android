package com.daimler.mbmobilesdk.implementation

import com.daimler.mbmobilesdk.business.NotificationsService
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

internal class NotificationsServiceProxy(
    private val service: NotificationsService
) : NotificationsService {

    override fun registerForNotifications(
        token: String,
        deviceToken: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        return service.registerForNotifications(token, deviceToken)
    }

    override fun unregisterFromNotifications(
        token: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        return service.unregisterFromNotifications(token)
    }
}