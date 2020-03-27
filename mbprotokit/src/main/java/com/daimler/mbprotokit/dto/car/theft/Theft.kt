package com.daimler.mbprotokit.dto.car.theft

import com.daimler.mbprotokit.dto.car.AttributeInfo
import com.daimler.mbprotokit.dto.car.zev.TheftWarningReasonState
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class Theft(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * State of interior protecton sensors
     * 0: not active, but selected
     * 1: not active, not selected
     * 2: Active
     */
    val interiorProtectionStatus: Pair<ActiveSelectionState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.INTERIOR_PROTECTION_SENSOR_STATE,
        ActiveSelectionState.map()
    )

    /**
     * Theft alarm active (ringing)
     * false: Not active
     * true: Active
     */
    val theftAlarmActive: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.THEFT_ALARM_ACTIVE
    )

    /**
     * Theft alarm system monitoring
     * false: theft system not armed
     * true: theft system armed
     */
    val theftSystemArmed: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.THEFT_SYSTEM_ARMED
    )

    /**
     * Tow protection sensor status
     * 0: not active, but selected
     * 1: not active, not selected
     * 2: Active
     */
    val towProtectionSensorStatus: Pair<ActiveSelectionState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TOW_PROTECTION_SENSOR_STATE,
        ActiveSelectionState.map()
    )

    /**
     * Reason of last theft warning
     */
    val lastTheftWarningReason: Pair<TheftWarningReasonState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.LAST_THEFT_WARNING_REASON,
        TheftWarningReasonState.map()
    )

    /**
     * UTC timestamp of last theft alarm
     * Range: 0..2^64-2
     *
     * Hint: Should be long in the future
     */
    val lastTheftWarning: Pair<Long?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.LAST_THEFT_WARNING
    )

    /**
     * Activation state of all vehicle keys
     * false: all vehicle keys are deactivated
     * true: all vehicle keys are activated
     */
    val keyActivationState: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.KEY_ACTIVATION_STATE
    )

    /**
     * Last collision details
     */
    val collision: Collision = Collision(attributes)
}
