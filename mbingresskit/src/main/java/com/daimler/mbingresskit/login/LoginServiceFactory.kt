package com.daimler.mbingresskit.login

import com.daimler.mbingresskit.common.UserCredentials
import com.daimler.mbingresskit.implementation.AuthstateRepository
import com.daimler.mbingresskit.implementation.UserCredentialsLoginService
import com.daimler.mbingresskit.implementation.network.ropc.TokenRepository

internal class LoginServiceFactory(
    private val authStateRepository: AuthstateRepository,
    private val tokenRepository: TokenRepository,
    private val deviceId: String
) {

    fun createLoginService(
        userCredentials: UserCredentials? = null
    ): LoginService = UserCredentialsLoginService(
        authStateRepository,
        userCredentials,
        tokenRepository,
        deviceId
    )
}
