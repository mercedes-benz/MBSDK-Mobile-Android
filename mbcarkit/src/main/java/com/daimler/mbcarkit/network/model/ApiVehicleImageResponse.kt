package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiVehicleImageResponse(
    @SerializedName("imageKey") val imageKey: String,
    @SerializedName("url") val imageUrl: String
)
