package com.daimler.mbcarkit.socket

import android.os.Handler
import android.os.Looper
import com.daimler.mbcarkit.business.SelectedVehicleStorage
import com.daimler.mbcarkit.business.model.services.ServiceActivationStatusUpdateWrapper
import com.daimler.mbcarkit.business.model.services.ServiceActivationStatusUpdates
import com.daimler.mbcarkit.business.model.services.ServicesActivationAcknowledgement
import com.daimler.mbcarkit.proto.tmp.service.ReceivedServiceMessageCallbackMapper
import com.daimler.mbcarkit.socket.observable.ServiceActivationObservableMessage
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.socket.message.BaseChainableMessageProcessor
import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.MessageProcessor
import com.daimler.mbnetworkkit.socket.message.Notifyable
import com.daimler.mbnetworkkit.socket.message.SendMessageService
import com.daimler.mbprotokit.ServiceMessageParser

class ServiceMessageProcessor(
    private val messageParser: ServiceMessageParser,
    private val selectedVehicleStorage: SelectedVehicleStorage,
    nextProcessor: MessageProcessor? = null,
    private val handler: Handler = Handler(Looper.getMainLooper())
) : BaseChainableMessageProcessor(nextProcessor), ReceivedServiceMessageCallback {

    private var notifyable: Notifyable? = null
    private var sendService: SendMessageService? = null
    private val callbackMapper = ReceivedServiceMessageCallbackMapper(this)

    override fun doHandleReceivedMessage(
        notifyable: Notifyable,
        sendService: SendMessageService,
        dataSocketMessage: DataSocketMessage
    ): Boolean {
        this.notifyable = notifyable
        this.sendService = sendService
        MBLoggerKit.d("Received message: $dataSocketMessage")
        return messageParser.parseReceivedMessage(dataSocketMessage, callbackMapper)
    }

    override fun onServiceStatusUpdate(statusUpdates: ServiceActivationStatusUpdates) {
        MBLoggerKit.d("Parsed ServiceActivationStatusUpdates: $statusUpdates.")
        handler.post { processServiceStatusUpdate(statusUpdates) }
    }

    override fun onError(socketMessage: DataSocketMessage, cause: String) {
        MBLoggerKit.e("Error while parsing received message: $cause.")
    }

    private fun processServiceStatusUpdate(updates: ServiceActivationStatusUpdates) {
        val selectedFinOrVin = selectedVehicleStorage.selectedFinOrVin()
        // send ACK before doing anything
        sendAckMessage(updates)
        // notify observers
        updates.updatesByVin.forEach {
            val data = ServiceActivationStatusUpdateWrapper(
                it.value.finOrVin == selectedFinOrVin,
                it.value
            )
            notifyable?.notifyChange(
                ServiceActivationStatusUpdateWrapper::class.java,
                ServiceActivationObservableMessage(data).data
            )
        }
    }

    private fun sendAckMessage(update: ServiceActivationStatusUpdates) {
        val message = ServicesActivationAcknowledgement(update.sequenceNumber)
        val success = sendService?.sendMessage(message)
        MBLoggerKit.d("Send ServiceActivationUpdate ACK: $message was successful: $success.")
    }
}
