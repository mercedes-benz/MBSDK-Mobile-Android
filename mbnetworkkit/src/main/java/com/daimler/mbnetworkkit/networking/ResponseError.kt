package com.daimler.mbnetworkkit.networking

import com.daimler.mbnetworkkit.common.HttpErrorDescription
import java.net.HttpURLConnection

/**
 * A ResponseError is returned for every error which occurs if data is requested from remote server.
 * The cause of the error is whether a [NetworkError] e.g. if there was no internet connection, or
 * a [RequestError]. But only one error is available at the same time.
 */
class ResponseError<T : RequestError> protected constructor(
    /**
     * This error is set, if there was a network issue when trying to load some data from server.
     */
    val networkError: NetworkError?,

    /**
     * This could be a [HttpError], if an invalid http code was received or it can be a custom
     * [RequestError] which can individual for every request.
     */
    val requestError: T?
) {

    companion object {
        fun <T : RequestError> networkError(networkError: NetworkError): ResponseError<T> {
            return ResponseError(networkError, null)
        }

        fun <T : RequestError> requestError(requestError: T): ResponseError<T> {
            return ResponseError(null, requestError)
        }

        fun httpError(httpResponseCode: Int, description: String? = null, rawError: String? = null): ResponseError<HttpError> {
            return ResponseError(null, httpErrorForResponseCode(httpResponseCode, description, rawError))
        }

        private fun httpErrorForResponseCode(httpResponseCode: Int, description: String?, rawError: String?): HttpError {
            return when (httpResponseCode) {
                HttpURLConnection.HTTP_BAD_REQUEST -> HttpError.BadRequest(HttpErrorDescription(description, rawError))
                HttpURLConnection.HTTP_UNAUTHORIZED -> HttpError.Unauthorized(HttpErrorDescription(description, rawError))
                HttpURLConnection.HTTP_FORBIDDEN -> HttpError.Forbidden(HttpErrorDescription(description, rawError))
                HttpURLConnection.HTTP_NOT_FOUND -> HttpError.NotFound(HttpErrorDescription(description, rawError))
                HttpURLConnection.HTTP_CONFLICT -> HttpError.Conflict(HttpErrorDescription(description, rawError))
                HttpURLConnection.HTTP_PRECON_FAILED -> HttpError.PreconditionFailed(HttpErrorDescription(description, rawError))
                HttpError.HTTP_UNPROCESSABLE_ENTITY -> HttpError.UnprocessableEntitiy(HttpErrorDescription(description, rawError))
                HttpError.HTTP_TOO_MANY_REQUESTS -> HttpError.TooManyRequests(HttpErrorDescription(description, rawError))
                HttpURLConnection.HTTP_INTERNAL_ERROR -> HttpError.InternalServerError(HttpErrorDescription(description, rawError))
                HttpURLConnection.HTTP_BAD_GATEWAY -> HttpError.BadGateway(HttpErrorDescription(description, rawError))
                HttpURLConnection.HTTP_UNAVAILABLE -> HttpError.ServiceUnavailable(HttpErrorDescription(description, rawError))
                else -> HttpError.Unknown()
            }
        }
    }
}
