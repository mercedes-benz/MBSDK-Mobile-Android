package com.daimler.mbprotokit.dto.car.drivingmodes

import com.daimler.mbprotokit.dto.car.AttributeInfo
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class DrivingModes(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * Teenage Driving Mode state
     * 0: Unknown
     * 1: Mode off
     * 2: Mode on
     * 3: Pending off
     * 4: Pending on
     * 5: Error
     */
    val teenageDrivingMode: Pair<DrivingModeState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TEENAGE_DRIVING_MODE,
        DrivingModeState.map()
    )

    /**
     * Valet Driving Mode state
     * 0: Unknown
     * 1: Mode off
     * 2: Mode on
     * 3: Pending off
     * 4: Pending on
     * 5: Error
     */
    val valetDrivingMode: Pair<DrivingModeState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.VALET_DRIVING_MODE,
        DrivingModeState.map()
    )
}
