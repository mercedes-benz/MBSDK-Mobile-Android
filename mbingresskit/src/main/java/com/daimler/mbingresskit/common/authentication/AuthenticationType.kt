package com.daimler.mbingresskit.common.authentication

enum class AuthenticationType(val authMode: String) {
    KEYCLOAK("KEYCLOAK"),
    CIAM("CIAMNG")
}
