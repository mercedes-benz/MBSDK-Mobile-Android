package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.customerfence.CustomerFenceViolation
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class ApiCustomerFenceViolations(
    @SerializedName("violations") val violations: List<ApiCustomerFenceViolation>?
) : Mappable<List<CustomerFenceViolation>> {

    override fun map(): List<CustomerFenceViolation> =
        violations?.map { it.toCustomerFenceViolation() } ?: emptyList()
}
