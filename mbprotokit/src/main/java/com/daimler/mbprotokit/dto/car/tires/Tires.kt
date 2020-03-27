package com.daimler.mbprotokit.dto.car.tires

import com.daimler.mbprotokit.dto.car.AttributeInfo
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class Tires(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * Tire pressure front left in given pressure unit
     * Range: 0..632.5
     */
    val tirePressureFrontLeft: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TIRE_PRESSURE_FRONT_LEFT
    )

    /**
     * Tire pressure front right in given pressure unit
     * Range: 0..632.5
     */
    val tirePressureFrontRight: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TIRE_PRESSURE_FRONT_RIGHT
    )

    /**
     * Tire pressure rear left in given pressure unit
     * Range: 0..632.5
     */
    val tirePressureRearLeft: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TIRE_PRESSURE_REAR_LEFT
    )

    /**
     * Tire pressure rear right in given pressure unit
     * Range: 0..632.5
     */
    val tirePressureRearRight: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TIRE_PRESSURE_REAR_RIGHT
    )

    /**
     * Tire marker front left
     * 0: "no warning"
     * 1: "soft warning"
     * 2: "low pressure"
     * 3: "deflation"
     * 4: "mark" (unknown kind of warning but there is one)
     */
    val tireMarkerFrontLeft: Pair<TireMarkerState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TIRE_MARKER_FRONT_LEFT,
        TireMarkerState.map()
    )

    /**
     * Tire marker front right
     * 0: "no warning"
     * 1: "soft warning"
     * 2: "low pressure"
     * 3: "deflation"
     * 4: "mark" (unknown kind of warning but there is one)
     */
    val tireMarkerFrontRight: Pair<TireMarkerState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TIRE_MARKER_FRONT_RIGHT,
        TireMarkerState.map()
    )

    /**
     * Tire marker rear left
     * 0: "no warning"
     * 1: "soft warning"
     * 2: "low pressure"
     * 3: "deflation"
     * 4: "mark" (unknown kind of warning but there is one)
     */
    val tireMarkerRearLeft: Pair<TireMarkerState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TIRE_MARKER_REAR_LEFT,
        TireMarkerState.map()
    )

    /**
     * Tire marker rear right
     * 0: "no warning"
     * 1: "soft warning"
     * 2: "low pressure"
     * 3: "deflation"
     * 4: "mark" (unknown kind of warning but there is one)
     */
    val tireMarkerRearRight: Pair<TireMarkerState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TIRE_MARKER_REAR_RIGHT,
        TireMarkerState.map()
    )

    /**
     * Latest pressure measurement UTC timestamp in seconds
     */
    val tirePressureMeasurementTimestamp: Pair<Long?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TIRE_PRESSURE_MEASUREMENT_TIMESTAMP
    )

    /**
     * Tire sensor availability:
     * 0: All sensors located
     * 1: 1 to 3 sensors are missing
     * 2: All sensors missing
     * 3: System Error
     * 4: Autolocate Error
     */
    val tireSensorAvailable: Pair<TireSensorState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TIRE_SENSOR_AVAILABLE,
        TireSensorState.map()
    )

    /**
     * Tire warning RDK
     * 0: "no warning"
     * 1: "soft warning"
     * 2: "low pressure"
     * 3: "deflation"
     */
    val warningTireSrdk: Pair<TireSrdkState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TIRE_WARNING_SRDK,
        TireSrdkState.map()
    )
}
