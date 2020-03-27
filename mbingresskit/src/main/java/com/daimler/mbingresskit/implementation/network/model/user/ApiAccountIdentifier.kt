package com.daimler.mbingresskit.implementation.network.model.user

import com.google.gson.annotations.SerializedName

internal enum class ApiAccountIdentifier {
    @SerializedName("EMAIL")
    EMAIL,
    @SerializedName("MOBILE")
    MOBILE,
    @SerializedName("EMAIL_AND_MOBILE")
    EMAIL_AND_MOBILE;

    companion object {
        private val map: Map<String, ApiAccountIdentifier> = values().associateBy(ApiAccountIdentifier::name)

        fun forName(name: String) = map[name]
    }
}
