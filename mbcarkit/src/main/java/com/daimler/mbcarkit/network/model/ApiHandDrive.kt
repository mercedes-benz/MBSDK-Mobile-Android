package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.HandDrive
import com.daimler.mbcarkit.network.model.ApiHandDrive.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiHandDrive {
    @SerializedName("Left") LEFT,
    @SerializedName("Right") RIGHT;

    companion object {
        val map: Map<String, HandDrive> = HandDrive.values().associateBy(HandDrive::name)
    }
}

internal fun ApiHandDrive?.toHandDrive(): HandDrive? =
    this?.let { map[name] }
