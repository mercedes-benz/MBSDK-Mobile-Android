package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapabilities
import com.google.gson.annotations.SerializedName

internal data class ApiSendToCarCapabilities(
    @SerializedName("capabilities") val capabilities: List<ApiSendToCarCapability>?,
    @SerializedName("preconditions") val preconditions: List<ApiSendToCarPrecondition>?
)

internal fun ApiSendToCarCapabilities.toSendToCarCapabilities() = SendToCarCapabilities(
    capabilities?.map { it.toSendToCarCapability() } ?: emptyList(),
    preconditions?.map { it.toSendToCarPrecondition() } ?: emptyList()
)
