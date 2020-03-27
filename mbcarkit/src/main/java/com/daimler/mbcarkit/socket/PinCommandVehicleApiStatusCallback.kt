package com.daimler.mbcarkit.socket

import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatus

interface PinCommandVehicleApiStatusCallback {

    /**
     * Called when Pin was accepted
     *
     * @param commandStatus current status of the request
     * @param pin the entered pin
     */
    fun onPinAccepted(commandStatus: CommandVehicleApiStatus, pin: String)

    /**
     * Called when the entered pin is invalid.
     *
     * @param commandStatus current status of the request
     * @param pin the entered pin
     * @param attempts number of attempted PINs
     */
    fun onPinInvalid(commandStatus: CommandVehicleApiStatus, pin: String, attempts: Int)

    /**
     * Called when this user is currently blocked.
     *
     * @param commandStatus current status of the request
     * @param pin the entered pin
     * @param number of attempted PINs
     * @param blockingTimeSeconds ammount of time the user is blocked in seconds
     */
    fun onUserBlocked(commandStatus: CommandVehicleApiStatus, pin: String, attempts: Int, blockingTimeSeconds: Int)
}
