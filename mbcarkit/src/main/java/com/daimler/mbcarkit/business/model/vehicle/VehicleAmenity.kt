package com.daimler.mbcarkit.business.model.vehicle

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VehicleAmenity(
    val code: String?,
    val description: String?
) : Parcelable
