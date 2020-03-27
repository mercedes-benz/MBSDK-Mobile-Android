package com.daimler.mbcarkit.proto.tmp.car

import com.daimler.mbcarkit.socket.ReceivedCarMessageCallback
import com.daimler.mbnetworkkit.socket.message.DataSocketMessage

/**
 * Temporary till full use of mbprotokit interfaces
 */
class ReceivedCarMessageCallbackMapper(
    private val receivedCarMessageCallback: ReceivedCarMessageCallback
) : com.daimler.mbprotokit.ReceivedCarMessageCallback {
    override fun onCommandVehicleApiStatusUpdates(commandStatusUpdates: com.daimler.mbprotokit.dto.command.CommandVehicleApiStatusUpdates) {
        receivedCarMessageCallback.onCommandVehicleApiStatusUpdates(commandStatusUpdates.map())
    }

    override fun onDebugMessageReceived(debugMessage: com.daimler.mbprotokit.dto.car.DebugMessage) {
        receivedCarMessageCallback.onDebugMessageReceived(debugMessage.map())
    }

    override fun onError(socketMessage: DataSocketMessage, cause: String) {
        receivedCarMessageCallback.onError(socketMessage, cause)
    }

    override fun onPendingCommandRequest() {
        receivedCarMessageCallback.onPendingCommandRequest()
    }

    override fun onVehicleAuthUpdate(vehicleUpdate: com.daimler.mbprotokit.dto.car.VehicleAuthUpdate) {
        receivedCarMessageCallback.onVehicleAuthUpdate(vehicleUpdate.map())
    }

    override fun onVehicleStatusUpdate(vehicleUpdates: com.daimler.mbprotokit.dto.car.VehicleStatusUpdates) {
        receivedCarMessageCallback.onVehicleStatusUpdate(vehicleUpdates.map())
    }

    override fun onVehiclesUpdate(vehicleUpdate: com.daimler.mbprotokit.dto.car.VehiclesUpdated) {
        receivedCarMessageCallback.onVehiclesUpdate(vehicleUpdate.map())
    }
}
