package com.daimler.mbmobilesdk.example.car

import com.daimler.mbcarkit.business.model.vehicle.DoorLockOverallStatus
import com.daimler.mbcarkit.business.model.vehicle.Tank
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbcarkit.business.model.vehicle.WindowsOverallStatus

interface CarKitVehicleStatusListener {

    fun onDoorLockOverallStatusChanged(doorLockOverallStatus: DoorLockOverallStatus)

    fun onWindowOverallStatusChanged(windowsOverallStatus: WindowsOverallStatus)

    fun onTankInformationChanged(tank: Tank)

    fun onVehicleStatusChanged(vehicleStatus: VehicleStatus)
}
