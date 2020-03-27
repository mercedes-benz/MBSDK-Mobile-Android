package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.valetprotect.DistanceUnit
import com.daimler.mbcarkit.network.model.ApiDistanceUnit.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiDistanceUnit {
    @SerializedName("KM") KM,
    @SerializedName("MI") MI;

    companion object {
        val map: Map<String, DistanceUnit> = DistanceUnit.values().associateBy(DistanceUnit::name)
        private val reverseMap: Map<String, ApiDistanceUnit> = values().associateBy(ApiDistanceUnit::name)

        fun fromDistanceUnit(distanceUnit: DistanceUnit) =
            reverseMap[distanceUnit.name] ?: KM
    }
}

internal fun ApiDistanceUnit?.toDistanceUnit(): DistanceUnit =
    this?.let { map[name] } ?: DistanceUnit.UNKNOWN
