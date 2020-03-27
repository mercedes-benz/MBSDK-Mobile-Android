package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.PinProvider
import com.daimler.mbcarkit.business.PinRequest
import com.daimler.mbcarkit.business.VehicleService
import com.daimler.mbcarkit.business.model.command.capabilities.CommandCapabilities
import com.daimler.mbcarkit.business.model.consumption.Consumption
import com.daimler.mbcarkit.business.model.vehicle.AvpReservationStatus
import com.daimler.mbcarkit.business.model.vehicle.VehicleDealer
import com.daimler.mbcarkit.business.model.vehicle.VehicleSoftwareUpdateInformation
import com.daimler.mbcarkit.business.model.vehicle.Vehicles
import com.daimler.mbcarkit.business.model.vehicle.toVehicles
import com.daimler.mbcarkit.network.VehicleApi
import com.daimler.mbcarkit.network.error.PinProviderNotSetError
import com.daimler.mbcarkit.network.error.PinRequestError
import com.daimler.mbcarkit.network.model.ApiAcceptAvpDriveRequest
import com.daimler.mbcarkit.network.model.ApiAvpReservationStatus
import com.daimler.mbcarkit.network.model.ApiCommandCapabilities
import com.daimler.mbcarkit.network.model.ApiConsumption
import com.daimler.mbcarkit.network.model.ApiLicensePlate
import com.daimler.mbcarkit.network.model.ApiResetDamageDetectionRequest
import com.daimler.mbcarkit.network.model.ApiVehicle
import com.daimler.mbcarkit.network.model.ApiVehicleDealersRequest
import com.daimler.mbcarkit.network.model.ApiVehicleSoftwareUpdateInformation
import com.daimler.mbcarkit.network.model.toAvpReservationStatus
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.MappableRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.NoContentRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.RequestExecutor
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.ResponseTaskObject
import com.daimler.mbnetworkkit.task.TaskObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.ArrayList

internal class RetrofitVehicleService(
    api: VehicleApi,
    private val pinProvider: PinProvider?,
    scope: CoroutineScope = NetworkCoroutineScope()
) : BaseRetrofitService<VehicleApi>(api, scope), VehicleService {

    override fun fetchVehicles(
        jwtToken: String
    ): FutureTask<Vehicles, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Vehicles>()
        scope.launch {
            MappableRequestExecutor<ArrayList<ApiVehicle>, Vehicles> {
                it.toVehicles()
            }.executeWithTask(task) {
                api.fetchVehicles(jwtToken)
            }
        }
        return task.futureTask()
    }

    override fun fetchCommandCapabilities(
        jwtToken: String,
        finOrVin: String
    ): FutureTask<CommandCapabilities, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<CommandCapabilities>()
        scope.launch {
            RequestExecutor<ApiCommandCapabilities, CommandCapabilities>()
                .executeWithTask(task) {
                    api.fetchCommandCapabilities(
                        jwtToken,
                        finOrVin
                    )
                }
        }
        return task.futureTask()
    }

    override fun resetDamageDetection(
        jwtToken: String,
        finOrVin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        pinProvider?.requestPin(
            object : PinRequest {

                override fun confirmPin(pin: String) {
                    scope.launch {
                        NoContentRequestExecutor().executeWithTask(task) {
                            api.resetDamageDetection(
                                jwtToken,
                                finOrVin,
                                ApiResetDamageDetectionRequest(pin)
                            )
                        }
                    }
                }

                override fun cancel(cause: String?) {
                    task.fail(
                        ResponseError.requestError(
                            PinRequestError(
                                cause
                                    ?: "Pin Request cancelled by user."
                            )
                        )
                    )
                }
            }
        ) ?: task.fail(ResponseError.requestError(PinProviderNotSetError()))
        return task.futureTask()
    }

    override fun acceptAvpDrive(
        jwtToken: String,
        finOrVin: String,
        bookingId: String,
        startDrive: Boolean
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.acceptAvpDrive(
                    jwtToken,
                    finOrVin,
                    ApiAcceptAvpDriveRequest(bookingId, startDrive)
                )
            }
        }
        return task.futureTask()
    }

    override fun fetchAvpReservationStatus(
        jwtToken: String,
        finOrVin: String,
        reservationIds: List<String>
    ): FutureTask<List<AvpReservationStatus>, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<List<AvpReservationStatus>>()
        scope.launch {
            MappableRequestExecutor<List<ApiAvpReservationStatus>, List<AvpReservationStatus>> { api ->
                api.map { it.toAvpReservationStatus() }
            }.executeWithTask(task) {
                api.fetchAvpReservationStatus(
                    jwtToken,
                    finOrVin,
                    reservationIds.joinToString(",")
                )
            }
        }
        return task.futureTask()
    }

    override fun updateLicensePlate(
        jwtToken: String,
        countryCode: String,
        finOrVin: String,
        licensePlate: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = TaskObject<Unit, ResponseError<out RequestError>?>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.updateLicensePlate(
                    jwtToken,
                    countryCode,
                    finOrVin,
                    ApiLicensePlate(licensePlate)
                )
            }
        }
        return task.futureTask()
    }

    override fun updateVehicleDealers(
        jwtToken: String,
        finOrVin: String,
        vehicleDealers: List<VehicleDealer>
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.updateVehicleDealers(
                    jwtToken,
                    finOrVin,
                    ApiVehicleDealersRequest.fromVehicleDealers(vehicleDealers)
                )
            }
        }
        return task.futureTask()
    }

    override fun fetchConsumption(
        jwtToken: String,
        finOrVin: String
    ): FutureTask<Consumption, ResponseError<out RequestError>?> {
        val task = TaskObject<Consumption, ResponseError<out RequestError>?>()
        scope.launch {
            RequestExecutor<ApiConsumption, Consumption>().executeWithTask(task) {
                api.fetchConsumption(
                    jwtToken,
                    finOrVin
                )
            }
        }
        return task.futureTask()
    }

    override fun createOrUpdateAssigningVehicle(finOrVin: String): Boolean = false

    override fun createOrUpdateUnassigningVehicle(finOrVin: String): Boolean = false

    override fun fetchSoftwareUpdates(
        jwtToken: String,
        finOrVin: String
    ): FutureTask<VehicleSoftwareUpdateInformation, ResponseError<out RequestError>?> {
        val task = TaskObject<VehicleSoftwareUpdateInformation, ResponseError<out RequestError>?>()
        scope.launch {
            RequestExecutor<ApiVehicleSoftwareUpdateInformation, VehicleSoftwareUpdateInformation>().executeWithTask(task) {
                api.fetchSoftwareUpdates(jwtToken, finOrVin)
            }
        }
        return task.futureTask()
    }
}
