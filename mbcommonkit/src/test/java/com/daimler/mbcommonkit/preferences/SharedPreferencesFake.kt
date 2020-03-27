package com.daimler.mbcommonkit.preferences

import android.content.SharedPreferences

class SharedPreferencesFake : SharedPreferences {
    val sharedPrefs = mutableMapOf<String, Any>()
    private val editor = SharedPreferencesEditorFake(this)
    internal val listeners = mutableListOf<SharedPreferences.OnSharedPreferenceChangeListener>()

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        listeners.remove(listener)
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        listeners += listener
    }

    override fun getString(key: String, defValue: String?): String {
        return sharedPrefs[key] as? String ?: defValue ?: ""
    }

    override fun contains(key: String): Boolean = sharedPrefs.containsKey(key)

    override fun getBoolean(key: String, defValue: Boolean): Boolean = getValue(key, defValue)

    override fun getInt(key: String, defValue: Int): Int = getValue(key, defValue)

    override fun getAll(): MutableMap<String, *> = sharedPrefs

    override fun edit(): SharedPreferences.Editor = editor

    override fun getLong(key: String, defValue: Long): Long =
        getValue(key, defValue)

    override fun getFloat(key: String, defValue: Float): Float =
        getValue(key, defValue)

    override fun getStringSet(key: String, defValues: MutableSet<String>?): MutableSet<String> =
        sharedPrefs[key] as? MutableSet<String> ?: defValues ?: mutableSetOf()

    inline fun <reified T> getValue(key: String?, defValue: T): T =
        sharedPrefs[key] as? T ?: defValue
}
