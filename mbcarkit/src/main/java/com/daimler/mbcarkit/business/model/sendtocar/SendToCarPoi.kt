package com.daimler.mbcarkit.business.model.sendtocar

data class SendToCarPoi(
    /**
     * POI to send to the car
     */
    val location: SendToCarWaypoint,
    /**
     * The title of the POI to be displayed
     */
    val title: String? = null,
    /**
     * Text of the notification
     */
    val notificationText: String? = null
)
