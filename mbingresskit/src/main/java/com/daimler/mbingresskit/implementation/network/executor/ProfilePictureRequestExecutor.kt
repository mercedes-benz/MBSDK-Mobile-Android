package com.daimler.mbingresskit.implementation.network.executor

import com.daimler.mbnetworkkit.common.ErrorMapStrategy
import com.daimler.mbnetworkkit.networking.coroutines.BaseRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.RequestResult
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.HttpURLConnection

internal class ProfilePictureRequestExecutor : BaseRequestExecutor<ResponseBody, ProfilePictureRequestExecutor.Result>(
    ErrorMapStrategy.Default
) {

    override fun shouldHandleResponse(response: Response<ResponseBody>): Boolean =
        response.isSuccessful || response.code() == HttpURLConnection.HTTP_NOT_MODIFIED

    override suspend fun onHandleResponse(response: Response<ResponseBody>): RequestResult<Result> =
        when (val code = response.code()) {
            HttpURLConnection.HTTP_OK -> getPictureResult(response)
            HttpURLConnection.HTTP_NOT_MODIFIED -> RequestResult.Success(Result.NotModified)
            else -> RequestResult.Error(createNoBodyException(code).map())
        }

    private fun getPictureResult(response: Response<ResponseBody>): RequestResult<Result> =
        response.body()?.bytes()?.let {
            RequestResult.Success(Result.Picture(it, response.getETag()))
        } ?: RequestResult.Error(createNoBodyException(response.code()).map())

    private fun Response<ResponseBody>.getETag() = headers()[HEADER_ETAG]

    sealed class Result {
        class Picture(val bytes: ByteArray, val eTag: String?) : Result()
        object NotModified : Result()
    }

    private companion object {

        private const val HEADER_ETAG = "ETag"
    }
}
