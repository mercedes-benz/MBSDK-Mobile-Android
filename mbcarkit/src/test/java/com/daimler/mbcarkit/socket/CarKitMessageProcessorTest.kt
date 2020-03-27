package com.daimler.mbcarkit.socket

import com.daimler.mbcarkit.business.SelectedVehicleStorage
import com.daimler.mbcarkit.business.model.DebugMessage
import com.daimler.mbcarkit.business.model.command.CarVehicleApiCommand
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiError
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiService
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatus
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatusAcknowledgement
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatusUpdate
import com.daimler.mbcarkit.business.model.command.CommandVehicleApiStatusUpdates
import com.daimler.mbcarkit.business.model.command.DoorsUnlockError
import com.daimler.mbcarkit.business.model.command.GenericCommandError
import com.daimler.mbcarkit.business.model.command.InternalVehicleCommandError
import com.daimler.mbcarkit.business.model.command.VehicleCommand
import com.daimler.mbcarkit.business.model.command.VehicleCommandStatus
import com.daimler.mbcarkit.business.model.vehicle.PendingCommandRequestAcknowledgement
import com.daimler.mbcarkit.business.model.vehicle.VehicleAuthUpdateAcknowledgement
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatusByVinAcknowledgement
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatusUpdate
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatusUpdates
import com.daimler.mbcarkit.business.model.vehicle.VehicleUpdate
import com.daimler.mbcarkit.business.model.vehicle.VehicleUpdateAcknowledgement
import com.daimler.mbcarkit.socket.observable.VehicleObservableMessage
import com.daimler.mbcarkit.utils.createCommandStatus
import com.daimler.mbcarkit.utils.createCommandVehicleApiError
import com.daimler.mbnetworkkit.socket.message.Notifyable
import com.daimler.mbnetworkkit.socket.message.SendMessageService
import com.daimler.mbprotokit.CarMessageParser
import com.daimler.testutils.coroutines.CoroutineTest
import com.daimler.testutils.coroutines.TestCoroutineExtension
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(SoftAssertionsExtension::class, TestCoroutineExtension::class)
internal class CarKitMessageProcessorTest : CoroutineTest {

    private val vehicleCache = mockk<VehicleCache>(relaxUnitFun = true)
    private val vehicleStatusCache = mockk<VehicleStatusCache>(relaxUnitFun = true)
    private val messageParser = mockk<CarMessageParser>(relaxUnitFun = true)
    private val commandVehicleService = mockk<CommandVehicleApiService>(relaxUnitFun = true)
    private val pinStatusCallback = mockk<PinCommandVehicleApiStatusCallback>(relaxUnitFun = true)
    private val selectedVehicleStorage = mockk<SelectedVehicleStorage>(relaxUnitFun = true)
    private val notifyable = mockk<Notifyable>(relaxUnitFun = true)
    private val sendService = mockk<SendMessageService>(relaxUnitFun = true)

    private val vehicle = mockk<VehicleInfo>()
    private val vehicleStatus = VehicleStatus.initialState(FIN_OR_VIN)

    private lateinit var scope: CoroutineScope

    private lateinit var subject: CarKitMessageProcessor

    override fun attachScope(scope: CoroutineScope) {
        this.scope = scope
    }

