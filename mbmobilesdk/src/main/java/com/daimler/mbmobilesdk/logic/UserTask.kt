package com.daimler.mbmobilesdk.logic

import com.auth0.android.jwt.JWT
import com.daimler.mbmobilesdk.utils.extensions.isLoggedIn
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.login.error.ClientNotAuthorizedException
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

/**
 * This task is used to provide a valid user object.
 */
class UserTask : TaskObject<UserTaskResult, Throwable?>() {

    /**
     * Provides the user. It tries to return the cached user object, otherwise the user will be
     * fetched through the network.
     *
     * @param forceRefresh indicates whether the user shall be requested through the network
     *                      in any case
     */
    fun fetchUser(forceRefresh: Boolean = false): FutureTask<UserTaskResult, Throwable?> {
        if (!isAuthorized()) {
            fail(ClientNotAuthorizedException("The user is not logged in!"))
            return this
        }

        if (forceRefresh) {
            requestUser()
        } else {
            tryGetCachedUser(true)
        }

        return this
    }

    fun getCachedUser(): FutureTask<UserTaskResult, Throwable?> {
        if (!isAuthorized()) {
            fail(ClientNotAuthorizedException("The user is not logged in!"))
            return this
        }

        tryGetCachedUser(false)

        return this
    }

    private fun tryGetCachedUser(fetchOnFailure: Boolean) {
        val token = MBIngressKit.authenticationService().getToken().jwtToken
        val ciamId = JWT(token.plainToken).getClaim(CLAIM_CIAM_ID).asString().toString()
        MBIngressKit.userService().getUserIfSameIdAvailable(ciamId)
            .onComplete {
                if (it == null) {
                    // request the user if there is no cached one available and we shall fetch
                    if (fetchOnFailure) {
                        requestUser()
                    } else {
                        fail(NoCachedUserAvailableException())
                    }
                } else {
                    complete(UserTaskResult(it, false))
                }
            }
            .onFailure {
                if (fetchOnFailure) {
                    requestUser()
                } else {
                    fail(it)
                }
            }
    }

    private fun requestUser() {
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                MBIngressKit.userService().loadUser(token.jwtToken.plainToken)
                    .onComplete { complete(UserTaskResult(it, true)) }
                    .onFailure { fail(null) }
            }
            .onFailure { fail(it) }
    }

    private fun isAuthorized() = MBIngressKit.authenticationService().isLoggedIn()

    class NoCachedUserAvailableException : RuntimeException("No cached user available.")

    companion object {
        const val CLAIM_CIAM_ID = "ciamid"
    }
}