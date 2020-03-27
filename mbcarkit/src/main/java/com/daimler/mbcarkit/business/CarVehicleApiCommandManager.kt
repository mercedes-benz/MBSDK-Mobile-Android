package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.command.VehicleCommand
import com.daimler.mbcarkit.business.model.command.VehicleCommandCallback
import com.daimler.mbcarkit.business.model.command.VehicleCommandError

interface CarVehicleApiCommandManager {
    fun <T : VehicleCommandError> sendCommand(commandRequest: VehicleCommand<T>, commandCallback: VehicleCommandCallback<T>)
}
