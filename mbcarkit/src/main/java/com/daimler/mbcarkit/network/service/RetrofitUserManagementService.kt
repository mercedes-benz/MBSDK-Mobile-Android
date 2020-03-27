package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.UserManagementService
import com.daimler.mbcarkit.business.model.vehicleusers.NormalizedProfileControl
import com.daimler.mbcarkit.business.model.vehicleusers.ProfileSyncStatus
import com.daimler.mbcarkit.business.model.vehicleusers.VehicleUserManagement
import com.daimler.mbcarkit.network.VehicleApi
import com.daimler.mbcarkit.network.model.ApiAutoSyncConfiguration
import com.daimler.mbcarkit.network.model.ApiAutomaticSyncResponse
import com.daimler.mbcarkit.network.model.ApiNormalizedProfileControl
import com.daimler.mbcarkit.network.model.ApiQrInvitationRequest
import com.daimler.mbcarkit.network.model.ApiVehicleUsersManagement
import com.daimler.mbcarkit.network.model.toVehicleUserManagement
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.networking.coroutines.MappableRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.NoContentRequestExecutor
import com.daimler.mbnetworkkit.networking.coroutines.RequestExecutor
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.ResponseTaskObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

internal class RetrofitUserManagementService(
    api: VehicleApi,
    scope: CoroutineScope = NetworkCoroutineScope()
) : BaseRetrofitService<VehicleApi>(api, scope), UserManagementService {

    override fun fetchVehicleUsers(
        jwtToken: String,
        finOrVin: String
    ): FutureTask<VehicleUserManagement, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<VehicleUserManagement>()
        scope.launch {
            MappableRequestExecutor<ApiVehicleUsersManagement, VehicleUserManagement> {
                it.toVehicleUserManagement(finOrVin)
            }.executeWithTask(task) {
                api.fetchVehicleUsers(
                    jwtToken,
                    finOrVin
                )
            }
        }
        return task.futureTask()
    }

    override fun createQrInvitationByteArray(
        jwtToken: String,
        finOrVin: String,
        correlationId: String
    ): FutureTask<ByteArray, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<ByteArray>()
        scope.launch {
            MappableRequestExecutor<ResponseBody, ByteArray> {
                it.bytes()
            }.executeWithTask(task) {
                api.createQrInvitation(
                    jwtToken,
                    ApiQrInvitationRequest(finOrVin, correlationId)
                )
            }
        }
        return task.futureTask()
    }

    override fun deleteUser(
        jwtToken: String,
        finOrVin: String,
        authorizationId: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.deleteSubUser(
                    jwtToken,
                    finOrVin,
                    authorizationId
                )
            }
        }
        return task.futureTask()
    }

    override fun configureAutomaticSync(
        jwtToken: String,
        finOrVin: String,
        autoSync: Boolean
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.configureProfileAutoSync(
                    jwtToken,
                    finOrVin,
                    ApiAutoSyncConfiguration(autoSync)
                )
            }
        }
        return task.futureTask()
    }

    override fun upgradeTemporaryUser(
        jwtToken: String,
        finOrVin: String,
        authorizationId: String
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.upgradeTemporaryUser(
                    jwtToken,
                    finOrVin,
                    authorizationId
                )
            }
        }
        return task.futureTask()
    }

    override fun fetchAutomaticSyncStatus(
        jwtToken: String,
        finOrVin: String
    ): FutureTask<ProfileSyncStatus, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<ProfileSyncStatus>()
        scope.launch {
            RequestExecutor<ApiAutomaticSyncResponse, ProfileSyncStatus>().executeWithTask(task) {
                api.fetchAutomaticSyncStatus(
                    jwtToken,
                    finOrVin
                )
            }
        }
        return task.futureTask()
    }

    override fun fetchNormalizedProfileControl(
        jwtToken: String
    ): FutureTask<NormalizedProfileControl, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<NormalizedProfileControl>()
        scope.launch {
            RequestExecutor<ApiNormalizedProfileControl, NormalizedProfileControl>()
                .executeWithTask(task) {
                    api.fetchNormalizedProfileControl(jwtToken)
                }
        }
        return task.futureTask()
    }

    override fun configureNormalizedProfileControl(
        jwtToken: String,
        enabled: Boolean
    ): FutureTask<Unit, ResponseError<out RequestError>?> {
        val task = ResponseTaskObject<Unit>()
        scope.launch {
            NoContentRequestExecutor().executeWithTask(task) {
                api.configureNormalizedProfileControl(
                    jwtToken,
                    ApiNormalizedProfileControl(enabled)
                )
            }
        }
        return task.futureTask()
    }
}
