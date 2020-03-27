package com.daimler.mbcarkit.business.model.services

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Service(
    val id: Int,
    val name: String,
    val description: String?,
    val shortName: String?,
    val categoryName: String?,
    val allowedActions: List<ServiceAction>,
    val activationStatus: ServiceStatus,
    val desiredActivationStatus: ServiceStatus,
    val actualActivationServiceStatus: ServiceStatus,
    val virtualActivationServiceStatus: ServiceStatus,
    @Deprecated("Use missingData instead.") val prerequisiteChecks: List<PrerequisiteCheck>,
    val rights: List<ServiceRight>,
    val missingData: MissingServiceData?
) : Parcelable
