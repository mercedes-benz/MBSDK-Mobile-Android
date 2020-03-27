package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.services.Service
import com.google.gson.annotations.SerializedName

internal data class ApiService(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("shortDescription") val shortDescription: String?,
    @SerializedName("shortName") val shortName: String?,
    @SerializedName("categoryName") val categoryName: String?,
    @SerializedName("allowedActions") val allowedActions: List<ApiAllowedServiceActions?>,
    @SerializedName("activationStatus") val activationStatus: ApiServiceStatus?,
    @SerializedName("desiredServiceStatus") val desiredServiceStatus: ApiServiceStatus?,
    @SerializedName("actualActivationServiceStatus") val actualActivationServiceStatus: ApiServiceStatus?,
    @SerializedName("virtualActivationServiceStatus") val virtualActivationServiceStatus: ApiServiceStatus?,
    @SerializedName("prerequisiteChecks") val prerequisiteCheck: List<ApiPrerequisiteCheck?>,
    @SerializedName("rights") val rights: List<ApiServiceRight?>,
    @SerializedName("missingData") val missingData: ApiMissingServiceData?
)

internal fun ApiService.toService(): Service =
    Service(
        id,
        name.orEmpty(),
        shortDescription,
        shortName,
        categoryName,
        allowedActions.map { it.toServiceAction() },
        activationStatus.toServiceStatus(),
        desiredServiceStatus.toServiceStatus(),
        actualActivationServiceStatus.toServiceStatus(),
        virtualActivationServiceStatus.toServiceStatus(),
        prerequisiteCheck.map { it.toPrerequisiteCheck() },
        rights.map { it.toServiceRight() },
        missingData?.toMissingServiceData()
    )
