package com.daimler.mbprotokit.dto.car.unit

import com.daimler.mbprotokit.generated.VehicleEvents.VehicleAttributeStatus

enum class CombustionConsumptionUnit(
    val consumptionUnit: VehicleAttributeStatus.CombustionConsumptionUnit
) : Unit {
    UNSPECIFIED_COMBUSTION_CONSUMPTION_UNIT(VehicleAttributeStatus.CombustionConsumptionUnit.UNSPECIFIED_COMBUSTION_CONSUMPTION_UNIT),

    /**
     * Liter per 100 km
     */
    LITER_PER_100KM(VehicleAttributeStatus.CombustionConsumptionUnit.LITER_PER_100KM),

    /**
     * Kilometers per liter
     */
    KM_PER_LITER(VehicleAttributeStatus.CombustionConsumptionUnit.KM_PER_LITER),

    /**
     * Miles Per imperial gallon
     */
    MPG_UK(VehicleAttributeStatus.CombustionConsumptionUnit.MPG_UK),

    /**
     * Miles Per US gallon
     */
    MPG_US(VehicleAttributeStatus.CombustionConsumptionUnit.MPG_US),

    UNRECOGNIZED(VehicleAttributeStatus.CombustionConsumptionUnit.UNRECOGNIZED);

    companion object {
        fun map(consumptionUnit: VehicleAttributeStatus.CombustionConsumptionUnit) = values().find {
            consumptionUnit == it.consumptionUnit
        } ?: UNRECOGNIZED
    }
}
