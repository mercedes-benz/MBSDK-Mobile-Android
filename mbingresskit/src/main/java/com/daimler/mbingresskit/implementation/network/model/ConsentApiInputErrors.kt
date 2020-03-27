package com.daimler.mbingresskit.implementation.network.model

import com.daimler.mbingresskit.common.RegistrationErrors
import com.daimler.mbnetworkkit.networking.RequestError
import com.google.gson.annotations.SerializedName

internal data class ConsentApiInputErrors(
    @SerializedName("errors") val errors: List<ApiInputError>?,
    @SerializedName("toasConsentNotGiven") val consentNotGiven: Boolean
) : RequestError

internal fun ConsentApiInputErrors.toRegistrationErrors() =
    RegistrationErrors(
        errors?.map { it.toUserInputError() } ?: emptyList(),
        consentNotGiven
    )
