package com.daimler.mbprotokit.dto.car.headunit

import com.daimler.mbprotokit.dto.car.AttributeInfo
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class Headunit(
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    /**
     * Selected HeadUnit language
     */
    val languageHu: Pair<LanguageState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.LANGUAGE_HU,
        LanguageState.map()
    )

    /**
     * Temperature unit in HeadUnit
     * false: Celsius
     * true: Fahrenheit
     */
    val temperatureUnitHu: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TEMPERATURE_UNIT_HU
    )

    /**
     * Time format in HeadUnit
     * false: 12h
     * true: 24h
     */
    val timeFormatHu: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TIME_FORMAT_HU
    )

    /**
     * Vehicle tracking on/off in HU
     * false: Off
     * true: On
     */
    val trackingStateHu: Pair<Boolean?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.TRACKING_STATE_HU
    )

    /**
     * List of up to 21 departure times set in HeadUnit
     */
    val weeklySetHU: Pair<List<DayTime>?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WEEKLY_SET_HU,
        DayTime.mapToDayTime()
    )

    /**
     * Weekly profile for preconditioning the vehicle.
     */
    val weeklyProfile: Pair<WeeklyProfile?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.WEEKLY_PROFILE,
        WeeklyProfile.mapToWeeklyProfile()
    )
}
