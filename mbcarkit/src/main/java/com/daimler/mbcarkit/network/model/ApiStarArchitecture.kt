package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.StarArchitecture
import com.daimler.mbcarkit.network.model.ApiStarArchitecture.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiStarArchitecture {
    @SerializedName("Star0") STAR_0,
    @SerializedName("Star1") STAR_1,
    @SerializedName("Star2") STAR_2,
    @SerializedName("Star2.3") STAR_2_3,
    @SerializedName("Star2.5") STAR_2_5,
    @SerializedName("Star3") STAR_3;

    companion object {
        val map: Map<String, StarArchitecture> = StarArchitecture.values().associateBy(StarArchitecture::name)
    }
}

internal fun ApiStarArchitecture?.toStarArchitecture(): StarArchitecture? =
    this?.let { map[name] }
