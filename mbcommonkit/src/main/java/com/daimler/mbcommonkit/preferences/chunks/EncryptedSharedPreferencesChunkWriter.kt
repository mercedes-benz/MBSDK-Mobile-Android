package com.daimler.mbcommonkit.preferences.chunks

import android.content.SharedPreferences
import com.daimler.mbcommonkit.preferences.chunks.EncryptedPreferencesChunkHelper.chunkKey
import com.daimler.mbloggerkit.MBLoggerKit

internal class EncryptedSharedPreferencesChunkWriter(
    private val putString: (key: String, value: String?) -> SharedPreferences.Editor
) {

    internal fun putStringInChunksIfTooLong(key: String, value: String) {
        if (value.length > MAX_STRING_LENGTH) {
            MBLoggerKit.d("Value length is ${value.length}, writing $key in chunks")
            val stringInChunks = value.chunked(MAX_STRING_LENGTH)
            putStringInChunks(key, stringInChunks)
        } else {
            putString(key, value)
        }
    }

    private fun putStringInChunks(key: String, stringChunks: List<String>) {
        stringChunks.forEachIndexed { index, s ->
            val currentChunkKey = chunkKey(key, index)
            putString(currentChunkKey, s)
        }
    }

    companion object {
        internal const val MAX_STRING_LENGTH = 2000
    }
}
