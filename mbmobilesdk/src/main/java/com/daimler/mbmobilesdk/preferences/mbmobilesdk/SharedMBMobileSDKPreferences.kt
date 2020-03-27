package com.daimler.mbmobilesdk.preferences.mbmobilesdk

import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbcommonkit.extensions.getMultiAppSharedPreferences
import com.daimler.mbcommonkit.utils.preferencesForSharedUserId
import com.daimler.mbmobilesdk.preferences.PreferencesInitializable

internal class SharedMBMobileSDKPreferences(
    context: Context,
    sharedUserId: String
) : BaseMBMobileSDKPreferences(), PreferencesInitializable {

    override val prefs: SharedPreferences =
        context.getMultiAppSharedPreferences(MULTI_APP_PREFERENCES_NAME, sharedUserId)

    init {
        initialize(context, sharedUserId, MULTI_APP_PREFERENCES_NAME)
    }

    override fun arePreferencesInitialized(preferences: SharedPreferences): Boolean =
        !preferences.getString(KEY_DEVICE_ID, null).isNullOrBlank()

    override fun needsInitialization(): Boolean = deviceId.get().isBlank()

    override fun copyFromPreferences(preferences: SharedPreferences) {
        // copy device id
        val tmpDeviceId = preferences.getString(KEY_DEVICE_ID, "").orEmpty()
        deviceId.set(tmpDeviceId)
    }

    override fun onNoInitializedPreferencesFound() {
        deviceId.set(generateDeviceId())
    }

    private companion object {

        private const val MULTI_APP_PREFERENCES_NAME = "mb.mobile.sdk.settings.shared"
    }
}

private fun PreferencesInitializable.initialize(context: Context, sharedUserId: String, settingsName: String) {
    if (needsInitialization()) {
        val prefs =
            preferencesForSharedUserId(context, settingsName, sharedUserId)
        prefs.firstOrNull { arePreferencesInitialized(it) }
            ?.let {
                copyFromPreferences(it)
            } ?: onNoInitializedPreferencesFound()
    }
}
