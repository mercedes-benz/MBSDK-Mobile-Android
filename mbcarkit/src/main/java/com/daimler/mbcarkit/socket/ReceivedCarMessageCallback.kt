package com.daimler.mbcarkit.socket

import com.daimler.mbcarkit.business.model.DebugMessage
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatusUpdates
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatusUpdates
import com.daimler.mbcarkit.business.model.vehicle.VehicleUpdate
import com.daimler.mbnetworkkit.socket.message.DataSocketMessage

interface ReceivedCarMessageCallback {
    fun onError(socketMessage: DataSocketMessage, cause: String)
    fun onVehicleStatusUpdate(vehicleUpdates: VehicleStatusUpdates)
    fun onVehiclesUpdate(vehicleUpdate: VehicleUpdate)
    fun onVehicleAuthUpdate(vehicleUpdate: VehicleUpdate)
    fun onDebugMessageReceived(debugMessage: DebugMessage)
    fun onCommandVehicleApiStatusUpdates(commandStatusUpdates: CommandVehicleApiStatusUpdates)
    fun onPendingCommandRequest()
}
