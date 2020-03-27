package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.DayTime
import com.daimler.mbcarkit.business.model.vehicle.HeadUnit
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus

class HeadUnitObservableMessage(headUnit: HeadUnit) : VehicleObservableMessage<HeadUnit>(headUnit) {

    override fun hasChanged(oldVehicleStatus: VehicleStatus?, updatedVehicleStatus: VehicleStatus): Boolean {
        return oldVehicleStatus == null || headUnitChanged(oldVehicleStatus.hu, updatedVehicleStatus.hu)
    }

    private fun headUnitChanged(oldHu: HeadUnit, updatedHu: HeadUnit): Boolean {
        return (oldHu.tracking != updatedHu.tracking)
            .or(oldHu.language != updatedHu.language)
            .or(oldHu.temperature != updatedHu.temperature)
            .or(oldHu.timeformat != updatedHu.timeformat)
            .or(weeklySetHUChanged(oldHu.weeklySetHU.value, updatedHu.weeklySetHU.value))
    }

    private fun weeklySetHUChanged(oldWeeklySetHU: List<DayTime>?, updatedWeeklySetHu: List<DayTime>?): Boolean {
        return if (oldWeeklySetHU != null && updatedWeeklySetHu != null) {
            oldWeeklySetHU.toTypedArray().contentDeepEquals(updatedWeeklySetHu.toTypedArray())
        } else {
            false
        }
    }
}
