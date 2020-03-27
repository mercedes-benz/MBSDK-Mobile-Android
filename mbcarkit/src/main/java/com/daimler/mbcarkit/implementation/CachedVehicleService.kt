package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.CommandCapabilitiesCache
import com.daimler.mbcarkit.business.SelectedVehicleStorage
import com.daimler.mbcarkit.business.VehicleService
import com.daimler.mbcarkit.business.model.command.capabilities.CommandCapabilities
import com.daimler.mbcarkit.business.model.vehicle.AssignmentPendingState
import com.daimler.mbcarkit.business.model.vehicle.VehicleDealer
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbcarkit.business.model.vehicle.Vehicles
import com.daimler.mbcarkit.socket.VehicleCache
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.Task
import com.daimler.mbnetworkkit.task.TaskObject

internal class CachedVehicleService(
    private val vehicleCache: VehicleCache,
    private val vehicleService: VehicleService,
    private val selectedVehicleStorage: SelectedVehicleStorage,
    private val commandCapabilitiesCache: CommandCapabilitiesCache
) : VehicleService {

    override fun fetchVehicles(jwtToken: String): FutureTask<Vehicles, ResponseError<out RequestError>?> {
        val vehicleTask = TaskObject<Vehicles, ResponseError<out RequestError>?>()
        vehicleService.fetchVehicles(jwtToken)
            .onComplete {
                updateCacheAndNotify(vehicleTask, it)
            }
            .onFailure {
                vehicleTask.complete(vehicleCache.loadVehicles())
            }
        return vehicleTask.futureTask()
    }

    override fun updateLicensePlate(
        jwtToken: String,
        countryCode: String,
        finOrVin: String,
        licensePlate: String
    ) = vehicleService.updateLicensePlate(jwtToken, countryCode, finOrVin, licensePlate)

    override fun updateVehicleDealers(
        jwtToken: String,
        finOrVin: String,
        vehicleDealers: List<VehicleDealer>
    ) = vehicleService.updateVehicleDealers(jwtToken, finOrVin, vehicleDealers)

    override fun fetchConsumption(
        jwtToken: String,
        finOrVin: String
    ) = vehicleService.fetchConsumption(jwtToken, finOrVin)

    override fun fetchCommandCapabilities(
        jwtToken: String,
        finOrVin: String
    ): FutureTask<CommandCapabilities, ResponseError<out RequestError>?> {
        val cachedCapabilities = commandCapabilitiesCache.loadFromCache(finOrVin)
        return cachedCapabilities?.let {
            val deferredTask = TaskObject<CommandCapabilities, ResponseError<out RequestError>?>()
            fetchCommandCapabilitiesFromNetworkAndCache(jwtToken, finOrVin)
            deferredTask.complete(it)
            deferredTask.futureTask()
        } ?: run {
            fetchCommandCapabilitiesFromNetworkAndCache(jwtToken, finOrVin)
        }
    }

    override fun resetDamageDetection(jwtToken: String, finOrVin: String) =
        vehicleService.resetDamageDetection(jwtToken, finOrVin)

    override fun acceptAvpDrive(
        jwtToken: String,
        finOrVin: String,
        bookingId: String,
        startDrive: Boolean
    ): FutureTask<Unit, ResponseError<out RequestError>?> =
        vehicleService.acceptAvpDrive(jwtToken, finOrVin, bookingId, startDrive)

    override fun fetchAvpReservationStatus(
        jwtToken: String,
        finOrVin: String,
        reservationIds: List<String>
    ) = vehicleService.fetchAvpReservationStatus(jwtToken, finOrVin, reservationIds)

    private fun fetchCommandCapabilitiesFromNetworkAndCache(
        jwtToken: String,
        finOrVin: String
    ): FutureTask<CommandCapabilities, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<CommandCapabilities, ResponseError<out RequestError>?>()

        vehicleService.fetchCommandCapabilities(jwtToken, finOrVin)
            .onComplete {
                commandCapabilitiesCache.writeCache(finOrVin, it)
                deferredTask.complete(it)
            }.onFailure {
                deferredTask.fail(it)
            }

        return deferredTask.futureTask()
    }

    override fun createOrUpdateAssigningVehicle(finOrVin: String): Boolean {
        return createOrUpdatePendingVehicle(finOrVin, AssignmentPendingState.ASSIGN)
    }

    override fun createOrUpdateUnassigningVehicle(finOrVin: String): Boolean {
        val result = createOrUpdatePendingVehicle(finOrVin, AssignmentPendingState.DELETE)
        selectedVehicleStorage.selectedFinOrVin()?.let {
            if (it == finOrVin)
                selectedVehicleStorage.clear()
        }
        return result
    }

    override fun fetchSoftwareUpdates(
        jwtToken: String,
        finOrVin: String
    ) = vehicleService.fetchSoftwareUpdates(jwtToken, finOrVin)

    private fun createOrUpdatePendingVehicle(
        finOrVin: String,
        assignmentPendingState: AssignmentPendingState
    ): Boolean {
        val current = vehicleCache.loadVehicleByVin(finOrVin)
        val vehicle = current?.copy(assignmentState = assignmentPendingState)
            ?: VehicleInfo.createPendingVehicle(finOrVin, finOrVin, assignmentPendingState)
        vehicleCache.updateVehicles(Vehicles(listOf(vehicle)))
        return current != null
    }

    private fun updateCacheAndNotify(
        vehicleTask: Task<Vehicles, ResponseError<out RequestError>?>,
        vehicles: Vehicles
    ) {
        // Remove all vehicles that are not returned by the API but are persisted in the cache
        // but NOT if it has a pending assignment.
        val removedVehicles = removedVehiclesIfUnassigned(vehicles)

        // Update vehicles if they are not removed and do not have a pending un-assignment.
        clearAndUpdateVehiclesCache(removedVehicles, vehicles.vehicles)
        clearSelectedVehicleIfRemoved(removedVehicles)

        vehicleTask.complete(vehicleCache.loadVehicles())
    }

    private fun removedVehiclesIfUnassigned(vehiclesFromRepo: Vehicles): Vehicles {
        val removedVehicles = removedVehicles(vehiclesFromRepo, vehicleCache.loadVehicles())
        vehicleCache.deleteVehicles(removedVehicles)
        return removedVehicles
    }

    private fun clearAndUpdateVehiclesCache(removedVehicles: Vehicles, vehiclesFromRepo: List<VehicleInfo>) {
        val updatedVehicles = ArrayList(vehiclesFromRepo)
        updatedVehicles.removeAll(removedVehicles)
        updatedVehicles.removeAll(unassignedVehicles(vehicleCache.loadVehicles()))
        vehicleCache.updateVehicles(Vehicles(updatedVehicles))
    }

    private fun clearSelectedVehicleIfRemoved(removedVehicles: Vehicles) {
        selectedVehicleStorage.selectedFinOrVin()?.let { finOrVin ->
            if (removedVehicles.any { it.finOrVin == finOrVin } ||
                unassignedVehicles(vehicleCache.loadVehicles()).any { it.finOrVin == finOrVin }
            ) {
                selectedVehicleStorage.clear()
            }
        }
    }

    private fun removedVehicles(currentVehicles: Vehicles, cachedVehicles: Vehicles): Vehicles =
        Vehicles(
            cachedVehicles.vehicles.filter {
                currentVehicles.vehicles.contains(it).not() && it.assignmentState != AssignmentPendingState.ASSIGN
            }
        )

    private fun unassignedVehicles(cachedVehicles: Vehicles): Vehicles =
        Vehicles(
            cachedVehicles.vehicles.filter {
                it.assignmentState == AssignmentPendingState.DELETE
            }
        )
}
