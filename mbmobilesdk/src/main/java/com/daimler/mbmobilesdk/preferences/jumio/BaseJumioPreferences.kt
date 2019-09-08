package com.daimler.mbmobilesdk.preferences.jumio

import android.content.SharedPreferences
import com.daimler.mbcommonkit.extensions.booleanPreference
import com.daimler.mbcommonkit.preferences.Preference

internal abstract class BaseJumioPreferences {

    protected abstract val prefs: SharedPreferences

    /**
     * Contains true if jumio terms and conditions were accepted
     */
    val jumioTermsAccepted: Preference<Boolean> by lazy {
        prefs.booleanPreference(KEY_JUMIO_TERMS_ACCEPTED, Defaults.JUMIO_TERMS_ACCEPTED)
    }

    val initialized: Preference<Boolean> by lazy {
        prefs.booleanPreference(KEY_INITIALIZED, Defaults.INITIALIZED)
    }

    protected object Defaults {
        const val JUMIO_TERMS_ACCEPTED = false
        const val INITIALIZED = false
    }

    protected companion object {
        const val KEY_JUMIO_TERMS_ACCEPTED = "JUMIO_TERMS_ACCEPTED"
        const val KEY_INITIALIZED = "JUMIO_INITIALIZED"
    }
}