package com.daimler.mbcommonkit.preferences.chunks

import com.daimler.mbcommonkit.preferences.SharedPreferencesFake
import com.daimler.mbcommonkit.preferences.TEST_KEY
import com.daimler.mbcommonkit.preferences.chunks.EncryptedPreferencesChunkHelper.chunkKey
import com.daimler.mbcommonkit.preferences.chunks.EncryptedSharedPreferencesChunkWriter.Companion.MAX_STRING_LENGTH
import com.daimler.mbcommonkit.preferences.getEncryptedSharedPreferencesWithMockContext
import com.daimler.mbcommonkit.preferences.mockCurrentDeviceHuaweiWithAndroid6
import com.daimler.mbcommonkit.security.RandomStringGenerator
import io.mockk.clearAllMocks
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class EncryptedSharedPreferencesChunkWriterTest {

    private lateinit var sharedPreferencesFake: SharedPreferencesFake
    private lateinit var encryptedSharedPreferences: EncryptedSharedPreferencesWithChunks
    private lateinit var chunkWriter: EncryptedSharedPreferencesChunkWriter

    @BeforeEach
    fun setupChunkWriter() {
        sharedPreferencesFake = SharedPreferencesFake()
        encryptedSharedPreferences = getEncryptedSharedPreferencesWithMockContext(
            sharedPreferencesFake
        )
        chunkWriter = EncryptedSharedPreferencesChunkWriter(
            encryptedSharedPreferences.edit()::putString
        )
        mockCurrentDeviceHuaweiWithAndroid6()
    }

    @AfterEach
    fun cleanupMocks() {
        clearAllMocks()
    }

    @Test
    fun `string should be written in chunks if longer than max length`(softly: SoftAssertions) {
        val chunkCount = 3
        val longString =
            RandomStringGenerator().generateString(MAX_STRING_LENGTH * chunkCount)
        chunkWriter.putStringInChunksIfTooLong(TEST_KEY, longString)

        // underlying fake should not contain normal "TEST_KEY" since it is written as chunks
        softly.assertThat(sharedPreferencesFake.contains(TEST_KEY)).isFalse
        softly.assertThat(encryptedSharedPreferences.contains(TEST_KEY)).isTrue

        val readString = encryptedSharedPreferences.getString(TEST_KEY, "")
        softly.assertThat(readString).isEqualTo(longString)
    }

    @Test
    fun `string is not written in chunks if not longer than max length`(softly: SoftAssertions) {
        val shorterString = RandomStringGenerator().generateString(MAX_STRING_LENGTH)
        chunkWriter.putStringInChunksIfTooLong(TEST_KEY, shorterString)

        softly.assertThat(encryptedSharedPreferences.contains(chunkKey(TEST_KEY, 0))).isFalse
        softly.assertThat(encryptedSharedPreferences.getString(TEST_KEY, ""))
            .isEqualTo(shorterString)
    }
}
