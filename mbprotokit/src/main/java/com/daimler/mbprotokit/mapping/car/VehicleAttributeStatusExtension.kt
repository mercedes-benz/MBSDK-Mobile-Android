package com.daimler.mbprotokit.mapping.car

import com.daimler.mbprotokit.dto.car.AttributeInfo
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.generated.VehicleEvents.VehicleAttributeStatus

/**
 * Retrieve proto data by expected type and applies provided mapping strategy
 * to get data as wanted type
 *
 * @param name is the key/attribute name (only for debugging purpose or logs)
 * @param mapping strategy/delegation. Maps the retrieved value from proto to your WANTED type with the given strategy
 *
 * @return pair with the wanted value and additional attribute information
 */
@Suppress("UNCHECKED_CAST")
internal inline fun <reified EXPECTED, WANTED> VehicleAttributeStatus?.mapValueAndInfo(
    name: String? = null,
    mapStrategy: ((EXPECTED?) -> (WANTED))
): Pair<WANTED, AttributeInfo> {
    return mapStrategy.invoke(this.getValue<EXPECTED>(name)) to this.getInfo()
}

/**
 * Retrieve proto data by expected type
 *
 * @param name is the key/attribute name (only for debugging purpose or logs)
 *
 * @return pair with the expected value and additional attribute information
 */
@Suppress("UNCHECKED_CAST")
internal inline fun <reified EXPECTED> VehicleAttributeStatus?.getValueAndInfo(name: String? = null): Pair<EXPECTED?, AttributeInfo> =
    this.getValue<EXPECTED>(name) to this.getInfo()

/**
 * Retrieve proto data by expected type
 *
 * @param name is the key/attribute name. It can be used for logs or debugging
 *
 * @return the expected value by given type
 */
@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
internal inline fun <reified EXPECTED> VehicleAttributeStatus?.getValue(name: String? = null): EXPECTED? {
    return when (EXPECTED::class) {
        // Primitive
        Int::class -> this?.intValue?.toInt() // Int32
        Long::class -> this?.intValue // Int64
        Boolean::class -> this?.boolValue
        String::class -> this?.stringValue
        Double::class -> this?.doubleValue
        // Custom
        VehicleEvents.TemperaturePointsValue::class -> this?.temperaturePointsValue
        VehicleEvents.WeekdayTariffValue::class -> this?.weekdayTariffValue
        VehicleEvents.WeekendTariffValue::class -> this?.weekendTariffValue
        VehicleEvents.StateOfChargeProfileValue::class -> this?.stateOfChargeProfileValue
        VehicleEvents.EcoHistogramValue::class -> this?.ecoHistogramValue
        VehicleEvents.WeeklyProfileValue::class -> this?.weeklyProfileValue
        VehicleEvents.ChargeProgramsValue::class -> this?.chargeProgramsValue
        VehicleEvents.SpeedAlertConfigurationValue::class -> this?.speedAlertConfigurationValue
        VehicleEvents.WeeklySettingsHeadUnitValue::class -> this?.weeklySettingsHeadUnitValue
        VehicleEvents.ChargingBreakClockTimer::class -> this?.chargingBreakClockTimer
        VehicleEvents.ChargingPowerControl::class -> this?.chargingPowerControl
        else -> null
    } as? EXPECTED
}
