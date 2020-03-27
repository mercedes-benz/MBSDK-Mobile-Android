package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit

data class Windows(
    /**
     * Open state for front left window, e.g. 'closed' or 'intermediate position'
     */
    var frontLeft: VehicleAttribute<WindowState, NoUnit>,

    /**
     * Open state for front right window, e.g. 'closed' or 'intermediate position'
     */
    var frontRight: VehicleAttribute<WindowState, NoUnit>,

    /**
     * Open state for rear left window, e.g. 'closed' or 'intermediate position'
     */
    var rearLeft: VehicleAttribute<WindowState, NoUnit>,

    /**
     * Open state for rear right window, e.g. 'closed' or 'intermediate position'
     */
    var rearRight: VehicleAttribute<WindowState, NoUnit>,

    /**
     * Open state for sunroof
     */
    var sunroof: Sunroof,

    /**
     * Overall open state for all windows combined, e.g. 'airing' or 'completely open'
     */
    var overallState: VehicleAttribute<WindowsOverallStatus, NoUnit>,

    /**
     * Flipper window rotary latch state
     */
    var flipWindowStatus: VehicleAttribute<OpenStatus, NoUnit>,

    /**
     * Rear roller blind state, e.g. 'intermediate position' or 'completely open'
     */
    var blindRear: VehicleAttribute<WindowBlindState, NoUnit>,

    /**
     * Rear passenger compartment side roller blind left state, e.g. 'intermediate position' or 'completely open'
     */
    var blindRearLeft: VehicleAttribute<WindowBlindState, NoUnit>,

    /**
     * Rear passenger compartment side roller blind right state, e.g. 'intermediate position' or 'completely open'
     */
    var blindRearRight: VehicleAttribute<WindowBlindState, NoUnit>
)
