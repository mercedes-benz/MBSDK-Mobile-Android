package com.daimler.mbingresskit.login.error

data class SessionExpiredException(val code: Int = -1, val body: String? = null) : Throwable()
