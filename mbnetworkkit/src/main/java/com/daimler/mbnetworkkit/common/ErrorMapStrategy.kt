package com.daimler.mbnetworkkit.common

import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.defaultErrorMapping

/**
 * Defines the mapping strategy for API errors.
 */
sealed class ErrorMapStrategy {

    abstract fun get(throwable: Throwable): ResponseError<out RequestError>

    object Default : ErrorMapStrategy() {

        override fun get(throwable: Throwable): ResponseError<out RequestError> =
            defaultErrorMapping(throwable)
    }

    class Custom(private val mapper: (Throwable) -> ResponseError<out RequestError>) : ErrorMapStrategy() {

        override fun get(throwable: Throwable): ResponseError<out RequestError> =
            mapper(throwable)
    }
}
