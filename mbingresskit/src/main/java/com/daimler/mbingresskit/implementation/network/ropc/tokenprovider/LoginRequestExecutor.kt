package com.daimler.mbingresskit.implementation.network.ropc.tokenprovider

import com.daimler.mbingresskit.implementation.network.model.pin.LoginUserResponse
import com.daimler.mbnetworkkit.common.ErrorMapStrategy
import com.daimler.mbnetworkkit.networking.coroutines.BaseRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.RequestResult
import retrofit2.Response
import java.net.HttpURLConnection

internal class LoginRequestExecutor(
    errorMapStrategy: ErrorMapStrategy = ErrorMapStrategy.Default
) : BaseRequestExecutor<LoginUserResponse, LoginRequestExecutor.LoginResult>(errorMapStrategy) {

    internal data class LoginResult(val response: LoginUserResponse, val authMode: String?)

    override suspend fun onHandleResponse(response: Response<LoginUserResponse>): RequestResult<LoginResult> =
        when (val code = response.code()) {
            HttpURLConnection.HTTP_OK -> {
                val authMode = response.getAuthMode()

                response.body()?.let {
                    RequestResult.Success(LoginResult(it, authMode))
                } ?: RequestResult.Error(createNoBodyException(code).map())
            }
            else -> RequestResult.Error(createNoBodyException(code).map())
        }

    private fun Response<LoginUserResponse>.getAuthMode() = headers()[HEADER_AUTH_MODE]

    private companion object {
        private const val HEADER_AUTH_MODE = "X-AuthMode"
    }
}
