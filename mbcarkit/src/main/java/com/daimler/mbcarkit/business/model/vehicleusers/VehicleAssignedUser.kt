package com.daimler.mbcarkit.business.model.vehicleusers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class VehicleAssignedUser(
    val authorizationId: String,
    val displayName: String,
    val email: String?,
    val mobileNumber: String?,
    val profilePictureLink: String?,
    val status: VehicleAssignedUserStatus,
    val validUntil: Date?
) : Parcelable
