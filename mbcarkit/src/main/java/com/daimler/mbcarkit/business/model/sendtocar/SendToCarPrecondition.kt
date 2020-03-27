package com.daimler.mbcarkit.business.model.sendtocar

enum class SendToCarPrecondition {
    /**
     * A user has to open MBApps on his Headunit first to register his vehicle in the backend.
     * Afterwards the user can use send to car for his vehicle.
     */
    MBAPPS_REGISTER_USER,

    /**
     * Send2Car will not be enabled for the user and can be enabled through admin tool from MBApps.
     */
    MBAPPS_ENABLE_MBAPPS
}
