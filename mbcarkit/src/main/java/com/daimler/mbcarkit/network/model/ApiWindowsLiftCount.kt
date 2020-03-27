package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.WindowsLiftCount
import com.daimler.mbcarkit.network.model.ApiWindowsLiftCount.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiWindowsLiftCount {
    @SerializedName("NoLift") NO_LIFT,
    @SerializedName("TwoLift") TWO_LIFT,
    @SerializedName("FourLift") FOUR_LIFT;

    companion object {
        val map: Map<String, WindowsLiftCount> = WindowsLiftCount.values().associateBy(WindowsLiftCount::name)
    }
}

internal fun ApiWindowsLiftCount?.toWindowsLiftCount(): WindowsLiftCount? =
    this?.let { map[name] }
