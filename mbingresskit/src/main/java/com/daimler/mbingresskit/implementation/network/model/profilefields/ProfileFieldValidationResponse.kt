package com.daimler.mbingresskit.implementation.network.model.profilefields

import com.daimler.mbingresskit.common.ProfileFieldValidation
import com.google.gson.annotations.SerializedName

internal data class ProfileFieldValidationResponse(
    @SerializedName("minLength") val minLength: Int?,
    @SerializedName("maxLength") val maxLength: Int?,
    @SerializedName("regularExpression") val regularExpression: String?
)

internal fun ProfileFieldValidationResponse.toProfileFieldValidation() = ProfileFieldValidation(
    minLength = minLength,
    maxLength = maxLength,
    regularExpression = regularExpression
)
