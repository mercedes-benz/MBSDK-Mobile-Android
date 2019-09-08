package com.daimler.mbmobilesdk.preferences.user

import android.content.Context
import android.content.SharedPreferences

internal class PrivateUserPreferences(context: Context) : BaseUserPreferences() {

    override val prefs: SharedPreferences = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE)

    private companion object {

        private const val SETTINGS_NAME = "mb.app.family.settings.user.private"
    }
}