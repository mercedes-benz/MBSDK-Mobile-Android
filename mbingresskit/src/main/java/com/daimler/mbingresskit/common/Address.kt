package com.daimler.mbingresskit.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    val street: String?,
    val houseNumber: String?,
    val zipCode: String?,
    val city: String?,
    val countryCode: String?,
    val state: String?,
    val province: String?,
    val streetType: String?,
    val houseName: String?,
    val floorNumber: String?,
    val doorNumber: String?,
    val addressLine1: String?,
    val addressLine2: String?,
    val addressLine3: String?,
    val postOfficeBox: String?
) : Parcelable
