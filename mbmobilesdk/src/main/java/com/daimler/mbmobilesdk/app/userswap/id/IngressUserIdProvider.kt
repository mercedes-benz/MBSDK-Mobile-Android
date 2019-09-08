package com.daimler.mbmobilesdk.app.userswap.id

import com.auth0.android.jwt.JWT
import com.daimler.mbmobilesdk.utils.extensions.isLoggedIn
import com.daimler.mbingresskit.login.AuthenticationService

internal class IngressUserIdProvider(
    private val userIdCache: UserIdCache,
    private val authenticationService: AuthenticationService
) : UserIdProvider {

    override fun activeUserId(): String? {
        if (authenticationService.isLoggedIn()) {
            val token = authenticationService.getToken().jwtToken.plainToken
            val jwt = JWT(token)
            return jwt.getClaim(CLAIM_CIAM_ID).asString()
        }
        return null
    }

    override fun lastKnownUserId(): String? {
        val last = userIdCache.userId
        return if (last.isBlank()) null else last
    }

    override fun saveCurrentUserId() {
        val active = activeUserId()
        userIdCache.userId = active.orEmpty()
    }

    private companion object {
        private const val CLAIM_CIAM_ID = "ciamid"
    }
}