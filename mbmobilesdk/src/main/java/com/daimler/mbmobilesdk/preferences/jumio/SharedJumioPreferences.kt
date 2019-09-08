package com.daimler.mbmobilesdk.preferences.jumio

import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbmobilesdk.preferences.PreferencesInitializable
import com.daimler.mbmobilesdk.preferences.PreferencesInitializer
import com.daimler.mbcommonkit.extensions.getMultiAppSharedPreferences

internal class SharedJumioPreferences(context: Context, sharedUserId: String) : BaseJumioPreferences(), PreferencesInitializable {

    override val prefs: SharedPreferences = context.getMultiAppSharedPreferences(JUMIO_SUPPORT, sharedUserId)

    init {
        PreferencesInitializer(this).initialize(context, sharedUserId, JUMIO_SUPPORT)
    }

    override fun needsInitialization(): Boolean = !initialized.get()

    override fun arePreferencesInitialized(preferences: SharedPreferences): Boolean =
            preferences.getBoolean(KEY_INITIALIZED, false)

    override fun copyFromPreferences(preferences: SharedPreferences) {
        // terms accepted
        jumioTermsAccepted.set(preferences.getBoolean(KEY_JUMIO_TERMS_ACCEPTED, Defaults.JUMIO_TERMS_ACCEPTED))

        // initialized
        initialized.set(true)
    }

    override fun onNoInitializedPreferencesFound() {
        initialized.set(true)
    }

    private companion object {
        private const val JUMIO_SUPPORT = "mb.app.family.jumio.support"
    }
}