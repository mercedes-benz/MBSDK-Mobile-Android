package com.daimler.mbcarkit.business.model.merchants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OpeningHours(
    val monday: Day?,
    val tuesday: Day?,
    val wednesday: Day?,
    val thursday: Day?,
    val friday: Day?,
    val saturday: Day?,
    val sunday: Day?
) : Parcelable
