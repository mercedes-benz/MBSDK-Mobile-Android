package com.daimler.mbingresskit.implementation.network.model.verification

import com.daimler.mbingresskit.common.VerificationType
import com.google.gson.annotations.SerializedName

enum class ApiVerificationType {
    @SerializedName("MOBILE_PHONE_NUMBER") MOBILE_PHONE_NUMBER,
    @SerializedName("EMAIL") EMAIL;

    companion object {
        fun fromVerificationType(verificationType: VerificationType) =
            values().associateBy(ApiVerificationType::name)[verificationType.name] ?: MOBILE_PHONE_NUMBER
    }
}
