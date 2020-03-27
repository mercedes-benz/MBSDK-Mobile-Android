package com.daimler.mbcarkit.business.model.services

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PrerequisiteCheck(
    val name: String?,
    val missingInformation: List<ServiceMissingField>,
    val actions: List<ServiceAction>
) : Parcelable
