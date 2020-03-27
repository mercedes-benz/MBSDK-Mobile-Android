package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.dto.car.tires.TireLampState
import com.daimler.mbprotokit.dto.car.tires.TireLevelPrwState
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class Warning(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * Brake fluid warning
     * false: "brake fluid warning inactive"
     * true: "brake fluid warning triggered"
     */
    val warningBrakeFluid: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WARNING_BRAKE_FLUID
    )

    /**
     * Brake lining wear warning
     * false: "brake lining warning inactive"
     * true: "brake lining warning triggered"
     */
    val warningBrakeLiningWear: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WARNING_BRAKE_LINING_WEAR
    )

    /**
     * Coolant level low warning
     * false: "coolant level warning inactive"
     * true: "coolant level warning triggered"
     */
    val warningCoolantLevelLow: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WARNING_COOLANT_LEVEL_LOW
    )
    val electricalRangeSkipIndication: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.ELECTRIC_RANGE_SKIP_INDICATION
    )
    val liquidRangeSkipIndication: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.LIQUID_RANGE_SKIP_INDICATION
    )

    /**
     * Engine warning light
     * false: Off
     * true: On
     */
    val warningEngineLight: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WARNING_ENGINE_LIGHT
    )

    /**
     * Starter battery warning
     * false: "no warning"
     * true: "Low battery power"
     */
    val warningLowBattery: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WARNING_LOW_BATTERY
    )

    /**
     * Tire warning lamp
     * 0: "No warning"
     * 1: "Permanent glowing"
     * 2: "Lamp blinking followed by permanent glowing"
     */
    val warningTireLamp: Pair<TireLampState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TIRE_WARNING_LAMP,
        TireLampState.map()
    )

    /**
     * Tire level Plattrollwarner warning
     * 0: no warning
     * 1: warning
     * 2: Visit workshop
     */
    val tireLevelPrw: Pair<TireLevelPrwState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TIRE_WARNING_LEVEL_PRW,
        TireLevelPrwState.map()
    )

    /**
     * Tire warning Plattrollwarner
     * false: "no warning"
     * true: "warning"
     */
    val warningTireSprw: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TIRE_WARNING_SPRW
    )

    /**
     * Wash water low warning
     * false: "wash water warning inactive"
     * true: "wash water warning triggered"
     */
    val warningWashWater: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WARNING_WASH_WATER
    )
}
