package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.PressureUnit

data class Tire(
    /**
     * Tire pressure in given pressure unit
     * Range: 0..632.5
     */
    var pressureKpa: VehicleAttribute<Double, PressureUnit>,

    /**
     * Current tire pressure warning, e.g. 'no warning' or 'deflation'
     */
    var warningLevel: VehicleAttribute<TireMarkerState, NoUnit>
)
