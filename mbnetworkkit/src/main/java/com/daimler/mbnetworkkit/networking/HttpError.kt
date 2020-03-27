package com.daimler.mbnetworkkit.networking

import com.daimler.mbnetworkkit.common.HttpErrorDescription
import java.net.HttpURLConnection

sealed class HttpError(val code: Int, val description: HttpErrorDescription) : RequestError {

    companion object {
        const val HTTP_UNPROCESSABLE_ENTITY = 422
        const val HTTP_TOO_MANY_REQUESTS = 429
    }

    class BadRequest(description: HttpErrorDescription) : HttpError(HttpURLConnection.HTTP_BAD_REQUEST, description)
    class Unauthorized(description: HttpErrorDescription) : HttpError(HttpURLConnection.HTTP_UNAUTHORIZED, description)
    class Forbidden(description: HttpErrorDescription) : HttpError(HttpURLConnection.HTTP_FORBIDDEN, description)
    class NotFound(description: HttpErrorDescription) : HttpError(HttpURLConnection.HTTP_NOT_FOUND, description)
    class Conflict(description: HttpErrorDescription) : HttpError(HttpURLConnection.HTTP_CONFLICT, description)
    class PreconditionFailed(description: HttpErrorDescription) : HttpError(HttpURLConnection.HTTP_PRECON_FAILED, description)
    class UnprocessableEntitiy(description: HttpErrorDescription) : HttpError(HTTP_UNPROCESSABLE_ENTITY, description)
    class TooManyRequests(description: HttpErrorDescription) : HttpError(HTTP_TOO_MANY_REQUESTS, description)

    class InternalServerError(description: HttpErrorDescription) : HttpError(HttpURLConnection.HTTP_INTERNAL_ERROR, description)
    class BadGateway(description: HttpErrorDescription) : HttpError(HttpURLConnection.HTTP_BAD_GATEWAY, description)
    class ServiceUnavailable(description: HttpErrorDescription) : HttpError(HttpURLConnection.HTTP_UNAVAILABLE, description)
    class Unknown : HttpError(-1, HttpErrorDescription(null, null))
}
