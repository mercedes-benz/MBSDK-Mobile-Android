package com.daimler.mbingresskit.implementation.network.ropc

import com.auth0.android.jwt.DecodeException
import com.auth0.android.jwt.JWT
import com.daimler.mbingresskit.common.JwtToken
import com.daimler.mbingresskit.common.Token
import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.google.gson.annotations.SerializedName
import java.util.concurrent.TimeUnit

data class TokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Int,
    @SerializedName("refresh_expires_in") val refreshExpiresIn: Int?,
    @SerializedName("scope") val scope: String?,
    @SerializedName("typ") val typ: String?
)

internal fun TokenResponse.mapToToken(authenticationType: AuthenticationType): Token = Token(
    typ = typ ?: getClaim(),
    accessToken = accessToken,
    refreshToken = refreshToken,
    tokenExpirationDate = expiresIn.toDateForExpiresIn(),
    refreshTokenExpirationDate = refreshExpiresIn?.toDateForExpiresIn(),
    scope = scope,
    jwtToken = accessToken.toJwtToken(),
    authenticationType = authenticationType
)

fun TokenResponse.getClaim(): String? {
    val jwtToken = refreshToken.toJwtToken()
    return if (jwtToken.payload.isNotBlank()) {
        JWT(jwtToken.plainToken).getClaim("typ").asString()
    } else {
        null
    }
}

@Deprecated(
    "After apps migrated to CIAM, the method isn't needed anymore",
    ReplaceWith("Use accessToken in Token")
)
private fun String.toJwtToken() = try {
    val jwt = JWT(this)
    JwtToken(jwt.toString(), jwt.signature)
} catch (e: DecodeException) {
    // This will happen for CIAM. CIAM Token is encrypted.
    JwtToken(this, "")
}

private fun Int.toDateForExpiresIn(): Long =
    System.currentTimeMillis() + this * TimeUnit.SECONDS.toMillis(1)
