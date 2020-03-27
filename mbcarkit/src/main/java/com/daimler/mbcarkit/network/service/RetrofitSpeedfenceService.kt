package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.SpeedfenceService
import com.daimler.mbcarkit.business.model.speedfence.SpeedFence
import com.daimler.mbcarkit.business.model.speedfence.SpeedFenceViolation
import com.daimler.mbcarkit.network.VehicleApi
import com.daimler.mbcarkit.network.model.ApiDeleteSpeedFenceViolationsRequest
import com.daimler.mbcarkit.network.model.ApiDeleteSpeedfencesRequest
import com.daimler.mbcarkit.network.model.ApiSpeedFence
import com.daimler.mbcarkit.network.model.ApiSpeedFenceViolations
import com.daimler.mbcarkit.network.model.ApiSpeedFences
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.NoContentRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.RequestExecutor
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.ResponseTaskObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class RetrofitSpeedfenceService(
    api: VehicleApi,
    scope: CoroutineScope = NetworkCoroutineScope()
) : BaseRetrofitService<VehicleApi>(api, scope), SpeedfenceService {

    override fun fetchSpeedFences(
        jwtToken: String,
        finOrVin: String
    ): FutureTask<List<SpeedFence>, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<List<SpeedFence>>()
        scope.launch {
            RequestExecutor<ApiSpeedFences, List<SpeedFence>>().executeWithTask(task) {
                api.fetchSpeedFences(
                    jwtToken,
                    finOrVin
                )
            }
        }
        return task.futureTask()
    }

    override fun createSpeedFences(
        jwtToken: String,
        finOrVin: String,
        speedFences: List<SpeedFence>
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.createSpeedFences(
                    jwtToken,
                    finOrVin,
                    ApiSpeedFences(speedFences.map { ApiSpeedFence.fromSpeedFence(it) })
                )
            }
        }
        return task.futureTask()
    }

    override fun updateSpeedFences(
        jwtToken: String,
        finOrVin: String,
        speedFences: List<SpeedFence>
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.updateSpeedFences(
                    jwtToken,
                    finOrVin,
                    ApiSpeedFences(speedFences.map { ApiSpeedFence.fromSpeedFence(it) })
                )
            }
        }
        return task.futureTask()
    }

    override fun deleteSpeedFences(
        jwtToken: String,
        finOrVin: String,
        ids: List<Int>
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.deleteSpeedFences(
                    jwtToken,
                    finOrVin,
                    ApiDeleteSpeedfencesRequest(ids)
                )
            }
        }
        return task.futureTask()
    }

    override fun deleteSpeedFence(
        jwtToken: String,
        finOrVin: String,
        fenceId: Int
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        return deleteSpeedFences(jwtToken, finOrVin, listOf(fenceId))
    }

    override fun deleteAllSpeedFences(
        jwtToken: String,
        finOrVin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        return deleteSpeedFences(jwtToken, finOrVin, emptyList())
    }

    override fun fetchSpeedFenceViolations(
        jwtToken: String,
        finOrVin: String
    ): FutureTask<List<SpeedFenceViolation>, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<List<SpeedFenceViolation>>()
        scope.launch {
            RequestExecutor<ApiSpeedFenceViolations, List<SpeedFenceViolation>>()
                .executeWithTask(task) {
                    api.fetchSpeedFenceViolations(
                        jwtToken,
                        finOrVin
                    )
                }
        }
        return task.futureTask()
    }

    override fun deleteSpeedFenceViolations(
        jwtToken: String,
        finOrVin: String,
        ids: List<Int>
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.deleteSpeedFenceViolations(
                    jwtToken,
                    finOrVin,
                    ApiDeleteSpeedFenceViolationsRequest(ids)
                )
            }
        }
        return task.futureTask()
    }
}
