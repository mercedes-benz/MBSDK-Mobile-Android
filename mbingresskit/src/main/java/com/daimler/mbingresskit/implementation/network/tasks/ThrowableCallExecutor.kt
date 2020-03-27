package com.daimler.mbingresskit.implementation.network.tasks

import com.daimler.mbnetworkkit.networking.exception.ResponseException
import retrofit2.Response

internal class ThrowableCallExecutor<T> {

    suspend fun execute(call: suspend () -> Response<T>): Result<T> =
        try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error(emptyBodyError(response.code()))
            } else {
                Result.Error(notSuccessfulError(response))
            }
        } catch (t: Throwable) {
            Result.Error(t)
        }

    private fun emptyBodyError(responseCode: Int) =
        ResponseException(responseCode, "ResponseBody was empty.")

    private fun notSuccessfulError(response: Response<T>) =
        ResponseException(
            response.code(),
            "Response was not successful.",
            response.errorBody()?.string()
        )

    sealed class Result<T> {
        class Success<T>(val body: T) : Result<T>()
        class Error<T>(val error: Throwable) : Result<T>()
    }
}
