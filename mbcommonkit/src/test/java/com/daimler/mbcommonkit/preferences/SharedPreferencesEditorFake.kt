package com.daimler.mbcommonkit.preferences

import android.content.SharedPreferences

class SharedPreferencesEditorFake(
    private val sharedPreferences: SharedPreferencesFake
) :
    SharedPreferences.Editor {

    override fun clear(): SharedPreferences.Editor =
        apply { sharedPreferences.sharedPrefs.clear() }

    override fun putLong(key: String?, value: Long): SharedPreferences.Editor =
        putValue(key, value)

    override fun putInt(key: String?, value: Int): SharedPreferences.Editor =
        putValue(key, value)

    override fun remove(key: String?): SharedPreferences.Editor =
        apply { key.let { sharedPreferences.sharedPrefs.remove(it) } }

    override fun putBoolean(key: String?, value: Boolean): SharedPreferences.Editor =
        putValue(key, value)

    override fun putStringSet(
        key: String?,
        values: MutableSet<String>?
    ): SharedPreferences.Editor = putValue(key, values)

    override fun commit(): Boolean = true

    override fun putFloat(key: String?, value: Float): SharedPreferences.Editor =
        putValue(key, value)

    override fun apply() = Unit

    override fun putString(key: String?, value: String?): SharedPreferences.Editor =
        putValue(key, value)

    private fun putValue(key: String?, value: Any?): SharedPreferences.Editor =
        apply {
            key?.let { k ->
                value?.let { v ->
                    sharedPreferences.sharedPrefs[k] = v
                    notifyListeners(k)
                }
            }
        }

    private fun notifyListeners(key: String) {
        sharedPreferences.listeners.forEach {
            it.onSharedPreferenceChanged(sharedPreferences, key)
        }
    }
}
