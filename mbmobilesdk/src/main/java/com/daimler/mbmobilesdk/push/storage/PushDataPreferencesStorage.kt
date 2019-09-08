package com.daimler.mbmobilesdk.push.storage

import android.content.Context
import com.daimler.mbmobilesdk.push.PushData
import com.daimler.mbloggerkit.MBLoggerKit
import com.google.gson.Gson

internal class PushDataPreferencesStorage(context: Context) : PushDataStorage {

    private val preferences = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE)

    override var latestPushData: PushData?
        get() = restoreLatestPushData()
        set(value) = storePushData(value)

    override fun clear() {
        preferences.edit().putString(KEY_DATA, null).apply()
    }

    private fun restoreLatestPushData(): PushData? {
        val json: String? = preferences.getString(KEY_DATA, null)
        return json?.let {
            tryParsePushData(it)
        }
    }

    private fun storePushData(pushData: PushData?) {
        val json = pushData?.let {
            Gson().toJson(it)
        }
        preferences.edit().putString(KEY_DATA, json).apply()
    }

    private fun tryParsePushData(json: String): PushData? {
        return try {
            Gson().fromJson<PushData>(json, PushData::class.java)
        } catch (e: Exception) {
            MBLoggerKit.e("Failed to parse PushData from JSON.")
            clear()
            null
        }
    }

    private companion object {

        private const val SETTINGS_NAME = "mb.app.family.push.data.storage"

        private const val KEY_DATA = "push.data.storage.latest"
    }
}