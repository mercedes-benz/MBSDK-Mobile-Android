package com.daimler.mbingresskit.login

interface LoginState {
    fun login(loginProcess: LoginProcess): LoginFailure?
    fun authorized(loginProcess: LoginProcess): LoginFailure?
    fun tokenReceived(loginProcess: LoginProcess): LoginFailure?
    fun logout(loginProcess: LoginProcess)
}
