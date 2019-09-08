package com.daimler.mbmobilesdk.biometric.pincache

import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbmobilesdk.preferences.PreferencesInitializable
import com.daimler.mbmobilesdk.preferences.PreferencesInitializer
import com.daimler.mbcommonkit.extensions.getEncryptedSharedPreferences
import com.daimler.mbcommonkit.utils.getPackagesWithSharedUserId

internal class SharedPinCachePreferences(
    private val context: Context,
    private val sharedUserId: String
) : BasePreferencesPinCache(), PreferencesInitializable {

    override var pin: String
        get() = encryptedPref().getString(KEY_PIN, "")
        set(value) = encryptedPrefs().forEach { it.edit().putString(KEY_PIN, value).apply() }

    init {
        PreferencesInitializer(this).initialize(context, sharedUserId, SETTINGS_NAME)
    }

    override fun needsInitialization(): Boolean = pin.isBlank()

    override fun arePreferencesInitialized(preferences: SharedPreferences): Boolean =
        !preferences.getString(KEY_PIN, "").isNullOrBlank()

    override fun copyFromPreferences(preferences: SharedPreferences) {
        pin = preferences.getString(KEY_PIN, "").orEmpty()
    }

    override fun onNoInitializedPreferencesFound() = Unit

    override fun clear() {
        pin = ""
    }

    private fun encryptedPrefs(): List<SharedPreferences> {
        val packages = getPackagesWithSharedUserId(context, sharedUserId)
        return packages.map {
            context.createPackageContext(it.packageName, 0)
                .getEncryptedSharedPreferences(ALIAS, SETTINGS_NAME)
        }
    }

    private fun encryptedPref() =
        context.getEncryptedSharedPreferences(ALIAS, SETTINGS_NAME)

    private companion object {
        private const val SETTINGS_NAME = "mb.settings.sdk.biometric.pin"
        private const val ALIAS = "com.daimler.mb.biometric.pin.alias"
    }
}