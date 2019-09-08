package com.daimler.mbmobilesdk.utils.extensions

import com.daimler.mbnetworkkit.networking.HttpError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError

internal inline fun <reified T : HttpError> ResponseError<*>.getHttpError(): T? =
    requestError as? T

internal inline fun <reified T : RequestError> ResponseError<*>.getRequestError(): T? =
    requestError as? T