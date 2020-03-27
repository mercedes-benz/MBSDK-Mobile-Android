package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.assignment.QRAssignment
import com.daimler.mbcarkit.business.model.rif.Rifability
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface AssignmentService {

    /**
     * Starts the vehicle assignment with a QR code.
     *
     * @param jwtToken The current access token of the user.
     * @param qrLink The QR link scanned from the headunit.
     * @param countryCode the country code of the user (optional)
     */
    fun assignVehicleWithQrCode(jwtToken: String, qrLink: String, countryCode: String? = null): FutureTask<QRAssignment, ResponseError<out RequestError>?>

    /**
     * Starts the vehicle assignment with a vin. This is only the first step to assign a vehicle by vin.
     * To finish the process, the VAC which is displayed afterwards, has to be confirmed by calling [confirmVehicleAssignmentWithVac]
     *
     * @param jwtToken
     *                      The token of the user, to which the vehicle should be assigned
     *
     * @param vin
     *                      The vin of the vehicle which should be assigned to the related user.
     *
     */
    fun assignVehicleByVin(jwtToken: String, vin: String): FutureTask<Rifability, ResponseError<out RequestError>?>

    /**
     * Confirms the vehicle assignment using the generated VAC from the headunit.
     *
     * @param jwtToken The current access token of the user.
     * @param vin The vin of the assigned vehicle.
     * @param vac The generated VAC from the headunit.
     */
    fun confirmVehicleAssignmentWithVac(jwtToken: String, vin: String, vac: String): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Unassigns the vehicle given by the VIN.
     *
     * @param jwtToken The access token of the user.
     * @param vin The VIN of the vehicle that shall be unassigned.
     */
    fun unassignVehicleByVin(jwtToken: String, vin: String): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Checks if the given vehicle has RIF support.
     *
     * @param jwtToken
     *                      The token of the user.
     *
     * @param vin
     *                      The vin of the vehicle of which the RIF support should be checked.
     */
    fun fetchRifability(jwtToken: String, vin: String): FutureTask<Rifability, ResponseError<out RequestError>?>
}
