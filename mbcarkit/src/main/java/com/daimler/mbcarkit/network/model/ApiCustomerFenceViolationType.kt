package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.customerfence.CustomerFenceViolationType
import com.daimler.mbcarkit.network.model.ApiCustomerFenceViolationType.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiCustomerFenceViolationType {
    @SerializedName("ENTER") ENTER,
    @SerializedName("LEAVE") LEAVE,
    @SerializedName("LEAVE_AND_ENTER") LEAVE_AND_ENTER;

    companion object {
        val map: Map<String, CustomerFenceViolationType> = CustomerFenceViolationType.values().associateBy(CustomerFenceViolationType::name)
        private val reverseMap: Map<String, ApiCustomerFenceViolationType> = values().associateBy(ApiCustomerFenceViolationType::name)

        fun fromCustomerFenceViolationType(customerFenceViolationType: CustomerFenceViolationType?) = customerFenceViolationType?.let { reverseMap[it.name] }
    }
}

internal fun ApiCustomerFenceViolationType?.toCustomerFenceViolationType(): CustomerFenceViolationType? =
    this?.let { map[name] }
