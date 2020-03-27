package com.daimler.mbprotokit.dto.car.position

import com.daimler.mbprotokit.dto.car.AttributeInfo
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class Position(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * Direction of vehicle in degrees
     * Range: 0..359.9
     */
    val heading: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.POSITION_HEADING
    )

    /**
     * Latitude in WGS84 coordinates
     * Range: -90.000000..90.000000
     */
    val latitude: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.POSITION_LAT
    )

    /**
     * Longitude in WGS84 coordinates
     * Range: -180.000000..180.000000
     */
    val longitude: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.POSITION_LONG
    )

    /**
     * Vehicle position error code.
     * 0: position not available
     * 1: service inactive
     * 2: location tracking disabled
     * 3: vehicle is not moving
     * 4: vehicle is moving
     */
    val errorCode: Pair<VehicleLocationErrorState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.POSITION_ERROR_CODE,
        VehicleLocationErrorState.map()
    )

    /**
     * True if proximity calculation is required for vehicle location.
     *
     * false - not required
     * true - is required
     */
    val proximityCalculationRequired: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.PROXIMITY_CALCULATION_POSITION_REQUIRED
    )
}
