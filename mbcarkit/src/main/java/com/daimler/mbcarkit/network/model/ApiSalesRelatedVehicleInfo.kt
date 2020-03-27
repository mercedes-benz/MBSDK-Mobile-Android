package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal data class ApiSalesRelatedVehicleInfo(
    @SerializedName("baumuster") val baumuster: ApiBaumuster?,
    @SerializedName("paint1") val paint: ApiVehicleAmenity?,
    @SerializedName("upholstery") val upholstery: ApiVehicleAmenity?,
    @SerializedName("line") val line: ApiVehicleAmenity?
)
