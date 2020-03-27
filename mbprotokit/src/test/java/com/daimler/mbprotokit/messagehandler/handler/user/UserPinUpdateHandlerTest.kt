package com.daimler.mbprotokit.messagehandler.handler.user

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.ReceivedUserMessageCallback
import com.daimler.mbprotokit.dto.user.UserPinUpdate
import com.daimler.mbprotokit.generated.UserEvents
import com.daimler.mbprotokit.generated.VehicleEvents
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class UserPinUpdateHandlerTest {

    private val socketMessage = mockk<DataSocketMessage>()
    private val pushMessage = mockk<VehicleEvents.PushMessage>()
    private val userPinUpdate = mockk<UserEvents.UserPINUpdate>()
    private val callback = mockk<ReceivedUserMessageCallback>(relaxUnitFun = true)

    private lateinit var subject: UserPinUpdateHandler

    @BeforeEach
    fun setup() {
        every { userPinUpdate.sequenceNumber } returns SEQUENCE_NUMBER
        every { pushMessage.userPinUpdate } returns userPinUpdate

        subject = createSubject()
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @Test
    fun `should pass transformed model and return true`(softly: SoftAssertions) {
        val result = subject.handle(socketMessage, pushMessage, callback)
        softly.assertThat(result).isTrue
        verify {
            callback.onUserPinUpdated(UserPinUpdate(SEQUENCE_NUMBER))
        }
    }

    @Test
    fun `should pass error and return true`(softly: SoftAssertions) {
        every { pushMessage.userPinUpdate } returns null
        val result = subject.handle(socketMessage, pushMessage, callback)
        softly.assertThat(result).isTrue
        verify {
            callback.onUserMessageError(socketMessage, any())
        }
    }

    private fun createSubject() = UserPinUpdateHandler()

    private companion object {

        private const val SEQUENCE_NUMBER = 1
    }
}
