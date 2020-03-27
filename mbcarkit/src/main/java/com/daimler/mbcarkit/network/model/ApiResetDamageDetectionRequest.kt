package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiResetDamageDetectionRequest(
    @SerializedName("pin") val pin: String
)
