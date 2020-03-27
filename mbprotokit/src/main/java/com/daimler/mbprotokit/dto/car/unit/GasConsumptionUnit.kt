package com.daimler.mbprotokit.dto.car.unit

import com.daimler.mbprotokit.generated.VehicleEvents.VehicleAttributeStatus

enum class GasConsumptionUnit(
    val gasConsumptionUnit: VehicleAttributeStatus.GasConsumptionUnit
) : Unit {
    UNSPECIFIED_GAS_CONSUMPTION_UNIT(VehicleAttributeStatus.GasConsumptionUnit.UNSPECIFIED_GAS_CONSUMPTION_UNIT),

    /**
     * kG per 100 km
     */
    KG_PER_100KM(VehicleAttributeStatus.GasConsumptionUnit.KG_PER_100KM),

    /**
     * km per kg
     */
    KM_PER_KG(VehicleAttributeStatus.GasConsumptionUnit.KM_PER_KG),

    /**
     * miles per kg
     */
    M_PER_KG(VehicleAttributeStatus.GasConsumptionUnit.M_PER_KG),

    UNRECOGNIZED(VehicleAttributeStatus.GasConsumptionUnit.UNRECOGNIZED);

    companion object {
        fun map(gasConsumptionUnit: VehicleAttributeStatus.GasConsumptionUnit) = values().find {
            gasConsumptionUnit == it.gasConsumptionUnit
        } ?: UNRECOGNIZED
    }
}
