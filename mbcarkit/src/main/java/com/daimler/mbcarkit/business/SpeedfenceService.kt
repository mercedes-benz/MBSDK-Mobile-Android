package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.speedfence.SpeedFence
import com.daimler.mbcarkit.business.model.speedfence.SpeedFenceViolation
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface SpeedfenceService {

    /**
     * Fetching a list of SpeedFences model of a car
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN or the VIN of the vehicle
     * @return List of SpeedFence model
     */
    fun fetchSpeedFences(jwtToken: String, finOrVin: String):
        FutureTask<List<SpeedFence>, ResponseError<out RequestError>?>

    /**
     * Creates a list of Speedfences of a car
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN or the VIN of the vehicle
     * @param speedfences List of SpeedFence model which should be created
     */
    fun createSpeedFences(jwtToken: String, finOrVin: String, speedFences: List<SpeedFence>):
        FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Updates the given SpeedFences.
     *
     * @param jwtToken authentication token
     * @param finOrVin FIN/ VIN of the vehicle
     * @param speedfences list of [SpeedFence] to update
     */
    fun updateSpeedFences(jwtToken: String, finOrVin: String, speedFences: List<SpeedFence>):
        FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Deletes the SpeedFences for the given ids.
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN/ VIN of the vehicle
     * @param ids list of [SpeedFence.speedFenceId] to delete; empty list to delete all SpeedFences
     */
    fun deleteSpeedFences(jwtToken: String, finOrVin: String, ids: List<Int> = emptyList()):
        FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Deleting all SpeedFences of a car
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN or the VIN of the vehicle
     */
    @Deprecated("Not longer supported.", ReplaceWith("deleteSpeedFences(jwtToken, finOrVin, emptyList())"))
    fun deleteAllSpeedFences(jwtToken: String, finOrVin: String):
        FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Deletes a specific SpeedFence of a car
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN or the VIN of the vehicle
     * @param fenceId id of the SpeedFence that will be deleted
     */
    @Deprecated("Not longer supported.", ReplaceWith("deleteSpeedFences(jwtToken, finOrVin, listOf(fenceId))"))
    fun deleteSpeedFence(jwtToken: String, finOrVin: String, fenceId: Int):
        FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Fetches the SpeedFence violations for a car.
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN or the VIN of the vehicle
     */
    fun fetchSpeedFenceViolations(jwtToken: String, finOrVin: String):
        FutureTask<List<SpeedFenceViolation>, ResponseError<out RequestError>?>

    /**
     * Deletes SpeedFence violations for a car.
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN or the VIN of the vehicle
     * @param ids list of [SpeedFenceViolation.violationId] to delete; empty list to delete all
     */
    fun deleteSpeedFenceViolations(jwtToken: String, finOrVin: String, ids: List<Int> = emptyList()):
        FutureTask<Unit, ResponseError<out RequestError>?>
}
