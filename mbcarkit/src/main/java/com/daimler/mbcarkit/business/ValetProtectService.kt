package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.valetprotect.DistanceUnit
import com.daimler.mbcarkit.business.model.valetprotect.ValetprotectItem
import com.daimler.mbcarkit.business.model.valetprotect.ValetprotectViolation
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface ValetProtectService {

    /**
     * Get valet protect item
     *
     * @param jwtToken The current access token of the user.
     * @param vin The fin or vin of the assigned vehicle.
     * @param unit Unit of the Valet Protect radius (kilometers or miles) - KM or MI , never pass UNKNOWN
     */
    fun fetchValetprotectItem(
        jwtToken: String,
        vin: String,
        unit: DistanceUnit
    ): FutureTask<ValetprotectItem, ResponseError<out RequestError>?>

    /**
     * Create valet protect item
     *
     * @param jwtToken The current access token of the user.
     * @param vin The fin or vin of the assigned vehicle.
     * @param item ValetprotectItem to be created
     */
    fun createValetprotectItem(
        jwtToken: String,
        vin: String,
        item: ValetprotectItem
    ): FutureTask<ValetprotectItem, ResponseError<out RequestError>?>

    /**
     * Delete (deactivate) valet protect item
     *
     * @param jwtToken The current access token of the user.
     * @param vin The fin or vin of the assigned vehicle.
     */
    fun deleteValetprotectItem(
        jwtToken: String,
        vin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Get all valet protect violations for a vehicle.
     *
     * @param jwtToken The current access token of the user.
     * @param vin The fin or vin of the assigned vehicle.
     * @param unit Unit of the Valet Protect radius (kilometers or miles) - KM or MI , never pass UNKNOWN
     */
    fun fetchAllValetprotectViolations(
        jwtToken: String,
        vin: String,
        unit: DistanceUnit
    ): FutureTask<List<ValetprotectViolation>, ResponseError<out RequestError>?>

    /**
     * Delete all valet protect violations for a vehicle.
     *
     * @param jwtToken The current access token of the user.
     * @param vin The fin or vin of the assigned vehicle.
     */
    fun deleteAllValetprotectViolations(
        jwtToken: String,
        vin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Get specific valet protect violation for a vehicle.
     *
     * @param jwtToken The current access token of the user.
     * @param vin The fin or vin of the assigned vehicle.
     * @param id The ID of Violation to fetch
     * @param unit Unit of the Valet Protect radius (kilometers or miles) - KM or MI , never pass UNKNOWN
     */
    fun fetchValetprotectViolation(
        jwtToken: String,
        vin: String,
        id: Int,
        unit: DistanceUnit
    ): FutureTask<ValetprotectViolation, ResponseError<out RequestError>?>

    /**
     * Delete specific valet protect violation for a vehicle.
     *
     * @param jwtToken The current access token of the user.
     * @param vin The fin or vin of the assigned vehicle.
     * @param id The ID of Violation to delete
     */
    fun deleteValetprotectViolation(
        jwtToken: String,
        vin: String,
        id: Int
    ): FutureTask<Unit, ResponseError<out RequestError>?>
}
