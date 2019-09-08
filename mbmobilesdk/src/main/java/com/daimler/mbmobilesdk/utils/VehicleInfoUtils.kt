package com.daimler.mbmobilesdk.utils

import com.daimler.mbcarkit.business.model.vehicle.AssignmentPendingState
import com.daimler.mbcarkit.business.model.vehicle.TirePressureMonitoringState
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo

internal fun emptyVehicleInfo(finOrVin: String = "") =
    VehicleInfo(
        finOrVin,
        finOrVin,
        "",
        "",
        AssignmentPendingState.NONE,
        0,
        "",
        TirePressureMonitoringState.NO_TIRE_PRESSURE,
        emptyList(),
        null,
        null,
        null,
        null,
        null,
        false,
        false,
        null,
        null,
        null,
        null,
        null,
        null
    )