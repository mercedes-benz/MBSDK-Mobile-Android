package com.daimler.mbingresskit.implementation.network.model.user

import com.google.gson.annotations.SerializedName

internal data class ApiVerifyUserRequest(
    @SerializedName("scanReference") val scanReference: String
)
