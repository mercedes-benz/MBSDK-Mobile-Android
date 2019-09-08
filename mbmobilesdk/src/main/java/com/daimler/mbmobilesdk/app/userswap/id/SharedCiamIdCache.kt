package com.daimler.mbmobilesdk.app.userswap.id

import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbmobilesdk.preferences.PreferencesInitializable
import com.daimler.mbmobilesdk.preferences.PreferencesInitializer
import com.daimler.mbcommonkit.extensions.getEncryptedSharedPreferences
import com.daimler.mbcommonkit.utils.getPackagesWithSharedUserId

internal class SharedCiamIdCache(
    private val context: Context,
    private val sharedUserId: String
) : BaseCiamIdCache(), PreferencesInitializable {

    override var userId: String
        get() = encryptedPref().getString(KEY_CIAM_ID, "")
        set(value) {
            encryptedPrefs().forEach { it.edit().putString(KEY_CIAM_ID, value).apply() }
        }

    init {
        PreferencesInitializer(this).initialize(context, sharedUserId, SETTINGS_NAME)
    }

    override fun needsInitialization(): Boolean = userId.isBlank()

    override fun arePreferencesInitialized(preferences: SharedPreferences): Boolean =
        preferences.getString(KEY_CIAM_ID, null).isNullOrBlank().not()

    override fun copyFromPreferences(preferences: SharedPreferences) {
        val tmpCiamId = preferences.getString(KEY_CIAM_ID, null)
        encryptedPref().edit().putString(KEY_CIAM_ID, tmpCiamId).apply()
    }

    override fun onNoInitializedPreferencesFound() = Unit

    override fun clear() {
        userId = ""
    }

    private fun encryptedPref() =
        context.getEncryptedSharedPreferences(KEY_ALIAS, SETTINGS_NAME)

    private fun encryptedPrefs() =
        getPackagesWithSharedUserId(context, sharedUserId).map {
            context.createPackageContext(it.packageName, 0)
                .getEncryptedSharedPreferences(KEY_ALIAS, SETTINGS_NAME)
        }

    private companion object {
        private const val SETTINGS_NAME = "mb.sdk.user.id.cache"
        private const val KEY_ALIAS = "mb.sdk.key.user.id.cache.alias"
    }
}