package com.daimler.mbingresskit.implementation

import android.content.Context
import android.content.pm.PackageInfo
import com.daimler.mbcommonkit.extensions.getEncryptedSharedPreferences
import com.daimler.mbcommonkit.preferences.EncryptedSharedPreferences
import com.daimler.mbcommonkit.security.Crypto
import com.daimler.mbingresskit.util.JWTHelper
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SsoAccountPrefsTest {
    private val mockContext: Context = mockk(relaxed = true)
    private val mockPackageInfo: PackageInfo = mockk()
    private val mockSharedPrefs: EncryptedSharedPreferences = mockk(relaxed = true)
    private val sharedUserId = "1234"
    private val packageSharedUserId = "5678"
    private val crypto = mockk<Crypto>()
    private val cryptoProvider = mockk<CryptoProvider>()
    private lateinit var subject: SsoAccountPrefs

    @BeforeEach
    fun setup() {
        mockPackageInfo.sharedUserId = sharedUserId
        every {
            mockContext.packageManager.getPackageInfo(
                any<String>(),
                any()
            )
        } returns mockPackageInfo

        mockkStatic("com.daimler.mbcommonkit.extensions.SharedPreferencesKt")
        every {
            mockContext.getEncryptedSharedPreferences(
                any(),
                any(),
                any(),
                any<Crypto>()
            )
        } returns mockSharedPrefs

        mockkObject(JWTHelper)
        every { JWTHelper.extractClaimFromToken(any(), any()) } returns SAMPLE_CIAM_ID

        every { cryptoProvider.crypto } returns crypto

        subject = SsoAccountPrefs(mockContext, sharedUserId, packageSharedUserId, cryptoProvider)
    }

    @AfterEach
    fun after() {
        clearAllMocks()
    }

    @Test
    fun `should throw exception if shared user ID does not match`() {
        mockPackageInfo.sharedUserId = packageSharedUserId

        assertThrows<SsoAccountPrefs.SharedUserIdNotSetException> {
            subject.initialize()
        }
    }

    @Test
    fun `should read user login ID from prefs`() {
        val loginId = "my-login-id"
        every { mockSharedPrefs.getString(KEY_LOGIN_ID, null) } returns loginId

        assertEquals(subject.readUserLoginId(), loginId)
    }

    @Test
    fun `should save user login ID to prefs`() {
        val spyPrefs = spyk(subject, recordPrivateCalls = true)

        val mockPackageInfo: PackageInfo = mockk(relaxed = true)
        mockPackageInfo.packageName = "a.b.c"
        every {
            spyPrefs["packageInfosWithSharedUserId"](any<String>())
        } returns listOf(mockPackageInfo, mockPackageInfo)

        every { spyPrefs.clearUserLoginId() } returns Unit
        every { spyPrefs["getPreferences"](any<Context>()) } returns mockSharedPrefs

        spyPrefs.saveUserLoginId(SAMPLE_CIAM_ID)
        // should clear before saving
        verify(exactly = 1) { spyPrefs.clearUserLoginId() }

        // access preferences twice because of 2 packages
        verify(exactly = 2) { spyPrefs["getPreferences"](any<Context>()) }
    }

    companion object {
        private const val KEY_AUTH_STATE = "AuthenticationState"
        private const val KEY_LOGIN_ID = "LoginId"
        private const val SAMPLE_CIAM_ID = "asfk12412kl214"
        private const val ANY_VALID_JWT_TOKEN = "{\n" +
            "\t\"token\": {\n" +
            "\t\t\"jwtToken\": {\n" +
            "\t\t\t\"plainToken\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjaWFtaWQiOiJ0ZXN0IiwidHlwIjoidHlwIn0.-afZ8n2_TIFIXeMQ1bGvrqO6LOYAfihQwUjomAHIOFg\"\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}"
    }
}
