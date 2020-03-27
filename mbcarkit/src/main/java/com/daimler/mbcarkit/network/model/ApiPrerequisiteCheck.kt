package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.services.PrerequisiteCheck
import com.google.gson.annotations.SerializedName

internal data class ApiPrerequisiteCheck(
    @SerializedName("name") val name: ApiPrerequisiteType,
    @SerializedName("missingFields") val missingFields: List<ApiServiceMissingFields>?,
    @SerializedName("actions") val actions: List<ApiAllowedServiceActions>?
)

internal fun ApiPrerequisiteCheck?.toPrerequisiteCheck(): PrerequisiteCheck =
    PrerequisiteCheck(
        this?.name?.name,
        this?.missingFields?.let { fields -> fields.map { it.toServiceMissingField() } } ?: emptyList(),
        this?.actions?.let { actions -> actions.map { it.toServiceAction() } } ?: emptyList()
    )
