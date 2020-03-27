package com.daimler.mbingresskit.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegistrationUserAgreementUpdates(
    val countryCode: String,
    val updates: List<RegistrationUserAgreementUpdate>
) : Parcelable
