package com.daimler.mbmobilesdk.preferences.user

import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbmobilesdk.preferences.PreferencesInitializable
import com.daimler.mbmobilesdk.preferences.PreferencesInitializer
import com.daimler.mbcommonkit.extensions.getMultiAppSharedPreferences

internal class SharedUserPreferences(
    context: Context,
    sharedUserId: String
) : BaseUserPreferences(), PreferencesInitializable {

    override val prefs: SharedPreferences = context.getMultiAppSharedPreferences(SETTINGS_USER, sharedUserId)

    init {
        PreferencesInitializer(this).initialize(context, sharedUserId, SETTINGS_USER)
    }

    override fun needsInitialization(): Boolean = !initialized.get()

    override fun arePreferencesInitialized(preferences: SharedPreferences): Boolean =
        preferences.getBoolean(KEY_INITIALIZED, false)

    override fun copyFromPreferences(preferences: SharedPreferences) {
        // pin biometrics
        val tmpBiometricsEnabled =
            preferences.getBoolean(KEY_PIN_BIOMETRICS_ENABLED, Defaults.PIN_BIOMETRICS_ENABLED)
        pinBiometricsEnabled.set(tmpBiometricsEnabled)

        // biometrics prompt
        val tmpPrompted =
            preferences.getBoolean(KEY_PROMPTED_FOR_BIOMETRICS, Defaults.PROMPTED_FOR_BIOMETRICS)
        promptedForBiometrics.set(tmpPrompted)

        // initialized
        initialized.set(true)
    }

    override fun onNoInitializedPreferencesFound() {
        initialized.set(true)
    }

    private companion object {
        private const val SETTINGS_USER = "mb.app.family.settings.user"
    }
}