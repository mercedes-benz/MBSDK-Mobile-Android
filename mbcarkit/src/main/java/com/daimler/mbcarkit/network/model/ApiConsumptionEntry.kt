package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.consumption.ConsumptionEntry
import com.google.gson.annotations.SerializedName

internal data class ApiConsumptionEntry(
    @SerializedName("changed") val changed: Boolean?,
    @SerializedName("value") val value: Double,
    @SerializedName("unit") val unit: ApiVehicleConsumptionUnit?
)

internal fun ApiConsumptionEntry.toConsumptionEntry() = ConsumptionEntry(
    changed ?: false,
    value,
    unit.toVehicleConsumptionUnit()
)
