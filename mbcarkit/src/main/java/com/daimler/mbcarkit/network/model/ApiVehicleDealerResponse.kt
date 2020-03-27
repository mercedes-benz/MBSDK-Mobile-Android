package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.VehicleDealer
import com.google.gson.annotations.SerializedName
import java.util.Date

internal data class ApiVehicleDealerResponse(
    @SerializedName("dealerId") val dealerId: String,
    @SerializedName("role") val role: ApiDealerRole,
    @SerializedName("updatedAt") val updatedAt: Date?,
    @SerializedName("dealerData") val dealerData: ApiMerchantResponse?
)

internal fun ApiVehicleDealerResponse.toVehicleDealer() =
    VehicleDealer(
        dealerId,
        role.toDealerRole(),
        updatedAt,
        dealerData?.toMerchant()
    )
