package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.services.ServiceRight
import com.daimler.mbcarkit.network.model.ApiServiceRight.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiServiceRight {
    @SerializedName("READ") READ,
    @SerializedName("ACTIVATE") ACTIVATE,
    @SerializedName("DEACTIVATE") DEACTIVATE,
    @SerializedName("EXECUTE") EXECUTE,
    @SerializedName("MANAGE") MANAGE;

    companion object {
        val map: Map<String, ServiceRight> = ServiceRight.values().associateBy(ServiceRight::name)
    }
}

internal fun ApiServiceRight?.toServiceRight(): ServiceRight =
    this?.let { map[name] } ?: ServiceRight.UNKNOWN
