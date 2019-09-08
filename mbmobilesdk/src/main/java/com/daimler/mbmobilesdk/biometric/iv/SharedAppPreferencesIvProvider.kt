package com.daimler.mbmobilesdk.biometric.iv

import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbcommonkit.extensions.getEncryptedSharedPreferences
import com.daimler.mbcommonkit.utils.getPackagesWithSharedUserId

internal class SharedAppPreferencesIvProvider(
    private val context: Context,
    private val sharedUserId: String
) : BaseAppPreferencesIvProvider() {

    override fun getIvFromPreferences(key: String, def: String): String {
        return encryptedPref().getString(key, def)
    }

    override fun putIvToPreferences(key: String, value: String) {
        encryptedPrefs().forEach {
            it.edit().putString(key, value).apply()
        }
    }

    override fun getPreferencesForDeletion(): List<SharedPreferences> {
        return encryptedPrefs()
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
        private const val SETTINGS_NAME = "mb.settings.sdk.secure.salt"
        private const val ALIAS = "com.daimler.mb.sdk.iv.provider.alias"
    }
}