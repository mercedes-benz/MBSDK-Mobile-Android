package com.daimler.mbcarkit.business.model.vehicle

data class DayTime(
    /**
     * Day of week
     */
    val day: Day,

    /**
     * Time in minutes since midnight
     * Range: 0..1439
     */
    val time: Int
)
