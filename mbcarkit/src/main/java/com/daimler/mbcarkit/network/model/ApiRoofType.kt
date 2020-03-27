package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.RoofType
import com.daimler.mbcarkit.network.model.ApiRoofType.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiRoofType {
    @SerializedName("NoSunroof") NO_SUNROOF,
    @SerializedName("SmallSunroof") SMALL_SUNROOF,
    @SerializedName("PanoramaSunroof") PANORAMA_SUNROOF,
    @SerializedName("Convertible") CONVERTIBLE;

    companion object {
        val map: Map<String, RoofType> = RoofType.values().associateBy(RoofType::name)
    }
}

internal fun ApiRoofType?.toRoofType(): RoofType? =
    this?.let { map[name] }
