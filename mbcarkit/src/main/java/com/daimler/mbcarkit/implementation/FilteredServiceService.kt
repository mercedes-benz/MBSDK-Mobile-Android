package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.ServiceService
import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbcarkit.business.model.services.ServiceFilter
import com.daimler.mbcarkit.business.model.services.ServiceGroup
import com.daimler.mbcarkit.business.model.services.ServiceGroupOption
import com.daimler.mbcarkit.business.model.services.ServiceRight
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

internal class FilteredServiceService(
    private val delegate: ServiceService
) : ServiceService by delegate {

    override fun fetchServices(
        jwtToken: String,
        vin: String,
        groupBy: ServiceGroupOption,
        locale: String?,
        checkPreconditions: Boolean,
        filter: ServiceFilter
    ): FutureTask<List<ServiceGroup>, ResponseError<out RequestError>?> {
        val task = TaskObject<List<ServiceGroup>, ResponseError<out RequestError>?>()
        delegate.fetchServices(jwtToken, vin, groupBy, locale, checkPreconditions, filter)
            .onComplete {
                task.complete(it.applyFilter(filter))
            }.onFailure {
                task.fail(it)
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
        delegate.fetchServices(jwtToken, vin, ids, groupBy, locale, checkPreconditions, filter)
            .onComplete {
                task.complete(it.applyFilter(filter))
            }.onFailure {
                task.fail(it)
            }
        return task.futureTask()
    }

    private fun List<ServiceGroup>.applyFilter(filter: ServiceFilter) =
        if (filter is ServiceFilter.None) {
            this
        } else {
            mapNotNull {
                filter.applyTo(it)
            }
        }

    private fun ServiceFilter.applyTo(group: ServiceGroup) =
        when (this) {
            ServiceFilter.ReadOnly -> group.filterBy { it.rights.contains(ServiceRight.READ) }
            is ServiceFilter.Rights -> group.filterBy { it.rights.containsAll(rights) }
            ServiceFilter.None -> group
        }

    private fun ServiceGroup.filterBy(condition: (Service) -> Boolean) =
        services
            .filter(condition)
            .takeIf { it.isNotEmpty() }
            ?.let {
                copy(services = it)
            }
}
