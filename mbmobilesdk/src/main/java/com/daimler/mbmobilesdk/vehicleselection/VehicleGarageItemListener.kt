package com.daimler.mbmobilesdk.vehicleselection

internal interface VehicleGarageItemListener {

    fun onVehicleSelected(vehicle: GarageVehicle)

    fun onAssignVehicle()

    fun onContinueAssignment(vehicle: GarageVehicle)

    fun onScrollToVehicle(itemId: String)
}