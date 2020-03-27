package com.daimler.mbcarkit.business.model.merchants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Day(
    val status: String?,
    val periods: List<Period>?
) : Parcelable
