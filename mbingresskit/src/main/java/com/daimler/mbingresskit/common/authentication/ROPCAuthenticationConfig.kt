package com.daimler.mbingresskit.common.authentication

class ROPCAuthenticationConfig(
    authenticationType: AuthenticationType,
    clientId: String,
    val baseUrl: String
) : AuthenticationConfiguration(authenticationType, clientId)
