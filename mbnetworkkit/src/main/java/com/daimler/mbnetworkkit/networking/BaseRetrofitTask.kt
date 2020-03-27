package com.daimler.mbnetworkkit.networking

import com.daimler.mbnetworkkit.networking.exception.ResponseException
import com.daimler.mbnetworkkit.task.TaskObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Base class for all kinds of RetrofitTasks. A RetrofitTask is both a [TaskObject] and
 * a [Callback] for Retrofit calls. The failure object for a RetrofitTask is always
 * a [Throwable].
 *
 * @param R the type of the response body
 * @param T the type of your actual completion result used for [TaskObject.complete]
 */
@Deprecated("Migrate to coroutines and use BaseRequestExecutor.")
abstract class BaseRetrofitTask<R, T> : TaskObject<T, Throwable?>(), Callback<R> {

    override fun onFailure(call: Call<R>, t: Throwable?) {
        fail(t)
    }

    override fun onResponse(call: Call<R>, response: Response<R>?) {
        response?.let {
            if (it.isSuccessful) {
                onHandleResponseBody(it.body(), it.code())
            } else {
                failResponseNotSuccessful(it)
            }
        } ?: failEmptyResponse()
    }

    /**
     * This function is called when the response is successful, more specifically, when
     * [Response.isSuccessful] returns true. You must [complete] or [fail] this task in this
     * function.
     */
    protected abstract fun onHandleResponseBody(body: R?, responseCode: Int)

    protected fun failEmptyBody(responseCode: Int) =
        fail(ResponseException(responseCode, "ResponseBody was empty."))

    private fun failResponseNotSuccessful(response: Response<R>) =
        fail(ResponseException(response.code(), "Response was not successful.", response.errorBody()?.string()))

    private fun failEmptyResponse() =
        fail(EmptyResponseException())

    class EmptyResponseException : RuntimeException("Received empty response")

    class EmptyRequestErrorImpl : RequestError
}
