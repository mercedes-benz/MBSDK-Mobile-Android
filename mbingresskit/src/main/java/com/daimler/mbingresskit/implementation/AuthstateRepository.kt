package com.daimler.mbingresskit.implementation

import com.daimler.mbingresskit.common.AuthenticationState

internal interface AuthstateRepository {
    fun saveAuthState(authState: AuthenticationState)
    fun readAuthState(): AuthenticationState
    fun clearAuthState()
    fun saveUserLoginId(loginId: String)
    fun clearUserLoginId()
    fun readUserLoginId(): String?
}
