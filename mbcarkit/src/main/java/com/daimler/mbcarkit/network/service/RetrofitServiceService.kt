package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.ServiceService
import com.daimler.mbcarkit.business.model.services.ServiceFilter
import com.daimler.mbcarkit.business.model.services.ServiceGroup
import com.daimler.mbcarkit.business.model.services.ServiceGroupOption
import com.daimler.mbcarkit.business.model.services.ServiceStatusDesire
import com.daimler.mbcarkit.network.VehicleApi
import com.daimler.mbcarkit.network.model.ApiDesireServiceStatus
import com.daimler.mbcarkit.network.model.ApiDesireServiceStatusRequest
import com.daimler.mbcarkit.network.model.ApiServiceGroupOption
import com.daimler.mbcarkit.network.model.ApiServiceResponse
import com.daimler.mbcarkit.network.model.toServiceGroups
import com.daimler.mbnetworkkit.header.HeaderService
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.MappableRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.NoContentRequestExecutor
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.ResponseTaskObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class RetrofitServiceService(
    api: VehicleApi,
    private val headerService: HeaderService,
    scope: CoroutineScope = NetworkCoroutineScope()
) : BaseRetrofitService<VehicleApi>(api, scope), ServiceService {

    override fun fetchServices(
        jwtToken: String,
        vin: String,
        groupBy: ServiceGroupOption,
        locale: String?,
        checkPreconditions: Boolean,
        filter: ServiceFilter
    ): FutureTask<List<ServiceGroup>, ResponseError<out RequestError>?> {
        val callLocale = locale ?: headerService.currentNetworkLocale()
        val task = ResponseTaskObject<List<ServiceGroup>>()
        scope.launch {
            MappableRequestExecutor<List<ApiServiceResponse>, List<ServiceGroup>> {
                it.toServiceGroups()
            }.executeWithTask(task) {
                api.fetchServices(
                    jwtToken,
                    callLocale,
                    vin,
                    callLocale,
                    ApiServiceGroupOption.fromServiceGroupOption(groupBy)?.apiName,
                    checkPreconditions
                )
            }
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
        val callLocale = locale ?: headerService.currentNetworkLocale()
        val task = ResponseTaskObject<List<ServiceGroup>>()
        scope.launch {
            MappableRequestExecutor<List<ApiServiceResponse>, List<ServiceGroup>> {
                it.toServiceGroups()
            }.executeWithTask(task) {
                api.fetchServices(
                    jwtToken,
                    callLocale,
                    vin,
                    callLocale,
                    ApiServiceGroupOption.fromServiceGroupOption(groupBy)?.apiName,
                    ids.joinToString(","),
                    checkPreconditions
                )
            }
        }
        return task.futureTask()
    }

    override fun requestServiceUpdate(
        jwtToken: String,
        vin: String,
        desires: List<ServiceStatusDesire>
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val apiDesires = desires.map { ApiDesireServiceStatus.fromServiceStatusDesire(it) }
        val body = ApiDesireServiceStatusRequest(apiDesires)
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.requestServiceUpdate(
                    jwtToken,
                    vin,
                    false,
                    body
                )
            }
        }
        return task.futureTask()
    }

    override fun activateAllServices(
        jwtToken: String,
        vin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.requestServiceUpdate(
                    jwtToken,
                    vin,
                    true,
                    ApiDesireServiceStatusRequest(emptyList())
                )
            }
        }
        return task.futureTask()
    }
}
