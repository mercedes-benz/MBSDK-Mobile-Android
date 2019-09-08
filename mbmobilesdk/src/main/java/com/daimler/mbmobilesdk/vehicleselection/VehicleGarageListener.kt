package com.daimler.mbmobilesdk.vehicleselection

import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo

/**
 * Listener for usage with the vehicle garage.
 */
interface VehicleGarageListener {

    /**
     * Called when a vehicle is selected.
     * This method is called in two cases:
     *  1. The user selected a vehicle by himself.
     *  2. There was no previously selected vehicle and the garage was told to auto select
     *      a vehicle after loading the vehicle data.
     * If the user deleted a vehicle and there was another vehicle that was auto-selected by
     * the garage, this method is called before [onVehicleDeleted].
     *
     * @param vehicle The selected vehicle.
     */
    fun onVehicleSelected(vehicle: VehicleInfo)

    /**
     * Called when the user is going to add a new vehicle.
     */
    fun onAddNewVehicle()

    /**
     * Called when a vehicle was unassigned by the user.
     * If there is another vehicle that will be auto-selected by the garage, [onVehicleSelected]
     * is called before this method.
     *
     * @param finOrVin the vin of the unassigned vehicle
     */
    fun onVehicleDeleted(finOrVin: String)

    /**
     * Called when a vehicle is selected from the garage.
     * [onVehicleSelected] is called directly afterwards.
     */
    fun onVehicleSelectedFromGarage(vehicle: VehicleInfo, autoSelected: Boolean)

    fun onVehiclesUnselected()
}