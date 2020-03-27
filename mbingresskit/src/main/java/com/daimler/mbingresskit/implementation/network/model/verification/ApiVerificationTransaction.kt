package com.daimler.mbingresskit.implementation.network.model.verification

import com.daimler.mbingresskit.common.VerificationTransaction
import com.google.gson.annotations.SerializedName

data class ApiVerificationTransaction(
    @SerializedName("type") val type: ApiVerificationType,
    @SerializedName("subject") val subject: String
) {
    companion object {
        fun fromVerificationTransaction(verificationTransaction: VerificationTransaction) =
            ApiVerificationTransaction(
                ApiVerificationType.fromVerificationType(verificationTransaction.type),
                verificationTransaction.subject
            )
    }
}
