package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.command.capabilities.CommandCapabilities
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class ApiCommandCapabilities(
    @SerializedName("commands") val commands: List<ApiCommand>?
) : Mappable<CommandCapabilities> {

    override fun map(): CommandCapabilities = toCommandCapabilities()
}

internal fun ApiCommandCapabilities.toCommandCapabilities() = CommandCapabilities(
    commands?.map { it.toCommand() } ?: emptyList()
)
