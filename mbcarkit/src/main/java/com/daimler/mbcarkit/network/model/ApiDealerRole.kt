package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.DealerRole
import com.daimler.mbcarkit.network.model.ApiDealerRole.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiDealerRole {
    @SerializedName("SALES") SALES,
    @SerializedName("SERVICE") SERVICE;

    companion object {
        val map: Map<String, DealerRole> = DealerRole.values().associateBy(DealerRole::name)
        private val reverseMap: Map<String, ApiDealerRole> = values().associateBy(ApiDealerRole::name)

        fun fromDealerRole(dealerRole: DealerRole?) = dealerRole?.let { reverseMap[it.name] }
    }
}

internal fun ApiDealerRole?.toDealerRole(): DealerRole =
    this?.let { map[name] } ?: DealerRole.UNKNOWN
