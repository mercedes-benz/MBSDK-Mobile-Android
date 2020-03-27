package com.daimler.mbcarkit.implementation.exceptions

import com.daimler.mbcarkit.business.model.sendtocar.SendToCarPrecondition
import com.daimler.mbnetworkkit.networking.RequestError

sealed class SendToCarNotPossibleError : RequestError {
    object Capability : SendToCarNotPossibleError()
    class Preconditions(
        val preconditions: List<SendToCarPrecondition>
    ) : SendToCarNotPossibleError()
    object BluetoothNotConfigured : SendToCarNotPossibleError()
}
