package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.services.ServiceStatus
import com.daimler.mbcarkit.network.model.ApiServiceStatus.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiServiceStatus {
    @SerializedName("UNKNOWN") UNKNOWN,
    @SerializedName("ACTIVE") ACTIVE,
    @SerializedName("INACTIVE") INACTIVE,
    @SerializedName("ACTIVATION_PENDING") ACTIVATION_PENDING,
    @SerializedName("DEACTIVATION_PENDING") DEACTIVATION_PENDING;

    companion object {
        val map: Map<String, ServiceStatus> = ServiceStatus.values().associateBy(ServiceStatus::name)
    }
}

internal fun ApiServiceStatus?.toServiceStatus(): ServiceStatus =
    this?.let { map[name] } ?: ServiceStatus.UNKNOWN
