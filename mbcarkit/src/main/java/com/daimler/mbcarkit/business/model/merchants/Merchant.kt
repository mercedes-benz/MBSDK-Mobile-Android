package com.daimler.mbcarkit.business.model.merchants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Merchant(
    val id: String,
    val legalName: String?,
    val address: Address?,
    val region: Region?,
    val coordinates: GeoCoordinates?,
    @Deprecated("Unsupported and will be removed in a future version.")
    val spokenLanguage: List<SpokenLanguage>?,
    val communication: Communication?,
    val openingHours: OpeningHours?
) : Parcelable
