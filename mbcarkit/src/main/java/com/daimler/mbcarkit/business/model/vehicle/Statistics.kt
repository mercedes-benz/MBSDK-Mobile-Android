package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.CombustionConsumptionUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.DistanceUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.ElectricityConsumptionUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.GasConsumptionUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.SpeedUnit

data class Statistics(
    /**
     * Average speed since start/reset
     * Range: 0..405.0
     */
    val averageSpeed: ResetStart<Double, SpeedUnit>,

    /**
     * Distance since start/reset
     */
    val distance: ZeResetStart<Double, DistanceUnit>,

    /**
     * Driven Time since start/reset in minutes
     *
     */
    val drivenTime: ZeResetStart<Int, NoUnit>,

    /**
     * Statistics of electrical (battery) consumption & driven distance since start/reset
     * - Consumption in given consumption unit, Range: 0..250.0
     * - Distance in given distance unit, Range: 0..999999.9
     */
    val electric: TankStatistics<ElectricityConsumptionUnit, DistanceUnit>,

    /**
     * Statistics of gas (LPG/H2) consumption & driven distance since start/reset
     * - Consumption in given consumption unit, Range: 0..100.0
     * - Distance in given distance unit, Range: 0..999999.9
     */
    val gas: TankStatistics<GasConsumptionUnit, DistanceUnit>,

    /**
     * Statistics of liquid (combustion vehicles) consumption & driven distance since start/reset
     * - Consumption in given consumption unit, Range: 0..100.0
     * - Distance in given distance unit, Range: 0..999999.9
     */
    val liquid: TankStatistics<CombustionConsumptionUnit, DistanceUnit>
)
