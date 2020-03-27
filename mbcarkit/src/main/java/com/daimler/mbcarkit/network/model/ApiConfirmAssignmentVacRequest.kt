package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiConfirmAssignmentVacRequest(
    @SerializedName("code") val code: String
)
