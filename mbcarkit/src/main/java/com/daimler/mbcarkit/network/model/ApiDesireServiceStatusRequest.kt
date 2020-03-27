package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiDesireServiceStatusRequest(
    @SerializedName("services") val services: List<ApiDesireServiceStatus>
)
