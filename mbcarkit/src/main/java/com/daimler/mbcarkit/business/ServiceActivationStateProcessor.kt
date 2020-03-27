package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.services.Service

interface ServiceActivationStateProcessor {

    /**
     * Checks whether the [Service.activationStatus] of the [currentService] needs to be updated
     * depending on the information given by [newService] and returns a new [Service] object.
     */
    fun processActivationState(currentService: Service, newService: Service): Service

    /**
     * Checks for each service contained in [newServices] if the [Service.activationStatus]
     * of the same service in [currentServices] needs to be changed depending on the given information.
     * If a service is contained by [newServices] but not by [currentServices], the new service
     * will be used.
     * Returns a list with services with an appropriate activation state.
     */
    fun processActivationStates(currentServices: List<Service>, newServices: List<Service>): List<Service>
}
