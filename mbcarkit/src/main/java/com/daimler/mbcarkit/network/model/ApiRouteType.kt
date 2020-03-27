package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.sendtocar.RouteType
import com.google.gson.annotations.SerializedName

internal enum class ApiRouteType {
    @SerializedName("singlePOI") SINGLE_POI,
    @SerializedName("staticRoute") STATIC_ROUTE,
    @SerializedName("dynamicRoute") DYNAMIC_ROUTE;

    companion object {
        private val map: Map<String, ApiRouteType> = values().associateBy(ApiRouteType::name)

        fun fromRouteType(routeType: RouteType) = map[routeType.name] ?: DYNAMIC_ROUTE
    }
}
