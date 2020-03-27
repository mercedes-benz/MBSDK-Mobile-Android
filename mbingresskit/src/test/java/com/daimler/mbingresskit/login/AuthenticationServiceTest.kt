package com.daimler.mbingresskit.login

import io.mockk.every
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class AuthenticationServiceTest {

    @Test
    fun isLoggedIn_stateAuthorized() {
        val authService = spyk<DummyAuthService> {
            every { getTokenState() } returns TokenState.AUTHORIZED
        }
        val isLoggedIn = authService.isLoggedIn()
        assertTrue(isLoggedIn)
    }

    @Test
    fun isLoggedIn_stateLoggedIn() {
        val authService = spyk<DummyAuthService> {
            every { getTokenState() } returns TokenState.LOGGEDIN
        }
        val isLoggedIn = authService.isLoggedIn()
        assertTrue(isLoggedIn)
    }

    @Test
    fun isLoggedIn_stateLoggedOut() {
        val authService = spyk<DummyAuthService> {
            every { getTokenState() } returns TokenState.LOGGEDOUT
        }
        val isLoggedIn = authService.isLoggedIn()
        assertFalse(isLoggedIn)
    }
}

private class DummyAuthService : AuthenticationService {
    override fun getTokenState(): TokenState {
        TODO("not implemented")
    }

    override fun needsTokenRefresh(): Boolean {
        TODO("not implemented")
    }

    override fun forceTokenRefresh() {
        TODO("not implemented")
    }

    override fun getUserLoginId(): String? {
        TODO("not implemented")
    }
}
