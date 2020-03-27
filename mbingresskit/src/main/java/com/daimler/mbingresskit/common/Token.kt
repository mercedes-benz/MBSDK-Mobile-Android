package com.daimler.mbingresskit.common

import com.daimler.mbingresskit.common.authentication.AuthenticationType

data class Token @JvmOverloads constructor(
    val typ: String?,
    val accessToken: String,
    val refreshToken: String,
    @Deprecated("With CIAM Migration this value is not longer supported. Use accessToken instead.", ReplaceWith("accessToken"))
    val jwtToken: JwtToken,
    val tokenExpirationDate: Long,
    val refreshTokenExpirationDate: Long?,
    val scope: String? = "",
    val IdToken: String = "",
    val authenticationType: AuthenticationType = AuthenticationType.KEYCLOAK
)

@Deprecated(
    "After CIAM Migration not longer supported. Use encrypted accessToken in Token instead",
    ReplaceWith("Token")
)
data class JwtToken internal constructor(
    val plainToken: String,
    val payload: String
)
