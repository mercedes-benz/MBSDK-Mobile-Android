package com.daimler.mbprotokit.messagehandler.handler.car

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.ReceivedCarMessageCallback
import com.daimler.mbprotokit.dto.car.VehicleStatusUpdates
import com.daimler.mbprotokit.dto.car.VehicleUpdate
import com.daimler.mbprotokit.generated.VehicleEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

internal class VepUpdatesHandler(
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) : CarPushMessageHandler {

    override fun handle(
        socketMessage: DataSocketMessage.ByteSocketMessage,
        pushMessage: VehicleEvents.PushMessage,
        messageCallback: ReceivedCarMessageCallback
    ): Boolean {
        pushMessage.vepUpdates?.let {
            scope.launch {
                val result = it.updatesMap.map { it.toVehicleUpdate() }.toMap()
                messageCallback.onVehicleStatusUpdate(
                    VehicleStatusUpdates(result, it.sequenceNumber)
                )
            }
        } ?: messageCallback.onError(socketMessage, "VehicleEvents.VEPUpdateByVIN are null")
        return true
    }

    private fun Map.Entry<String, VehicleEvents.VEPUpdate>.toVehicleUpdate() =
        key to VehicleUpdate(
            value.fullUpdate,
            value.vin,
            value.emitTimestampInMs,
            value.sequenceNumber,
            value.attributesMap
        )
}
