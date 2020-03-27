package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.customerfence.CustomerFence
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class ApiCustomerFences(
    @SerializedName("customerfences") val customerFences: List<ApiCustomerFence>?
) : Mappable<List<CustomerFence>> {

    override fun map(): List<CustomerFence> =
        customerFences?.map { it.toCustomerFence() } ?: emptyList()
}
