package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiVehicleAssignedSubusers(
    @SerializedName("pendingSubusers") val pendingSubusers: List<ApiVehicleAssignedUser>?,
    @SerializedName("validSubusers") val validSubusers: List<ApiVehicleAssignedUser>?,
    @SerializedName("temporarySubusers") val temporarySubusers: List<ApiVehicleAssignedUser>?
)
