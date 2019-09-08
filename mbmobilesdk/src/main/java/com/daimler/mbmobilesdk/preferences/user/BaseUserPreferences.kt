package com.daimler.mbmobilesdk.preferences.user

import android.content.SharedPreferences
import com.daimler.mbcommonkit.extensions.booleanPreference
import com.daimler.mbcommonkit.preferences.Preference

internal abstract class BaseUserPreferences {

    protected abstract val prefs: SharedPreferences

    val pinBiometricsEnabled: Preference<Boolean> by lazy {
        prefs.booleanPreference(KEY_PIN_BIOMETRICS_ENABLED, Defaults.PIN_BIOMETRICS_ENABLED)
    }

    val promptedForBiometrics: Preference<Boolean> by lazy {
        prefs.booleanPreference(KEY_PROMPTED_FOR_BIOMETRICS, Defaults.PROMPTED_FOR_BIOMETRICS)
    }

    val initialized: Preference<Boolean> by lazy {
        prefs.booleanPreference(KEY_INITIALIZED, Defaults.INITIALIZED)
    }

    fun reset() {
        pinBiometricsEnabled.set(Defaults.PIN_BIOMETRICS_ENABLED)
        promptedForBiometrics.set(Defaults.PROMPTED_FOR_BIOMETRICS)
        initialized.set(Defaults.INITIALIZED)
    }

    protected object Defaults {
        const val PIN_BIOMETRICS_ENABLED = false
        const val PROMPTED_FOR_BIOMETRICS = false
        const val INITIALIZED = false
    }

    protected companion object {
        const val KEY_PIN_BIOMETRICS_ENABLED = "user.pin.biometrics.enabled"
        const val KEY_PROMPTED_FOR_BIOMETRICS = "user.pin.biometrics.prompted"
        const val KEY_INITIALIZED = "user.settings.initialized"
    }
}