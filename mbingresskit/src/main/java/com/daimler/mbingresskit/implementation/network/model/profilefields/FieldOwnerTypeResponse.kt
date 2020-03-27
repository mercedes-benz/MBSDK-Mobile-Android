package com.daimler.mbingresskit.implementation.network.model.profilefields

import com.google.gson.annotations.SerializedName

internal enum class FieldOwnerTypeResponse {
    @SerializedName("ACCOUNT")
    ACCOUNT,
    @SerializedName("VEHICLE")
    VEHICLE
}
