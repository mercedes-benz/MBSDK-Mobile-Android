package com.daimler.mbingresskit.util

import com.auth0.android.jwt.DecodeException
import com.auth0.android.jwt.JWT
import com.daimler.mbloggerkit.MBLoggerKit

object JWTHelper {
    @Deprecated("Will be removed since information extraction from token will be impossbile with CIAM NG")
    fun extractClaimFromToken(token: String, claim: String): String? =
        try {
            val jwt = JWT(token)
            jwt.getClaim(claim).asString()
        } catch (e: DecodeException) {
            MBLoggerKit.i("Invalid JWT Token provided: $token, error is: ${e.message}")
            null
        }
}
