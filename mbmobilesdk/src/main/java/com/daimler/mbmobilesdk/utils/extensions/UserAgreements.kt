package com.daimler.mbmobilesdk.utils.extensions

import com.daimler.mbingresskit.common.*

internal fun UserAgreement.isAccepted() = acceptedByUser == AgreementAcceptanceState.ACCEPTED

internal fun UserAgreement.hasPdf() = contentType == UserAgreementContentType.PDF

internal fun UserAgreement.hasWebContent() = contentType == UserAgreementContentType.WEB

internal fun List<UserAgreement>.allAccepted() = none { !it.isAccepted() }

internal fun UserAgreements<SoeUserAgreement>.generalAgreementsAccepted() =
    agreements.isNotEmpty() && agreements.filter { it.generalUserAgreement }.none { !it.isAccepted() }

internal fun <T : UserAgreement> emptyUserAgreements() =
    UserAgreements<T>("", "", emptyList())