package com.daimler.mbingresskit.implementation.network.ropc.tokenexchange.ciam

import com.daimler.mbingresskit.common.Token
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbingresskit.implementation.network.ropc.TokenResponse
import com.daimler.mbingresskit.implementation.network.ropc.error.mapToResponseError
import com.daimler.mbingresskit.implementation.network.ropc.mapToToken
import com.daimler.mbingresskit.implementation.network.ropc.tokenexchange.TokenExchanger
import com.daimler.mbingresskit.implementation.network.ropc.tokenprovider.ciam.CiamApi
import com.daimler.mbingresskit.implementation.network.service.BaseRetrofitService
import com.daimler.mbingresskit.implementation.network.service.NetworkCoroutineScope
import com.daimler.mbnetworkkit.common.ErrorMapStrategy
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.MappableRequestExecutor
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.ResponseTaskObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class CiamTokenExchanger(
    ciamApi: CiamApi,
    private val clientId: String,
    private val stage: String,
    scope: CoroutineScope = NetworkCoroutineScope()
) : TokenExchanger, BaseRetrofitService<CiamApi>(ciamApi, scope) {

    override val exchangeAuthType: AuthenticationType = AuthenticationType.CIAM

    override fun exchangeToken(
        deviceId: String,
        userName: String,
        accessToken: String
    ): FutureTask<Token, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Token>()
        scope.launch {
            MappableRequestExecutor<TokenResponse, Token>(
                ErrorMapStrategy.Custom {
                    it.mapToResponseError()
                }
            ) {
                it.mapToToken(AuthenticationType.CIAM)
            }.executeWithTask(task) {
                api.requestToken(
                    stage = stage,
                    deviceId = deviceId,
                    clientId = clientId,
                    userName = userName,
                    password = accessToken
                )
            }
        }
        return task.futureTask()
    }
}
