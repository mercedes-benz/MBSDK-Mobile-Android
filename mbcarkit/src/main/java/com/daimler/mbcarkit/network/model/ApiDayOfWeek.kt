package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.Day
import com.google.gson.annotations.SerializedName

internal enum class ApiDayOfWeek {
    @SerializedName("MONDAY") MONDAY,
    @SerializedName("TUESDAY") TUESDAY,
    @SerializedName("WEDNESDAY") WEDNESDAY,
    @SerializedName("THURSDAY") THURSDAY,
    @SerializedName("FRIDAY") FRIDAY,
    @SerializedName("SATURDAY") SATURDAY,
    @SerializedName("SUNDAY") SUNDAY;

    companion object {
        private val map: Map<String, ApiDayOfWeek> = values().associateBy(ApiDayOfWeek::name)

        fun fromDay(day: Day) = map[day.name]
    }
}

internal fun ApiDayOfWeek.toDay(): Day? =
    let { Day.map[name] }
