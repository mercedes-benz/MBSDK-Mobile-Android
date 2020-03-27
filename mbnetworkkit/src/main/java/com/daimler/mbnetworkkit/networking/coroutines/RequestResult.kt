package com.daimler.mbnetworkkit.networking.coroutines

import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError

sealed class RequestResult<T> {

    class Success<T>(val body: T) : RequestResult<T>()
    class Error<T>(val error: ResponseError<out RequestError>) : RequestResult<T>()
}
