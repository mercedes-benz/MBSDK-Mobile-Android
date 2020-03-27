package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.TirePressureMonitoringState
import com.daimler.mbcarkit.network.model.ApiTirePressureMonitoringState.Companion.map
import com.google.gson.annotations.SerializedName

internal enum class ApiTirePressureMonitoringState {
    @SerializedName("NoTirePressure") NO_TIRE_PRESSURE,
    @SerializedName("FlatRunner") FLAT_RUNNER,
    @SerializedName("TirePressureMonitoring") TIRE_PRESSURE_MONITORING;

    companion object {
        val map: Map<String, TirePressureMonitoringState> = TirePressureMonitoringState.values().associateBy(TirePressureMonitoringState::name)
    }
}

internal fun ApiTirePressureMonitoringState?.toTirePressureMonitoringState(): TirePressureMonitoringState? =
    this?.let { map[name] }
