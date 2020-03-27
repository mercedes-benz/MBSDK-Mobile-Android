package com.daimler.mbingresskit.implementation.etag

import android.content.Context

internal class PreferencesETagProvider(
    context: Context
) : ETagProvider {

    private val prefs = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE)

    override fun get(key: String): String? = prefs.getString(key, null)

    override fun set(key: String, value: String?) = prefs.edit().putString(key, value).apply()

    override fun clear(key: String) = prefs.edit().remove(key).apply()

    override fun clearAll() = prefs.edit().clear().apply()

    private companion object {

        private const val SETTINGS_NAME = "mb.ingress.kit.etag.provider.prefs"
    }
}
