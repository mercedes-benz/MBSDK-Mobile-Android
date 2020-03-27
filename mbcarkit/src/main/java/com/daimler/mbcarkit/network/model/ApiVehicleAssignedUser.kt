package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicleusers.VehicleAssignedUser
import com.daimler.mbcarkit.business.model.vehicleusers.VehicleAssignedUserStatus
import com.google.gson.annotations.SerializedName
import java.util.Date

internal data class ApiVehicleAssignedUser(
    @SerializedName("authorizationId") val authorizationId: String,
    @SerializedName("ciamId") val ciamId: String?,
    @SerializedName("displayName") val displayName: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("mobileNumber") val mobileNumber: String?,
    @SerializedName("profilePictureLink") val profilePictureLink: String?,
    @SerializedName("validUntil") val validUntil: Date?
)

internal fun ApiVehicleAssignedUser.toVehicleAssignedUser(status: VehicleAssignedUserStatus) =
    VehicleAssignedUser(
        authorizationId,
        displayName.orEmpty(),
        email,
        mobileNumber,
        profilePictureLink,
        status,
        validUntil
    )
