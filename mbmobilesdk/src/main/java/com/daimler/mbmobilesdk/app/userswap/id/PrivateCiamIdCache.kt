package com.daimler.mbmobilesdk.app.userswap.id

import android.content.Context
import com.daimler.mbcommonkit.extensions.getEncryptedSharedPreferences

internal class PrivateCiamIdCache(private val context: Context) : BaseCiamIdCache() {

    override var userId: String
        get() = encryptedPref().getString(KEY_CIAM_ID, "")
        set(value) {
            encryptedPref().edit().putString(KEY_CIAM_ID, value).apply()
        }

    override fun clear() {
        userId = ""
    }

    private fun encryptedPref() =
        context.getEncryptedSharedPreferences(KEY_ALIAS, SETTINGS_NAME)

    private companion object {
        private const val SETTINGS_NAME = "mb.sdk.user.id.cache.private"
        private const val KEY_ALIAS = "mb.sdk.key.user.id.cache.private.alias"
    }
}