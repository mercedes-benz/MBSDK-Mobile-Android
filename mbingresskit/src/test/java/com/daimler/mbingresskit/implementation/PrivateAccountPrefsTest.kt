package com.daimler.mbingresskit.implementation

import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbcommonkit.extensions.getEncryptedSharedPreferences
import com.daimler.mbcommonkit.preferences.EncryptedSharedPreferences
import com.daimler.mbingresskit.common.AuthenticationState
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PrivateAccountPrefsTest {

    private val mockContext: Context = mockk(relaxed = true)
    private val mockSharedPrefs: EncryptedSharedPreferences = mockk(relaxed = true)
    private val mockEditor: SharedPreferences.Editor = mockk(relaxed = true)
    private lateinit var subject: PrivateAccountPrefs

    @BeforeEach
    fun setup() {
        every { mockSharedPrefs.getString(KEY_AUTH_STATE, "") } returns ""

        mockkStatic("com.daimler.mbcommonkit.extensions.SharedPreferencesKt")
        every {
            mockContext.getEncryptedSharedPreferences(
                any(),
                any(),
                any()
            )
        } returns mockSharedPrefs

        mockkStatic("com.daimler.mbingresskit.implementation.SharedPreferencesKt")
        every { mockSharedPrefs.edit() } returns mockEditor

        subject = PrivateAccountPrefs(mockContext, "5678")
    }

    @AfterEach
    fun after() {
        clearAllMocks()
    }

    @Test
    fun `should save the auth state`() {
        subject.saveAuthState(AuthenticationState())

        verify { mockEditor.remove(KEY_AUTH_STATE) }
        verify { mockEditor.putString(KEY_AUTH_STATE, any()) }
    }

    @Test
    fun `should read the auth state`() {
        val state = AuthenticationState()
        subject.saveAuthState(state)

        val authState = subject.readAuthState()

        assertEquals(state.getToken().accessToken, authState.getToken().accessToken)
    }

    companion object {
        private const val KEY_AUTH_STATE = "AuthenticationState"
    }
}
