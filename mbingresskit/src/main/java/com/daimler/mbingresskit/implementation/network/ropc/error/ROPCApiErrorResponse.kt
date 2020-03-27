package com.daimler.mbingresskit.implementation.network.ropc.error

import com.daimler.mbingresskit.login.LoginFailure
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.defaultErrorMapping
import com.google.gson.annotations.SerializedName

internal data class ROPCApiErrorResponse(
    @SerializedName("error") val error: ROPCApiError?,
    @SerializedName("error_description") val description: String?
) : RequestError

internal fun Throwable?.mapToResponseError(): ResponseError<out RequestError> {
    val defaultError = defaultErrorMapping(this, ROPCApiErrorResponse::class.java)
    return if (defaultError.requestError is ROPCApiErrorResponse) {
        val apiError = defaultError.requestError as ROPCApiErrorResponse
        val failure = when (apiError.error) {
            ROPCApiError.INVALID_GRANT -> LoginFailure.WRONG_CREDENTIALS
            else -> LoginFailure.AUTHORIZATION_FAILED
        }
        ResponseError.requestError(failure)
    } else {
        defaultError
    }
}
