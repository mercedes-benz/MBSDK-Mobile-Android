package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit

data class DrivingModes(

    /**
     * Current state of teenage driving mode, p.e. 'ON' or 'ERROR'
     */
    var teenageDrivingMode: VehicleAttribute<DrivingModeState, NoUnit>,

    /**
     * Current state of valet driving mode, p.e. 'ON' or 'ERROR'
     */
    var valetDrivingMode: VehicleAttribute<DrivingModeState, NoUnit>
)
