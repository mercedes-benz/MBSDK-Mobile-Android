package com.daimler.mbingresskit.implementation

import com.daimler.mbingresskit.common.AuthenticationState
import com.daimler.mbingresskit.common.AuthorizationException
import com.daimler.mbingresskit.common.AuthorizationResponse
import com.daimler.mbingresskit.common.Token
import com.daimler.mbingresskit.common.UserCredentials
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbingresskit.implementation.network.ropc.TokenRepository
import com.daimler.mbingresskit.login.AuthenticationStateTokenState
import com.daimler.mbingresskit.login.LoginActionHandler
import com.daimler.mbingresskit.login.LoginFailure
import com.daimler.mbingresskit.login.LoginProcess
import com.daimler.mbingresskit.login.LoginService
import com.daimler.mbingresskit.login.UserCredentialsLoginState
import com.daimler.mbingresskit.login.error.SessionExpiredException
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.exception.ResponseException
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.Task
import com.daimler.mbnetworkkit.task.TaskObject
import java.net.HttpURLConnection

internal class UserCredentialsLoginService(
    private val authStateRepository: AuthstateRepository,
    private val userCredentials: UserCredentials?,
    private val tokenRepository: TokenRepository,
    private val deviceId: String,
    initialLoginState: UserCredentialsLoginState = UserCredentialsLoginState.LoggedOut
) : LoginService, LoginActionHandler {

    private var loginProcess: LoginProcess = LoginProcess(this, initialLoginState)

    private var loginTask: Task<Unit, ResponseError<out RequestError>?> = TaskObject()

    private var logoutTask: Task<Unit, ResponseError<out RequestError>?> = TaskObject()

    // LoginService START

    override fun startLogin(): FutureTask<Unit, ResponseError<out RequestError>?> {
        loginTask = TaskObject()
        if (userCredentials != null) {
            loginProcess.login()
        } else {
            loginTask.fail(ResponseError.requestError(LoginFailure.MISSING_USER_CREDENTIALS))
        }
        return loginTask.futureTask()
    }

    override fun startLogout(): FutureTask<Unit, ResponseError<out RequestError>?> {
        logoutTask = TaskObject()
        // We always confirm the logout here.
        tokenRepository.logout(
            authStateRepository.readAuthState().getToken()
        )
            .onAlways { _, _, _ -> logoutConfirmed() }
        return logoutTask.futureTask()
    }

    // LoginService END

    // LoginStateService START

    override fun authorizationStarted() = Unit

    override fun receivedAuthResponse(
        authResponse: AuthorizationResponse?,
        authException: AuthorizationException?
    ) = Unit

    override fun loginCancelled() = Unit

    override fun receivedLogoutResponse(
        authResponse: AuthorizationResponse?,
        authException: AuthorizationException?
    ) = Unit

    override fun logoutCancelled() = Unit

    override fun logoutConfirmed() {
        logoutTask.complete(Unit)
        loginProcess.logout()
    }

    // LoginEndService END

    // LoginActionHandler START

    override fun authorize() = Unit

    override fun requestToken() {
        userCredentials?.password ?: run {
            MBLoggerKit.e("Request Token failed because of missing >userCredentials>")
            loginTask.fail(ResponseError.requestError(LoginFailure.MISSING_USER_CREDENTIALS))
            return
        }

        MBLoggerKit.d("Request Token: user=${userCredentials.userName}")
        tokenRepository.requestToken(
            deviceId = deviceId,
            userCredentials = userCredentials
        )
            .onComplete {
                MBLoggerKit.d("TokenRepository call to request token finished: $it")
                val authState = authStateRepository.readAuthState()
                authState.update(it)
                authStateRepository.saveAuthState(authState)
                MBLoggerKit.d("Updated Token in Authstate ")
                loginProcess.tokenReceived()
            }
            .onFailure {
                MBLoggerKit.d("TokenRepository call to request token failed: ${it?.requestError}")
                loginTask.fail(it)
            }
    }

    override fun refreshToken(): FutureTask<Token, Throwable?> {
        val deferredTask = TaskObject<Token, Throwable?>()
        tokenRepository.refreshToken(
            token = authStateRepository.readAuthState().getToken()
        )
            .onFailure { error ->
                if (error is ResponseException &&
                    (isBadRequest(error.responseCode) || isUnauthorized(error.responseCode))
                ) {
                    MBLoggerKit.d("Received status code ${error.responseCode} with body \"${error.errorBody}\"")
                    deferredTask.fail(SessionExpiredException(error.responseCode, error.errorBody))
                } else {
                    MBLoggerKit.d("TokenRepository call to refresh token failed: $error")
                    deferredTask.fail(error)
                }
            }.onComplete {
                MBLoggerKit.d("TokenRepository call to refresh token finished: $it")
                val authState = authStateRepository.readAuthState()
                authState.update(it)
                authStateRepository.saveAuthState(authState)
                MBLoggerKit.d("Updated Token in Authstate ")
                deferredTask.complete(it)
            }
        return deferredTask.futureTask()
    }

    private fun isBadRequest(responseCode: Int) = responseCode == HttpURLConnection.HTTP_BAD_REQUEST

    private fun isUnauthorized(responseCode: Int) =
        responseCode == HttpURLConnection.HTTP_UNAUTHORIZED

    override fun finishLogin() {
        val authState = authStateRepository.readAuthState()
        val tokenState = AuthenticationStateTokenState(authState).getTokenState()
        if (authState.isAuthorized()) {
            MBLoggerKit.d("Login finished: current state = ${tokenState.name}")
            loginTask.complete(Unit)
        } else {
            MBLoggerKit.d("Login failed: current state = ${tokenState.name}")
            loginTask.fail(ResponseError.requestError(LoginFailure.AUTHORIZATION_FAILED))
        }
    }

    override fun finishLogout() {
        authStateRepository.saveAuthState(AuthenticationState())
    }

    override fun changeAuthenticationType(authenticationType: AuthenticationType) {
        tokenRepository.replaceAuthenticationType(authenticationType)
    }

    // LoginActionHandler STO
}
