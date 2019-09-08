package com.daimler.mbmobilesdk.preferences.support

import android.content.Context
import android.content.SharedPreferences

internal class PrivateSupportPreferences(context: Context) : BaseSupportPreferences() {

    override val prefs: SharedPreferences = context.getSharedPreferences(SETTINGS_SUPPORT, Context.MODE_PRIVATE)

    private companion object {
        private const val SETTINGS_SUPPORT = "mb.app.family.settings.support.private"
    }
}