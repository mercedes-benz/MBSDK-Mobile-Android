package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.Sunroof
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbcarkit.business.model.vehicle.Windows

class WindowsObservableMessage(windows: Windows) : VehicleObservableMessage<Windows>(windows) {

    override fun hasChanged(
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ): Boolean {
        return oldVehicleStatus == null || windowsChanged(
            oldVehicleStatus.windows,
            updatedVehicleStatus.windows
        )
    }

    private fun windowsChanged(oldWindows: Windows, updatedWindows: Windows): Boolean {
        return (oldWindows.frontLeft != updatedWindows.frontLeft)
            .or(oldWindows.frontRight != updatedWindows.frontRight)
            .or(oldWindows.rearLeft != updatedWindows.rearLeft)
            .or(oldWindows.rearRight != updatedWindows.rearRight)
            .or(sunroofChanged(oldWindows.sunroof, updatedWindows.sunroof))
            .or(oldWindows.overallState != updatedWindows.overallState)
            .or(oldWindows.flipWindowStatus != updatedWindows.flipWindowStatus)
            .or(oldWindows.blindRear != updatedWindows.blindRear)
            .or(oldWindows.blindRearLeft != updatedWindows.blindRearLeft)
            .or(oldWindows.blindRearRight != updatedWindows.blindRearRight)
    }

    private fun sunroofChanged(oldSunroof: Sunroof?, updatedSunroof: Sunroof?): Boolean {
        return (oldSunroof?.status != updatedSunroof?.status)
            .or(oldSunroof?.event != updatedSunroof?.event)
            .or(oldSunroof?.isEventActive != updatedSunroof?.isEventActive)
            .or(oldSunroof?.blindFront != updatedSunroof?.blindFront)
            .or(oldSunroof?.blindRear != updatedSunroof?.blindRear)
    }
}
