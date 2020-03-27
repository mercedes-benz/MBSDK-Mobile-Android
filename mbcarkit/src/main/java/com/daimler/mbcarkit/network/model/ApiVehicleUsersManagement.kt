package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicleusers.VehicleAssignedUser
import com.daimler.mbcarkit.business.model.vehicleusers.VehicleAssignedUserStatus
import com.daimler.mbcarkit.business.model.vehicleusers.VehicleUserManagement
import com.google.gson.annotations.SerializedName

internal data class ApiVehicleUsersManagement(
    @SerializedName("generalData") val generalData: ApiVehicleUserGeneralData?,
    @SerializedName("owner") val owner: ApiVehicleAssignedUser?,
    @SerializedName("subUsers") val subUsers: ApiVehicleAssignedSubusers?,
    @SerializedName("localProfiles") val localProfiles: List<ApiLocalProfile>?
)

internal fun ApiVehicleUsersManagement.toVehicleUserManagement(
    finOrVin: String
) = VehicleUserManagement(
    finOrVin,
    generalData?.maxNumberOfProfiles,
    generalData?.numberOfOccupiedProfiles,
    owner?.toVehicleAssignedUser(VehicleAssignedUserStatus.OWNER),
    mapSubUsers(subUsers?.pendingSubusers, subUsers?.validSubusers, subUsers?.temporarySubusers),
    localProfiles?.mapNotNull { it.toVehicleLocalProfile() }
)

private fun mapSubUsers(
    pendingSubUsers: List<ApiVehicleAssignedUser>?,
    validSubUsers: List<ApiVehicleAssignedUser>?,
    temporarySubUsers: List<ApiVehicleAssignedUser>?
): List<VehicleAssignedUser> =
    mutableListOf<VehicleAssignedUser>().apply {
        addAllOf(VehicleAssignedUserStatus.PENDING_USER, pendingSubUsers)
        addAllOf(VehicleAssignedUserStatus.VALID_USER, validSubUsers)
        addAllOf(VehicleAssignedUserStatus.TEMPORARY_USER, temporarySubUsers)
    }

private fun MutableList<VehicleAssignedUser>.addAllOf(
    status: VehicleAssignedUserStatus,
    users: List<ApiVehicleAssignedUser>?
) {
    users?.map {
        it.toVehicleAssignedUser(status)
    }?.let {
        addAll(it)
    }
}
