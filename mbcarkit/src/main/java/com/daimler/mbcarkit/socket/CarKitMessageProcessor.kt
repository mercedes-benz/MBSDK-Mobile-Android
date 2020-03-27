package com.daimler.mbcarkit.socket

import com.daimler.mbcarkit.business.SelectedVehicleStorage
import com.daimler.mbcarkit.business.model.DebugMessage
import com.daimler.mbcarkit.business.model.command.CarVehicleApiCommand
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiService
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatus
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatusAcknowledgement
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatusUpdate
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatusUpdates
import com.daimler.mbcarkit.business.model.command.GenericCommandError
import com.daimler.mbcarkit.business.model.command.VehicleCommandStatus
import com.daimler.mbcarkit.business.model.vehicle.PendingCommand
import com.daimler.mbcarkit.business.model.vehicle.PendingCommandRequestAcknowledgement
import com.daimler.mbcarkit.business.model.vehicle.VehicleAuthUpdateAcknowledgement
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatusByVinAcknowledgement
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatusUpdate
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatusUpdates
import com.daimler.mbcarkit.business.model.vehicle.VehicleUpdate
import com.daimler.mbcarkit.business.model.vehicle.VehicleUpdateAcknowledgement
import com.daimler.mbcarkit.business.model.vehicle.VehicleUpdateObservableMessage
import com.daimler.mbcarkit.proto.tmp.car.ReceivedCarMessageCallbackMapper
import com.daimler.mbcarkit.util.containsPinInvalidError
import com.daimler.mbcarkit.util.containsUserBlockedError
import com.daimler.mbcarkit.util.findGenericError
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.socket.message.BaseChainableMessageProcessor
import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.MessageProcessor
import com.daimler.mbnetworkkit.socket.message.Notifyable
import com.daimler.mbnetworkkit.socket.message.SendMessageService
import com.daimler.mbnetworkkit.socket.message.SendableMessage
import com.daimler.mbnetworkkit.socket.message.notifyChange
import com.daimler.mbprotokit.CarMessageParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Date

