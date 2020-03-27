package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.ServiceActivationStateProcessor
import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbcarkit.business.model.services.ServiceAction
import com.daimler.mbcarkit.business.model.services.ServiceStatus

internal class ServiceActivationStateProcessorImpl : ServiceActivationStateProcessor {

    override fun processActivationState(currentService: Service, newService: Service): Service {
        return when (currentService.activationStatus) {
            ServiceStatus.UNKNOWN -> newService
            ServiceStatus.ACTIVE -> newService
            ServiceStatus.INACTIVE -> newService
            ServiceStatus.ACTIVATION_PENDING -> processPendingActivation(currentService, newService)
            ServiceStatus.DEACTIVATION_PENDING -> processPendingDeactivation(currentService, newService)
        }
    }

    override fun processActivationStates(currentServices: List<Service>, newServices: List<Service>): List<Service> {
        return newServices.map { newService ->
            currentServices.find {
                it.id == newService.id
            }?.let {
                processActivationState(it, newService)
            } ?: newService
        }
    }

    private fun processPendingActivation(currentService: Service, newService: Service): Service {
        return when {
            !currentService.isActivationPending() -> newService
            newService.isActive() -> newService
            newService.isActivationPending() && newService.canDeactivateService() -> newService
            else -> newService.copy(activationStatus = currentService.activationStatus)
        }
    }

    private fun processPendingDeactivation(currentService: Service, newService: Service): Service {
        return when {
            !currentService.isDeactivationPending() -> newService
            newService.isInactive() -> newService
            newService.isDeactivationPending() && newService.canActivateService() -> newService
            else -> newService.copy(activationStatus = currentService.activationStatus)
        }
    }

    private fun Service.isActive() = activationStatus == ServiceStatus.ACTIVE

    private fun Service.isInactive() = activationStatus == ServiceStatus.INACTIVE

    private fun Service.isActivationPending() = activationStatus == ServiceStatus.ACTIVATION_PENDING

    private fun Service.isDeactivationPending() = activationStatus == ServiceStatus.DEACTIVATION_PENDING

    private fun Service.isActiveOrActivating() = isActive() || isActivationPending()

    private fun Service.isInactiveOrDeactivating() = isInactive() || isDeactivationPending()

    private fun Service.canActivateService() = allowedActions.contains(ServiceAction.SET_DESIRED_ACTIVE)

    private fun Service.canDeactivateService() = allowedActions.contains(ServiceAction.SET_DESIRED_INACTIVE)
}
