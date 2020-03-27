package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.SpeedAlertService
import com.daimler.mbcarkit.business.model.speedalert.SpeedAlertViolation
import com.daimler.mbcarkit.business.model.speedalert.SpeedUnit
import com.daimler.mbcarkit.network.VehicleApi
import com.daimler.mbcarkit.network.model.ApiSpeedAlertViolation
import com.daimler.mbcarkit.network.model.toSpeedAlertViolations
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.MappableRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.NoContentRequestExecutor
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.ResponseTaskObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class RetrofitSpeedAlertService(
    api: VehicleApi,
    scope: CoroutineScope = NetworkCoroutineScope()
) : BaseRetrofitService<VehicleApi>(api, scope), SpeedAlertService {

    override fun fetchViolations(
        jwtToken: String,
        finOrVin: String,
        unit: SpeedUnit
    ): FutureTask<List<SpeedAlertViolation>, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<List<SpeedAlertViolation>>()
        scope.launch {
            MappableRequestExecutor<List<ApiSpeedAlertViolation>, List<SpeedAlertViolation>> {
                it.toSpeedAlertViolations()
            }.executeWithTask(task) {
                api.fetchViolations(
                    jwtToken,
                    finOrVin,
                    unit
                )
            }
        }
        return task.futureTask()
    }

    override fun deleteViolation(
        jwtToken: String,
        finOrVin: String,
        violationId: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.deleteViolation(
                    jwtToken,
                    finOrVin,
                    violationId
                )
            }
        }
        return task.futureTask()
    }

    override fun deleteViolations(
        jwtToken: String,
        finOrVin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.deleteViolations(
                    jwtToken,
                    finOrVin
                )
            }
        }
        return task.futureTask()
    }
}
