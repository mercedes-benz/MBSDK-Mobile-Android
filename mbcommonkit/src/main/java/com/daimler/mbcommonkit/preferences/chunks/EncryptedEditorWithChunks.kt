package com.daimler.mbcommonkit.preferences.chunks

import android.content.SharedPreferences
import com.daimler.mbloggerkit.MBLoggerKit

internal class EncryptedEditorWithChunks(
    private val encryptedSharedPreferences: EncryptedSharedPreferencesWithChunks,
    private val editor: SharedPreferences.Editor
) : SharedPreferences.Editor by editor {

    private val chunkWriter = EncryptedSharedPreferencesChunkWriter { key, value ->
        editor.putString(key, value)
    }

    override fun putString(key: String, value: String?): SharedPreferences.Editor {
        chunkWriter.putStringInChunksIfTooLong(key, value ?: "")
        return this
    }

    override fun remove(key: String): SharedPreferences.Editor {
        if (encryptedSharedPreferences.shouldUseChunksForKey(key)) {
            removeChunksForKey(key)
        } else {
            editor.remove(key)
        }
        return this
    }

    private fun removeChunksForKey(key: String) {
        MBLoggerKit.d("Removing chunks for key $key")
        encryptedSharedPreferences.chunkKeyIterator(key).forEach {
            remove(it)
        }
    }
}
