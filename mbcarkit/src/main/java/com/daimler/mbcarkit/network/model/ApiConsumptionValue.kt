package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.consumption.ConsumptionValue
import com.google.gson.annotations.SerializedName

internal data class ApiConsumptionValue(
    @SerializedName("consumption") val consumption: Double,
    @SerializedName("group") val group: Int?,
    @SerializedName("percentage") val percentage: Double,
    @SerializedName("unit") val unit: ApiVehicleConsumptionUnit?
)

internal fun ApiConsumptionValue.toConsumptionValue() = ConsumptionValue(
    consumption,
    group ?: 0,
    percentage,
    unit.toVehicleConsumptionUnit()
)
