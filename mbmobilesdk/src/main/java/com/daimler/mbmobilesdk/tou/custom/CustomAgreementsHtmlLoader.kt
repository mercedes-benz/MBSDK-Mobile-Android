package com.daimler.mbmobilesdk.tou.custom

import com.daimler.mbmobilesdk.tou.WrappedUserAgreementService
import com.daimler.mbingresskit.common.CustomUserAgreement
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject
import java.io.IOException

internal class CustomAgreementsHtmlLoader(
    private val agreementsService: WrappedUserAgreementService<CustomUserAgreement, CustomUserAgreements>
) : HtmlLoadingUserAgreementsService<CustomUserAgreement, CustomUserAgreements>,
    WrappedUserAgreementService<CustomUserAgreement, CustomUserAgreements> by agreementsService {

    override fun loadHtmlContent(
        locale: String,
        countryCode: String,
        accessor: CustomUserAgreements.() -> CustomUserAgreement?
    ): FutureTask<HtmlUserAgreementContent, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<HtmlUserAgreementContent, ResponseError<out RequestError>?>()
        loadCached(locale, countryCode, accessor)
            .onComplete { deferredTask.complete(it) }
            .onFailure {
                loadRefreshed(locale, countryCode, accessor)
                    .onComplete { deferredTask.complete(it) }
                    .onFailure { deferredTask.fail(it) }
            }
        return deferredTask.futureTask()
    }

    private fun loadCached(
        locale: String,
        countryCode: String,
        accessor: CustomUserAgreements.() -> CustomUserAgreement?
    ): FutureTask<HtmlUserAgreementContent, ResponseError<out RequestError>?> {
        return loadAndGet(locale, countryCode, accessor, true)
    }

    private fun loadRefreshed(
        locale: String,
        countryCode: String,
        accessor: CustomUserAgreements.() -> CustomUserAgreement?
    ): FutureTask<HtmlUserAgreementContent, ResponseError<out RequestError>?> {
        return loadAndGet(locale, countryCode, accessor, false)
    }

    private fun loadAndGet(
        locale: String,
        countryCode: String,
        accessor: CustomUserAgreements.() -> CustomUserAgreement?,
        allowCache: Boolean
    ): FutureTask<HtmlUserAgreementContent, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<HtmlUserAgreementContent, ResponseError<out RequestError>?>()
        agreementsService.fetchAgreements(locale, countryCode, allowCache)
            .onComplete {
                it.accessor()?.withFileContent { agreement, bytes ->
                    deferredTask.complete(HtmlUserAgreementContent(agreement.displayName, String(bytes)))
                }
                    ?: deferredTask.fail(ResponseError.requestError(AgreementsFileContentNotFoundException()))
            }.onFailure {
                deferredTask.fail(it)
            }
        return deferredTask.futureTask()
    }

    private fun <T> CustomUserAgreement.withFileContent(
        action: (CustomUserAgreement, ByteArray) -> T
    ): T? = fileContent?.let { action(this, it) }

    class AgreementsFileContentNotFoundException : IOException(), RequestError
}