package com.daimler.mbcarkit.utils

import com.daimler.mbcarkit.network.model.ApiAllowedServiceActions
import com.daimler.mbcarkit.network.model.ApiMissingServiceData
import com.daimler.mbcarkit.network.model.ApiPrerequisiteCheck
import com.daimler.mbcarkit.network.model.ApiService
import com.daimler.mbcarkit.network.model.ApiServiceRight
import com.daimler.mbcarkit.network.model.ApiServiceStatus

internal object ApiServiceFactory {

    fun createService(
        id: Int = 0,
        name: String? = null,
        description: String? = null,
        shortDescription: String? = null,
        shortName: String? = null,
        categoryName: String? = null,
        allowedActions: List<ApiAllowedServiceActions?> = emptyList(),
        activationStatus: ApiServiceStatus? = null,
        desiredServiceStatus: ApiServiceStatus? = null,
        actualServiceStatus: ApiServiceStatus? = null,
        virtualServiceStatus: ApiServiceStatus? = null,
        prerequisiteCheck: List<ApiPrerequisiteCheck?> = emptyList(),
        rights: List<ApiServiceRight?> = emptyList(),
        missingData: ApiMissingServiceData? = null
    ) = ApiService(
        id = id,
        name = name,
        description = description,
        shortDescription = shortDescription,
        shortName = shortName,
        categoryName = categoryName,
        allowedActions = allowedActions,
        activationStatus = activationStatus,
        desiredServiceStatus = desiredServiceStatus,
        actualActivationServiceStatus = actualServiceStatus,
        virtualActivationServiceStatus = virtualServiceStatus,
        prerequisiteCheck = prerequisiteCheck,
        rights = rights,
        missingData = missingData
    )
}
