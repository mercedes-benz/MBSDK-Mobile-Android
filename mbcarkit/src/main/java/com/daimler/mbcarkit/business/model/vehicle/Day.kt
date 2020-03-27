package com.daimler.mbcarkit.business.model.vehicle

enum class Day {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY,
    UNKNOWN;

    companion object {
        val map: Map<String, Day> = values().associateBy(Day::name)
    }
}
