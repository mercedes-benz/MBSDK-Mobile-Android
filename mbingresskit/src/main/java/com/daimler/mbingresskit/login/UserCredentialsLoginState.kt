package com.daimler.mbingresskit.login

sealed class UserCredentialsLoginState : LoginState {
    abstract class BaseUserCredentialsLoginState : UserCredentialsLoginState() {
        override fun authorized(loginProcess: LoginProcess): LoginFailure? = LoginFailure.OPERATION_NOT_REQUIRED

        override fun logout(loginProcess: LoginProcess) {
            loginProcess.apply {
                loginState = LoggedOut
                finishLogout()
            }
        }
    }

    object LoggedOut : BaseUserCredentialsLoginState() {
        override fun login(loginProcess: LoginProcess): LoginFailure? {
            loginProcess.apply {
                loginState = RequestingToken
                requestToken()
            }
            return null
        }

        override fun tokenReceived(loginProcess: LoginProcess): LoginFailure? = LoginFailure.TOKENRECEIVED_CALLED_WHEN_CLIENT_NOT_AUTHORIZED
    }

    object RequestingToken : BaseUserCredentialsLoginState() {
        override fun login(loginProcess: LoginProcess): LoginFailure? = LoginFailure.LOGIN_CALLED_WHEN_LOGIN_ALREADY_STARTED

        override fun tokenReceived(loginProcess: LoginProcess): LoginFailure? {
            loginProcess.apply {
                loginState = LoggedIn
                finishLogin()
            }
            return null
        }
    }

    object LoggedIn : BaseUserCredentialsLoginState() {
        override fun login(loginProcess: LoginProcess): LoginFailure? = LoginFailure.LOGIN_CALLED_WHEN_ALREADY_LOGGED_IN

        override fun tokenReceived(loginProcess: LoginProcess): LoginFailure? = LoginFailure.TOKENRECEIVED_CALLED_WHEN_ALREADY_LOGGED_IN
    }
}
