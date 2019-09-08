package com.daimler.mbmobilesdk.biometric.iv

import android.content.SharedPreferences
import android.util.Base64

internal abstract class BaseAppPreferencesIvProvider : IvProvider {

    protected abstract fun getIvFromPreferences(key: String, def: String): String

    protected abstract fun putIvToPreferences(key: String, value: String)

    protected abstract fun getPreferencesForDeletion(): List<SharedPreferences>

    override fun getIvForAlias(alias: String, tag: String): ByteArray? {
        val raw = getIvFromPreferences(key(alias, tag), "")
        return if (raw.isBlank()) null else Base64.decode(raw, Base64.NO_WRAP)
    }

    override fun saveIvForAlias(alias: String, tag: String, iv: ByteArray) {
        val raw = Base64.encodeToString(iv, Base64.NO_WRAP)
        putIvToPreferences(key(alias, tag), raw)
    }

    override fun deleteIvForAlias(alias: String) {
        getPreferencesForDeletion().forEach { prefs ->
            val editor = prefs.edit()
            prefs.all.filter {
                it.key.contains(alias)
            }.forEach {
                editor.remove(it.key)
            }
            editor.apply()
        }
    }

    private fun key(alias: String, tag: String) = "$alias:$tag.key"
}