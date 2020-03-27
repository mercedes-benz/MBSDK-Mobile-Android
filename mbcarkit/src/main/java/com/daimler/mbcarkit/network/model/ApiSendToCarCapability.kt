package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapability
import com.daimler.mbcarkit.network.model.ApiSendToCarCapability.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiSendToCarCapability {
    @SerializedName("SINGLE_POI_BLUETOOTH") SINGLE_POI_BLUETOOTH,
    @SerializedName("SINGLE_POI_BACKEND") SINGLE_POI_BACKEND,
    @SerializedName("STATIC_ROUTE_BACKEND") STATIC_ROUTE_BACKEND,
    @SerializedName("DYNAMIC_ROUTE_BACKEND") DYNAMIC_ROUTE_BACKEND;

    companion object {
        val map: Map<String, SendToCarCapability> = SendToCarCapability.values().associateBy(SendToCarCapability::name)
    }
}

internal fun ApiSendToCarCapability?.toSendToCarCapability(): SendToCarCapability =
    this?.let { map[name] } ?: SendToCarCapability.DYNAMIC_ROUTE_BACKEND
