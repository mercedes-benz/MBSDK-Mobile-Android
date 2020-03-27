package com.daimler.mbprotokit.dto.car.unit

import com.daimler.mbprotokit.generated.VehicleEvents.VehicleAttributeStatus

enum class ElectricityConsumptionUnit(
    val electricityConsumptionUnit: VehicleAttributeStatus.ElectricityConsumptionUnit
) : Unit {
    UNSPECIFIED_ELECTRICITY_CONSUMPTION_UNIT(VehicleAttributeStatus.ElectricityConsumptionUnit.UNSPECIFIED_ELECTRICITY_CONSUMPTION_UNIT),

    /**
     * kWh per 100 km
     */
    KWH_PER_100KM(VehicleAttributeStatus.ElectricityConsumptionUnit.KWH_PER_100KM),

    /**
     * Kilometers per kWh
     */
    KM_PER_KWH(VehicleAttributeStatus.ElectricityConsumptionUnit.KM_PER_KWH),

    /**
     * kWh per 100 miles
     */
    KWH_PER_100MI(VehicleAttributeStatus.ElectricityConsumptionUnit.KWH_PER_100MI),

    /**
     * miles per kWh
     */
    M_PER_KWH(VehicleAttributeStatus.ElectricityConsumptionUnit.M_PER_KWH),

    /**
     * Miles per gallon gasoline equivalent
     */
    MPGE(VehicleAttributeStatus.ElectricityConsumptionUnit.MPGE),

    UNRECOGNIZED(VehicleAttributeStatus.ElectricityConsumptionUnit.UNRECOGNIZED);

    companion object {
        fun map(electricityConsumptionUnit: VehicleAttributeStatus.ElectricityConsumptionUnit) = values().find {
            electricityConsumptionUnit == it.electricityConsumptionUnit
        } ?: UNRECOGNIZED
    }
}
