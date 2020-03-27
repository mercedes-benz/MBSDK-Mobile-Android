package com.daimler.mbcarkit.business.model.sendtocar

enum class SendToCarCapability {
    /**
     * Ability to send a single POI via Bluetooth
     */
    SINGLE_POI_BLUETOOTH,

    /**
     * Ability to send a single POI via Backend
     */
    SINGLE_POI_BACKEND,

    /**
     * Ability to send a static route to Backend
     */
    STATIC_ROUTE_BACKEND,

    /**
     * Ability to send a dynamic route to Backend
     */
    DYNAMIC_ROUTE_BACKEND
}
