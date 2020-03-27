package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.command.capabilities.CommandParameter
import com.google.gson.annotations.SerializedName

internal data class ApiCommandParameter(
    @SerializedName("parameterName") val name: ApiCommandParameterName?,
    @SerializedName("minValue") val minValue: Double,
    @SerializedName("maxValue") val maxValue: Double,
    @SerializedName("steps") val steps: Double,
    @SerializedName("allowedEnums") val allowedEnums: List<ApiAllowedEnums?>?,
    @SerializedName("allowedBools") val allowedBools: ApiAllowedBools?
)

internal fun ApiCommandParameter.toCommandParameter() = CommandParameter(
    name.toCommandParameterName(),
    minValue,
    maxValue,
    steps,
    allowedEnums?.map { it.toAllowedEnums() } ?: emptyList(),
    allowedBools.toAllowedBools()
)
