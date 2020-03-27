package com.daimler.mbingresskit.common

internal data class AuthorizationRequest(
    val redirectUri: String,
    val clientId: String,
    val prompt: String?,
    val responseType: String,
    val scope: String? = null,
    var additionalParameters: Map<String, String> = emptyMap(),
    var codeVerifier: String? = null
)
