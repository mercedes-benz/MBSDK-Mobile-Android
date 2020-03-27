package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit

data class HeadUnit(
    /**
     * HeadUnit tracking, on or off
     */
    var tracking: VehicleAttribute<ActiveState, NoUnit>,

    /**
     * Language of HeadUnit
     */
    var language: VehicleAttribute<LanguageState, NoUnit>,

    /**
     * Temperature unit of HeadUnit, e.g. Celsius or Fahrenheit
     */
    var temperature: VehicleAttribute<TemperatureType, NoUnit>,

    /**
     * Timeformat of HeadUnit, e.g. 12h or 24h
     */
    var timeformat: VehicleAttribute<TimeFormatType, NoUnit>,

    /**
     * List of 0..21 departure-times currently set in HeadUnit
     */
    var weeklySetHU: VehicleAttribute<List<DayTime>, NoUnit>,

    /**
     * Weekly profile for preconditioning.
     */
    var weeklyProfile: VehicleAttribute<WeeklyProfile, NoUnit>
)
