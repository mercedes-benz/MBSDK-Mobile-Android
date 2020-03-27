package com.daimler.mbcarkit.business.model.consumption

data class Consumption(
    /**
     *
     */
    val averageConsumption: ConsumptionEntry?,
    /**
     *
     */
    val consumptionData: ConsumptionData?,
    /**
     *
     */
    val individualLifetimeConsumption: ConsumptionEntry?,
    /**
     *
     */
    val individualResetConsumption: ConsumptionEntry?,
    /**
     *
     */
    val individualStartConsumption: ConsumptionEntry?,
    /**
     *
     */
    val wltpCombined: ConsumptionEntry?,
    /**
     *
     */
    val individual30DaysConsumptionData: Individual30DaysConsumptionData?
)
