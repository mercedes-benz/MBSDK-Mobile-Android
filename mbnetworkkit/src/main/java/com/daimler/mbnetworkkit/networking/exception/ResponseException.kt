package com.daimler.mbnetworkkit.networking.exception

open class ResponseException(
    val responseCode: Int,
    message: String? = null,
    val errorBody: String? = null
) : RuntimeException(message)
