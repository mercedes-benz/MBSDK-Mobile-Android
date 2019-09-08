package com.daimler.mbmobilesdk.preferences

import com.daimler.mbcommonkit.preferences.Preference

/**
 * Interface for push notification related preferences that might by useful.
 * These settings are exclusively set by the SDK and cannot be changed from outside of the SDK.
 */
internal interface PushSettings {

    /**
     * The FCM token as received from Firebase.
     */
    val fcmToken: Preference<String>

    /**
     * The push registration ID as received from the BFF.
     */
    val registrationId: Preference<String>

    /**
     * A flag that indicates whether the user allowed push notifications.
     */
    val pushEnabled: Preference<Boolean>
}