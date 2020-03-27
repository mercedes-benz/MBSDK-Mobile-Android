package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapabilities
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarPoi
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarRoute
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

/**
 * Service that handles "SendToCar" features.
 */
interface SendToCarService {

    /**
     * Returns the currently possible actions for this vehicle
     * @param token The users authorization token
     * @param finOrVin selected vehicle VIN or FIN for sending to
     */
    fun fetchCapabilities(
        token: String,
        finOrVin: String
    ): FutureTask<SendToCarCapabilities, ResponseError<out RequestError>?>

    /**
     * Sends a POI to the given car.
     * This method takes care of sending the given Route in the correct way, depending
     * on each HeadUnit's individual capabilities, e.g. RIF, Push or Bluetooth.
     * @param token The users authorization token
     * @param finOrVin selected vehicle VIN or FIN for sending to
     * @param poi the POI object which should be sent to car
     */
    fun sendPoi(
        token: String,
        finOrVin: String,
        poi: SendToCarPoi
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Sends a route to the given car.
     * This method takes care of sending the given Route in the correct way, depending
     * on each HeadUnit's individual capabilities, e.g. RIF, Push.
     * This method currently does not support bluetooth
     * @param token The users authorization token
     * @param finOrVin selected vehicle VIN or FIN for sending to
     * @param route the route object which should be sent to car
     */
    fun sendRoute(
        token: String,
        finOrVin: String,
        route: SendToCarRoute
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Used to enable/disable bluetooth functionality
     * @param isEnabled determines whether bluetooth functionality should be toggled on or off
     */
    fun enableBluetooth(isEnabled: Boolean)
}
