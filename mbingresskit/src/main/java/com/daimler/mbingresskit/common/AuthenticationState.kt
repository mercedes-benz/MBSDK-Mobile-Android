package com.daimler.mbingresskit.common

import com.daimler.mbingresskit.common.authentication.AuthenticationType

const val MINIMUM_TOKEN_REST_LIFETIME_IN_MS = 60000

internal class AuthenticationState(
    private var token: Token = Token("", "", "", JwtToken("", ""), 0, 0),
    private var authorizationResponse: AuthorizationResponse? = null,
    private var authorizationException: AuthorizationException? = null,
    private var tokenRefreshRequired: Boolean = false
) {

    internal fun update(
        authorizationResponse: AuthorizationResponse?,
        authorizationException: AuthorizationException? = null
    ) {
        this.authorizationResponse = authorizationResponse
        this.authorizationException = authorizationException
    }

    fun update(token: Token) {
        this.token = token
        tokenRefreshRequired = false
    }

    internal fun lastAuthorizationResponse() = authorizationResponse

    fun lastAuthorizationException() = authorizationException

    fun isAuthorized(): Boolean {
        return (authorizationException == null).and(token.accessToken.isNotEmpty())
            .and(needsAccessTokenRefresh().not())
    }

    fun forceTokenRefresh() {
        tokenRefreshRequired = true
    }

    fun needsAccessTokenRefresh() =
        tokenRefreshRequired.or(token.tokenExpirationDate - MINIMUM_TOKEN_REST_LIFETIME_IN_MS <= System.currentTimeMillis())

    fun isValidRefreshToken(): Boolean {
        return when {
            token.typ == TYPE_OFFLINE_TOKEN -> true
            token.authenticationType == AuthenticationType.KEYCLOAK -> {
                token.refreshTokenExpirationDate?.let { expiration ->
                    token.refreshToken.isNotEmpty() && expiration >= System.currentTimeMillis()
                } ?: false
            }
            // refreshtoken is valid if the session is used at least every 30 days
            token.authenticationType == AuthenticationType.CIAM -> token.refreshToken.isNotEmpty()
            else -> false
        }
    }

    fun getToken() = token

    fun authenticationType() = token.authenticationType

    fun authorizationCode() = authorizationResponse?.authorizationCode ?: ""
}

const val TYPE_OFFLINE_TOKEN = "Offline"
