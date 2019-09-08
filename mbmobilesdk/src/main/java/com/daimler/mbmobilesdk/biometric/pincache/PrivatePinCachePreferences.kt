package com.daimler.mbmobilesdk.biometric.pincache

import android.content.Context
import com.daimler.mbcommonkit.extensions.getEncryptedSharedPreferences

internal class PrivatePinCachePreferences(private val context: Context) : BasePreferencesPinCache(), PinCache {

    override var pin: String
        get() = encryptedPref().getString(KEY_PIN, "")
        set(value) {
            encryptedPref().edit().putString(KEY_PIN, value).apply()
        }

    override fun clear() {
        pin = ""
    }

    private fun encryptedPref() =
        context.getEncryptedSharedPreferences(ALIAS, SETTINGS_NAME)

    private companion object {
        private const val SETTINGS_NAME = "mb.settings.sdk.biometric.pin.private"
        private const val ALIAS = "com.daimler.mb.biometric.pin.private.alias"
    }
}