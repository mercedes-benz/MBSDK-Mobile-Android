package com.daimler.mbcarkit.business.model.command.capabilities

data class Command(
    val name: CommandName,
    val available: Boolean,
    val parameters: List<CommandParameter>,
    val additionalInformation: List<String>
)
