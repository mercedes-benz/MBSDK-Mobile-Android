package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.geofencing.ActiveTimes
import com.google.gson.annotations.SerializedName

internal data class ApiActiveTimes(
    @SerializedName("days") val days: List<Int>,
    @SerializedName("begin") val begin: Int,
    @SerializedName("end") val end: Int
) {
    companion object {
        fun fromActiveTimes(activeTimes: ActiveTimes) =
            ApiActiveTimes(activeTimes.days, activeTimes.begin, activeTimes.end)
    }
}
