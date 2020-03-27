package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit

data class Door(
    /**
     * Door Open state
     */
    var state: VehicleAttribute<OpenStatus, NoUnit>,

    /**
     * Door Lock state
     */
    var lockStatus: VehicleAttribute<LockStatus, NoUnit>
)
