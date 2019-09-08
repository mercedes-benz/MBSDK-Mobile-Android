package com.daimler.mbmobilesdk.push

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.push.dialog.PushDialogActivity
import com.daimler.mbmobilesdk.utils.extensions.isLoggedIn
import com.daimler.mbmobilesdk.utils.isOreo
import com.daimler.mbmobilesdk.utils.postToMainThread
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

open class MBMobileSDKMessagingService : FirebaseMessagingService() {

    final override fun onNewToken(token: String) {
        super.onNewToken(token)
        MBLoggerKit.d("Received new FCM token.")
        postToMainThread { MBMobileSDK.pushSettings().fcmToken.set(token) }
        onTokenReceived(token)
    }

    final override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (!MBIngressKit.authenticationService().isLoggedIn()) {
            MBLoggerKit.w("User is logged out, ignoring push notification.")
            return
        }

        if (!areNotificationsEnabledOnAppSettings()) {
            MBLoggerKit.w("Notifications are disabled on internal app settings.")
            return
        }

        val pushData = message.toPushData()
        if (pushData.isSdkPush()) {
            handleSdkPush(pushData)
        } else {
            onHandleNotification(message)
        }
    }

    /**
     * Called when a new FCM token was received.
     */
    @WorkerThread
    protected open fun onTokenReceived(token: String) = Unit

    /**
     * Called when a new notification was received that is not handled by the MBMobileSDK SDK.
     */
    @WorkerThread
    protected open fun onHandleNotification(message: RemoteMessage) = Unit

    @DrawableRes
    protected open fun getSmallIconResource(): Int = R.drawable.ic_menu_mme

    private fun handleSdkPush(pushData: PushData) {
        MBLoggerKit.d("Handling SDK Push: $pushData.")
        if (MBMobileSDK.appInForeground) {
            showPushDialog(pushData)
        } else {
            sendNotification(pushData)
        }
    }

    private fun showPushDialog(pushData: PushData) {
        val intent = PushDialogActivity.getStartIntent(this, pushData).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun sendNotification(data: PushData) {
        if (isOreo()) createNotificationChannel()

        val intent = Intent(this, PendingPushReceiver::class.java).apply {
            putExtra(PendingPushReceiver.ARG_PUSH_DATA, data)
        }
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0,
            intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification =
            NotificationCompat.Builder(this, CHANNEL_ID).apply {
                setSmallIcon(getSmallIconResource())
                setContentTitle(data.title)
                setContentText(data.body)
                setStyle(NotificationCompat.BigTextStyle().bigText(data.body))
                setContentIntent(pendingIntent)
                priority = NotificationCompat.PRIORITY_HIGH
            }.build()

        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID_SDK, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        // TODO name and description
        val name = "NotificationChannel"
        val description = "Description"
        val importance = NotificationManager.IMPORTANCE_HIGH
        NotificationChannel(CHANNEL_ID, name, importance).let {
            it.description = description
            val manager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(it)
        }
    }

    private fun RemoteMessage.toPushData() =
        data?.let {
            PushData(
                title = it[KEY_TITLE],
                body = it[KEY_BODY],
                url = it[KEY_URL],
                category = it[KEY_CATEGORY],
                deepLinkReference = it[KEY_DEEP_LINK_REFERENCE]
            )
        } ?: PushData()

    private fun PushData.isSdkPush() = category == CATEGORY_SDK

    companion object {

        private const val CHANNEL_ID = "com.daimler.risingstars"

        private const val KEY_TITLE = "Title"
        private const val KEY_BODY = "Body"
        private const val KEY_DEEP_LINK_REFERENCE = "DeepLinkReference"
        private const val KEY_URL = "Url"
        private const val KEY_CATEGORY = "Category"

        private const val CATEGORY_SDK = "RIS_SDK"

        const val NOTIFICATION_ID_SDK = 21
    }
}