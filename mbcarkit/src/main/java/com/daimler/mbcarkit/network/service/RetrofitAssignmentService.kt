package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.AssignmentService
import com.daimler.mbcarkit.business.model.assignment.QRAssignment
import com.daimler.mbcarkit.business.model.rif.Rifability
import com.daimler.mbcarkit.network.VehicleApi
import com.daimler.mbcarkit.network.model.ApiAssignmentPreconditionError
import com.daimler.mbcarkit.network.model.ApiConfirmAssignmentVacRequest
import com.daimler.mbcarkit.network.model.ApiQrAssignmentRequest
import com.daimler.mbcarkit.network.model.ApiQrAssignmentResponse
import com.daimler.mbcarkit.network.model.ApiRifability
import com.daimler.mbcarkit.network.model.toAssignmentPreconditionError
import com.daimler.mbnetworkkit.common.ErrorMapStrategy
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.NoContentRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.RequestExecutor
import com.daimler.mbnetworkkit.networking.defaultErrorMapping
import com.daimler.mbnetworkkit.networking.exception.ResponseException
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.ResponseTaskObject
import com.daimler.mbnetworkkit.task.TaskObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

internal class RetrofitAssignmentService(
    api: VehicleApi,
    scope: CoroutineScope = NetworkCoroutineScope()
) : BaseRetrofitService<VehicleApi>(api, scope), AssignmentService {

    override fun assignVehicleWithQrCode(
        jwtToken: String,
        qrLink: String,
        countryCode: String?
    ): FutureTask<QRAssignment, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<QRAssignment>()
        scope.launch {
            RequestExecutor<ApiQrAssignmentResponse, QRAssignment>(
                getAssignmentPreconditionErrorMapping()
            )
                .executeWithTask(task) {
                    api.assignVehicleWithQrCode(
                        jwtToken,
                        countryCode,
                        ApiQrAssignmentRequest(qrLink)
                    )
                }
        }
        return task.futureTask()
    }

    override fun assignVehicleByVin(
        jwtToken: String,
        vin: String
    ): FutureTask<Rifability, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Rifability>()
        scope.launch {
            RequestExecutor<ApiRifability, Rifability>().executeWithTask(task) {
                api.assignVehicleByVin(
                    jwtToken,
                    vin
                )
            }
        }
        return task.futureTask()
    }

    override fun confirmVehicleAssignmentWithVac(
        jwtToken: String,
        vin: String,
        vac: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor(getAssignmentPreconditionErrorMapping())
                .executeWithTask(task) {
                    api.confirmVehicleAssignmentWithVac(
                        jwtToken,
                        vin,
                        ApiConfirmAssignmentVacRequest(vac)
                    )
                }
        }
        return task.futureTask()
    }

    override fun unassignVehicleByVin(
        jwtToken: String,
        vin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = TaskObject<Unit, ResponseError<out RequestError>?>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.unassignVehicleByVin(
                    jwtToken,
                    vin
                )
            }
        }
        return task.futureTask()
    }

    override fun fetchRifability(
        jwtToken: String,
        vin: String
    ): FutureTask<Rifability, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Rifability>()
        scope.launch {
            RequestExecutor<ApiRifability, Rifability>().executeWithTask(task) {
                api.fetchRifability(
                    jwtToken,
                    vin
                )
            }
        }
        return task.futureTask()
    }

    private fun mapAssignmentPreconditionError(error: Throwable?): ResponseError<out RequestError> {
        val defaultError = if (error is ResponseException &&
            error.responseCode == HttpURLConnection.HTTP_PRECON_FAILED
        ) {
            defaultErrorMapping(error, ApiAssignmentPreconditionError::class.java)
        } else {
            defaultErrorMapping(error)
        }
        return if (defaultError.requestError is ApiAssignmentPreconditionError) {
            val apiPreconditionError = defaultError.requestError as ApiAssignmentPreconditionError
            return ResponseError.requestError(apiPreconditionError.toAssignmentPreconditionError())
        } else {
            defaultError
        }
    }

    private fun getAssignmentPreconditionErrorMapping() = ErrorMapStrategy.Custom {
        mapAssignmentPreconditionError(it)
    }
}
