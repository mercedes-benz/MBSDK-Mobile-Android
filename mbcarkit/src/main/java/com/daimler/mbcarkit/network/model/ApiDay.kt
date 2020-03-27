package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.merchants.Day
import com.google.gson.annotations.SerializedName

internal data class ApiDay(
    @SerializedName("status") val status: String?,
    @SerializedName("periods") val periods: List<ApiPeriod>?
)

internal fun ApiDay?.toDay() = this?.let { apiDay ->
    Day(apiDay.status, apiDay.periods?.map { it.toPeriod() })
}
