package com.daimler.mbmobilesdk.preferences.support

import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbmobilesdk.preferences.PreferencesInitializable
import com.daimler.mbmobilesdk.preferences.PreferencesInitializer
import com.daimler.mbcommonkit.extensions.getMultiAppSharedPreferences

internal class SharedSupportPreferences(context: Context, sharedUserId: String) : BaseSupportPreferences(), PreferencesInitializable {

    override val prefs: SharedPreferences = context.getMultiAppSharedPreferences(SETTINGS_SUPPORT, sharedUserId)

    init {
        PreferencesInitializer(this).initialize(context, sharedUserId, SETTINGS_SUPPORT)
    }

    override fun needsInitialization(): Boolean = !initialized.get()

    override fun arePreferencesInitialized(preferences: SharedPreferences): Boolean =
            preferences.getBoolean(KEY_INITIALIZED, false)

    override fun copyFromPreferences(preferences: SharedPreferences) {
        // cacCallDataSwitch isEnabled
        cacCallDataSwitchEnabled.set(preferences.getBoolean(KEY_CAC_CALL_DATA_SWITCH_ENABLED, Defaults.CAC_CALL_DATA_SWITCH_ENABLED))

        // own phone number with !!-Operator because it could never be null (Default is an empty String)
        ownPhoneNumber.set(preferences.getString(KEY_OWN_PHONE_NUMBER, Defaults.OWN_PHONE_NUMBER)!!)

        // cac phone number
        cacPhoneNumber.set(preferences.getString(KEY_CAC_PHONE_NUMBER, Defaults.CAC_PHONE_NUMBER)!!)

        // initialized
        initialized.set(true)
    }

    override fun onNoInitializedPreferencesFound() {
        initialized.set(true)
    }

    private companion object {
        private const val SETTINGS_SUPPORT = "mb.app.family.settings.support"
    }
}