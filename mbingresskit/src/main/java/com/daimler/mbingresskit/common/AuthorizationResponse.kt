package com.daimler.mbingresskit.common

internal class AuthorizationResponse(
    val authorizationCode: String,
    val authorizationRequest: AuthorizationRequest
) {

    fun getRedirectUri() = authorizationRequest.redirectUri

    fun getScope() = authorizationRequest.scope

    fun getCodeVerifier() = authorizationRequest.codeVerifier

    fun getAdditionalParameters() = authorizationRequest.additionalParameters
}
