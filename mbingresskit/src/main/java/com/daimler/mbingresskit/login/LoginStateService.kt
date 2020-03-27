package com.daimler.mbingresskit.login

import com.daimler.mbingresskit.common.AuthorizationException
import com.daimler.mbingresskit.common.AuthorizationResponse

internal interface LoginStateService {
    fun authorizationStarted()
    fun receivedAuthResponse(authResponse: AuthorizationResponse?, authException: AuthorizationException?)
    fun loginCancelled()

    fun receivedLogoutResponse(authResponse: AuthorizationResponse?, authException: AuthorizationException?)
    fun logoutCancelled()
    fun logoutConfirmed()
}
