package com.daimler.mbmobilesdk.preferences

import android.content.Context
import com.daimler.mbcommonkit.utils.preferencesForSharedUserId
import com.daimler.mbloggerkit.MBLoggerKit

internal class PreferencesInitializer(private val initializable: PreferencesInitializable) {

    fun initialize(context: Context, sharedUserId: String, settingsName: String) {
        if (initializable.needsInitialization()) {
            MBLoggerKit.d("Initializing $settingsName.")
            val prefs =
                preferencesForSharedUserId(context, settingsName, sharedUserId)
            prefs.firstOrNull { initializable.arePreferencesInitialized(it) }
                ?.let {
                    MBLoggerKit.d("Copying preferences for $settingsName.")
                    initializable.copyFromPreferences(it)
                } ?: initializable.onNoInitializedPreferencesFound()
        }
    }
}