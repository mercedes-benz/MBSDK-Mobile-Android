package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbcarkit.business.model.vehicle.VehicleWarnings

class WarningsObservableMessage(warnings: VehicleWarnings) : VehicleObservableMessage<VehicleWarnings>(warnings) {

    override fun hasChanged(oldVehicleStatus: VehicleStatus?, updatedVehicleStatus: VehicleStatus): Boolean {
        return oldVehicleStatus == null || warningsChange(oldVehicleStatus.warnings, updatedVehicleStatus.warnings)
    }

    private fun warningsChange(oldWarnings: VehicleWarnings, updateWarnings: VehicleWarnings): Boolean {
        return (oldWarnings.washWater != updateWarnings.washWater)
            .or(oldWarnings.brakeFluid != updateWarnings.brakeFluid)
            .or(oldWarnings.brakeLiningWear != updateWarnings.brakeLiningWear)
            .or(oldWarnings.coolantLevelLow != updateWarnings.coolantLevelLow)
            .or(oldWarnings.electricalRangeScipIndication != updateWarnings.electricalRangeScipIndication)
            .or(oldWarnings.engineLight != updateWarnings.engineLight)
            .or(oldWarnings.liquidRangeSkipIndication != updateWarnings.liquidRangeSkipIndication)
            .or(oldWarnings.lowBattery != updateWarnings.lowBattery)
            .or(oldWarnings.tireLamp != updateWarnings.tireLamp)
            .or(oldWarnings.tireSprw != updateWarnings.tireSprw)
            .or(oldWarnings.tireLevelPrw != updateWarnings.tireLevelPrw)
    }
}
