package com.daimler.mbingresskit.implementation.network.model.user.fetch

import com.google.gson.annotations.SerializedName

internal enum class UserPinStatusResponse {
    @SerializedName("SET") SET,
    @SerializedName("NOT_SET") NOT_SET,
    @SerializedName("UNKNOWN") UNKNOWN
}
