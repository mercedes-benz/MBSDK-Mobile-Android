package com.daimler.mbmobilesdk.biometric.iv

import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbcommonkit.extensions.getEncryptedSharedPreferences

internal class PrivateAppPreferencesIvProvider(
    private val context: Context
) : BaseAppPreferencesIvProvider() {

    override fun getIvFromPreferences(key: String, def: String): String {
        return encryptedPref().getString(key, def)
    }

    override fun putIvToPreferences(key: String, value: String) {
        encryptedPref().edit().putString(key, value).apply()
    }

    override fun getPreferencesForDeletion(): List<SharedPreferences> {
        return listOf(encryptedPref())
    }

    private fun encryptedPref() =
        context.getEncryptedSharedPreferences(ALIAS, SETTINGS_NAME)

    private companion object {
        private const val SETTINGS_NAME = "mb.settings.sdk.secure.salt.private"
        private const val ALIAS = "com.daimler.mb.sdk.iv.provider.private.alias"
    }
}