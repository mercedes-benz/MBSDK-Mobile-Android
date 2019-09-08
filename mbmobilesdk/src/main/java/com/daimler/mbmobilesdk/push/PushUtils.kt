package com.daimler.mbmobilesdk.push

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.utils.extensions.isResolvable
import com.daimler.mbmobilesdk.utils.isOreo

private const val ACTION_PUSH_SETTINGS = "android.settings.APP_NOTIFICATION_SETTINGS"
private const val EXTRA_APP_PACKAGE = "app_package"

/**
 * Returns true if push notifications are enabled on the system.
 */
internal fun areNotificationsEnabledOnSystemSettings(context: Context) =
    NotificationManagerCompat.from(context).areNotificationsEnabled()

/**
 * Returns true if push notifications are enabled in the internal settings.
 */
internal fun areNotificationsEnabledOnAppSettings() =
    MBMobileSDK.pushSettings().pushEnabled.get()

/**
 * Returns true if push notifications are enabled on the system and in the internal settings.
 *
 * @see areNotificationsEnabledOnAppSettings
 * @see areNotificationsEnabledOnSystemSettings
 */
internal fun areNotificationsEnabled(context: Context) =
    areNotificationsEnabledOnAppSettings() &&
        areNotificationsEnabledOnSystemSettings(context)

internal fun getPushSettingsIntent(context: Context): Intent? {
    val (action: String, extra: String) = if (isOreo()) {
        Settings.ACTION_APP_NOTIFICATION_SETTINGS to Settings.EXTRA_APP_PACKAGE
    } else {
        ACTION_PUSH_SETTINGS to EXTRA_APP_PACKAGE
    }
    val intent = Intent(action)
    intent.putExtra(extra, context.packageName)
    return if (intent.isResolvable(context)) intent else null
}