package com.daimler.mbingresskit.implementation.network.ropc.tokenprovider.ciam

import com.daimler.mbingresskit.common.Token
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbingresskit.implementation.network.ropc.TokenResponse
import com.daimler.mbingresskit.implementation.network.ropc.error.mapToResponseError
import com.daimler.mbingresskit.implementation.network.ropc.mapToToken
import com.daimler.mbingresskit.implementation.network.ropc.nonce.NonceProvider
import com.daimler.mbingresskit.implementation.network.ropc.nonce.loginNonce
import com.daimler.mbingresskit.implementation.network.ropc.tokenprovider.TokenService
import com.daimler.mbingresskit.implementation.network.service.BaseRetrofitService
import com.daimler.mbingresskit.implementation.network.service.NetworkCoroutineScope
import com.daimler.mbingresskit.implementation.network.tasks.ThrowableCallExecutor
import com.daimler.mbnetworkkit.common.ErrorMapStrategy
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.MappableRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.NoContentRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.dispatchError
import com.daimler.mbnetworkkit.networking.coroutines.dispatchResult
import com.daimler.mbnetworkkit.networking.exception.ResponseException
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.ResponseTaskObject
import com.daimler.mbnetworkkit.task.TaskObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

internal class CiamTokenService(
    ciamApi: CiamApi,
    private val clientId: String,
    private val stage: String,
    private val nonceProvider: NonceProvider,
    scope: CoroutineScope = NetworkCoroutineScope()
) : TokenService, BaseRetrofitService<CiamApi>(ciamApi, scope) {

    override val authenticationType: AuthenticationType = AuthenticationType.CIAM

    override fun requestToken(
        deviceId: String,
        userName: String,
        password: String
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
                    password = "${nonceProvider.loginNonce}:$password"
                )
            }
        }
        return task.futureTask()
    }

    override fun refreshToken(token: Token): FutureTask<Token, Throwable?> {
        val task = TaskObject<Token, Throwable?>()
        if (token.refreshToken.isBlank()) {
            task.fail(
                ResponseException(
                    HttpURLConnection.HTTP_BAD_REQUEST,
                    "RefreshToken was empty."
                )
            )
        } else {
            scope.launch {
                val result =
                    ThrowableCallExecutor<TokenResponse>().execute {
                        api.refreshToken(
                            stage = stage,
                            refreshToken = token.refreshToken
                        )
                    }
                when (result) {
                    is ThrowableCallExecutor.Result.Success -> task.dispatchResult(
                        result.body.mapToToken(AuthenticationType.CIAM)
                    )
                    is ThrowableCallExecutor.Result.Error -> task.dispatchError(result.error)
                }
            }
        }
        return task.futureTask()
    }

    override fun logout(token: Token): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.logout(
                    stage = stage,
                    clientId = clientId,
                    token = token.accessToken
                )
            }
        }
        nonceProvider.clearAll()
        return task.futureTask()
    }
}
