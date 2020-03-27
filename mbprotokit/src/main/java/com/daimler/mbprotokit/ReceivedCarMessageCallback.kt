package com.daimler.mbprotokit

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.dto.car.DebugMessage
import com.daimler.mbprotokit.dto.car.VehicleAuthUpdate
import com.daimler.mbprotokit.dto.car.VehicleStatusUpdates
import com.daimler.mbprotokit.dto.car.VehiclesUpdated
import com.daimler.mbprotokit.dto.command.CommandVehicleApiStatusUpdates

interface ReceivedCarMessageCallback {
    fun onError(socketMessage: DataSocketMessage, cause: String)
    fun onVehicleStatusUpdate(vehicleUpdates: VehicleStatusUpdates)
    fun onVehiclesUpdate(vehicleUpdate: VehiclesUpdated)
    fun onVehicleAuthUpdate(vehicleUpdate: VehicleAuthUpdate)
    fun onDebugMessageReceived(debugMessage: DebugMessage)
    fun onCommandVehicleApiStatusUpdates(commandStatusUpdates: CommandVehicleApiStatusUpdates)
    fun onPendingCommandRequest()
}
