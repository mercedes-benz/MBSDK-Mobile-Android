package com.daimler.mbcarkit.business.model.vehicle

data class TankStatistics<CONS : Enum<*>, DIST : Enum<*>>(
    /**
     * Consumption from start/reset for electric-, gas-, combustion vehicles
     */
    val consumption: ResetStart<Double, CONS>,

    /**
     * Distance from start/reset for electric-, gas-, combustion vehicles
     */
    val distance: ResetStart<Double, DIST>
)
