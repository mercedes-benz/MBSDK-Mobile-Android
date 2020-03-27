package com.daimler.mbcommonkit.preferences

import android.content.Context
import com.daimler.mbcommonkit.extensions.getEncryptedSharedPreferences
import com.daimler.mbcommonkit.preferences.chunks.EncryptedSharedPreferencesWithChunks
import io.mockk.clearAllMocks
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SharedPreferencesTest {
    private lateinit var context: Context

    @BeforeEach
    fun setupContext() {
        context = mockContext(SharedPreferencesFake())
    }

    @AfterEach
    fun cleanupMocks() {
        clearAllMocks()
    }

    @Test
    fun `with huawei device should return EncryptedSharedPreferencesWithChunks`() {
        mockCurrentDeviceHuaweiWithAndroid6()
        val sharedPreferences =
            context.getEncryptedSharedPreferences("test", "test", crypto = mockCrypto())
        assertThat(sharedPreferences).isInstanceOf(EncryptedSharedPreferencesWithChunks::class.java)
    }

    @Test
    fun `with normal device should return EncryptedSharedPreferences`() {
        mockStandardDeviceBrand()
        val sharedPreferences =
            context.getEncryptedSharedPreferences("test", "test", crypto = mockCrypto())
        assertThat(sharedPreferences).isInstanceOf(EncryptedSharedPreferences::class.java)
    }
}
