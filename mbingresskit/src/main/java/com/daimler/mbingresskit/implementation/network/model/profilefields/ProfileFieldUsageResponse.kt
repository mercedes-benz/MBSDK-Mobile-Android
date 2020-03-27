package com.daimler.mbingresskit.implementation.network.model.profilefields

import com.google.gson.annotations.SerializedName

internal enum class ProfileFieldUsageResponse {
    @SerializedName("OPTIONAL")
    OPTIONAL,
    @SerializedName("MANDATORY")
    MANDATORY,
    @SerializedName("INVISIBLE")
    INVISIBLE,
    @SerializedName("READONLY")
    READ_ONLY
}
