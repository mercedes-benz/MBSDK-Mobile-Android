package com.daimler.mbcarkit.bluetooth

import com.daimler.mbcarkit.business.model.sendtocar.SendToCarWaypoint
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface BluetoothSendToCarService {
    /**
     * Sends a single POI to the currently connected HeadUnit.
     * @param vin the vin of the vehicle to send POI to
     * @param destination the POI to be sent
     * @param enqueueDestination if set true, this service will cache the last POI and try to
     *      deliver it as soon as a HeadUnit connection is available
     */
    fun sendPoi(vin: String, destination: SendToCarWaypoint, enqueueDestination: Boolean): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Indicates if has connection to HeadUnit with given VIN
     */
    fun isBluetoothConnectionAvailable(vin: String): Boolean
}
