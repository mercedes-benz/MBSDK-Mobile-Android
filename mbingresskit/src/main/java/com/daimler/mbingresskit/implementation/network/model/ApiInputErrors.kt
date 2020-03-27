package com.daimler.mbingresskit.implementation.network.model

import com.daimler.mbingresskit.common.UserInputErrors
import com.daimler.mbnetworkkit.networking.RequestError
import com.google.gson.annotations.SerializedName

internal data class ApiInputErrors(
    @SerializedName("errors") val errors: List<ApiInputError>?
) : RequestError

internal fun ApiInputErrors.toUserInputErrors() = UserInputErrors(
    errors?.map {
        it.toUserInputError()
    } ?: emptyList()
)
