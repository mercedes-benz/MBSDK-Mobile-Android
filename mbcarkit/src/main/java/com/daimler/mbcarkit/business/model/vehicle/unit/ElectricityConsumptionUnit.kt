package com.daimler.mbcarkit.business.model.vehicle.unit

enum class ElectricityConsumptionUnit {
    UNSPECIFIED_ELECTRICITY_CONSUMPTION_UNIT,
    /**
     * kWh per 100 km
     */
    KWH_PER_100KM,
    /**
     * Kilometers per kWh
     */
    KM_PER_KWH,
    /**
     * kWh per 100 miles
     */
    KWH_PER_100MI,
    /**
     * miles per kWh
     */
    M_PER_KWH,
    /**
     * Miles per gallon gasoline equivalent
     */
    MPGE
}
