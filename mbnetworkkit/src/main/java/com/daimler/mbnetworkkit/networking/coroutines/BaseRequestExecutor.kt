package com.daimler.mbnetworkkit.networking.coroutines

import com.daimler.mbnetworkkit.common.ErrorMapStrategy
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.exception.ResponseException
import com.daimler.mbnetworkkit.task.TaskObject
import retrofit2.Response

/**
 * Utility class for Retrofit API calls that use coroutines.
 */
abstract class BaseRequestExecutor<T, R>(
    private val errorMapStrategy: ErrorMapStrategy
) {

    /**
     * Executes the given call within a try-catch block.
     * Calls [onHandleResponse] if [shouldHandleResponse] returned true and the call did not
     * fail with an exception.
     *
     * @return [RequestResult.Error] if an exception occurred or [shouldHandleResponse] returned false;
     * returns the result of [onHandleResponse] otherwise
     */
    suspend fun execute(call: suspend () -> Response<T>): RequestResult<R> =
        try {
            val response = call()
            if (shouldHandleResponse(response)) {
                onHandleResponse(response)
            } else {
                RequestResult.Error(createHttpException(response).map())
            }
        } catch (t: Throwable) {
            RequestResult.Error(t.map())
        }

    /**
     * Calls [execute] and finishes the given [TaskObject] depending on the result.
     * Result dispatching is done on the main dispatcher.
     */
    suspend fun executeWithTask(
        taskObject: TaskObject<R, ResponseError<out RequestError>?>,
        call: suspend () -> Response<T>
    ) {
        when (val result = execute(call)) {
            is RequestResult.Success -> taskObject.dispatchResult(result.body)
            is RequestResult.Error -> taskObject.dispatchError(result.error)
        }
    }

    /**
     * Returns true if this response should be handled by the client.
     */
    protected open fun shouldHandleResponse(response: Response<T>): Boolean =
        response.isSuccessful

    /**
     * Handle the actual response.
     */
    protected abstract suspend fun onHandleResponse(response: Response<T>): RequestResult<R>

    protected fun createNoBodyException(code: Int) =
        ResponseException(code, "Response was empty.")

    protected fun createHttpException(code: Int) =
        createHttpException(code, null)

    private fun createHttpException(response: Response<*>) =
        createHttpException(response.code(), response.errorBody()?.string())

    private fun createHttpException(
        code: Int,
        errorBody: String?
    ) = ResponseException(code, "Response was not successful.", errorBody)

    protected fun Throwable.map() = errorMapStrategy.get(this)
}
