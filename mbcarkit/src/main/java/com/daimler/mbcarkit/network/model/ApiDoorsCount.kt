package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.DoorsCount
import com.daimler.mbcarkit.network.model.ApiDoorsCount.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiDoorsCount {
    @SerializedName("TwoDoors") TWO_DOORS,
    @SerializedName("FourDoors") FOUR_DOORS,
    @SerializedName("ThreeDoors") THREE_DOORS,
    @SerializedName("NoDoors") NO_DOORS;

    companion object {
        val map: Map<String, DoorsCount> = DoorsCount.values().associateBy(DoorsCount::name)
    }
}

internal fun ApiDoorsCount?.toDoorsCount(): DoorsCount? =
    this?.let { map[name] }
