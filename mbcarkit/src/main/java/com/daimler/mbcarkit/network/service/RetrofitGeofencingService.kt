package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.GeofencingService
import com.daimler.mbcarkit.business.model.geofencing.Fence
import com.daimler.mbcarkit.business.model.geofencing.GeofenceViolation
import com.daimler.mbcarkit.network.VehicleApi
import com.daimler.mbcarkit.network.model.ApiFence
import com.daimler.mbcarkit.network.model.ApiGeofenceViolation
import com.daimler.mbcarkit.network.model.toFences
import com.daimler.mbcarkit.network.model.toGeofenceViolations
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.MappableRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.NoContentRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.RequestExecutor
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.ResponseTaskObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class RetrofitGeofencingService(
    api: VehicleApi,
    scope: CoroutineScope = NetworkCoroutineScope()
) : BaseRetrofitService<VehicleApi>(api, scope), GeofencingService {

    override fun fetchAllFences(
        jwtToken: String,
        vin: String?
    ): FutureTask<List<Fence>, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<List<Fence>>()
        scope.launch {
            MappableRequestExecutor<List<ApiFence>, List<Fence>> {
                it.toFences()
            }.executeWithTask(task) {
                api.fetchAllFences(
                    jwtToken,
                    vin
                )
            }
        }
        return task.futureTask()
    }

    override fun createFence(
        jwtToken: String,
        fence: Fence,
        vin: String?
    ): FutureTask<Fence, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Fence>()
        scope.launch {
            RequestExecutor<ApiFence, Fence>().executeWithTask(task) {
                api.createFence(
                    jwtToken,
                    vin,
                    ApiFence.fromFence(fence)
                )
            }
        }
        return task.futureTask()
    }

    override fun fetchFenceById(
        jwtToken: String,
        id: Int
    ): FutureTask<Fence, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Fence>()
        scope.launch {
            RequestExecutor<ApiFence, Fence>().executeWithTask(task) {
                api.fetchFenceById(
                    jwtToken,
                    id.toString()
                )
            }
        }
        return task.futureTask()
    }

    override fun updateFence(
        jwtToken: String,
        id: Int,
        fence: Fence
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.updateFence(
                    jwtToken,
                    id.toString(),
                    ApiFence.fromFence(fence)
                )
            }
        }
        return task.futureTask()
    }

    override fun deleteFence(
        jwtToken: String,
        id: Int
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.deleteFence(
                    jwtToken,
                    id.toString()
                )
            }
        }
        return task.futureTask()
    }

    override fun fetchAllVehicleFences(
        jwtToken: String,
        vin: String
    ): FutureTask<List<Fence>, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<List<Fence>>()
        scope.launch {
            MappableRequestExecutor<List<ApiFence>, List<Fence>> {
                it.toFences()
            }.executeWithTask(task) {
                api.fetchAllVehicleFences(
                    jwtToken,
                    vin
                )
            }
        }
        return task.futureTask()
    }

    override fun activateVehicleFence(
        jwtToken: String,
        vin: String,
        id: Int
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.activateVehicleFence(
                    jwtToken,
                    vin,
                    id.toString()
                )
            }
        }
        return task.futureTask()
    }

    override fun deleteVehicleFence(
        jwtToken: String,
        vin: String,
        id: Int
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.deleteVehicleFence(
                    jwtToken,
                    vin,
                    id.toString()
                )
            }
        }
        return task.futureTask()
    }

    override fun fetchAllViolations(
        jwtToken: String,
        vin: String
    ): FutureTask<List<GeofenceViolation>, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<List<GeofenceViolation>>()
        scope.launch {
            MappableRequestExecutor<List<ApiGeofenceViolation>, List<GeofenceViolation>> {
                it.toGeofenceViolations()
            }.executeWithTask(task) {
                api.fetchAllViolations(
                    jwtToken,
                    vin
                )
            }
        }
        return task.futureTask()
    }

    override fun deleteAllViolations(
        jwtToken: String,
        vin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.deleteAllViolations(
                    jwtToken,
                    vin
                )
            }
        }
        return task.futureTask()
    }

    override fun deleteViolation(
        jwtToken: String,
        vin: String,
        id: Int
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.deleteGeofencingViolation(
                    jwtToken,
                    vin,
                    id.toString()
                )
            }
        }
        return task.futureTask()
    }
}
