package com.daimler.mbcarkit.socket

import android.os.Handler
import com.daimler.mbcarkit.business.SelectedVehicleStorage
import com.daimler.mbcarkit.business.model.services.ServicesActivationAcknowledgement
import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.SendMessageService
import com.daimler.mbnetworkkit.socket.message.SendableMessage
import com.daimler.mbprotokit.ServiceKitMessageParser
import com.daimler.mbprotokit.generated.ServiceActivation
import com.daimler.mbprotokit.generated.VehicleEvents
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ServiceMessageProcessorTest {

    lateinit var subject: ServiceMessageProcessor

    @MockK
    lateinit var handler: Handler

    @MockK
    lateinit var selectedVehicleStorage: SelectedVehicleStorage

    @MockK
    lateinit var sendService: SendMessageService

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { handler.post(any()) } answers {
            firstArg<Runnable>().run()
            true
        }
        every { selectedVehicleStorage.selectedFinOrVin() } returns VIN

        subject = ServiceMessageProcessor(
            ServiceKitMessageParser(),
            selectedVehicleStorage,
            null,
            handler
        )
    }

    @Test
    fun testParseServiceStatusUpdate() {
        val serviceStatusUpdates = ServiceActivation.ServiceStatusUpdatesByVIN.newBuilder()
            .setSequenceNumber(SEQUENCE_NUMBER)
            .putUpdates(
                VIN,
                ServiceActivation.ServiceStatusUpdate.newBuilder()
                    .putAllUpdates(mapOf(SERVICE_ID to ServiceActivation.ServiceStatus.SERVICE_STATUS_ACTIVE))
                    .build()
            )
        val sendableMessages = ArrayList<SendableMessage>()
        every { sendService.sendMessage(capture(sendableMessages)) } returns true
        val message = VehicleEvents.PushMessage.newBuilder()
            .setServiceStatusUpdates(serviceStatusUpdates)
            .build()
        val byteSocketMessage =
            DataSocketMessage.ByteSocketMessage(TIMESTAMP, message.toByteArray())

        subject.doHandleReceivedMessage(mockk(relaxed = true), sendService, byteSocketMessage)

        verify { sendService.sendMessage(any()) }
        Assertions.assertEquals(1, sendableMessages.size)
        Assertions.assertEquals(
            SEQUENCE_NUMBER,
            (sendableMessages[0] as ServicesActivationAcknowledgement).sequenceNumber
        )
    }

    companion object {
        private const val VIN = "DUMMYVIN"
        private const val SEQUENCE_NUMBER = 4
        private const val SERVICE_ID = 564
        private const val TIMESTAMP = 0L
    }
}
