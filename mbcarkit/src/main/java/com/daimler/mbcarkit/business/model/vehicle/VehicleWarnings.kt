package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit

data class VehicleWarnings(
    /**
     * Brake fluid warning active/inactive
     */
    var brakeFluid: VehicleAttribute<OnOffState, NoUnit>,

    /**
     * Brake lining warning active/inactive
     */
    var brakeLiningWear: VehicleAttribute<OnOffState, NoUnit>,

    /**
     * Coolant level warning active/inactive
     */
    var coolantLevelLow: VehicleAttribute<OnOffState, NoUnit>,

    /**
     * Electric range scip warning active/inactive
     */
    var electricalRangeScipIndication: VehicleAttribute<OnOffState, NoUnit>,

    /**
     * Engine light warning active/inactive
     */
    var engineLight: VehicleAttribute<OnOffState, NoUnit>,

    /**
     * Liquid range skip warning active/inactive
     */
    var liquidRangeSkipIndication: VehicleAttribute<OnOffState, NoUnit>,

    /**
     * Low battery warning active/inactive
     */
    var lowBattery: VehicleAttribute<OnOffState, NoUnit>,

    /**
     * Tire lamp warning active/inactive
     */
    var tireLamp: VehicleAttribute<TireLampState, NoUnit>,

    /**
     * Tire level plattrollwarner warning active/inactive
     */
    var tireLevelPrw: VehicleAttribute<TireLevelPrwState, NoUnit>,

    /**
     * Tire level Sprw warning active/inactive
     */
    var tireSprw: VehicleAttribute<OnOffState, NoUnit>,

    /**
     * Wash water warning active/inactive
     */
    var washWater: VehicleAttribute<OnOffState, NoUnit>
)
