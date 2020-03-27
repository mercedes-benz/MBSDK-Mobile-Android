package com.daimler.mbingresskit.implementation.network.model

import com.daimler.mbingresskit.common.UserInputError
import com.google.gson.annotations.SerializedName

internal data class ApiInputError(
    @SerializedName("fieldName") val fieldName: String?,
    @SerializedName("description") val description: String?
)

internal fun ApiInputError.toUserInputError() = UserInputError(
    fieldName = fieldName,
    description = description
)
