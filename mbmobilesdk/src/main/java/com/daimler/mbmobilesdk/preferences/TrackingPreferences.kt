package com.daimler.mbmobilesdk.preferences

import android.content.Context
import com.daimler.mbcommonkit.extensions.booleanPreference
import com.daimler.mbcommonkit.preferences.Preference

internal class TrackingPreferences(context: Context) {

    val trackingEnabled: Preference<Boolean> by lazy {
        context.getSharedPreferences(SETTINGS_KEY, Context.MODE_PRIVATE)
            .booleanPreference(KEY_TRACKING_ENABLED, Defaults.TRACKING_ENABLED)
    }

    fun reset() {
        trackingEnabled.set(Defaults.TRACKING_ENABLED)
    }

    private object Defaults {
        const val TRACKING_ENABLED = true
    }

    private companion object {

        private const val SETTINGS_KEY = "mb.app.family.settings.tracking"

        private const val KEY_TRACKING_ENABLED = "tracking.enabled"
    }
}