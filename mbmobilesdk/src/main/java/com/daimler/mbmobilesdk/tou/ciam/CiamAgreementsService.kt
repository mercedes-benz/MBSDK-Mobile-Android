package com.daimler.mbmobilesdk.tou.ciam

import com.daimler.mbmobilesdk.tou.BaseWrappedAgreementService
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.CiamUserAgreement
import com.daimler.mbingresskit.common.UserAgreements
import com.daimler.mbingresskit.login.UserService
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

internal class CiamAgreementsService(
    service: UserService
) : BaseWrappedAgreementService<CiamUserAgreement, CiamUserAgreements>(service) {

    override fun loadAgreements(
        service: UserService,
        countryCode: String
    ): FutureTask<UserAgreements<CiamUserAgreement>, ResponseError<out RequestError>?> {
        return service.fetchCiamTermsAndConditions(countryCode)
    }

    override fun cachedAgreements(locale: String, countryCode: String): UserAgreements<CiamUserAgreement>? {
        return MBIngressKit.cachedCiamAgreements(locale, countryCode)
    }

    override fun wrapAgreements(agreements: UserAgreements<CiamUserAgreement>): CiamUserAgreements {
        return CiamUserAgreements(
            agreements.locale,
            agreements.countryCode,
            agreements.documentById(DOCUMENT_ID_ACCESS_CONDITIONS),
            agreements.documentById(DOCUMENT_ID_COOKIES),
            agreements.documentById(DOCUMENT_ID_DATA_PRIVACY)
        )
    }

    private companion object {
        private const val DOCUMENT_ID_ACCESS_CONDITIONS = "Access conditions"
        private const val DOCUMENT_ID_COOKIES = "Cookies"
        private const val DOCUMENT_ID_DATA_PRIVACY = "Data privacy"
    }
}