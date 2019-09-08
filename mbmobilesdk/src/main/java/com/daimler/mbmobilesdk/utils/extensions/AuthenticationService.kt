package com.daimler.mbmobilesdk.utils.extensions

import com.daimler.mbingresskit.login.AuthenticationService
import com.daimler.mbingresskit.login.TokenState

fun AuthenticationService.isLoggedIn(): Boolean {
    val state = getTokenState()
    return TokenState.LOGGEDIN == state || TokenState.AUTHORIZED == state
}