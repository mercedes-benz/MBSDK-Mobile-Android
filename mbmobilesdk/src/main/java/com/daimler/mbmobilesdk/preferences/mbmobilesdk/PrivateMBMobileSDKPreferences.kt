package com.daimler.mbmobilesdk.preferences.mbmobilesdk

import android.content.Context
import android.content.SharedPreferences

internal class PrivateMBMobileSDKPreferences(
    context: Context
) : BaseMBMobileSDKPreferences() {

    override val prefs: SharedPreferences = context.getSharedPreferences(PRIVATE_PREFERENCES_NAME, Context.MODE_PRIVATE)

    init {
        if (deviceId.get().isBlank()) {
            deviceId.set(generateDeviceId())
        }
    }

    private companion object {
        private const val PRIVATE_PREFERENCES_NAME = "mb.mobile.sdk.settings.private"
    }
}
