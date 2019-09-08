package com.daimler.mbmobilesdk.vehicleselection

import com.daimler.mbcarkit.business.model.vehicle.AssignmentPendingState
import com.daimler.mbcarkit.business.model.vehicle.FuelType
import com.daimler.mbcarkit.business.model.vehicle.VehicleConnectivity

data class GarageVehicle(
    val finOrVin: String,
    val model: String,
    val licensePlate: String,
    val tankLevel: Int?,
    val energyLevel: Int?,
    val fuelType: FuelType?,
    val locked: Boolean?,
    val selected: Boolean,
    val trustLevel: Int,
    val assignmentState: AssignmentPendingState,
    val vehicleConnectivity: VehicleConnectivity
)

internal fun GarageVehicle.isSelectableVehicle() =
    assignmentState == AssignmentPendingState.NONE && isTrustLevelSufficient()

internal fun GarageVehicle.isTrustLevelSufficient() =
    trustLevel > REQUIRED_TRUST_LEVEL || (trustLevel == REQUIRED_TRUST_LEVEL && vehicleConnectivity != VehicleConnectivity.BUILT_IN)

private const val REQUIRED_TRUST_LEVEL = 1
