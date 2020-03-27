package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.DriveType
import com.daimler.mbcarkit.network.model.ApiDriveType.Companion.map
import com.google.gson.annotations.SerializedName

enum class ApiDriveType {
    @SerializedName("PICK_UP") PICK_UP,
    @SerializedName("DROP_OFF") DROP_OFF;

    companion object {
        val map: Map<String, DriveType> = DriveType.values().associateBy(DriveType::name)
    }
}

internal fun ApiDriveType?.toDriveType(): DriveType? =
    this?.let { map[name] }
