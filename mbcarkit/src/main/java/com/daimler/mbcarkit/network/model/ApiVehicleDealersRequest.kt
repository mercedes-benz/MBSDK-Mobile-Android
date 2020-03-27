package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.VehicleDealer
import com.google.gson.annotations.SerializedName

internal data class ApiVehicleDealersRequest(
    @SerializedName("items") val items: List<ApiVehicleDealerRequest>
) {
    companion object {
        fun fromVehicleDealers(vehicleDealers: List<VehicleDealer>) =
            ApiVehicleDealersRequest(
                vehicleDealers.mapNotNull { dealer ->
                    ApiDealerRole.fromDealerRole(dealer.role)?.let { role ->
                        ApiVehicleDealerRequest(dealer.dealerId, role)
                    }
                }
            )
    }
}
