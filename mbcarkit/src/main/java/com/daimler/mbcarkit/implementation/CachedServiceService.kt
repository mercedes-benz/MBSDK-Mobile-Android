package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.ServiceActivationStateProcessor
import com.daimler.mbcarkit.business.ServiceCache
import com.daimler.mbcarkit.business.ServiceService
import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbcarkit.business.model.services.ServiceFilter
import com.daimler.mbcarkit.business.model.services.ServiceGroup
import com.daimler.mbcarkit.business.model.services.ServiceGroupOption
import com.daimler.mbcarkit.business.model.services.ServiceStatus
import com.daimler.mbcarkit.business.model.services.ServiceStatusDesire
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

internal class CachedServiceService(
    private val service: ServiceService,
    private val cache: ServiceCache,
    private val stateProcessor: ServiceActivationStateProcessor
) : ServiceService {

    override fun fetchServices(
        jwtToken: String,
        vin: String,
        groupBy: ServiceGroupOption,
        locale: String?,
        checkPreconditions: Boolean,
        filter: ServiceFilter
    ): FutureTask<List<ServiceGroup>, ResponseError<out RequestError>?> {
        val task = TaskObject<List<ServiceGroup>, ResponseError<out RequestError>?>()
        service.fetchServices(jwtToken, vin, groupBy, locale, checkPreconditions)
            .onComplete { groups ->
                val services = groups.flatMap { it.services }
                updateAllServices(vin, services, checkPreconditions)
                task.complete(groups)
            }.onFailure { error ->
                cache.loadServices(vin)?.let {
                    task.complete(groupServices(it, groupBy))
                } ?: task.fail(error)
            }
        return task.futureTask()
    }

    override fun fetchServices(
        jwtToken: String,
        vin: String,
        ids: List<Int>,
        groupBy: ServiceGroupOption,
        locale: String?,
        checkPreconditions: Boolean,
        filter: ServiceFilter
    ): FutureTask<List<ServiceGroup>, ResponseError<out RequestError>?> {
        val task = TaskObject<List<ServiceGroup>, ResponseError<out RequestError>?>()
        service.fetchServices(jwtToken, vin, ids, groupBy, locale, checkPreconditions)
            .onComplete { groups ->
                val services = groups.flatMap { it.services }
                cache.updateServices(vin, services, checkPreconditions)
                task.complete(groups)
            }.onFailure { error ->
                cache.loadServicesById(vin, ids)?.let {
                    task.complete(groupServices(it, groupBy))
                } ?: task.fail(error)
            }
        return task.futureTask()
    }

    override fun requestServiceUpdate(
        jwtToken: String,
        vin: String,
        desires: List<ServiceStatusDesire>
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = TaskObject<Unit, ResponseError<out RequestError>?>()
        service.requestServiceUpdate(jwtToken, vin, desires)
            .onComplete {
                updatePendingStates(vin, desires)
                task.complete(it)
            }.onFailure {
                task.fail(it)
            }
        return task.futureTask()
    }

    override fun activateAllServices(
        jwtToken: String,
        vin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> =
        service.activateAllServices(jwtToken, vin)

    private fun updateAllServices(finOrVin: String, services: List<Service>, checkPreconditions: Boolean) {
        cache.loadServices(finOrVin)?.let { cachedServices ->
            // Remove services that are not available in the given list.
            deleteRemovedServices(finOrVin, cachedServices, services)

            // Process activation states and update.
            cache.updateServices(
                finOrVin,
                stateProcessor.processActivationStates(cachedServices, services),
                checkPreconditions
            )
        } ?: cache.updateServices(finOrVin, services, checkPreconditions)
    }

    private fun deleteRemovedServices(finOrVin: String, cachedServices: List<Service>, newServices: List<Service>) {
        // Get cached services that are not received via the API and delete them.
        cachedServices.filter { cachedService ->
            newServices.find { cachedService.id == it.id } == null
        }.takeIf {
            it.isNotEmpty()
        }?.let {
            cache.deleteServices(finOrVin, it)
        }
    }

    private fun groupServices(services: List<Service>, groupBy: ServiceGroupOption): List<ServiceGroup> {
        return when (groupBy) {
            ServiceGroupOption.NONE -> listOf(ServiceGroup("", services))
            ServiceGroupOption.CATEGORY ->
                services.groupBy {
                    it.categoryName.orEmpty()
                }.map {
                    ServiceGroup(it.key, it.value)
                }
        }
    }

    /** Updates the activation states of cached services based on the desires. */
    private fun updatePendingStates(finOrVin: String, desires: List<ServiceStatusDesire>) {
        cache.loadServicesById(finOrVin, desires.map { it.serviceId })?.takeIf {
            it.isNotEmpty()
        }?.mapNotNull { cachedService ->
            desires.find {
                it.serviceId == cachedService.id
            }?.let {
                getActivationStateByDesire(it)
            }?.let {
                MBLoggerKit.d("Changing activation state for ${cachedService.id} to $it.")
                cachedService.copy(activationStatus = it)
            }
        }?.let {
            cache.updateServices(finOrVin, it, false)
        }
    }

    private fun getActivationStateByDesire(desire: ServiceStatusDesire) =
        if (desire.activate) {
            ServiceStatus.ACTIVATION_PENDING
        } else {
            ServiceStatus.DEACTIVATION_PENDING
        }
}
