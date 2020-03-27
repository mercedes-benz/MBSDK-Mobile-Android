package com.daimler.mbcommonkit.preferences.chunks

import android.os.Build
import com.daimler.mbcommonkit.preferences.chunks.EncryptedPreferencesChunkHelper.chunkKey
import com.daimler.mbcommonkit.preferences.chunks.EncryptedPreferencesChunkHelper.getAndroidRelease
import com.daimler.mbcommonkit.preferences.chunks.EncryptedPreferencesChunkHelper.shouldUseChunksForCurrentDevice
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class EncryptedPreferencesChunkHelperTest {

    @AfterEach
    fun cleanupMocks() {
        clearAllMocks()
    }

    @Test
    fun testChunkKey() {
        val testIndex = 0
        val chunkKey = chunkKey(TEST_KEY, testIndex)
        val expectedChunkKey = "$TEST_KEY-chunk-$testIndex"
        assertEquals(expectedChunkKey, chunkKey)
    }

    @ParameterizedTest
    @ValueSource(ints = [Build.VERSION_CODES.M, Build.VERSION_CODES.N, Build.VERSION_CODES.N_MR1, Build.VERSION_CODES.O, Build.VERSION_CODES.O_MR1])
    fun `should use chunks from Android 6 to Android_8_1`(androidVersion: Int) {
        mockkObject(EncryptedPreferencesChunkHelper)
        every { getAndroidRelease() } returns androidVersion
        assertTrue(shouldUseChunksForCurrentDevice())
    }

    @Test
    fun `should not use chunks for newer android versions`() {
        mockkObject(EncryptedPreferencesChunkHelper)
        every { getAndroidRelease() } returns Build.VERSION_CODES.P
        assertFalse(shouldUseChunksForCurrentDevice())
    }

    companion object {

        private const val TEST_KEY = "testKey"
    }
}
