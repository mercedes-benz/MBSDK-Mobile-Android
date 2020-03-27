package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.services.ServiceGroup
import com.google.gson.annotations.SerializedName

internal data class ApiServiceResponse(
    @SerializedName("group") val group: String,
    @SerializedName("services") val services: List<ApiService>
)

internal fun ApiServiceResponse.toServiceGroup(): ServiceGroup =
    ServiceGroup(
        group,
        services.map { it.toService() }
    )

internal fun List<ApiServiceResponse>.toServiceGroups() =
    map { it.toServiceGroup() }
