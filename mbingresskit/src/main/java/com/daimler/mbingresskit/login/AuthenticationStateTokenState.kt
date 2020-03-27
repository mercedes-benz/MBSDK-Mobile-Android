package com.daimler.mbingresskit.login

import com.daimler.mbingresskit.common.AuthenticationState

internal class AuthenticationStateTokenState(
    private val authenticationState: AuthenticationState
) : TokenStateService {

    override fun getTokenState(): TokenState {
        return when {
            authenticationState.isAuthorized() -> TokenState.AUTHORIZED
            authenticationState.isValidRefreshToken() -> TokenState.LOGGEDIN
            else -> TokenState.LOGGEDOUT
        }
    }
}
