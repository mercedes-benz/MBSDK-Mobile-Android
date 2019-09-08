package com.daimler.mbmobilesdk.preferences.jumio

import android.content.Context
import android.content.SharedPreferences

internal class PrivateJumioPreferences(context: Context) : BaseJumioPreferences() {

    override val prefs: SharedPreferences = context.getSharedPreferences(SETTINGS_JUMIO, Context.MODE_PRIVATE)

    private companion object {
        private const val SETTINGS_JUMIO = "mb.app.family.settings.jumio.private"
    }
}