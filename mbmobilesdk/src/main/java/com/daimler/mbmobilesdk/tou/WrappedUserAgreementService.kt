package com.daimler.mbmobilesdk.tou

import com.daimler.mbingresskit.common.UserAgreement
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

internal interface WrappedUserAgreementService<T : UserAgreement, R : BaseWrappedUserAgreement> {

    /**
     * Loads the user agreements.
     */
    fun fetchAgreements(locale: String, countryCode: String, allowCachedVariation: Boolean): FutureTask<R, ResponseError<out RequestError>?>

    /**
     * Returns the cached user agreements.
     */
    fun getCachedAgreements(locale: String, countryCode: String): R?
}