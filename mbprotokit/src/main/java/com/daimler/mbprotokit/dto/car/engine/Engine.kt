package com.daimler.mbprotokit.dto.car.engine

import com.daimler.mbprotokit.dto.car.AttributeInfo
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class Engine(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * Ignition switch state
     * 0: "ignition lock (0)"
     * 1: "ignition off (15c)"
     * 2: "ignition accessory (15r)"
     * 4: "ignition on (15)"
     * 5: "ignition start (50)"
     */
    val ignitionState: Pair<IgnitionState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.IGNITION_STATE,
        IgnitionState.map()
    )

    /**
     * Engine running state
     * false.. Engine not running
     * true.. Engine running
     */
    val engineState: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.ENGINE_STATE
    )

    /**
     * Remote Start Active
     * false.. Remote Start not active
     * true.. Remote Start active
     */
    val remoteStartActive: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.REMOTE_START_ACTIVE
    )

    /**
     * Remote Start inside Temperature
     * Step 0.5 °C
     * Range: -40.0..85.0
     */
    val remoteStartTemperature: Pair<Double?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.REMOTE_START_TEMPERATURE
    )

    /**
     * Remote Start End Time in s since 1.1.1970 00:00:00
     * Range: 0..2^64-2
     *
     * Hint: Should be a long in the future
     */
    val remoteStartEndtime: Pair<Long?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.REMOTE_START_ENDTIME
    )
}
