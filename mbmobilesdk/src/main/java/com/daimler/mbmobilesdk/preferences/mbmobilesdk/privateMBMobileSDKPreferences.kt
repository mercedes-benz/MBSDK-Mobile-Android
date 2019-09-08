package com.daimler.mbmobilesdk.preferences.mbmobilesdk

import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbmobilesdk.app.Endpoint

internal class privateMBMobileSDKPreferences(
    context: Context,
    endpoint: Endpoint
) : BaseMBMobileSDKPreferences(endpoint) {

    override val prefs: SharedPreferences = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE)

    private companion object {
        private const val SETTINGS_NAME = "mb.app.family.settings.private"
    }
}