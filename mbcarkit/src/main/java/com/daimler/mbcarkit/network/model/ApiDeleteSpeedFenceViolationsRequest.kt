package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiDeleteSpeedFenceViolationsRequest(
    @SerializedName("ids") val ids: List<Int>
)
