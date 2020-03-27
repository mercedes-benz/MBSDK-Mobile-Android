package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.AssignmentService
import com.daimler.mbcarkit.business.VehicleService
import com.daimler.mbcarkit.business.model.assignment.QRAssignment
import com.daimler.mbcarkit.business.model.rif.Rifability
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

internal class CachedAssignmentService(
    private val vehicleService: VehicleService,
    private val assignmentService: AssignmentService
) : AssignmentService {

    override fun assignVehicleWithQrCode(
        jwtToken: String,
        qrLink: String,
        countryCode: String?
    ): FutureTask<QRAssignment, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<QRAssignment, ResponseError<out RequestError>?>()
        assignmentService.assignVehicleWithQrCode(jwtToken, qrLink, countryCode)
            .onComplete {
                if (it.finOrVin.isNotBlank()) {
                    vehicleService.createOrUpdateAssigningVehicle(it.finOrVin)
                }
                deferredTask.complete(it)
            }.onFailure {
                deferredTask.fail(it)
            }
        return deferredTask.futureTask()
    }

    override fun assignVehicleByVin(
        jwtToken: String,
        vin: String
    ): FutureTask<Rifability, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<Rifability, ResponseError<out RequestError>?>()
        assignmentService.assignVehicleByVin(jwtToken, vin)
            .onComplete {
                vehicleService.createOrUpdateAssigningVehicle(vin)
                deferredTask.complete(it)
            }
            .onFailure {
                deferredTask.fail(it)
            }
        return deferredTask.futureTask()
    }

    override fun confirmVehicleAssignmentWithVac(
        jwtToken: String,
        vin: String,
        vac: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<Unit, ResponseError<out RequestError>?>()
        assignmentService.confirmVehicleAssignmentWithVac(jwtToken, vin, vac)
            .onComplete {
                vehicleService.createOrUpdateAssigningVehicle(vin)
                deferredTask.complete(it)
            }.onFailure {
                deferredTask.fail(it)
            }
        return deferredTask.futureTask()
    }

    override fun unassignVehicleByVin(
        jwtToken: String,
        vin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<Unit, ResponseError<out RequestError>?>()
        assignmentService.unassignVehicleByVin(jwtToken, vin)
            .onComplete {
                vehicleService.createOrUpdateUnassigningVehicle(vin)
                deferredTask.complete(it)
            }.onFailure {
                deferredTask.fail(it)
            }
        return deferredTask.futureTask()
    }

    override fun fetchRifability(
        jwtToken: String,
        vin: String
    ): FutureTask<Rifability, ResponseError<out RequestError>?> =
        assignmentService.fetchRifability(jwtToken, vin)
}
