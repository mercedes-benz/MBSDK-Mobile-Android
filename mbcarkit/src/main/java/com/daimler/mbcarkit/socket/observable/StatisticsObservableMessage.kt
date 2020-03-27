package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.ResetStart
import com.daimler.mbcarkit.business.model.vehicle.Statistics
import com.daimler.mbcarkit.business.model.vehicle.TankStatistics
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbcarkit.business.model.vehicle.ZeResetStart

class StatisticsObservableMessage(statistics: Statistics) : VehicleObservableMessage<Statistics>(statistics) {

    override fun hasChanged(oldVehicleStatus: VehicleStatus?, updatedVehicleStatus: VehicleStatus): Boolean {
        return oldVehicleStatus == null || statisticsChanged(oldVehicleStatus.statistics, updatedVehicleStatus.statistics)
    }

    private fun statisticsChanged(oldStatistics: Statistics, updatedStatistics: Statistics): Boolean {
        return (tankStatsChanged(oldStatistics.electric, updatedStatistics.electric))
            .or(tankStatsChanged(oldStatistics.gas, updatedStatistics.gas))
            .or(tankStatsChanged(oldStatistics.liquid, updatedStatistics.liquid))
            .or(resetStarteChanged(oldStatistics.averageSpeed, updatedStatistics.averageSpeed))
            .or(zeResetStartChanged(oldStatistics.distance, updatedStatistics.distance))
            .or(zeResetStartChanged(oldStatistics.drivenTime, updatedStatistics.drivenTime))
    }

    private fun tankStatsChanged(oldTankStatistics: TankStatistics<*, *>, updateTankStatistics: TankStatistics<*, *>): Boolean {
        return (resetStarteChanged(oldTankStatistics.consumption, updateTankStatistics.consumption))
            .or(resetStarteChanged(oldTankStatistics.distance, updateTankStatistics.distance))
    }

    private fun zeResetStartChanged(oldZeResetStart: ZeResetStart<*, *>, updatedZeResetStart: ZeResetStart<*, *>): Boolean {
        return (resetStarteChanged(oldZeResetStart.ze, updatedZeResetStart.ze))
            .or(oldZeResetStart.start != updatedZeResetStart.start)
            .or(oldZeResetStart.reset != updatedZeResetStart.reset)
    }

    private fun resetStarteChanged(oldResetStart: ResetStart<*, *>, updateResetStart: ResetStart<*, *>): Boolean {
        return (oldResetStart.start != updateResetStart.start)
            .or(oldResetStart.reset != updateResetStart.reset)
    }
}
