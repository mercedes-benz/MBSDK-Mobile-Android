package com.daimler.mbingresskit.implementation.network.ropc.tokenexchange.ciam

import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbingresskit.common.identifier
import com.daimler.mbingresskit.implementation.AuthstateRepository
import com.daimler.mbingresskit.implementation.network.ropc.tokenexchange.TokenExchangeService
import com.daimler.mbingresskit.implementation.network.ropc.tokenexchange.TokenExchanger
import com.daimler.mbingresskit.persistence.UserCache
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

internal class CiamTokenExchangeService(
    private val tokenExchanger: TokenExchanger,
    private val authStateRepository: AuthstateRepository,
    private val userCache: UserCache,
    private val deviceId: String
) : TokenExchangeService {

    override fun isExchangeTokenPossible(): Boolean {
        val authenticationState = authStateRepository.readAuthState()
        return authenticationState.isAuthorized() && authenticationState.getToken().authenticationType != AuthenticationType.CIAM
    }

    override fun exchangeToken(): FutureTask<Unit, TokenExchangeService.Error> {
        val exchangeTask = TaskObject<Unit, TokenExchangeService.Error>()
        if (!isExchangeTokenPossible()) {
            MBLoggerKit.d("TokenExchange failed: Token in AuthStateRepository is already a CIAM Token")
            exchangeTask.fail(TokenExchangeService.Error.Impossible)
            return exchangeTask.futureTask()
        }
        val authState = authStateRepository.readAuthState()
        tokenExchanger.exchangeToken(
            deviceId = deviceId,
            userName = userCache.loadUser()?.identifier.orEmpty(),
            accessToken = authState.getToken().accessToken
        ).onComplete {
            MBLoggerKit.d("TokenExchange successful: update token in AuthStateRepository")
            authState.update(it)
            authStateRepository.saveAuthState(authState)
            exchangeTask.complete(Unit)
        }.onFailure {
            MBLoggerKit.d("TokenExchange failed: network error ${it?.networkError?.toString()}")
            exchangeTask.fail(
                TokenExchangeService.Error.NetworkFailure(
                    it?.networkError?.toString().orEmpty()
                )
            )
        }
        return exchangeTask.futureTask()
    }
}
