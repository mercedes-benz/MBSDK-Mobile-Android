package com.daimler.mbmobilesdk.serviceactivation.precondition

import com.daimler.mbcarkit.business.model.services.Service

internal class MultipleServicesPreparation(
    private val services: List<Service>
) : BasePreconditionPreparation() {

    override fun preconditions(): List<ServicePreconditionType> {
        val list = mutableListOf<ServicePreconditionType>()
        services.forEach { service ->
            service.prerequisiteChecks.forEach { check ->
                val preconditions = mapServiceInformationToPreconditions(
                    service.id, check.actions, check.missingInformation
                )
                preconditions.forEach { precondition ->
                    if (precondition.isMultipleAllowed() ||
                        !list.containsPrecondition(precondition)) {
                        list.add(precondition)
                    }
                }
            }
        }
        return list
    }

    private fun List<ServicePreconditionType>.containsPrecondition(
        preconditionType: ServicePreconditionType
    ) = any { it::class.java == preconditionType::class.java }

    private fun ServicePreconditionType.isMultipleAllowed() =
        this is ServicePreconditionType.PurchaseLicense
}