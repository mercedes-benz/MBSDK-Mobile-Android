package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.merchants.OutletActivity
import com.google.gson.annotations.SerializedName

/**
 * Activity an outlet can support. Currently only sales and service are relevant.
 */
internal enum class ApiOutletActivity {
    @SerializedName("SALES") SALES,
    @SerializedName("SERVICE") SERVICE;

    companion object {
        private val map: Map<String, ApiOutletActivity> = values().associateBy(ApiOutletActivity::name)

        fun fromOutletActivity(outletActivity: OutletActivity?) = outletActivity?.let { map[it.name] }
    }
}
