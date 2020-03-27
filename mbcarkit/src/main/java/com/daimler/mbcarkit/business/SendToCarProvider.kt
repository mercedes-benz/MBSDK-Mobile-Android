package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapabilities
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarPoi
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarRoute
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

internal interface SendToCarProvider {
    /**
     * Returns the capabilities and preconditions of the HeadUnit for the given vehicle.
     */
    fun fetchCapabilities(
        token: String,
        finOrVin: String
    ): FutureTask<SendToCarCapabilities, ResponseError<out RequestError>?>

    /**
     * Sends a POI to the given car via Backend.
     */
    fun sendPoi(
        token: String,
        finOrVin: String,
        poi: SendToCarPoi
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Sends a route to the given car via Backend.
     */
    fun sendRoute(
        token: String,
        finOrVin: String,
        route: SendToCarRoute
    ): FutureTask<Unit, ResponseError<out RequestError>?>
}
