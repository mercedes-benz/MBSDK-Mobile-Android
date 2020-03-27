package com.daimler.mbprotokit.dto.car.theft

import com.daimler.mbprotokit.dto.car.AttributeInfo
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class Collision(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * Last parking collision event as UTC timestamp
     * Range: 0..2^64-2
     */
    val lastParkEvent: Pair<Long?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.LAST_PARK_EVENT
    )

    /**
     * Request HU to show park (damage) event.
     * false: popup confirmed
     * true: popup not confirmed
     */
    val lastParkEventNotConfirmed: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.LAST_PARK_EVENT_NOT_CONFIRMED
    )

    /**
     * Park event sensor status, e.g. 'active' or 'inactive'
     */
    val parkEventSensorStatus: Pair<ActiveSelectionState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.PARK_EVENT_SENSOR_STATUS,
        ActiveSelectionState.map()
    )

    /**
     * Parking collision level
     * 0.. low
     * 1.. medium
     * 2.. high
     */
    val parkEventLevel: Pair<ParkEventLevel?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.PARK_EVENT_LEVEL,
        ParkEventLevel.map()
    )

    /**
     * Parking collision direction
     * 0: idle
     * 1: front left
     * 2: front middle
     * 3: front right
     * 4: right
     * 5: rear right
     * 6: rear middle
     * 7: rear left
     * 8: left
     * 9: direction unknown
     */
    val parkEventType: Pair<ParkEventType?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.PARK_EVENT_TYPE,
        ParkEventType.map()
    )
}
