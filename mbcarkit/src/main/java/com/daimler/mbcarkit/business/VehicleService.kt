package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.command.capabilities.CommandCapabilities
import com.daimler.mbcarkit.business.model.consumption.Consumption
import com.daimler.mbcarkit.business.model.vehicle.AvpReservationStatus
import com.daimler.mbcarkit.business.model.vehicle.VehicleDealer
import com.daimler.mbcarkit.business.model.vehicle.VehicleSoftwareUpdateInformation
import com.daimler.mbcarkit.business.model.vehicle.Vehicles
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface VehicleService {

    /**
     * Load all assigned vehicles from repository and update local cache. Vehicles that are not available
     * anymore because they have been unassigned, will be deleted from cache too.<br>
     *
     * Be aware: If the current selected vehicle was unassigned and removed, it will be cleared from
     * cache too. In this case, [selectedVehicle] could than return null.
     *
     */
    fun fetchVehicles(jwtToken: String): FutureTask<Vehicles, ResponseError<out RequestError>?>

    /**
     * Updates the license plate for the given vehicle.
     *
     * @param jwtToken authentication token
     * @param countryCode market country code (ISO 3166 ALPHA2)
     * @param finOrVin the FIN or the VIN of the vehicle
     * @param licensePlate the new license plate
     */
    fun updateLicensePlate(
        jwtToken: String,
        countryCode: String,
        finOrVin: String,
        licensePlate: String
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Updates the vehicle's dealers.
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN or the VIN of the vehicle
     * @param vehicleDealers a list of the dealers
     */
    fun updateVehicleDealers(
        jwtToken: String,
        finOrVin: String,
        vehicleDealers: List<VehicleDealer>
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Updates the consumption for the given vehicle.
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN or the VIN of the vehicle
     */
    fun fetchConsumption(jwtToken: String, finOrVin: String): FutureTask<Consumption, ResponseError<out RequestError>?>

    /**
     * Fetches the command capabilities for the given vehicle.
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN/ VIN of the vehicle
     */
    fun fetchCommandCapabilities(jwtToken: String, finOrVin: String): FutureTask<CommandCapabilities, ResponseError<out RequestError>?>

    /**
     * Resets last parking collision event. A PinProvider must have been given through
     * the MBCarKit configuration.
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN/ VIN of the vehicle
     */
    fun resetDamageDetection(jwtToken: String, finOrVin: String): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Accepts and verifies whether to go ahead with an AVP (Automatic Valet Parking) drive or not.
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN/ VIN of the vehicle
     * @param bookingId the booking id of the drive
     * @param startDrive indication whether to start the drive or not
     */
    fun acceptAvpDrive(jwtToken: String, finOrVin: String, bookingId: String, startDrive: Boolean): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Fetches data about reserved AVP (Automatic Valet Parking) drives.
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN/ VIN of the vehicle
     * @param reservationIds list of desired reservation status ids
     */
    fun fetchAvpReservationStatus(
        jwtToken: String,
        finOrVin: String,
        reservationIds: List<String>
    ): FutureTask<List<AvpReservationStatus>, ResponseError<out RequestError>?>

    fun createOrUpdateAssigningVehicle(finOrVin: String): Boolean

    fun createOrUpdateUnassigningVehicle(finOrVin: String): Boolean

    /**
     * Fetches vehicle software update information
     */
    fun fetchSoftwareUpdates(jwtToken: String, finOrVin: String): FutureTask<VehicleSoftwareUpdateInformation, ResponseError<out RequestError>?>
}
