package com.daimler.mbingresskit.common

class AuthorizationException(
    val type: Int,
    val code: Int,
    message: String? = null
) : RuntimeException(message)
