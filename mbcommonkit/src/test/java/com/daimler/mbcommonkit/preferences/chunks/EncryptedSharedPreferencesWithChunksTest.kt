package com.daimler.mbcommonkit.preferences.chunks

import com.daimler.mbcommonkit.preferences.SharedPreferencesFake
import com.daimler.mbcommonkit.preferences.TEST_KEY
import com.daimler.mbcommonkit.preferences.chunks.EncryptedSharedPreferencesChunkWriter.Companion.MAX_STRING_LENGTH
import com.daimler.mbcommonkit.preferences.encrypt
import com.daimler.mbcommonkit.preferences.getEncryptedSharedPreferencesWithMockContext
import com.daimler.mbcommonkit.preferences.mockStandardDeviceBrand
import com.daimler.mbcommonkit.security.RandomStringGenerator
import io.mockk.clearAllMocks
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class EncryptedSharedPreferencesWithChunksTest {

    private lateinit var sharedPreferencesFake: SharedPreferencesFake
    private lateinit var sharedPreferencesWithChunks: EncryptedSharedPreferencesWithChunks

    @BeforeEach
    fun setUp() {
        mockStandardDeviceBrand()
        sharedPreferencesFake = SharedPreferencesFake()
        sharedPreferencesWithChunks = getEncryptedSharedPreferencesWithMockContext(
            sharedPreferencesFake
        )
    }

    @AfterEach
    fun clearMocks() {
        clearAllMocks()
    }

    @Test
    fun `getString should read chunks if chunk key exists`() {
        val tooLongString = tooLongString()
        putChunks(tooLongString)

        val chunkString = sharedPreferencesWithChunks.getString(
            TEST_KEY,
            DEFAULT_VALUE
        )
        assertEquals(tooLongString, chunkString)
    }

    @Test
    fun `getString should not read chunks if no chunk key`() {
        putTestValue()
        val testValue = sharedPreferencesWithChunks.getString(
            TEST_KEY,
            DEFAULT_VALUE
        )
        assertEquals(TEST_VALUE, testValue)
    }

    @Test
    fun `getString should not delete values if exactly max length`() {
        val testValue = RandomStringGenerator().generateString(MAX_STRING_LENGTH)
        sharedPreferencesFake.edit().putString(encrypt(TEST_KEY), testValue)
        val testValueRead = sharedPreferencesWithChunks.getString(
            TEST_KEY,
            DEFAULT_VALUE
        )
        assertEquals(testValue, testValueRead)
    }

    @Test
    fun `getString should return default and delete existing string if decryption failed`(softly: SoftAssertions) {
        // use the underlying sharedPreferencesFake to ensure string is not written in chunks
        sharedPreferencesFake.edit().putString(encrypt(TEST_KEY), tooLongString())
        sharedPreferencesFake.edit().putString(encrypt("$TEST_KEY-decryption-status"), DecryptionStatus.FAIL.name)
        val testValue = sharedPreferencesWithChunks.getString(
            TEST_KEY,
            DEFAULT_VALUE
        )

        softly.assertThat(testValue).isEqualTo(DEFAULT_VALUE)
        softly.assertThat(sharedPreferencesWithChunks.contains(TEST_KEY)).isFalse
    }

    private fun putTestValue(testValue: String = TEST_VALUE) {
        sharedPreferencesWithChunks.edit().putString(
            TEST_KEY,
            testValue
        )
    }

    private fun putChunks(tooLongString: String) {
        sharedPreferencesWithChunks.edit().putString(TEST_KEY, tooLongString)
    }

    companion object {
        private const val DEFAULT_VALUE = "defaultValue"
        private const val TEST_VALUE = "testValue"
    }
}
