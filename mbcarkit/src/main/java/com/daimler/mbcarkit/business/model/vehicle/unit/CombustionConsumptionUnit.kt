package com.daimler.mbcarkit.business.model.vehicle.unit

enum class CombustionConsumptionUnit {
    UNSPECIFIED_COMBUSTION_CONSUMPTION_UNIT,
    /**
     * Liter per 100 km
     */
    LITER_PER_100KM,
    /**
     * Kilometers per liter
     */
    KM_PER_LITER,
    /**
     * Miles Per imperial gallon
     */
    MPG_UK,
    /**
     * Miles Per US gallon
     */
    MPG_US
}
