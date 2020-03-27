package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.consumption.ConsumptionData
import com.google.gson.annotations.SerializedName

internal data class ApiConsumptionData(
    @SerializedName("changed") val changed: Boolean?,
    @SerializedName("value") val value: List<ApiConsumptionValue>
)

internal fun ApiConsumptionData.toConsumptionData() = ConsumptionData(
    changed ?: false,
    value.map { it.toConsumptionValue() }
)
