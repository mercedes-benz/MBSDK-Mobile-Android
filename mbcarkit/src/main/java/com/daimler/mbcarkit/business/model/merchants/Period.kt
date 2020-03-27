package com.daimler.mbcarkit.business.model.merchants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Period(
    val from: String?,
    val until: String?
) : Parcelable
