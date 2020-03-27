package com.daimler.mbingresskit.implementation.network.model.user

import com.daimler.mbingresskit.common.RegistrationUserAgreementUpdate
import com.google.gson.annotations.SerializedName

internal data class ApiAgreementUpdate(
    @SerializedName("documentId") val documentId: String,
    @SerializedName("version") val version: String,
    @SerializedName("acceptanceState") val accepted: Boolean,
    @SerializedName("acceptedLocale") val acceptedLocale: String
)

internal fun RegistrationUserAgreementUpdate.toApiAgreementUpdate() = ApiAgreementUpdate(
    documentId = userAgreementId,
    version = versionId,
    accepted = accepted,
    acceptedLocale = locale
)
