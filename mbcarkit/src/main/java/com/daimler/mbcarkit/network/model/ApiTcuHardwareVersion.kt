package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.TcuHardwareVersion
import com.daimler.mbcarkit.network.model.ApiTcuHardwareVersion.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiTcuHardwareVersion {
    @SerializedName("NoTCU") NO_TCU,
    @SerializedName("KOM") KOM,
    @SerializedName("Hermes1") HERMES_1,
    @SerializedName("Hermes1.5") HERMES_1_5,
    @SerializedName("Hermes2") HERMES_2,
    @SerializedName("Hermes2.1") HERMES_2_1,
    @SerializedName("HermesFup1") HERMES_FUP_1,
    @SerializedName("HermesFup2") HERMES_FUP_2,
    @SerializedName("Ramses") RAMSES;

    companion object {
        val map: Map<String, TcuHardwareVersion> = TcuHardwareVersion.values().associateBy(TcuHardwareVersion::name)
    }
}

internal fun ApiTcuHardwareVersion?.toTcuHardwareVersion(): TcuHardwareVersion? =
    this?.let { map[name] }
