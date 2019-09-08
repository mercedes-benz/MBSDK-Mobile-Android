package com.daimler.mbmobilesdk.utils.extensions

import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.networking.HttpError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError

fun MBLoggerKit.re(msg: String, error: ResponseError<out RequestError>?) {
    e(msg)
    error?.let {
        e("NetworkError: ${it.networkError}")
        if (it.requestError is HttpError) {
            val httpError = it.requestError as HttpError
            e("HttpError: ${httpError.code}: ${httpError.description}")
        } else {
            e("RequestError: ${it.requestError}")
        }
    } ?: e("No error was thrown by the Service.")
}