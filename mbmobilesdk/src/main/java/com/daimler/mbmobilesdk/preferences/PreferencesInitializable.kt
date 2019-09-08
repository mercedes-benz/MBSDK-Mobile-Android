package com.daimler.mbmobilesdk.preferences

import android.content.SharedPreferences

internal interface PreferencesInitializable {

    fun needsInitialization(): Boolean

    fun arePreferencesInitialized(preferences: SharedPreferences): Boolean

    fun copyFromPreferences(preferences: SharedPreferences)

    fun onNoInitializedPreferencesFound()
}