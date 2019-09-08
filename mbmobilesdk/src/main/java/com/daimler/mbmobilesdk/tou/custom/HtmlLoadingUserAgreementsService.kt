package com.daimler.mbmobilesdk.tou.custom

import com.daimler.mbmobilesdk.tou.BaseWrappedUserAgreement
import com.daimler.mbmobilesdk.tou.WrappedUserAgreementService
import com.daimler.mbingresskit.common.UserAgreement
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

internal interface HtmlLoadingUserAgreementsService<T : UserAgreement, R : BaseWrappedUserAgreement> :
    WrappedUserAgreementService<T, R> {

    /**
     * Returns [HtmlUserAgreementContent] according to the document returned by [accessor].
     * This method tries to use cached user agreements and requests new ones if no cached
     * agreements are available.
     */
    fun loadHtmlContent(
        locale: String,
        countryCode: String,
        accessor: R.() -> T?
    ): FutureTask<HtmlUserAgreementContent, ResponseError<out RequestError>?>
}