package com.daimler.mbmobilesdk.tou.custom

import com.daimler.mbmobilesdk.tou.BaseWrappedAgreementService
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.CustomUserAgreement
import com.daimler.mbingresskit.common.UserAgreements
import com.daimler.mbingresskit.login.UserService
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

internal class CustomAgreementsService(
    service: UserService
) : BaseWrappedAgreementService<CustomUserAgreement, CustomUserAgreements>(service) {

    override fun loadAgreements(
        service: UserService,
        countryCode: String
    ): FutureTask<UserAgreements<CustomUserAgreement>, ResponseError<out RequestError>?> {
        return service.fetchCustomTermsAndConditions(countryCode)
    }

    override fun cachedAgreements(locale: String, countryCode: String): UserAgreements<CustomUserAgreement>? {
        return MBIngressKit.cachedCustomAgreements(locale, countryCode)
    }

    override fun wrapAgreements(agreements: UserAgreements<CustomUserAgreement>): CustomUserAgreements {
        return CustomUserAgreements(
            agreements.locale,
            agreements.countryCode,
            agreements.documentById(DOCUMENT_ID_TERMS_OF_USE),
            agreements.documentById(DOCUMENT_ID_DATA_PROTECTION),
            agreements.documentById(DOCUMENT_ID_BETA_CONTENT),
            agreements.documentById(DOCUMENT_ID_APP_DESCRIPTION),
            agreements.documentById(DOCUMENT_ID_LEGAL_NOTICES),
            agreements.documentById(DOCUMENT_ID_IMPRINT),
            agreements.documentById(DOCUMENT_ID_THIRD_PARTY),
            agreements.documentById(DOCUMENT_ID_JUMIO),
            agreements.documentById(DOCUMENT_ID_TOU)
        )
    }

    private companion object {

        private const val DOCUMENT_ID_BETA_CONTENT = "Family_and_Friends_additional_agreement"
        private const val DOCUMENT_ID_TERMS_OF_USE = "terms_of_use"
        private const val DOCUMENT_ID_DATA_PROTECTION = "data_protection"
        private const val DOCUMENT_ID_APP_DESCRIPTION = "app_description"
        private const val DOCUMENT_ID_LEGAL_NOTICES = "mercedes_me_legal_information"
        private const val DOCUMENT_ID_IMPRINT = "legal_provider"
        private const val DOCUMENT_ID_THIRD_PARTY = "third_party_content"
        private const val DOCUMENT_ID_JUMIO = "jumio_data_protection"
        private const val DOCUMENT_ID_TOU = "tou_online_id"
    }
}