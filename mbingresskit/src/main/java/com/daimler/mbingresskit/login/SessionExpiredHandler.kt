package com.daimler.mbingresskit.login

interface SessionExpiredHandler {

    fun onSessionExpired(statusCode: Int, errorBody: String?)
}
