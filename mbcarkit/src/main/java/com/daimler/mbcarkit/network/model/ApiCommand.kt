package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.command.capabilities.Command
import com.google.gson.annotations.SerializedName

internal data class ApiCommand(
    @SerializedName("commandName") val name: ApiCommandName?,
    @SerializedName("isAvailable") val available: Boolean,
    @SerializedName("parameters") val parameters: List<ApiCommandParameter>?,
    @SerializedName("additionalInformation") val additionalInformation: List<String>?
)

internal fun ApiCommand.toCommand() = Command(
    name.toCommandName(),
    available,
    parameters?.map { it.toCommandParameter() } ?: emptyList(),
    additionalInformation ?: emptyList()
)
