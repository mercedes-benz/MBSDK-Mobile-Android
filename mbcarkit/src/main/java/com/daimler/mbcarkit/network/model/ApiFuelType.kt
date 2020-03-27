package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.FuelType
import com.daimler.mbcarkit.network.model.ApiFuelType.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiFuelType {
    @SerializedName("Gas") GAS,
    @SerializedName("Electric") ELECTRIC,
    @SerializedName("FuelCellPlugin") FUEL_CELL_PLUGIN,
    @SerializedName("Hybrid") HYBRID,
    @SerializedName("Plugin") PLUGIN,
    @SerializedName("Combustion") COMBUSTION;

    companion object {
        val map: Map<String, FuelType> = FuelType.values().associateBy(FuelType::name)
    }
}

internal fun ApiFuelType?.toFuelType(): FuelType? =
    this?.let { map[name] }
