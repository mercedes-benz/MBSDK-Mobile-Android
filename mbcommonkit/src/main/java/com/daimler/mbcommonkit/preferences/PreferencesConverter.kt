package com.daimler.mbcommonkit.preferences

import android.content.SharedPreferences

/**
 * Converter that is used for custom types in usage with the [SharedPreferences].
 */
abstract class PreferencesConverter<T> {

    /**
     * Returns the property that was read from the preferences.
     */
    abstract fun readFromPreferences(preferences: SharedPreferences): T

    /**
     * Writes the given value to the preferences.
     */
    abstract fun writeToPreferences(editor: SharedPreferences.Editor, value: T)
}
