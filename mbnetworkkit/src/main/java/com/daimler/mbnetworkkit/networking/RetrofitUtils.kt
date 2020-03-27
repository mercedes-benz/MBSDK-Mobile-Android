package com.daimler.mbnetworkkit.networking

import com.daimler.mbloggerkit.Priority
import com.daimler.mbnetworkkit.networking.exception.ResponseException
import com.daimler.mbnetworkkit.networking.model.ApiDefaultErrorModel
import com.google.gson.Gson
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Creates an [HttpLoggingInterceptor] that uses [com.daimler.mbloggerkit.MBLoggerKit] to log request and response outputs
 * according to the given http and log levels.
 *
 * @param httpLevel specifies which output to log
 * @param logLevel specifies the log priority (INFO, DEBUG, ...)
 */
fun createHttpLoggingInterceptor(
    httpLevel: HttpLoggingInterceptor.Level,
    logLevel: Priority
): HttpLoggingInterceptor =
    HttpLoggingInterceptor(RetrofitHttpLogger(logLevel)).apply { level = httpLevel }

fun defaultErrorMapping(error: Throwable?): ResponseError<out RequestError> =
    defaultErrorMapping<RequestError>(error, null)

fun <T : RequestError> defaultErrorMapping(error: Throwable?, errorClass: Class<T>?): ResponseError<out RequestError> {
    return when (error) {
        is ConnectivityInterceptor.NotConnectedException -> ResponseError.networkError(NetworkError.NO_CONNECTION)
        is ResponseException -> {
            val customError = errorClass?.let { createCustomError(error.errorBody, it) }
            customError ?: createDefaultHttpError(error.responseCode, error.errorBody)
        }
        else -> ResponseError.requestError(BaseRetrofitTask.EmptyRequestErrorImpl())
    }
}

private fun <T : RequestError> createCustomError(errorBody: String?, cls: Class<T>): ResponseError<out RequestError>? {
    val error = tryParseErrorModel(errorBody, cls)
    return error?.let { ResponseError.requestError(it) }
}

private fun createDefaultHttpError(responseCode: Int, errorBody: String?): ResponseError<HttpError> {
    val model = tryParseErrorModel(errorBody, ApiDefaultErrorModel::class.java)
    return ResponseError.httpError(responseCode, model?.description, errorBody)
}

private fun <T> tryParseErrorModel(errorBody: String?, cls: Class<T>): T? =
    try {
        Gson().fromJson(errorBody, cls)
    } catch (e: Exception) {
        null
    }
