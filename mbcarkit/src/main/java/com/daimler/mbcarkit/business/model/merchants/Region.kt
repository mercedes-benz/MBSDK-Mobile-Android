package com.daimler.mbcarkit.business.model.merchants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Region(
    val region: String?,
    val subRegion: String?
) : Parcelable
