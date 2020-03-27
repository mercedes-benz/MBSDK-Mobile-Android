package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.consumption.VehicleConsumptionUnit
import com.daimler.mbcarkit.network.model.ApiVehicleConsumptionUnit.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiVehicleConsumptionUnit {
    @SerializedName("l/100km") LITERS_PER_100KM,
    @SerializedName("km/l") KM_PER_LITER,
    @SerializedName("mpg (UK)") MPG_UK,
    @SerializedName("mpg (US)") MPG_US;

    companion object {
        val map: Map<String, VehicleConsumptionUnit> = VehicleConsumptionUnit.values().associateBy(VehicleConsumptionUnit::name)
    }
}

internal fun ApiVehicleConsumptionUnit?.toVehicleConsumptionUnit(): VehicleConsumptionUnit =
    this?.let { map[name] } ?: VehicleConsumptionUnit.UNKNOWN
