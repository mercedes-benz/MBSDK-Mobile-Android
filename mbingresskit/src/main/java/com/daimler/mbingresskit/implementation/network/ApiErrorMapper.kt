package com.daimler.mbingresskit.implementation.network

import com.daimler.mbingresskit.implementation.network.model.ApiInputErrors
import com.daimler.mbingresskit.implementation.network.model.ConsentApiInputErrors
import com.daimler.mbingresskit.implementation.network.model.toRegistrationErrors
import com.daimler.mbingresskit.implementation.network.model.toUserInputErrors
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.defaultErrorMapping
import com.daimler.mbnetworkkit.networking.exception.ResponseException
import java.net.HttpURLConnection

internal fun String.toNullIfBlank() = if (isBlank()) null else this

internal fun mapDefaultInputError(error: Throwable?): ResponseError<out RequestError> {
    val defaultError = defaultErrorMappingIfBadRequest(error, ApiInputErrors::class.java)
    return if (defaultError.requestError is ApiInputErrors) {
        val inputErrors = defaultError.requestError as ApiInputErrors
        ResponseError.requestError(inputErrors.toUserInputErrors())
    } else {
        defaultError
    }
}

internal fun mapRegistrationErrors(error: Throwable?): ResponseError<out RequestError> {
    val defaultError = defaultErrorMappingIfBadRequest(error, ConsentApiInputErrors::class.java)
    return if (defaultError.requestError is ConsentApiInputErrors) {
        val registrationErrors = defaultError.requestError as ConsentApiInputErrors
        ResponseError.requestError(registrationErrors.toRegistrationErrors())
    } else {
        defaultError
    }
}

private fun <T : RequestError> defaultErrorMappingIfBadRequest(error: Throwable?, type: Class<T>) =
    (error as? ResponseException)?.takeIf {
        it.responseCode == HttpURLConnection.HTTP_BAD_REQUEST
    }?.let {
        defaultErrorMapping(error, type)
    } ?: defaultErrorMapping(error)
