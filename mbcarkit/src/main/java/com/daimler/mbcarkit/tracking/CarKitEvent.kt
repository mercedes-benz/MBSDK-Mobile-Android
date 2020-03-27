package com.daimler.mbcarkit.tracking

import com.daimler.mbcommonkit.tracking.TrackingEvent

sealed class CarKitEvent(
    value: Map<String, String>,
    tag: String? = null
) : TrackingEvent(value, tag) {
    class VehicleCommandEvent(
        value: Map<String, String>,
        tag: String
    ) : CarKitEvent(value, tag)
}
