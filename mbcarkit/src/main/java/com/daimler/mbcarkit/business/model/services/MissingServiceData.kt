package com.daimler.mbcarkit.business.model.services

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MissingServiceData(
    val missingAccountLinkage: MissingAccountLinkage?
) : Parcelable
