package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit

data class Doors(
    /**
     * Door overview front left
     */
    val frontLeft: Door,

    /**
     * Door overview front right
     */
    val frontRight: Door,

    /**
     * Door overview rear left
     */
    val rearLeft: Door,

    /**
     * Door overview rear right
     */
    val rearRight: Door,

    /**
     * Door overview decklid
     */
    val decklid: Door,

    /**
     * Door open status for all doors/decklid combined
     */
    var stateOverall: VehicleAttribute<DoorOverallStatus, NoUnit>,

    /**
     * Door lock status for all doors/decklid combined
     */
    var lockStateOverall: VehicleAttribute<DoorLockOverallStatus, NoUnit>
)
