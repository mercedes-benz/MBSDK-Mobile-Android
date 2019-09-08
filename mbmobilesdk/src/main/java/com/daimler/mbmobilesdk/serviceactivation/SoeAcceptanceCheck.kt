package com.daimler.mbmobilesdk.serviceactivation

import android.os.Handler
import android.os.Looper
import com.daimler.mbmobilesdk.utils.extensions.generalAgreementsAccepted
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

internal object SoeAcceptanceCheck {

    private val handler = Handler(Looper.getMainLooper())

    /**
     * Checks if SOE agreements are accepted in the local cache.
     * Requests SOE agreements if none are cached.
     */
    fun areSoeAccepted(locale: String, countryCode: String): FutureTask<Boolean, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<Boolean, ResponseError<out RequestError>?>()
        val cachedAgreements = MBIngressKit.cachedSoeAgreements(locale, countryCode)
        cachedAgreements?.let {
            handler.post { deferredTask.complete(it.generalAgreementsAccepted()) }
        } ?: fetchSoeTermsAndCheck(countryCode, deferredTask)
        return deferredTask.futureTask()
    }

    private fun fetchSoeTermsAndCheck(countryCode: String, task: TaskObject<Boolean, ResponseError<out RequestError>?>) {
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                MBIngressKit.userService().fetchSOETermsAndConditions(token.jwtToken.plainToken, countryCode)
                    .onComplete {
                        task.complete(it.generalAgreementsAccepted())
                    }
                    .onFailure { task.fail(it) }
            }.onFailure {
                task.fail(ResponseError.requestError(TokenRefreshError(it)))
            }
    }

    private class TokenRefreshError(throwable: Throwable?) : Throwable(throwable?.message), RequestError
}