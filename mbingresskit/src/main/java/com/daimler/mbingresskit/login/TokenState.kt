package com.daimler.mbingresskit.login

import android.os.Handler
import com.daimler.mbingresskit.NoLoginServiceDefinedException
import com.daimler.mbingresskit.NotLoggedInException
import com.daimler.mbingresskit.RefreshQueue
import com.daimler.mbingresskit.TooManyConcurrentRequestsException
import com.daimler.mbingresskit.common.Token
import com.daimler.mbingresskit.login.error.SessionExpiredException
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

sealed class TokenState {

    val name: String = this::class.java.simpleName

    /**
     * The user is authorized if an Access-Token is available and not expired. This allows to perform
     * any requests where an AT is required.
     */
    object AUTHORIZED : TokenState()

    /**
     * If an Access-Token is available, but expired, the user will be logged in but not able to perform
     * any request where an AT is required. In this state, the AT must be refreshed.
     */
    object LOGGEDIN : TokenState() {
        internal fun handleTokenRefresh(
            handler: Handler,
            sessionExpiredHandler: SessionExpiredHandler?,
            refreshQueue: RefreshQueue,
            loginService: LoginService?
        ): FutureTask<Token, Throwable?> {
            MBLoggerKit.d("refreshToken -> needs token refresh")
            val deferredTask = TaskObject<Token, Throwable?>()
            if (refreshQueue.isEmpty()) {
                MBLoggerKit.d("Queue is empty, refreshing.")
                val success = refreshQueue.offer(deferredTask)
                if (!success) {
                    MBLoggerKit.w("refreshQueue did not accept task")
                }
                createTokenRefreshTask(loginService, refreshQueue, sessionExpiredHandler)
            } else {
                val success = refreshQueue.offer(deferredTask)
                if (success) {
                    MBLoggerKit.d("Refresh is running, request was put into the queue.")
                    MBLoggerKit.d("There are now ${refreshQueue.size} requests in the queue.")
                } else {
                    // This will not happen if the queue stays at its default capacity,
                    // which is Integer.MAX_VALUE.
                    MBLoggerKit.w("refreshQueue did not accept task")
                    handler.post { deferredTask.fail(TooManyConcurrentRequestsException()) }
                }
            }
            return deferredTask.futureTask()
        }

        private fun createTokenRefreshTask(
            loginService: LoginService?,
            refreshQueue: RefreshQueue,
            sessionExpiredHandler: SessionExpiredHandler?
        ) {
            val refreshTokenTask = loginService?.refreshToken()
                ?: throw NoLoginServiceDefinedException()
            refreshTokenTask
                .onComplete {
                    MBLoggerKit.d("Access token: ${it.accessToken}")
                    MBLoggerKit.d("Refresh token: ${it.refreshToken}")
                    notifyRefreshQueue(refreshQueue, it, null)
                }.onFailure {
                    MBLoggerKit.e("Error while refreshing Token: $it")
                    if (handleSessionExpired(sessionExpiredHandler, it)) {
                        // no error blocks are called upon a handled expired session
                        refreshQueue.clear()
                    } else {
                        MBLoggerKit.d("Token cannot be refreshed. Notifying all waiting tasks")
                        notifyRefreshQueue(refreshQueue, null, it)
                    }
                }
        }

        private fun notifyRefreshQueue(refreshQueue: RefreshQueue, result: Token?, error: Throwable?) {
            while (refreshQueue.isNotEmpty()) {
                val task = refreshQueue.poll()
                task?.let { refreshTask ->
                    result?.let { refreshTask.complete(it) } ?: refreshTask.fail(error)
                }
            }
        }

        private fun handleSessionExpired(sessionExpiredHandler: SessionExpiredHandler?, error: Throwable?): Boolean {
            if (error is SessionExpiredException) {
                sessionExpiredHandler?.let {
                    it.onSessionExpired(error.code, error.body)
                    return true
                }
            }
            return false
        }
    }

    /**
     * If no Access-Token is available, the user will be logged out. This state could also exist, if
     * Access-Token exists, but the Refresh-Token, which is required to update its related Access-Token,
     * has been expired.
     */
    object LOGGEDOUT : TokenState() {
        internal fun handleTokenRefresh(handler: Handler): FutureTask<Token, Throwable?> {
            MBLoggerKit.d("refreshToken -> not logged in")
            val notLoggedInTask = TaskObject<Token, Throwable?>()
            handler.post {
                notLoggedInTask.fail(NotLoggedInException())
            }
            return notLoggedInTask.futureTask()
        }
    }
}
