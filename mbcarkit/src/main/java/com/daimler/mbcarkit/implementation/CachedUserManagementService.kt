package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.UserManagementService
import com.daimler.mbcarkit.business.model.vehicleusers.NormalizedProfileControl
import com.daimler.mbcarkit.business.model.vehicleusers.ProfileSyncStatus
import com.daimler.mbcarkit.business.model.vehicleusers.VehicleUserManagement
import com.daimler.mbcarkit.socket.UserManagementCache
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

internal class CachedUserManagementService(
    private val userManagementCache: UserManagementCache,
    private val userManagementService: UserManagementService
) : UserManagementService {

    override fun fetchVehicleUsers(
        jwtToken: String,
        finOrVin: String
    ): FutureTask<VehicleUserManagement, ResponseError<out RequestError>?> {
        val vehicleTask = TaskObject<VehicleUserManagement, ResponseError<out RequestError>?>()
        userManagementService.fetchVehicleUsers(jwtToken, finOrVin)
            .onComplete {
                userManagementCache.updateUserManagement(it)
                vehicleTask.complete(it)
            }
            .onFailure {
                userManagementCache.loadUserManagement(finOrVin)?.let { userManagement ->
                    vehicleTask.complete(userManagement)
                } ?: vehicleTask.fail(it)
            }
        return vehicleTask.futureTask()
    }

    override fun createQrInvitationByteArray(
        jwtToken: String,
        finOrVin: String,
        correlationId: String
    ): FutureTask<ByteArray, ResponseError<out RequestError>?> {
        return userManagementService.createQrInvitationByteArray(jwtToken, finOrVin, correlationId)
    }

    override fun deleteUser(
        jwtToken: String,
        finOrVin: String,
        authorizationId: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val deleteTask = TaskObject<Unit, ResponseError<out RequestError>?>()
        userManagementService.deleteUser(jwtToken, finOrVin, authorizationId)
            .onComplete {
                userManagementCache.deleteUserManagement(finOrVin)
                deleteTask.complete(it)
            }.onFailure {
                deleteTask.fail(it)
            }
        return deleteTask.futureTask()
    }

    override fun configureAutomaticSync(
        jwtToken: String,
        finOrVin: String,
        autoSync: Boolean
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val configureTask = TaskObject<Unit, ResponseError<out RequestError>?>()
        userManagementService.configureAutomaticSync(jwtToken, finOrVin, autoSync)
            .onComplete {
                configureTask.complete(it)
            }.onFailure {
                configureTask.fail(it)
            }
        return configureTask.futureTask()
    }

    override fun upgradeTemporaryUser(
        jwtToken: String,
        finOrVin: String,
        authorizationId: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val upgradeTask = TaskObject<Unit, ResponseError<out RequestError>?>()
        userManagementService.upgradeTemporaryUser(jwtToken, finOrVin, authorizationId)
            .onComplete {
                userManagementCache.upgradeTemporaryUser(authorizationId)
                upgradeTask.complete(it)
            }.onFailure {
                upgradeTask.fail(it)
            }
        return upgradeTask.futureTask()
    }

    override fun fetchAutomaticSyncStatus(
        jwtToken: String,
        finOrVin: String
    ): FutureTask<ProfileSyncStatus, ResponseError<out RequestError>?> =
        userManagementService.fetchAutomaticSyncStatus(jwtToken, finOrVin)

    override fun fetchNormalizedProfileControl(
        jwtToken: String
    ): FutureTask<NormalizedProfileControl, ResponseError<out RequestError>?> =
        userManagementService.fetchNormalizedProfileControl(jwtToken)

    override fun configureNormalizedProfileControl(
        jwtToken: String,
        enabled: Boolean
    ): FutureTask<Unit, ResponseError<out RequestError>?> =
        userManagementService.configureNormalizedProfileControl(jwtToken, enabled)

    private fun profileSyncStateForBoolean(enabled: Boolean) =
        if (enabled) ProfileSyncStatus.ON else ProfileSyncStatus.OFF
}
