package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.services.ServiceFilter
import com.daimler.mbcarkit.business.model.services.ServiceGroup
import com.daimler.mbcarkit.business.model.services.ServiceGroupOption
import com.daimler.mbcarkit.business.model.services.ServiceStatusDesire
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface ServiceService {

    fun fetchServices(
        jwtToken: String,
        vin: String,
        groupBy: ServiceGroupOption,
        locale: String? = null,
        checkPreconditions: Boolean = false,
        filter: ServiceFilter = ServiceFilter.None
    ): FutureTask<List<ServiceGroup>, ResponseError<out RequestError>?>

    fun fetchServices(
        jwtToken: String,
        vin: String,
        ids: List<Int>,
        groupBy: ServiceGroupOption,
        locale: String? = null,
        checkPreconditions: Boolean = false,
        filter: ServiceFilter = ServiceFilter.None
    ): FutureTask<List<ServiceGroup>, ResponseError<out RequestError>?>

    fun requestServiceUpdate(jwtToken: String, vin: String, desires: List<ServiceStatusDesire>):
        FutureTask<Unit, ResponseError<out RequestError>?>

    fun activateAllServices(jwtToken: String, vin: String):
        FutureTask<Unit, ResponseError<out RequestError>?>
}
