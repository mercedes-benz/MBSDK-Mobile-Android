package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.NormalizedProfileControlSupport
import com.daimler.mbcarkit.network.model.ApiNormalizedProfileControlSupport.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiNormalizedProfileControlSupport {
    @SerializedName("SUPPORTED") SUPPORTED,
    @SerializedName("UNSUPPORTED") UNSUPPORTED,
    @SerializedName("UNKNOWN") UNKNOWN;

    companion object {
        val map: Map<String, NormalizedProfileControlSupport> = NormalizedProfileControlSupport.values().associateBy(NormalizedProfileControlSupport::name)
    }
}

internal fun ApiNormalizedProfileControlSupport?.toNormalizedProfileControlSupport(): NormalizedProfileControlSupport? =
    this?.let { map[name] }
