package com.daimler.mbcarkit.business.model.merchants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    /**
     * Street name
     */
    val street: String?,
    /**
     * Address addition
     */
    val addressAddition: String?,
    /**
     * ZIP code
     */
    val zipCode: String?,
    /**
     * City
     */
    val city: String?,
    /**
     * District
     */
    val district: String?,
    /**
     * Country ISO code
     */
    val countryIsoCode: String?
) : Parcelable
