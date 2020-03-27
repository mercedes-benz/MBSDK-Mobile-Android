package com.daimler.mbingresskit.login

interface AuthenticationService {
    fun getTokenState(): TokenState
    fun needsTokenRefresh(): Boolean
    fun forceTokenRefresh()
    fun getUserLoginId(): String?

    fun isLoggedIn(): Boolean {
        val state = getTokenState()
        return state is TokenState.LOGGEDIN || state is TokenState.AUTHORIZED
    }
}
