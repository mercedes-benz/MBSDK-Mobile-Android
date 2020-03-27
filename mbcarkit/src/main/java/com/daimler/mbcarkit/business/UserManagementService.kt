package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.vehicleusers.NormalizedProfileControl
import com.daimler.mbcarkit.business.model.vehicleusers.ProfileSyncStatus
import com.daimler.mbcarkit.business.model.vehicleusers.VehicleUserManagement
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface UserManagementService {

    /**
     * Returns a list of users who are authorized for a particular vehicle.
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN or the VIN of the vehicle
     */
    fun fetchVehicleUsers(
        jwtToken: String,
        finOrVin: String
    ): FutureTask<VehicleUserManagement, ResponseError<out RequestError>?>

    /**
     * Returns a QR code for user invitation in a byte array.
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN or the VIN of the vehicle
     * @param correlationId correlation id
     */
    fun createQrInvitationByteArray(jwtToken: String, finOrVin: String, correlationId: String): FutureTask<ByteArray, ResponseError<out RequestError>?>

    /**
     * Deletes user from vehicle sub user list.
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN or the VIN of the vehicle
     * @param authorizationId authorization id
     */
    fun deleteUser(jwtToken: String, finOrVin: String, authorizationId: String): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Enables or disables the profile sync for vehicles with NTG >= 6.
     *
     * @param jwtToken authentication token
     * @param finOrVin FIN or VIN of the vehicle
     * @param autoSync true to enable the automatic sync, false to disable
     */
    fun configureAutomaticSync(jwtToken: String, finOrVin: String, autoSync: Boolean): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Upgrades a temporary user (aka. any-user) to a permanent subuser.
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN or the VIN of the vehicle
     * @param authorizationId authorization id
     */
    fun upgradeTemporaryUser(jwtToken: String, finOrVin: String, authorizationId: String): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Fetches the current [ProfileSyncStatus] of the given vehicle.
     *
     * @param jwtToken authentication token
     * @param finOrVin the FIN or the VIN of the vehicle
     */
    fun fetchAutomaticSyncStatus(jwtToken: String, finOrVin: String): FutureTask<ProfileSyncStatus, ResponseError<out RequestError>?>

    /**
     * Fetches the [NormalizedProfileControl] which is used to sync personalization settings between NTG7 head units.
     */
    fun fetchNormalizedProfileControl(jwtToken: String): FutureTask<NormalizedProfileControl, ResponseError<out RequestError>?>

    /**
     * Enables/ disables the normalized profile control.
     */
    fun configureNormalizedProfileControl(jwtToken: String, enabled: Boolean): FutureTask<Unit, ResponseError<out RequestError>?>
}
