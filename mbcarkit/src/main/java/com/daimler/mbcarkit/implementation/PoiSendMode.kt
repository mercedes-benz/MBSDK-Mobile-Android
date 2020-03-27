package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.bluetooth.BluetoothSendToCarService
import com.daimler.mbcarkit.business.SendToCarProvider
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarPoi
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

internal sealed class PoiSendMode {

    abstract fun sendPoi(): FutureTask<Unit, ResponseError<out RequestError>?>

    class Bluetooth(
        private val bluetoothSendToCarService: BluetoothSendToCarService,
        private val finOrVin: String,
        private val poi: SendToCarPoi,
        private val isBluetoothConnected: Boolean
    ) : PoiSendMode() {
        override fun sendPoi(): FutureTask<Unit, ResponseError<out RequestError>?> =
            bluetoothSendToCarService.sendPoi(finOrVin, poi.location, !isBluetoothConnected)
    }

    class Network(
        private val s2cProvider: SendToCarProvider,
        private val token: String,
        private val finOrVin: String,
        private val poi: SendToCarPoi
    ) : PoiSendMode() {
        override fun sendPoi(): FutureTask<Unit, ResponseError<out RequestError>?> =
            s2cProvider.sendPoi(token, finOrVin, poi)
    }
}
