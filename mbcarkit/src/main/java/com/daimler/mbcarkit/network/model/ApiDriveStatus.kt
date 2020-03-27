package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.DriveStatus
import com.daimler.mbcarkit.network.model.ApiDriveStatus.Companion.map
import com.google.gson.annotations.SerializedName

enum class ApiDriveStatus {
    @SerializedName("CHECKED_OUT") CHECKED_OUT,
    @SerializedName("READY") READY,
    @SerializedName("IN_PROGRESS") IN_PROGRESS,
    @SerializedName("FAILED_RETRY_POSSIBLE_USER") FAILED_RETRY_POSSIBLE_USER,
    @SerializedName("FAILED_RETRY_POSSIBLE_OPERATOR") FAILED_RETRY_POSSIBLE_OPERATOR,
    @SerializedName("FAILED") FAILED,
    @SerializedName("READY_FOR_PRODUCT_LIABILITY") READY_FOR_PRODUCT_LIABILITY,
    @SerializedName("DRIVE_IN_PROGRESS") DRIVE_IN_PROGRESS,
    @SerializedName("OPERATOR_DRIVE_IN_PROGRESS") OPERATOR_DRIVE_IN_PROGRESS,
    @SerializedName("COMPLETED") COMPLETED;

    companion object {
        val map: Map<String, DriveStatus> = DriveStatus.values().associateBy(DriveStatus::name)
    }
}

internal fun ApiDriveStatus?.toDriveSatus(): DriveStatus? =
    this?.let { map[name] }
