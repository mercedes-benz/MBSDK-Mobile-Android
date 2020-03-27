package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.consumption.Consumption
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class ApiConsumption(
    @SerializedName("averageConsumption") val averageConsumption: ApiConsumptionEntry?,
    @SerializedName("consumptionData") val consumptionData: ApiConsumptionData?,
    @SerializedName("individualLifetimeConsumption") val individualLifetimeConsumption: ApiConsumptionEntry?,
    @SerializedName("individualResetConsumption") val individualResetConsumption: ApiConsumptionEntry?,
    @SerializedName("individualStartConsumption") val individualStartConsumption: ApiConsumptionEntry?,
    @SerializedName("wltpCombined") val wltpCombined: ApiConsumptionEntry?,
    @SerializedName("individual30DaysConsumption") val individual30DaysConsumptionData: ApiIndividual30DaysConsumptionData?
) : Mappable<Consumption> {

    override fun map(): Consumption = toConsumption()
}

internal fun ApiConsumption.toConsumption() = Consumption(
    averageConsumption?.toConsumptionEntry(),
    consumptionData?.toConsumptionData(),
    individualLifetimeConsumption?.toConsumptionEntry(),
    individualResetConsumption?.toConsumptionEntry(),
    individualStartConsumption?.toConsumptionEntry(),
    wltpCombined?.toConsumptionEntry(),
    individual30DaysConsumptionData?.toIndividual30DaysConsumptionData()
)
