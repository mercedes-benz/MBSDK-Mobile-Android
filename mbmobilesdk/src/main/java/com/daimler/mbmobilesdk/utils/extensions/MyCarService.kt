package com.daimler.mbmobilesdk.utils.extensions

import com.daimler.mbmobilesdk.vehicleselection.GarageVehicle
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.vehicle.AssignmentPendingState
import com.daimler.mbcarkit.business.model.vehicle.DoorLockState
import com.daimler.mbcarkit.business.model.vehicle.Tank
import com.daimler.mbcarkit.business.model.vehicle.VehicleConnectivity
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbcarkit.business.model.vehicle.Vehicles
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.defaultErrorMapping
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

internal fun MBCarKit.loadGarageVehicles(): FutureTask<List<GarageVehicle>, ResponseError<out RequestError>?> {
    val garageVehicleTask = TaskObject<List<GarageVehicle>, ResponseError<out RequestError>?>()
    MBIngressKit.refreshTokenIfRequired()
        .onComplete { token ->
            MBCarKit.vehicleService().fetchVehicles(token.jwtToken.plainToken)
                .onComplete { vehicles ->
                    garageVehicleTask.complete(garageVehiclesFromVehicleInfo(vehicles))
                }
                .onFailure {
                    MBLoggerKit.re("Load vehicles failed", it)
                    garageVehicleTask.fail(it)
                }
        }.onFailure {
            MBLoggerKit.e("Error while refreshing token.", throwable = it)
            garageVehicleTask.fail(defaultErrorMapping(it))
        }
    return garageVehicleTask.futureTask()
}

internal fun MBCarKit.garageVehiclesFromVehicleInfo(vehicles: Vehicles): List<GarageVehicle> {
    MBLoggerKit.d("Map ${vehicles.vehicles.size} vehicles for Garage")
    val selectedFinOrVin = selectedFinOrVin()
    return vehicles.map { vehicleInfo ->
        val vehicleStatus = loadCurrentVehicleState(vehicleInfo.finOrVin)
        garageVehicleFromVehicle(vehicleInfo, vehicleStatus, vehicleInfo.finOrVin == selectedFinOrVin)
    }.toList()
}

internal fun MBCarKit.loadSelectedGarageVehicle(): GarageVehicle? {
    return selectedFinOrVin()?.let { finOrVin ->
        loadVehicleByVin(finOrVin)?.let { vehicle ->
            garageVehicleFromVehicle(vehicle, loadCurrentVehicleState(vehicle.finOrVin), true)
        }
    }
}

internal fun MBCarKit.loadGarageVehicleByVin(finOrVin: String): GarageVehicle? {
    return loadVehicleByVin(finOrVin)?.let { vehicle ->
        garageVehicleFromVehicle(
            vehicle,
            loadCurrentVehicleState(vehicle.finOrVin),
            vehicle.finOrVin == selectedFinOrVin()
        )
    }
}

/**
 * Returns the locally cached vehicle with the given [finOrVin] creates a pending vehicle
 * with state [AssignmentPendingState.ASSIGN] if none exists in the cache.
 *
 * Note: It is theoretically possible that this method returns a vehicle with another state
 * than [AssignmentPendingState.ASSIGN].
 */
internal fun MBCarKit.getAssigningGarageVehicle(finOrVin: String): GarageVehicle {
    return getPendingGarageVehicle(finOrVin, AssignmentPendingState.ASSIGN)
}

/**
 * Returns the locally cached vehicle with the given [finOrVin] creates a pending vehicle
 * with state [AssignmentPendingState.DELETE] if none exists in the cache.
 */
internal fun MBCarKit.getDeletingGarageVehicle(finOrVin: String): GarageVehicle {
    return getPendingGarageVehicle(finOrVin, AssignmentPendingState.DELETE)
}

private fun MBCarKit.getPendingGarageVehicle(finOrVin: String, state: AssignmentPendingState): GarageVehicle {
    return loadGarageVehicleByVin(finOrVin)?.copy(assignmentState = state)
        ?: createPendingGarageVehicle(finOrVin, state)
}

private fun createPendingGarageVehicle(finOrVin: String, state: AssignmentPendingState): GarageVehicle {
    val vehicle = VehicleInfo.createPendingVehicle(finOrVin, finOrVin, state)
    return garageVehicleFromVehicle(vehicle, VehicleStatus.initialState(finOrVin), false)
}

internal fun garageVehicleFromVehicle(vehicleInfo: VehicleInfo, vehicleStatus: VehicleStatus, selected: Boolean): GarageVehicle {
    return GarageVehicle(
        vehicleInfo.finOrVin,
        vehicleInfo.model,
        vehicleInfo.licensePlate,
        vehicleStatus.tank.tankLevel(),
        vehicleStatus.vehicle.soc.value,
        vehicleInfo.fuelType,
        vehicleStatus.vehicle.doorLockState.value?.isLocked(),
        selected,
        vehicleInfo.trustLevel,
        vehicleInfo.assignmentState ?: AssignmentPendingState.NONE,
        vehicleInfo.vehicleConnectivity ?: VehicleConnectivity.NONE)
}

internal fun Tank.tankLevel(): Int? = liquidLevel.value

internal fun DoorLockState.isLocked(): Boolean? = when (this) {
    DoorLockState.UNKNOWN -> null
    DoorLockState.LOCKED_EXTERNAL, DoorLockState.LOCKED_INTERNAL -> true
    else -> false
}