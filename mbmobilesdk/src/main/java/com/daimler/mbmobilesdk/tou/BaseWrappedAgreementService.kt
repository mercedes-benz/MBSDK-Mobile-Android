package com.daimler.mbmobilesdk.tou

import android.os.Handler
import android.os.Looper
import com.daimler.mbingresskit.common.UserAgreement
import com.daimler.mbingresskit.common.UserAgreements
import com.daimler.mbingresskit.login.UserService
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

internal abstract class BaseWrappedAgreementService<T : UserAgreement, R : BaseWrappedUserAgreement>(
    private val service: UserService
) : WrappedUserAgreementService<T, R> {

    private val handler = Handler(Looper.getMainLooper())
    private var agreements: R? = null

    override fun fetchAgreements(
        locale: String,
        countryCode: String,
        allowCachedVariation: Boolean
    ): FutureTask<R, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<R, ResponseError<out RequestError>?>()
        return if (allowCachedVariation) {
            val cached = getCachedAgreements(locale, countryCode)
            cached?.let {
                keepAgreementsInMemory(it)
                handler.post {
                    deferredTask.complete(it)
                }
                deferredTask.futureTask()
            } ?: load(countryCode)
        } else {
            load(countryCode)
        }
    }

    override fun getCachedAgreements(locale: String, countryCode: String): R? {
        return getFromMemoryCache(locale, countryCode) ?: getFromDelegatedCache(locale, countryCode)
    }

    protected abstract fun loadAgreements(
        service: UserService,
        countryCode: String
    ): FutureTask<UserAgreements<T>, ResponseError<out RequestError>?>

    protected abstract fun cachedAgreements(locale: String, countryCode: String): UserAgreements<T>?

    protected abstract fun wrapAgreements(agreements: UserAgreements<T>): R

    protected fun UserAgreements<T>.documentById(id: String): T? =
        agreements.find { it.documentId == id }

    private fun load(countryCode: String): FutureTask<R, ResponseError<out RequestError>?> {
        val task = TaskObject<R, ResponseError<out RequestError>?>()
        loadAgreements(service, countryCode)
            .onComplete {
                val wrapped = wrapAgreements(it)
                keepAgreementsInMemory(wrapped)
                task.complete(wrapped)
            }.onFailure {
                task.fail(it)
            }
        return task.futureTask()
    }

    private fun keepAgreementsInMemory(agreements: R) {
        this.agreements = agreements
    }

    private fun getFromMemoryCache(locale: String, countryCode: String): R? =
        agreements?.takeIf { it.locale == locale && it.countryCode == countryCode }

    private fun getFromDelegatedCache(locale: String, countryCode: String): R? {
        val cached = cachedAgreements(locale, countryCode)
        return cached?.let { wrapAgreements(it) }
    }
}