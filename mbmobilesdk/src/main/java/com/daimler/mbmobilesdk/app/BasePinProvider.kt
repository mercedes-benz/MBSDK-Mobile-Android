package com.daimler.mbmobilesdk.app

import com.daimler.mbmobilesdk.utils.WeakRefActivityLifecycleCallbacks
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbcarkit.business.PinProvider
import com.daimler.mbcarkit.business.PinRequest
import com.daimler.mbcarkit.business.model.command.CommandStatus
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatus
import com.daimler.mbcarkit.socket.PinCommandStatusCallback
import com.daimler.mbcarkit.socket.PinCommandVehicleApiStatusCallback

abstract class BasePinProvider(
    private val next: BasePinProvider?
) : WeakRefActivityLifecycleCallbacks(true),
    PinProvider,
    PinCommandStatusCallback, PinCommandVehicleApiStatusCallback {

    final override fun requestPin(pinRequest: PinRequest) {
        if (isEligible()) {
            doRequestPin(pinRequest)
        } else {
            next?.requestPin(pinRequest) ?: MBLoggerKit.e("No eligible PinProvider available.")
        }
    }

    override fun onPinAccepted(commandStatus: CommandStatus, pin: String) = Unit

    override fun onPinInvalid(commandStatus: CommandStatus, pin: String) = Unit

    override fun onPinAccepted(commandStatus: CommandVehicleApiStatus, pin: String) = Unit

    override fun onPinInvalid(commandStatus: CommandVehicleApiStatus, pin: String) = Unit

    protected abstract fun isEligible(): Boolean

    protected abstract fun doRequestPin(pinRequest: PinRequest)
}