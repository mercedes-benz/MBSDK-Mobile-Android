package com.daimler.mbingresskit

import com.daimler.mbingresskit.common.AuthenticationState
import com.daimler.mbingresskit.common.JwtToken
import com.daimler.mbingresskit.common.MINIMUM_TOKEN_REST_LIFETIME_IN_MS
import com.daimler.mbingresskit.common.Token
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbingresskit.login.AuthenticationStateTokenState
import com.daimler.mbingresskit.login.TokenState
import com.daimler.mbingresskit.login.TokenStateService
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class AuthenticationStateTest {
    @ParameterizedTest
    @EnumSource(AuthenticationType::class)
    fun authenticationSuccess(authenticationType: AuthenticationType) {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                Long.MAX_VALUE,
                0,
                authenticationType = authenticationType
            )
        )
        assertTrue(authenticationState.isAuthorized())
    }

    @ParameterizedTest
    @EnumSource(AuthenticationType::class)
    fun sameAccessToken(authenticationType: AuthenticationType) {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                0,
                0,
                authenticationType = authenticationType
            )
        )
        assertEquals(TEST_ACCESS_TOKEN, authenticationState.getToken().accessToken)
    }

    @ParameterizedTest
    @EnumSource(AuthenticationType::class)
    fun sameRefreshToken(authenticationType: AuthenticationType) {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                0,
                0,
                authenticationType = authenticationType
            )
        )
        assertEquals(TEST_REFRESH_TOKEN, authenticationState.getToken().refreshToken)
    }

    @Test
    fun notAuthorizedBecauseOfNoToken() {
        val authenticationState = AuthenticationState()
        assertFalse(authenticationState.isAuthorized())
    }

    @ParameterizedTest
    @EnumSource(AuthenticationType::class)
    fun validRefreshTokenBecauseItsAnOfflineToken(authenticationType: AuthenticationType) {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_Offline,
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                Long.MAX_VALUE,
                0,
                authenticationType = authenticationType
            )
        )
        assertEquals(true, authenticationState.isValidRefreshToken())
    }

    @ParameterizedTest
    @EnumSource(AuthenticationType::class)
    fun authorizedBecauseItsAnOfflineToken(authenticationType: AuthenticationType) {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_Offline,
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                Long.MAX_VALUE,
                0,
                authenticationType = authenticationType
            )
        )
        assertTrue(authenticationState.isAuthorized())
    }

    @ParameterizedTest
    @EnumSource(AuthenticationType::class)
    fun notAuthorizedBecauseOfEmptyAccessToken(authenticationType: AuthenticationType) {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_REFRESH,
                "",
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                0,
                0,
                authenticationType = authenticationType
            )
        )
        assertFalse(authenticationState.isAuthorized())
    }

    @ParameterizedTest
    @EnumSource(AuthenticationType::class)
    fun notAuthorizedBecauseOfEmptyRefreshToken(authenticationType: AuthenticationType) {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                "",
                JwtToken(TEST_JWT_TOKEN, ""),
                0,
                0,
                authenticationType = authenticationType
            )
        )
        assertFalse(authenticationState.isAuthorized())
    }

    @ParameterizedTest
    @EnumSource(AuthenticationType::class)
    fun notAuthorizedBecauseOfEmptyJwtToken(authenticationType: AuthenticationType) {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                TEST_ACCESS_TOKEN,
                JwtToken("", ""),
                0,
                0,
                authenticationType = authenticationType
            )
        )
        assertFalse(authenticationState.isAuthorized())
    }

    @ParameterizedTest
    @EnumSource(AuthenticationType::class)
    fun tokenRefreshRequiredIfExpired(authenticationType: AuthenticationType) {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                0,
                0,
                authenticationType = authenticationType
            )
        )
        assertTrue(authenticationState.needsAccessTokenRefresh())
    }

    @ParameterizedTest
    @EnumSource(AuthenticationType::class)
    fun tokenRefreshRequired30SecondsBeforeExpiration(authenticationType: AuthenticationType) {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_REFRESH,
                "",
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                System.currentTimeMillis() + 30000,
                Long.MAX_VALUE,
                authenticationType = authenticationType
            )

        )
        assertTrue(authenticationState.needsAccessTokenRefresh())
    }

    @Test
    fun tokenRefreshNotRequiredIfValidForMoreThan60Seconds() {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_REFRESH,
                "",
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                System.currentTimeMillis() + MINIMUM_TOKEN_REST_LIFETIME_IN_MS + 1000,
                Long.MAX_VALUE,
                authenticationType = AuthenticationType.KEYCLOAK
            )
        )
        assertFalse(authenticationState.needsAccessTokenRefresh())
    }

    @Test
    fun tokenRefreshRequiredIfForced() {
        val authenticationState = AuthenticationState()
        authenticationState.forceTokenRefresh()
        assertTrue(authenticationState.needsAccessTokenRefresh())
    }

    @ParameterizedTest
    @EnumSource(AuthenticationType::class)
    fun accessTokenNotExpired(authenticationType: AuthenticationType) {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                Long.MAX_VALUE,
                Long.MAX_VALUE,
                authenticationType = authenticationType
            )
        )
        assertTrue(authenticationState.isAuthorized())
    }

    @Test
    fun loggedOutNoToken() {
        val authenticationState = AuthenticationState()
        val tokenStateService: TokenStateService =
            AuthenticationStateTokenState(authenticationState)
        assertEquals(TokenState.LOGGEDOUT, tokenStateService.getTokenState())
    }

    @Test
    fun loggedOutExpiredRefreshTokenKeycloak() {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                0,
                0,
                authenticationType = AuthenticationType.KEYCLOAK
            )
        )
        val tokenStateService: TokenStateService =
            AuthenticationStateTokenState(authenticationState)
        assertEquals(TokenState.LOGGEDOUT, tokenStateService.getTokenState())
    }

    @Test
    fun loggedOutExpiredRefreshTokenCiam() {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_ACCESS_TOKEN, ""),
                0,
                0,
                authenticationType = AuthenticationType.CIAM
            )
        )
        val tokenStateService: TokenStateService =
            AuthenticationStateTokenState(authenticationState)
        assertEquals(TokenState.LOGGEDIN, tokenStateService.getTokenState())
    }

    @ParameterizedTest
    @EnumSource(AuthenticationType::class)
    fun loggedOutEmptyRefreshToken(authenticationType: AuthenticationType) {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                "",
                JwtToken(TEST_JWT_TOKEN, ""),
                0,
                Long.MAX_VALUE,
                authenticationType = authenticationType
            )
        )
        val tokenStateService: TokenStateService =
            AuthenticationStateTokenState(authenticationState)
        assertEquals(TokenState.LOGGEDOUT, tokenStateService.getTokenState())
    }

    @ParameterizedTest
    @EnumSource(AuthenticationType::class)
    fun authorizedState(authenticationType: AuthenticationType) {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                Long.MAX_VALUE,
                Long.MAX_VALUE,
                authenticationType = authenticationType
            )
        )
        val tokenStateService: TokenStateService =
            AuthenticationStateTokenState(authenticationState)
        assertEquals(TokenState.AUTHORIZED, tokenStateService.getTokenState())
    }

    @ParameterizedTest
    @EnumSource(AuthenticationType::class)
    fun loggedInExpiredAccessToken(authenticationType: AuthenticationType) {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_REFRESH,
                TEST_ACCESS_TOKEN,
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                0,
                Long.MAX_VALUE,
                authenticationType = authenticationType
            )
        )
        val tokenStateService: TokenStateService =
            AuthenticationStateTokenState(authenticationState)
        assertEquals(TokenState.LOGGEDIN, tokenStateService.getTokenState())
    }

    @ParameterizedTest
    @EnumSource(AuthenticationType::class)
    fun loggedInEmptyAccessToken(authenticationType: AuthenticationType) {
        val authenticationState = AuthenticationState()
        authenticationState.update(
            Token(
                TOKEN_TYPE_REFRESH,
                "",
                TEST_REFRESH_TOKEN,
                JwtToken(TEST_JWT_TOKEN, ""),
                Long.MAX_VALUE,
                Long.MAX_VALUE,
                authenticationType = authenticationType
            )
        )
        val tokenStateService: TokenStateService =
            AuthenticationStateTokenState(authenticationState)
        assertEquals(TokenState.LOGGEDIN, tokenStateService.getTokenState())
    }

    companion object {
        private const val TEST_ACCESS_TOKEN = "1234asdf5678jklö"
        private const val TEST_REFRESH_TOKEN = "asdf1234jklö5678"
        private const val TEST_JWT_TOKEN = "asdfjklöasdfjklö"
        private const val TOKEN_TYPE_REFRESH = "Refresh"
        private const val TOKEN_TYPE_Offline = "Offline"
    }
}
