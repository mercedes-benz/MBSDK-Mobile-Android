package com.daimler.mbcarkit.utils

import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiError
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatus
import com.daimler.mbcarkit.business.model.command.VehicleCommandStatus
import com.daimler.mbcarkit.business.model.services.MissingAccountLinkage
import com.daimler.mbcarkit.business.model.services.MissingServiceData
import com.daimler.mbcarkit.business.model.services.PrerequisiteCheck
import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbcarkit.business.model.services.ServiceAction
import com.daimler.mbcarkit.business.model.services.ServiceRight
import com.daimler.mbcarkit.business.model.services.ServiceStatus
import io.mockk.spyk

internal fun createService(
    id: Int = 0,
    name: String = "",
    description: String? = null,
    shortName: String? = null,
    categoryName: String? = null,
    allowedActions: List<ServiceAction> = emptyList(),
    activationStatus: ServiceStatus = ServiceStatus.UNKNOWN,
    desiredActivationStatus: ServiceStatus = ServiceStatus.UNKNOWN,
    actualActivationServiceStatus: ServiceStatus = ServiceStatus.UNKNOWN,
    virtualActivationServiceStatus: ServiceStatus = ServiceStatus.UNKNOWN,
    prerequisiteChecks: List<PrerequisiteCheck> = emptyList(),
    rights: List<ServiceRight> = emptyList(),
    missingServiceData: MissingServiceData? = null
) = Service(
    id = id,
    name = name,
    description = description,
    shortName = shortName,
    categoryName = categoryName,
    allowedActions = allowedActions,
    activationStatus = activationStatus,
    desiredActivationStatus = desiredActivationStatus,
    actualActivationServiceStatus = actualActivationServiceStatus,
    virtualActivationServiceStatus = virtualActivationServiceStatus,
    prerequisiteChecks = prerequisiteChecks,
    rights = rights,
    missingData = missingServiceData
)

internal fun createMissingAccountLinkage(
    mandatory: Boolean = false,
    accountType: AccountType? = null
) = MissingAccountLinkage(
    mandatory = mandatory,
    type = accountType
)

internal fun createMissingServiceData(
    missingAccountLinkage: MissingAccountLinkage? = null
) = MissingServiceData(
    missingAccountLinkage = missingAccountLinkage
)

internal fun createCommandVehicleApiError(code: String) = CommandVehicleApiError(
    code = code,
    message = "",
    subErrors = emptyList(),
    attributes = emptyMap()
)

internal fun createCommandStatus(
    errors: List<CommandVehicleApiError> = emptyList(),
    pid: String = "",
    commandState: VehicleCommandStatus = VehicleCommandStatus.UNKNOWN,
    requestId: String? = null,
    timestamp: Long = 0,
    type: Int = 0
) =
    CommandVehicleApiStatus(
        errors = errors,
        pid = pid,
        commandState = commandState,
        requestId = requestId,
        timestamp = timestamp,
        type = type
    )

internal inline fun <reified T : Any> T.asSpy() = spyk(this)
