package com.daimler.mbcarkit.business.model.merchants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GeoCoordinates(
    /**
     * Latitude
     */
    val latitude: Double?,
    /**
     * Longitude
     */
    val longitude: Double?
) : Parcelable
