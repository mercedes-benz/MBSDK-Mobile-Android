package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.sendtocar.SendToCarRoute
import com.google.gson.annotations.SerializedName

internal data class ApiSendToCarRoute(
    @SerializedName("routeType") val routeType: ApiRouteType,
    @SerializedName("routeTitle") val routeTitle: String?,
    @SerializedName("notificationText") val notificationText: String?,
    @SerializedName("waypoints") val waypoints: List<ApiSendToCarWaypoint>
) {
    companion object {
        fun fromSendToCarRoute(sendToCarRoute: SendToCarRoute) = ApiSendToCarRoute(
            ApiRouteType.fromRouteType(sendToCarRoute.routeType),
            sendToCarRoute.routeTitle,
            sendToCarRoute.notificationText,
            sendToCarRoute.waypoints.map { ApiSendToCarWaypoint.fromSendToCarWaypoint(it) }
        )
    }
}
