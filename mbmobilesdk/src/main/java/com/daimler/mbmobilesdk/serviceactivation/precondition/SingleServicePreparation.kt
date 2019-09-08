package com.daimler.mbmobilesdk.serviceactivation.precondition

import com.daimler.mbcarkit.business.model.services.Service

internal class SingleServicePreparation(private val service: Service) : BasePreconditionPreparation() {

    override fun preconditions(): List<ServicePreconditionType> {
        val list = mutableListOf<ServicePreconditionType>()
        service.prerequisiteChecks.forEach {
            list.addAll(mapServiceInformationToPreconditions(service.id, it.actions, it.missingInformation))
        }
        return list
    }
}