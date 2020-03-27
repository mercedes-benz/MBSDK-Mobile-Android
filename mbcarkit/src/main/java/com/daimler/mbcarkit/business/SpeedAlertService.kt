package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.speedalert.SpeedAlertViolation
import com.daimler.mbcarkit.business.model.speedalert.SpeedUnit
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

/**
 * Service that fetch and delete the SpeedViolations of a car
 */
interface SpeedAlertService {

    /**
     * Fetching a list of [SpeedAlertViolation] of a car
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN or the VIN of the vehicle
     * @param unit the unit of the violation that should be returned
     * @return list of [SpeedAlertViolation]
     */
    fun fetchViolations(jwtToken: String, finOrVin: String, unit: SpeedUnit):
        FutureTask<List<SpeedAlertViolation>, ResponseError<out RequestError>?>

    /**
     * Deleting all [SpeedAlertViolation] of a car
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN or the VIN of the vehicle
     */
    fun deleteViolations(jwtToken: String, finOrVin: String):
        FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Deletes a specific [SpeedAlertViolation] of a car
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN or the VIN of the vehicle
     * @param violationId id of the SpeedViolation that will be deleted
     */
    fun deleteViolation(jwtToken: String, finOrVin: String, violationId: String):
        FutureTask<Unit, ResponseError<out RequestError>?>
}
