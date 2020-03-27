package com.daimler.mbcommonkit.preferences.chunks

import android.content.SharedPreferences
import com.daimler.mbcommonkit.preferences.chunks.EncryptedPreferencesChunkHelper.chunkKey

internal class ChunkKeyIterator(
    private val preferences: SharedPreferences,
    private val key: String
) : AbstractIterator<String>() {

    private var index = 0

    override fun computeNext() {
        if (preferences.contains(chunkKey(key, index))) {
            setNext(chunkKey(key, index++))
        } else {
            done()
        }
    }
}

internal fun SharedPreferences.chunkKeyIterator(key: String) =
    ChunkKeyIterator(this, key)
