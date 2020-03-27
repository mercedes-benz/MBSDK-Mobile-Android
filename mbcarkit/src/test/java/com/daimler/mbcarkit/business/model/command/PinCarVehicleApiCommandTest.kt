package com.daimler.mbcarkit.business.model.command

import com.daimler.mbcarkit.business.PinProvider
import com.daimler.mbcarkit.implementation.PinCarVehicleApiCommandManager
import com.daimler.mbnetworkkit.socket.ConnectionState
import com.daimler.mbnetworkkit.socket.SocketService
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

class PinCarVehicleApiCommandTest {

    lateinit var pinCarCommandManager: PinCarVehicleApiCommandManager

    @RelaxedMockK
    lateinit var commandServiceMock: CommandVehicleApiService
    @RelaxedMockK
    lateinit var pinProviderMock: PinProvider

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        pinCarCommandManager = PinCarVehicleApiCommandManager(commandServiceMock, pinProviderMock)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun test_sendCommand_startAuxHeat_shouldNotRequestPin() {
        mockkObject(SocketService)
        every { SocketService.connectionState() } returns ConnectionState.Disconnected

        pinCarCommandManager.sendCommand(
            VehicleCommand.AuxHeatStart(""),
            object : VehicleCommandCallback<AuxHeatStartError> {
                override fun onSuccess(timestamp: Long?) {
                }

                override fun onUpdate(status: VehicleCommandStatusUpdate) {
                }

                override fun onError(timestamp: Long?, errors: List<AuxHeatStartError>) {
                }
            }
        )

        verify(exactly = 0) { pinProviderMock.requestPin(any()) }
    }

    @Test
    fun test_sendCommand_unlockDoor_shouldRequestPin() {
        pinCarCommandManager.sendCommand(
            VehicleCommand.DoorsUnlock(""),
            object : VehicleCommandCallback<DoorsUnlockError> {
                override fun onSuccess(timestamp: Long?) {
                }

                override fun onUpdate(status: VehicleCommandStatusUpdate) {
                }

                override fun onError(timestamp: Long?, errors: List<DoorsUnlockError>) {
                }
            }
        )

        verify { pinProviderMock.requestPin(any()) }
    }
}
