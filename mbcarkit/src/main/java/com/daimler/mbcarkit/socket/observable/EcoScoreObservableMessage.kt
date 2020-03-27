package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.EcoScore
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus

class EcoScoreObservableMessage(ecoScore: EcoScore) : VehicleObservableMessage<EcoScore>(ecoScore) {

    override fun hasChanged(oldVehicleStatus: VehicleStatus?, updatedVehicleStatus: VehicleStatus): Boolean {
        return oldVehicleStatus == null || ecoScoreChanged(oldVehicleStatus.ecoScore, updatedVehicleStatus.ecoScore)
    }

    private fun ecoScoreChanged(oldEcoScore: EcoScore, updateEcoScore: EcoScore): Boolean {
        return (oldEcoScore.accel != updateEcoScore.accel)
            .or(oldEcoScore.bonusRange != updateEcoScore.bonusRange)
            .or(oldEcoScore.const != updateEcoScore.const)
            .or(oldEcoScore.freeWhl != updateEcoScore.freeWhl)
            .or(oldEcoScore.total != updateEcoScore.total)
    }
}
