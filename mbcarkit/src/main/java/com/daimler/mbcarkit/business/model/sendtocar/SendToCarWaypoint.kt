package com.daimler.mbcarkit.business.model.sendtocar

data class SendToCarWaypoint(
    /**
     * Location latitude value (required)
     */
    val latitude: Double,
    /**
     * Location longitude value (required)
     */
    val longitude: Double,
    /**
     * Waypoint title
     */
    val title: String? = null,
    val country: String? = null,
    val state: String? = null,
    val city: String? = null,
    val district: String? = null,
    val postalCode: String? = null,
    val street: String? = null,
    val houseNumber: String? = null,
    val subdivision: String? = null,
    val phoneNumber: String? = null
)
