package com.daimler.mbnetworkkit.networking.coroutines

import com.daimler.mbnetworkkit.common.ErrorMapStrategy
import retrofit2.Response

/**
 * RequestExecutor that maps the response result automatically.
 * Handles responses if [Response.isSuccessful] returns true.
 *
 * @param T the API model type
 * @param R the result model type
 */
open class MappableRequestExecutor<T, R>(
    errorMapStrategy: ErrorMapStrategy = ErrorMapStrategy.Default,
    private val map: (T) -> R
) : BaseRequestExecutor<T, R>(errorMapStrategy) {

    override suspend fun onHandleResponse(response: Response<T>): RequestResult<R> =
        response.body()?.let {
            safeMap(it, map)
        } ?: RequestResult.Error(createNoBodyException(response.code()).map())

    private fun safeMap(t: T, map: (T) -> R) =
        try {
            RequestResult.Success(map(t))
        } catch (t: Throwable) {
            RequestResult.Error(RuntimeException("Failed to map result.").map())
        }
}
