package com.daimler.mbcommonkit.preferences.chunks

import com.daimler.mbcommonkit.preferences.SharedPreferencesFake
import com.daimler.mbcommonkit.preferences.TEST_KEY
import com.daimler.mbcommonkit.preferences.getEncryptedSharedPreferencesWithMockContext
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class EncryptedEditorWithChunksTest {

    private lateinit var sharedPreferencesFake: SharedPreferencesFake
    private lateinit var sharedPreferencesWithChunks: EncryptedSharedPreferencesWithChunks

    @BeforeEach
    fun setupSharedPreferences() {
        sharedPreferencesFake = SharedPreferencesFake()
        sharedPreferencesWithChunks =
            getEncryptedSharedPreferencesWithMockContext(sharedPreferencesFake)
    }

    @Test
    fun `putString should use chunks`(softly: SoftAssertions) {
        putTooLongString()

        // sharedPreferencesWithChunks will also check for chunk keys whereas the fake won't
        softly.assertThat(sharedPreferencesWithChunks.contains(TEST_KEY)).isTrue
        softly.assertThat(sharedPreferencesFake.contains(TEST_KEY)).isFalse
    }

    @Test
    fun `value is saved as empty string when null`() {
        sharedPreferencesWithChunks.edit().putString(TEST_KEY, null)
        assertThat(sharedPreferencesWithChunks.getString(TEST_KEY, null)).isEmpty()
    }

    @Test
    fun `removes chunks`() {
        putTooLongString()
        assertThat(sharedPreferencesWithChunks.contains(TEST_KEY)).isTrue

        sharedPreferencesWithChunks.edit().remove(TEST_KEY)
        assertThat(sharedPreferencesWithChunks.contains(TEST_KEY)).isFalse
    }

    @Test
    fun `remove normal value`() {
        val testValue = "testValue"
        sharedPreferencesWithChunks.edit().putString(TEST_KEY, testValue)
        assertThat(sharedPreferencesWithChunks.contains(TEST_KEY)).isTrue

        sharedPreferencesWithChunks.edit().remove(TEST_KEY)
        assertThat(sharedPreferencesWithChunks.contains(TEST_KEY)).isFalse
    }

    private fun putTooLongString() {
        sharedPreferencesWithChunks.edit().putString(TEST_KEY, tooLongString())
    }
}
