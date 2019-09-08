package com.daimler.mbmobilesdk.vehicleselection

import com.daimler.mbcarkit.business.model.vehicle.AssignmentPendingState

internal object GarageItemFactory {

    private const val ITEM_ID_ADD_VEHICLE = "item.id.garage.vehicle.add"

    fun getGarageItemType(vehicle: GarageVehicle) =
        when {
            vehicle.assignmentState != AssignmentPendingState.NONE ->
                GarageItemType.PENDING
            !vehicle.isTrustLevelSufficient() ->
                GarageItemType.INSUFFICIENT
            else ->
                GarageItemType.FULL_ASSIGNED
        }

    fun createAddVehicleItem(listener: VehicleGarageItemListener) =
        AddVehicleGarageItem(ITEM_ID_ADD_VEHICLE, GarageItemType.ADD, listener)

    fun createGarageVehicleItem(vehicle: GarageVehicle, listener: VehicleGarageItemListener): BaseVehicleGarageItem =
        when (val type = getGarageItemType(vehicle)) {
            GarageItemType.ADD -> {
                createAddVehicleItem(listener)
            }
            GarageItemType.FULL_ASSIGNED -> {
                VehicleGarageItem(vehicle.finOrVin, vehicle, type, listener)
            }
            GarageItemType.PENDING -> {
                PendingVehicleGarageItem(vehicle.finOrVin, vehicle.finOrVin, vehicle.assignmentState, type, listener)
            }
            GarageItemType.INSUFFICIENT -> {
                InsufficientVehicleGarageItem(vehicle.finOrVin, vehicle, type, listener)
            }
        }
}