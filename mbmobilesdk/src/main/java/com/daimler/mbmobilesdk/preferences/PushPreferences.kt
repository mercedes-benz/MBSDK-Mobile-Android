package com.daimler.mbmobilesdk.preferences

import android.content.Context
import com.daimler.mbcommonkit.extensions.booleanPreference
import com.daimler.mbcommonkit.extensions.stringPreference
import com.daimler.mbcommonkit.preferences.Preference

internal class PushPreferences(context: Context) {

    private val preferences = context.getSharedPreferences(SETTINGS_KEY, Context.MODE_PRIVATE)

    val fcmPushToken: Preference<String> by lazy {
        preferences.stringPreference(KEY_FCM_TOKEN)
    }

    val registrationId: Preference<String> by lazy {
        preferences.stringPreference(KEY_REG_ID)
    }

    val pushEnabled: Preference<Boolean> by lazy {
        preferences.booleanPreference(KEY_PUSH_ENABLED, Defaults.PUSH_ENABLED)
    }

    private object Defaults {
        const val PUSH_ENABLED = true
    }

    private companion object {

        private const val SETTINGS_KEY = "mb.app.family.settings.push"

        private const val KEY_FCM_TOKEN = "push.fcm.token"
        private const val KEY_REG_ID = "push.registration.id"
        private const val KEY_PUSH_ENABLED = "push.enabled"
    }
}