    @BeforeEach
    fun setup() {
        every { selectedVehicleStorage.selectedFinOrVin() } returns FIN_OR_VIN

        every { commandVehicleService.containsAndRequiresPin(any()) } returns false
        every { commandVehicleService.getPendingCommands() } returns emptyList()

        // VehicleCache returns "vehicle" for FIN_OR_VIN, null otherwise.
        every { vehicleCache.loadVehicleByVin(any()) } returns null
        every { vehicleCache.loadVehicleByVin(FIN_OR_VIN) } returns vehicle

        // VehicleStatusCache returns "vehicleStatus" for FIN_OR_VIN, an empty vehicleStatus
        // otherwise.
        every { vehicleStatusCache.currentVehicleState(any()) } answers {
            VehicleStatus.initialState(firstArg())
        }
        every { vehicleStatusCache.currentVehicleState(FIN_OR_VIN) } returns vehicleStatus

        every { vehicleStatusCache.update(any(), any()) } answers {
            vehicleStatusCache.currentVehicleState(firstArg())
        }

        every { messageParser.parseReceivedMessage(any(), any()) } returns true

        every { sendService.sendMessage(any()) } returns true

        every { vehicle.finOrVin } returns FIN_OR_VIN

        subject = createSubject()
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @Test
    fun `doHandleReceivedMessage() should call messageParser`() {
        subject.doHandleReceivedMessage(mockk(), mockk(), mockk())
        verify {
            messageParser.parseReceivedMessage(any(), any())
        }
    }

    @Test
    fun `onVehicleStatusUpdate() should send ACK and notify for the selected vehicle`(scope: TestCoroutineScope) {
        val updateCorrectFin = mockk<VehicleStatusUpdate>().also {
            every { it.finOrVin } returns FIN_OR_VIN
        }
        val updateIncorrectFin = mockk<VehicleStatusUpdate>().also {
            every { it.finOrVin } returns ""
        }
        val updates = VehicleStatusUpdates(
            mapOf(
                updateCorrectFin.finOrVin to updateCorrectFin,
                updateIncorrectFin.finOrVin to updateIncorrectFin
            ),
            20
        )

        scope.runBlockingTest {
            prepareSocketMessage()
            subject.onVehicleStatusUpdate(updates)

            mockkConstructor(VehicleObservableMessage::class)
            every {
                anyConstructed<VehicleObservableMessage<Any>>().hasChanged(
                    any(),
                    any()
                )
            } returns true
            coVerify(exactly = 1) {
                sendService.sendMessage(any<VehicleStatusByVinAcknowledgement>())
                notifyable.notifyChange(VehicleStatus::class.java, vehicleStatus)
            }
        }
    }

    @Test
    fun `onDebugMessageReceived() should notify`(scope: TestCoroutineScope) {
        scope.runBlockingTest {
            prepareSocketMessage()
            val message = DebugMessage(100, "message")
            subject.onDebugMessageReceived(message)
            coVerify {
                notifyable.notifyChange(DebugMessage::class.java, message)
            }
        }
    }

    @Test
    fun `onVehicleUpdate() should send ACK and notify`(scope: TestCoroutineScope) {
        scope.runBlockingTest {
            prepareSocketMessage()
            val message = VehicleUpdate(100, 20)
            subject.onVehiclesUpdate(message)

            coVerify {
                sendService.sendMessage(any<VehicleUpdateAcknowledgement>())
                notifyable.notifyChange(VehicleUpdate::class.java, message)
            }
        }
    }

    @Test
    fun `onVehicleAuthUpdate() should send ACK and notify`(scope: TestCoroutineScope) {
        scope.runBlockingTest {
            prepareSocketMessage()
            val message = VehicleUpdate(100, 20)
            subject.onVehicleAuthUpdate(message)

            coVerify {
                sendService.sendMessage(any<VehicleAuthUpdateAcknowledgement>())
                notifyable.notifyChange(VehicleUpdate::class.java, message)
            }
        }
    }

    @Test
    fun `onCommandVehicleApiStatusUpdates() should send ACK and notify`(scope: TestCoroutineScope) {
        val commandStatus = prepareCommandStatus()
        val updates = CommandVehicleApiStatusUpdates(
            mapOf(
                FIN_OR_VIN to CommandVehicleApiStatusUpdate(
                    listOf(commandStatus),
                    0,
                    FIN_OR_VIN
                )
            )
        )
        scope.runBlockingTest {
            prepareSocketMessage()
            subject.onCommandVehicleApiStatusUpdates(updates)
            coVerify {
                pinStatusCallback.onPinAccepted(commandStatus, any())
                sendService.sendMessage(any<CommandVehicleApiStatusAcknowledgement>())
            }
        }
    }

    @Test
    fun `onPendingCommandRequest() should send ACK`(scope: TestCoroutineScope) {
        scope.runBlockingTest {
            prepareSocketMessage()
            subject.onPendingCommandRequest()

            coVerify {
                sendService.sendMessage(any<PendingCommandRequestAcknowledgement>())
            }
        }
    }

    @Test
    fun `should notify correct PinInvalid error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val request = spyk(VehicleCommand.DoorsUnlock(FIN_OR_VIN))

        val pinError = GenericCommandError.PinInvalid("", 5)
        val error = DoorsUnlockError.GenericError(pinError)
        every { request.convertToSpecificError(any()) } returns error

        val commandStatus = prepareCommandStatus(
            request = request,
            errors = listOf(createCommandVehicleApiError(InternalVehicleCommandError.PinInvalid.errorCodes.first())),
            commandState = VehicleCommandStatus.FAILED
        )
        val updates = CommandVehicleApiStatusUpdates(
            mapOf(
                FIN_OR_VIN to CommandVehicleApiStatusUpdate(
                    listOf(commandStatus),
                    0,
                    FIN_OR_VIN
                )
            )
        )
        scope.runBlockingTest {
            subject.onCommandVehicleApiStatusUpdates(updates)
            coVerify {
                pinStatusCallback.onPinInvalid(commandStatus, any(), pinError.attempt)
            }
        }
    }

    @Test
    fun `should notify correct UserBlocked error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val request = spyk(VehicleCommand.DoorsUnlock(FIN_OR_VIN))

        val blockedError = GenericCommandError.UserBlocked("", 5, 100)
        val error = DoorsUnlockError.GenericError(blockedError)
        every { request.convertToSpecificError(any()) } returns error

        val commandStatus = prepareCommandStatus(
            request = request,
            errors = listOf(createCommandVehicleApiError(InternalVehicleCommandError.UserBlocked.errorCodes.first())),
            commandState = VehicleCommandStatus.FAILED
        )

        val updates = CommandVehicleApiStatusUpdates(
            mapOf(
                FIN_OR_VIN to CommandVehicleApiStatusUpdate(
                    listOf(commandStatus),
                    0,
                    FIN_OR_VIN
                )
            )
        )
        scope.runBlockingTest {
            subject.onCommandVehicleApiStatusUpdates(updates)
            coVerify {
                pinStatusCallback.onUserBlocked(
                    commandStatus,
                    any(),
                    blockedError.attempt,
                    blockedError.blockedUntil
                )
            }
        }
    }

