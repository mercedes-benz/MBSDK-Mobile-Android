package com.daimler.mbcarkit.business.model.sendtocar

data class SendToCarRoute(
    /**
     * Represents the HU capability which is used
     */
    val routeType: RouteType,
    /**
     * List of waypoints sent to the car.
     * The correct amount of waypoints depends on the route type.
     * A single waypoint for singlePOI, 2-5 for staticRoute and dynamicRoute.
     */
    val waypoints: List<SendToCarWaypoint>,
    /**
     * The title of the route to be displayed
     */
    val routeTitle: String? = null,
    /**
     * Text of the notification
     */
    val notificationText: String? = null
)
