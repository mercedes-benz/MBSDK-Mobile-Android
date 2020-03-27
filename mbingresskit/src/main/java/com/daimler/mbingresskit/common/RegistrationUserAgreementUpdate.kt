package com.daimler.mbingresskit.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegistrationUserAgreementUpdate(
    val userAgreementId: String,
    val versionId: String,
    val locale: String,
    val accepted: Boolean
) : Parcelable
