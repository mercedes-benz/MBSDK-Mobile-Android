package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.customerfence.CustomerFence
import com.google.gson.annotations.SerializedName

internal data class ApiCustomerFence(
    @SerializedName("customerfenceid") val customerFenceId: Int?,
    @SerializedName("geofenceid") val geoFenceId: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("days") val days: List<ApiDayOfWeek>,
    @SerializedName("beginMinutes") val beginMinutes: Int?,
    @SerializedName("endMinutes") val endMinutes: Int?,
    @SerializedName("ts") val timestamp: Long?,
    @SerializedName("violationtype") val violationType: ApiCustomerFenceViolationType?
) {
    companion object {
        fun fromCustomerFence(customerFence: CustomerFence) = ApiCustomerFence(
            customerFence.customerFenceId,
            customerFence.geoFenceId,
            customerFence.name,
            customerFence.days.mapNotNull { ApiDayOfWeek.fromDay(it) },
            customerFence.beginMinutes,
            customerFence.endMinutes,
            customerFence.timestamp,
            ApiCustomerFenceViolationType.fromCustomerFenceViolationType(customerFence.violationType)
        )
    }
}

internal fun ApiCustomerFence.toCustomerFence(): CustomerFence =
    CustomerFence(
        customerFenceId,
        geoFenceId,
        name,
        days.mapNotNull { it.toDay() },
        beginMinutes,
        endMinutes,
        timestamp,
        violationType.toCustomerFenceViolationType()
    )
