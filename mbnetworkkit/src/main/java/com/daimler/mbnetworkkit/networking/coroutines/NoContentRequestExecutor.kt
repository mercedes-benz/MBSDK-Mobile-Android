package com.daimler.mbnetworkkit.networking.coroutines

import com.daimler.mbnetworkkit.common.ErrorMapStrategy
import retrofit2.Response

/**
 * Executor for API calls that complete without a dedicated body. Mostly used for endpoints
 * that return with a 204 on success case. This executor always returns [RequestResult.Success]
 * with a Unit parameter if [Response.isSuccessful] returned true.
 */
class NoContentRequestExecutor(
    errorMapStrategy: ErrorMapStrategy = ErrorMapStrategy.Default
) : BaseRequestExecutor<Unit, Unit>(errorMapStrategy) {

    override suspend fun onHandleResponse(response: Response<Unit>): RequestResult<Unit> =
        RequestResult.Success(Unit)
}
