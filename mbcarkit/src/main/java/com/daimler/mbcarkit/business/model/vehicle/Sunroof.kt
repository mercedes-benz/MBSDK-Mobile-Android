package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit

data class Sunroof(
    /**
     * Event Info Rainclose, e.g. rain lift position
     */
    var event: VehicleAttribute<SunroofEventState, NoUnit>,

    /**
     * Rainclose monitoring active state
     */
    var isEventActive: VehicleAttribute<ActiveState, NoUnit>,

    /**
     * Sliding roof opened / closed state, e.g. 'opening' or 'intermediate sliding'
     */
    var status: VehicleAttribute<SunroofState, NoUnit>,

    /**
     * Front roller blind state sunroof, e.g. 'intermediate position' or 'opening'
     */
    var blindFront: VehicleAttribute<SunroofBlindState, NoUnit>,

    /**
     * Rear roller blind state sunroof, e.g. 'intermediate position' or 'opening'
     */
    var blindRear: VehicleAttribute<SunroofBlindState, NoUnit>
)