internal class CarKitMessageProcessor(
    private val vehicleCache: VehicleCache,
    private val vehicleStatusCache: VehicleStatusCache,
    private val messageParser: CarMessageParser,
    private val commandVehicleApiService: CommandVehicleApiService,
    private val pinVehicleApiStatusCallback: PinCommandVehicleApiStatusCallback,
    private val selectedVehicleStorage: SelectedVehicleStorage,
    nextProcessor: MessageProcessor? = null,
    private val scope: CoroutineScope = SocketProcessingCoroutineScope()
) : BaseChainableMessageProcessor(nextProcessor), ReceivedCarMessageCallback {

    private var notifyable: Notifyable? = null

    private var sendService: SendMessageService? = null

    // Temporary because of old dtos and interfaces
    private val callbackMapper = ReceivedCarMessageCallbackMapper(this)

    // region BaseChainableMessageProcessor

    override fun doHandleReceivedMessage(
        notifyable: Notifyable,
        sendService: SendMessageService,
        dataSocketMessage: DataSocketMessage
    ): Boolean {
        this.notifyable = notifyable
        this.sendService = sendService
        MBLoggerKit.d("Received message on Socket: $dataSocketMessage")
        return messageParser.parseReceivedMessage(dataSocketMessage, callbackMapper)
    }

    // endregion

    // region ReceivedCarMessageCallback -> The called methods might be still on WorkerThread -> post on Main to ensure not thread issues

    override fun onError(socketMessage: DataSocketMessage, cause: String) {
        MBLoggerKit.e("Error while parsing received Message: $cause")
    }

    override fun onVehicleStatusUpdate(vehicleUpdates: VehicleStatusUpdates) {
        dispatchAction { processVehicleStatusUpdate(vehicleUpdates) }
    }

    override fun onDebugMessageReceived(debugMessage: DebugMessage) {
        MBLoggerKit.d("Parsed $debugMessage")
        dispatchAction { notifyable?.notifyChange(debugMessage) }
    }

    override fun onVehiclesUpdate(vehicleUpdate: VehicleUpdate) {
        MBLoggerKit.d("Parsed VehicleUpdate: $vehicleUpdate.")
        dispatchAction { processVehicleUpdate(vehicleUpdate) }
    }

    override fun onVehicleAuthUpdate(vehicleUpdate: VehicleUpdate) {
        MBLoggerKit.d("Parsed auth update: $vehicleUpdate")
        dispatchAction { processVehicleAuthUpdate(vehicleUpdate) }
    }

    override fun onCommandVehicleApiStatusUpdates(commandStatusUpdates: CommandVehicleApiStatusUpdates) {
        MBLoggerKit.d("Parsed CommandVehicleApiStatusUpdatse: ${commandStatusUpdates.commandsByVin}")
        dispatchAction { processCommandVehicleApiStatusUpdates(commandStatusUpdates) }
    }

    override fun onPendingCommandRequest() {
        MBLoggerKit.d("Parsed pending command request")
        dispatchAction { processPendingCommandRequest() }
    }

    // endregion

    private fun dispatchAction(action: () -> Unit) {
        scope.launch { action() }
    }

    private fun processVehicleStatusUpdate(vehicleUpdates: VehicleStatusUpdates) {
        val selectedVehicle = selectedVehicleStorage.selectedFinOrVin()?.let {
            vehicleCache.loadVehicleByVin(it)
        }
        val selectedVehicleStatus = selectedVehicle?.let {
            vehicleStatusCache.currentVehicleState(it.finOrVin)
        }
        sendVehicleUpdatesByVinAckMessage(vehicleUpdates)
        vehicleUpdates.vehiclesByVin.forEach { entry ->
            val updatedVehicleStatus = updateCache(entry.key, entry.value)
            updatedVehicleStatus?.let {
                it.sequenceNumber = vehicleUpdates.sequenceNumber
                notifyObserverIfSameVehicle(selectedVehicle, selectedVehicleStatus, it)
            }
        }
    }

    private fun processCommandVehicleApiStatusUpdates(commandStatusUpdates: CommandVehicleApiStatusUpdates) {
        commandStatusUpdates.commandsByVin.forEach {
            it.value.commandVehicleApiStatusModels.forEach { commandStatus ->
                commandStatus.requestId?.let { rid ->
                    if (rid.isNotEmpty()) {
                        commandVehicleApiService.correlateRequest(rid, commandStatus.pid)
                    }
                }
                notifyPinCallbackIfRequired(commandStatus)
                commandVehicleApiService.commandUpdate(commandStatus)
            }
            sendCommandVehicleApiStatusAckMessage(it.value)
        }
    }

    private fun processPendingCommandRequest() {
        mutableListOf<PendingCommand>().apply {
            addAll(
                commandVehicleApiService.getPendingCommands().map {
                    PendingCommand(it.vin(), it.processId().orEmpty(), it.requestId(), it.type())
                }
            )
            sendPendingCommandRequestAckMessage(this)
        }
    }

    private fun processVehicleUpdate(vehicleUpdate: VehicleUpdate) {
        notifyVehicleUpdate(vehicleUpdate)
        sendVehicleUpdateAckMessage(vehicleUpdate)
    }

    private fun processVehicleAuthUpdate(vehicleUpdate: VehicleUpdate) {
        notifyVehicleUpdate(vehicleUpdate)
        sendVehicleAuthUpdateAckMessage(vehicleUpdate)
    }

    private fun notifyPinCallbackIfRequired(commandStatus: CommandVehicleApiStatus) {
        if (!commandStatus.containedAndRequiresPin() || !commandStatus.commandState.isDone()) {
            return
        }

        val command =
            commandStatus.requestId?.let {
                commandVehicleApiService.getCommandForRequestId(it)
            } ?: return

        when {
            commandStatus.containsUserBlockedError() -> notifyUserBlockedError(
                commandStatus,
                command
            )
            commandStatus.containsPinInvalidError() -> notifyPinInvalidError(commandStatus, command)
            commandStatus.errors.isEmpty() -> notifyPinAccepted(commandStatus, command)
            else -> Unit // This should never happen
        }
    }

    private fun notifyUserBlockedError(
        commandStatus: CommandVehicleApiStatus,
        command: CarVehicleApiCommand<*>
    ) {
        commandStatus.findGenericError<GenericCommandError.UserBlocked>(command.commandRequest())
            ?.let {
                pinVehicleApiStatusCallback.onUserBlocked(
                    commandStatus,
                    command.pin.orEmpty(),
                    it.attempt,
                    it.blockedUntil
                )
            }
    }

    private fun notifyPinInvalidError(
        commandStatus: CommandVehicleApiStatus,
        command: CarVehicleApiCommand<*>
    ) {
        val error =
            commandStatus.findGenericError<GenericCommandError.PinInvalid>(command.commandRequest())
        pinVehicleApiStatusCallback.onPinInvalid(
            commandStatus,
            command.pin.orEmpty(),
            error?.attempt ?: 1
        )
    }

    private fun notifyPinAccepted(
        commandStatus: CommandVehicleApiStatus,
        command: CarVehicleApiCommand<*>
    ) {
        pinVehicleApiStatusCallback.onPinAccepted(
            commandStatus,
            command.pin.orEmpty()
        )
    }

    private fun updateCache(
        finOrVin: String,
        vehicleStatusUpdate: VehicleStatusUpdate
    ): VehicleStatus? {
        val vehicleStatus = vehicleStatusCache.update(finOrVin, vehicleStatusUpdate)
        MBLoggerKit.d("Updated cache for vin ${vehicleStatus.finOrVin} | ${Date(vehicleStatus.timestamp)}")
        return vehicleStatus
    }

    private fun sendVehicleUpdatesByVinAckMessage(vehicleStatusByVinUpdate: VehicleStatusUpdates) {
        sendAcknowledgement(
            VehicleStatusByVinAcknowledgement(vehicleStatusByVinUpdate.sequenceNumber)
        )
    }

    private fun sendCommandVehicleApiStatusAckMessage(commandStatusUpdate: CommandVehicleApiStatusUpdate) {
        sendAcknowledgement(
            CommandVehicleApiStatusAcknowledgement(commandStatusUpdate.sequenceNumber)
        )
    }

    private fun sendVehicleUpdateAckMessage(vehicleUpdate: VehicleUpdate) {
        sendAcknowledgement(
            VehicleUpdateAcknowledgement(vehicleUpdate.sequenceNumber)
        )
    }

    private fun sendVehicleAuthUpdateAckMessage(vehicleUpdate: VehicleUpdate) {
        sendAcknowledgement(
            VehicleAuthUpdateAcknowledgement(vehicleUpdate.sequenceNumber)
        )
    }

    private fun sendPendingCommandRequestAckMessage(pendingCommands: List<PendingCommand>) {
        sendAcknowledgement(
            PendingCommandRequestAcknowledgement(pendingCommands)
        )
    }

    private fun sendAcknowledgement(message: SendableMessage) {
        sendService?.let {
            if (it.sendMessage(message)) {
                MBLoggerKit.d("ACK - Sent acknowledgement: $message")
            } else {
                MBLoggerKit.e("ACK - Failed to send acknowledgement: $message")
            }
        }
            ?: MBLoggerKit.e("ACK - Could not send acknowledgement since no SendService is available.")
    }

    // region notify vehicle updates

    private fun notifyVehicleUpdate(vehicleUpdate: VehicleUpdate) {
        dispatchAction {
            notifyable?.notifyChange(
                VehicleUpdateObservableMessage(vehicleUpdate).data
            )
        }
    }

    private fun notifyObserverIfSameVehicle(
        selectedVehicle: VehicleInfo?,
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        selectedVehicle?.let { vehicle ->
            if (vehicle.finOrVin == updatedVehicleStatus.finOrVin) {
                MBLoggerKit.d("Notify observers for selected vehicle ${selectedVehicle.finOrVin}")
                dispatchAction {
                    notifyable?.let {
                        CarKitMessageNotificator(it).notifyVehicleRelatedObservers(
                            oldVehicleStatus,
                            updatedVehicleStatus
                        )
                    }
                }
            } else {
                MBLoggerKit.d("Skipped notification of VehicleStatus because vin ${updatedVehicleStatus.finOrVin} is not selected vin ${selectedVehicle.finOrVin}")
            }
        }
    }

    private fun VehicleCommandStatus.isDone() =
        this == VehicleCommandStatus.FINISHED || this == VehicleCommandStatus.FAILED

    private fun CommandVehicleApiStatus.containedAndRequiresPin() =
        commandVehicleApiService.containsAndRequiresPin(this)
}
