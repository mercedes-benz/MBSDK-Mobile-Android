package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiVehicleDealersResponse(
    @SerializedName("items") val items: List<ApiVehicleDealerResponse>?
)
