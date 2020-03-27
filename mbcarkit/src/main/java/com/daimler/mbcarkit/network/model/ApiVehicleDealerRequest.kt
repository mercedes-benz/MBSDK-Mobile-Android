package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiVehicleDealerRequest(
    @SerializedName("dealerId") val dealerId: String,
    @SerializedName("role") val role: ApiDealerRole
)
