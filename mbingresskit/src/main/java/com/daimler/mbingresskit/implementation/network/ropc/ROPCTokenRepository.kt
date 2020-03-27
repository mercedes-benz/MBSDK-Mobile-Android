package com.daimler.mbingresskit.implementation.network.ropc

import com.daimler.mbingresskit.common.Token
import com.daimler.mbingresskit.common.UserCredentials
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbingresskit.implementation.network.ropc.tokenprovider.TokenService
import com.daimler.mbingresskit.implementation.network.ropc.tokenprovider.TokenServiceFactory
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

internal class ROPCTokenRepository(
    private var currentTokenService: TokenService,
    private val tokenServiceFactory: TokenServiceFactory
) : TokenRepository {

    override fun requestToken(
        deviceId: String,
        userCredentials: UserCredentials
    ): FutureTask<Token, ResponseError<out RequestError>?> = currentTokenService.requestToken(
        deviceId = deviceId,
        userName = userCredentials.userName,
        password = userCredentials.password.orEmpty()
    )

    override fun refreshToken(
        token: Token
    ): FutureTask<Token, Throwable?> = currentTokenService.refreshToken(token)

    override fun logout(
        token: Token
    ): FutureTask<Unit, ResponseError<out RequestError>?> = currentTokenService.logout(token)

    override fun replaceAuthenticationType(authenticationType: AuthenticationType) {
        if (currentTokenService.authenticationType != authenticationType) {
            this.currentTokenService = tokenServiceFactory.createTokenService(authenticationType)
        }
    }

    override fun currentAuthenticationType(): AuthenticationType =
        currentTokenService.authenticationType
}