    @Test
    fun `should notify pin accepted if no error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val commandStatus = prepareCommandStatus()
        val updates = CommandVehicleApiStatusUpdates(
            mapOf(
                FIN_OR_VIN to CommandVehicleApiStatusUpdate(
                    listOf(commandStatus),
                    0,
                    FIN_OR_VIN
                )
            )
        )
        scope.runBlockingTest {
            subject.onCommandVehicleApiStatusUpdates(updates)
            verify {
                pinStatusCallback.onPinAccepted(commandStatus, any())
            }
        }
    }

    private fun prepareSocketMessage() {
        subject.doHandleReceivedMessage(notifyable, sendService, mockk())
    }

    private fun prepareCommandStatus(
        request: VehicleCommand<*> = spyk(VehicleCommand.DoorsUnlock(FIN_OR_VIN)),
        errors: List<CommandVehicleApiError> = emptyList(),
        commandState: VehicleCommandStatus = VehicleCommandStatus.FINISHED
    ): CommandVehicleApiStatus {
        val command = CarVehicleApiCommand(request)

        every { commandVehicleService.containsAndRequiresPin(any()) } returns true
        every { commandVehicleService.getCommandForRequestId(any()) } returns command

        return createCommandStatus(
            errors = errors,
            requestId = "id",
            commandState = commandState
        )
    }

    private fun createSubject() =
        CarKitMessageProcessor(
            vehicleCache,
            vehicleStatusCache,
            messageParser,
            commandVehicleService,
            pinStatusCallback,
            selectedVehicleStorage,
            null,
            scope
        )

    private companion object {

        private const val FIN_OR_VIN = "finOrVin"
    }
}
