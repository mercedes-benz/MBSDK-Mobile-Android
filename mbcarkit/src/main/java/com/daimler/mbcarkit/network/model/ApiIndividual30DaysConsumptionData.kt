package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.consumption.Individual30DaysConsumptionData
import com.google.gson.annotations.SerializedName

internal data class ApiIndividual30DaysConsumptionData(
    @SerializedName("value") val value: Double,
    @SerializedName("lastUpdated") val lastUpdated: Long?,
    @SerializedName("unit") val unit: ApiVehicleConsumptionUnit?
)

internal fun ApiIndividual30DaysConsumptionData.toIndividual30DaysConsumptionData() = Individual30DaysConsumptionData(
    value,
    lastUpdated,
    unit.toVehicleConsumptionUnit()
)
