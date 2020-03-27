package com.daimler.mbcommonkit.preferences.chunks

import android.content.SharedPreferences
import com.daimler.mbcommonkit.preferences.SharedPreferencesFake
import com.daimler.mbcommonkit.preferences.TEST_KEY
import com.daimler.mbcommonkit.preferences.chunks.EncryptedPreferencesChunkHelper.chunkKey
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ChunkKeyIteratorTest {
    private lateinit var sharedPreferences: SharedPreferences

    @BeforeEach
    fun setupSharedPreferences() {
        sharedPreferences = SharedPreferencesFake()
    }

    @Test
    fun `iterator returns all chunk keys`() {
        val chunkKeys = (0..3).map { index ->
            chunkKey(TEST_KEY, index).also { sharedPreferences.edit().putString(it, "test") }
        }
        val chunkKeysFromIterator = mutableListOf<String>()
        sharedPreferences.chunkKeyIterator(TEST_KEY).forEach {
            chunkKeysFromIterator += it
        }
        assertThat(chunkKeysFromIterator).isEqualTo(chunkKeys)
    }
}
