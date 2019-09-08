package com.daimler.mbmobilesdk.preferences

import android.content.SharedPreferences
import com.daimler.mbcommonkit.preferences.PreferencesConverter
import com.google.gson.Gson

internal class JsonPreferencesConverter<T>(
    private val key: String,
    private val defaultValue: T,
    private val cls: Class<T>
) : PreferencesConverter<T>() {

    override fun readFromPreferences(preferences: SharedPreferences): T {
        val json = preferences.getString(key, null)
        return json?.let { Gson().fromJson(it, cls) } ?: defaultValue
    }

    override fun writeToPreferences(editor: SharedPreferences.Editor, value: T) {
        editor.putString(key, Gson().toJson(value)).apply()
    }
}