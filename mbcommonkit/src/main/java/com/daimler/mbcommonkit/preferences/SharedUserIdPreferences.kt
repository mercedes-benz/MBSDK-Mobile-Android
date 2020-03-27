package com.daimler.mbcommonkit.preferences

import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbcommonkit.utils.checkSharedUserIdConfigured
import com.daimler.mbcommonkit.utils.preferencesForSharedUserId

internal class SharedUserIdPreferences(
    private val context: Context,
    private val preferences: SharedPreferences,
    private val sharedUserId: String,
    private val settingsName: String
) : SharedPreferences by preferences {

    init {
        checkSharedUserIdConfigured(context, sharedUserId)
    }

    override fun edit(): SharedPreferences.Editor = SharedEditor(getEditors())

    private fun getEditors(): List<SharedPreferences.Editor> =
        preferencesForSharedUserId(context, settingsName, sharedUserId).map { it.edit() }

    private inner class SharedEditor(
        private val editors: List<SharedPreferences.Editor>
    ) : SharedPreferences.Editor {

        override fun clear(): SharedPreferences.Editor {
            execute { clear() }
            return this
        }

        override fun putLong(key: String, value: Long): SharedPreferences.Editor {
            execute { putLong(key, value) }
            return this
        }

        override fun putInt(key: String, value: Int): SharedPreferences.Editor {
            execute { putInt(key, value) }
            return this
        }

        override fun remove(key: String): SharedPreferences.Editor {
            execute { remove(key) }
            return this
        }

        override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
            execute { putBoolean(key, value) }
            return this
        }

        override fun putStringSet(key: String, values: MutableSet<String>?): SharedPreferences.Editor {
            execute { putStringSet(key, values) }
            return this
        }

        override fun commit(): Boolean = executeCommit()

        override fun putFloat(key: String, value: Float): SharedPreferences.Editor {
            execute { putFloat(key, value) }
            return this
        }

        override fun apply() {
            execute { apply() }
        }

        override fun putString(key: String, value: String?): SharedPreferences.Editor {
            execute { putString(key, value) }
            return this
        }

        private fun execute(action: SharedPreferences.Editor.() -> Unit) =
            editors.forEach { it.action() }

        private fun executeCommit(): Boolean {
            var result = true
            editors.forEach { result = result && it.commit() }
            return result
        }
    }
}
