package com.daimler.mbcarkit.business.model.vehicleusers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NormalizedProfileControl(
    val enabled: Boolean
) : Parcelable
