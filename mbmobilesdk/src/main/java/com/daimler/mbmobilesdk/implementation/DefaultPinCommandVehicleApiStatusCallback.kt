package com.daimler.mbmobilesdk.implementation

import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatus
import com.daimler.mbcarkit.socket.PinCommandVehicleApiStatusCallback
import com.daimler.mbloggerkit.MBLoggerKit

internal class DefaultPinCommandVehicleApiStatusCallback : PinCommandVehicleApiStatusCallback {

    override fun onPinAccepted(commandStatus: CommandVehicleApiStatus, pin: String) {
        MBLoggerKit.i("Pin was accepted.")
    }

    override fun onPinInvalid(commandStatus: CommandVehicleApiStatus, pin: String, attempts: Int) {
        MBLoggerKit.e("Pin is invalid. Attempts: $attempts.")
    }

    override fun onUserBlocked(
        commandStatus: CommandVehicleApiStatus,
        pin: String,
        attempts: Int,
        blockingTimeSeconds: Int
    ) {
        MBLoggerKit.e("User is blocked. Attempts = $attempts, blockingTimeSeconds = $blockingTimeSeconds.")
    }
}
