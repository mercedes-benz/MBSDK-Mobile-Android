package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.ValetProtectService
import com.daimler.mbcarkit.business.model.valetprotect.DistanceUnit
import com.daimler.mbcarkit.business.model.valetprotect.ValetprotectItem
import com.daimler.mbcarkit.business.model.valetprotect.ValetprotectViolation
import com.daimler.mbcarkit.network.VehicleApi
import com.daimler.mbcarkit.network.model.ApiValetprotectItem
import com.daimler.mbcarkit.network.model.ApiValetprotectViolation
import com.daimler.mbcarkit.network.model.toValetprotectViolation
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.MappableRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.NoContentRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.RequestExecutor
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.ResponseTaskObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class RetrofitValetProtectService(
    api: VehicleApi,
    scope: CoroutineScope = NetworkCoroutineScope()
) : BaseRetrofitService<VehicleApi>(api, scope), ValetProtectService {

    override fun fetchValetprotectItem(
        jwtToken: String,
        vin: String,
        unit: DistanceUnit
    ): FutureTask<ValetprotectItem, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<ValetprotectItem>()
        scope.launch {
            RequestExecutor<ApiValetprotectItem, ValetprotectItem>().executeWithTask(task) {
                api.fetchValetprotectItem(
                    jwtToken,
                    vin,
                    unit.name
                )
            }
        }
        return task.futureTask()
    }

    override fun createValetprotectItem(
        jwtToken: String,
        vin: String,
        item: ValetprotectItem
    ): FutureTask<ValetprotectItem, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<ValetprotectItem>()
        scope.launch {
            RequestExecutor<ApiValetprotectItem, ValetprotectItem>().executeWithTask(task) {
                api.createValetprotectItem(
                    jwtToken,
                    vin,
                    ApiValetprotectItem.fromValetprotectItem(item)
                )
            }
        }
        return task.futureTask()
    }

    override fun deleteValetprotectItem(
        jwtToken: String,
        vin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.deleteValetprotectItem(
                    jwtToken,
                    vin
                )
            }
        }
        return task.futureTask()
    }

    override fun fetchAllValetprotectViolations(
        jwtToken: String,
        vin: String,
        unit: DistanceUnit
    ): FutureTask<List<ValetprotectViolation>, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<List<ValetprotectViolation>>()
        scope.launch {
            MappableRequestExecutor<List<ApiValetprotectViolation>, List<ValetprotectViolation>> {
                it.map { it.toValetprotectViolation() }
            }.executeWithTask(task) {
                api.fetchAllValetprotectViolations(
                    jwtToken,
                    vin,
                    unit.name
                )
            }
        }
        return task.futureTask()
    }

    override fun deleteAllValetprotectViolations(
        jwtToken: String,
        vin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.deleteAllValetprotectViolations(
                    jwtToken,
                    vin
                )
            }
        }
        return task.futureTask()
    }

    override fun fetchValetprotectViolation(
        jwtToken: String,
        vin: String,
        id: Int,
        unit: DistanceUnit
    ): FutureTask<ValetprotectViolation, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<ValetprotectViolation>()
        scope.launch {
            RequestExecutor<ApiValetprotectViolation, ValetprotectViolation>().executeWithTask(task) {
                api.fetchValetprotectViolation(
                    jwtToken,
                    vin,
                    id.toString(),
                    unit.name
                )
            }
        }
        return task.futureTask()
    }

    override fun deleteValetprotectViolation(
        jwtToken: String,
        vin: String,
        id: Int
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.deleteValetprotectViolation(
                    jwtToken,
                    vin,
                    id.toString()
                )
            }
        }
        return task.futureTask()
    }
}
