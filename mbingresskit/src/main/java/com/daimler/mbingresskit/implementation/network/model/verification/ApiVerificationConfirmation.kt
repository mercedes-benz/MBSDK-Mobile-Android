package com.daimler.mbingresskit.implementation.network.model.verification

import com.daimler.mbingresskit.common.VerificationConfirmation
import com.google.gson.annotations.SerializedName

data class ApiVerificationConfirmation(
    @SerializedName("type") val type: ApiVerificationType,
    @SerializedName("subject") val subject: String,
    @SerializedName("code") val code: String
) {
    companion object {
        fun fromVerificationConfirmation(verificationConfirmation: VerificationConfirmation) =
            ApiVerificationConfirmation(
                ApiVerificationType.fromVerificationType(verificationConfirmation.type),
                verificationConfirmation.subject,
                verificationConfirmation.code
            )
    }
}
