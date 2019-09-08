package com.daimler.mbmobilesdk.settings

import com.daimler.mbmobilesdk.business.NotificationsService
import com.daimler.mbmobilesdk.preferences.PushSettings
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

internal class PushSettingsHandlerImpl(
    private val notificationsService: NotificationsService,
    private val pushSettings: PushSettings
) : PushSettingsHandler {

    override fun enablePushNotifications(
        token: String,
        fcmToken: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        return registerForPushes(token, fcmToken)
    }

    override fun disablePushNotifications(
        token: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        return unregisterFromPushes(token)
    }

    private fun registerForPushes(jwtToken: String, fcmToken: String): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = TaskObject<Unit, ResponseError<out RequestError>?>()
        notificationsService.registerForNotifications(
            jwtToken, fcmToken
        ).onComplete {
            MBLoggerKit.d("Registered for push notifications.")
            setPushState(true)
            task.complete(it)
        }.onFailure {
            MBLoggerKit.re("Registering for push notifications failed.", it)
            setPushState(false)
            task.fail(it)
        }
        return task.futureTask()
    }

    private fun unregisterFromPushes(jwtToken: String): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = TaskObject<Unit, ResponseError<out RequestError>?>()
        notificationsService.unregisterFromNotifications(jwtToken)
            .onAlways { _, _, _ ->
                setPushState(false)
                task.complete(Unit)
            }
        return task.futureTask()
    }

    private fun setPushState(enabled: Boolean) {
        pushSettings.pushEnabled.set(enabled)
    }
}