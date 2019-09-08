package com.daimler.mbmobilesdk.example

import com.daimler.mbmobilesdk.push.MBMobileSDKMessagingService
import com.daimler.mbloggerkit.MBLoggerKit
import com.google.firebase.messaging.RemoteMessage

class SampleMessagingService : MBMobileSDKMessagingService() {

    override fun onTokenReceived(token: String) {
        super.onTokenReceived(token)
        MBLoggerKit.d("onTokenReceived()")
    }

    override fun onHandleNotification(message: RemoteMessage) {
        super.onHandleNotification(message)
        MBLoggerKit.d("onHandleNotification()")
        message.notification?.let {
            MBLoggerKit.d("Notification title = ${it.title}.")
            MBLoggerKit.d("Notification body = ${it.body}.")
        }
        message.data?.let { data ->
            data.forEach { "${it.key} -> ${it.value}" }
        }
    }
}