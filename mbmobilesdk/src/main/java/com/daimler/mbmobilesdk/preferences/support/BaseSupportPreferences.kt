package com.daimler.mbmobilesdk.preferences.support

import android.content.SharedPreferences
import com.daimler.mbcommonkit.extensions.booleanPreference
import com.daimler.mbcommonkit.extensions.stringPreference
import com.daimler.mbcommonkit.preferences.Preference

internal abstract class BaseSupportPreferences {

    protected abstract val prefs: SharedPreferences

    val cacCallDataSwitchEnabled: Preference<Boolean> by lazy {
        prefs.booleanPreference(KEY_CAC_CALL_DATA_SWITCH_ENABLED, Defaults.CAC_CALL_DATA_SWITCH_ENABLED)
    }

    val ownPhoneNumber: Preference<String> by lazy {
        prefs.stringPreference(KEY_OWN_PHONE_NUMBER, Defaults.OWN_PHONE_NUMBER)
    }

    val cacPhoneNumber: Preference<String> by lazy {
        prefs.stringPreference(KEY_CAC_PHONE_NUMBER, Defaults.CAC_PHONE_NUMBER)
    }

    val initialized: Preference<Boolean> by lazy {
        prefs.booleanPreference(KEY_INITIALIZED, Defaults.INITIALIZED)
    }

    fun resetSupportSettings() {
        cacCallDataSwitchEnabled.set(Defaults.CAC_CALL_DATA_SWITCH_ENABLED)
        ownPhoneNumber.set(Defaults.OWN_PHONE_NUMBER)
        cacPhoneNumber.set(Defaults.CAC_PHONE_NUMBER)
        initialized.set(Defaults.INITIALIZED)
    }

    protected object Defaults {
        const val CAC_CALL_DATA_SWITCH_ENABLED = false
        const val OWN_PHONE_NUMBER = ""
        const val CAC_PHONE_NUMBER = ""
        const val INITIALIZED = false
    }

    protected companion object {
        const val KEY_CAC_CALL_DATA_SWITCH_ENABLED = "support.caccalldata.switch.enabled"
        const val KEY_OWN_PHONE_NUMBER = "support.phonenumber.own"
        const val KEY_CAC_PHONE_NUMBER = "support.phonenumber.cac"
        const val KEY_INITIALIZED = "support.settings.initialized"
    }
}