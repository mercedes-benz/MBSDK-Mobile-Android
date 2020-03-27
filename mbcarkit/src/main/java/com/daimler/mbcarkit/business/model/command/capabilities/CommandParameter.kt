package com.daimler.mbcarkit.business.model.command.capabilities

data class CommandParameter(
    val name: CommandParameterName,
    val minValue: Double,
    val maxValue: Double,
    val steps: Double,
    val allowedEnums: List<AllowedEnums>,
    val allowedBools: AllowedBools
)
