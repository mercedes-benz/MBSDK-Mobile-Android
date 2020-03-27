package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.geofencing.Fence
import com.daimler.mbcarkit.business.model.geofencing.GeofenceViolation
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface GeofencingService {

    /**
     * Get all fences for a given user.
     *
     * @param jwtToken The current access token of the user.
     * @param vin The fin or vin of the assigned vehicle, OPTIONAL parameter, required to Fence.isActive to be set correctly
     * @return List<Fence>
     */
    fun fetchAllFences(
        jwtToken: String,
        vin: String? = null
    ): FutureTask<List<Fence>, ResponseError<out RequestError>?>

    /**
     * Create a fence for user (and add it to the active vehicle when a VIN is given)
     *
     * @param jwtToken The current access token of the user.
     * @param fence Fence to be created
     * @param vin The fin or vin of the assigned vehicle, OPTIONAL parameter; when passed, it will activate fence for given vehicle
     * @return created Fence
     */
    fun createFence(
        jwtToken: String,
        fence: Fence,
        vin: String? = null
    ): FutureTask<Fence, ResponseError<out RequestError>?>

    /**
     * Get a specific fence
     *
     * @param jwtToken The current access token of the user.
     * @param id Id of fence to fetch
     * @return Fence fetched by given id
     */
    fun fetchFenceById(
        jwtToken: String,
        id: Int
    ): FutureTask<Fence, ResponseError<out RequestError>?>

    /**
     * Update a fence of a given user
     *
     * @param jwtToken The current access token of the user.
     * @param id Id of fence to update
     * @param fence Fence that will replace old one
     */
    fun updateFence(
        jwtToken: String,
        id: Int,
        fence: Fence
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Delete fences of an associated user.
     *
     * @param jwtToken The current access token of the user.
     * @param id Id of Fence to delete
     */
    fun deleteFence(
        jwtToken: String,
        id: Int
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Get all fences for a given vehicle.
     *
     * @param jwtToken The current access token of the user.
     * @param vin The fin or vin of the assigned vehicle.
     * @return List<Fence>
     */
    fun fetchAllVehicleFences(
        jwtToken: String,
        vin: String
    ): FutureTask<List<Fence>, ResponseError<out RequestError>?>

    /**
     * Activate specific fence on current car and user.
     *
     * @param jwtToken The current access token of the user.
     * @param vin The fin or vin of the assigned vehicle.
     * @param id Id of Fence to activate
     */
    fun activateVehicleFence(
        jwtToken: String,
        vin: String,
        id: Int
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Delete specific fence on current car.
     *
     * @param jwtToken The current access token of the user.
     * @param vin The fin or vin of the assigned vehicle.
     * @param id Id of Fence to delete
     */
    fun deleteVehicleFence(
        jwtToken: String,
        vin: String,
        id: Int
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Get all violations for selected vehicle.
     *
     * @param jwtToken The current access token of the user.
     * @param vin The fin or vin of the assigned vehicle.
     * @return List<GeofenceViolation>
     */
    fun fetchAllViolations(
        jwtToken: String,
        vin: String
    ): FutureTask<List<GeofenceViolation>, ResponseError<out RequestError>?>

    /**
     * Delete all violations for selected vehicle.
     *
     * @param jwtToken The current access token of the user.
     * @param vin The fin or vin of the assigned vehicle.
     */
    fun deleteAllViolations(
        jwtToken: String,
        vin: String
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Delete specific fence violation stored for a vehicle and user association.
     *
     * @param jwtToken The current access token of the user.
     * @param vin The fin or vin of the assigned vehicle.
     * @param id Id of GeofenceViolation to delete
     */
    fun deleteViolation(
        jwtToken: String,
        vin: String,
        id: Int
    ): FutureTask<Unit, ResponseError<out RequestError>?>
}
