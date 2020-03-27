package com.daimler.mbprotokit.messagehandler.handler.user

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.ReceivedUserMessageCallback
import com.daimler.mbprotokit.dto.user.UserPictureUpdate
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
internal class UserPictureUpdateHandlerTest {

    private val socketMessage = mockk<DataSocketMessage>()
    private val pushMessage = mockk<VehicleEvents.PushMessage>()
    private val userPictureUpdate = mockk<UserEvents.UserPictureUpdate>()
    private val callback = mockk<ReceivedUserMessageCallback>(relaxUnitFun = true)

    private lateinit var subject: UserPictureUpdateHandler

    @BeforeEach
    fun setup() {
        every { userPictureUpdate.sequenceNumber } returns SEQUENCE_NUMBER
        every { pushMessage.userPictureUpdate } returns userPictureUpdate

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
            callback.onUserPictureUpdated(UserPictureUpdate(SEQUENCE_NUMBER))
        }
    }

    @Test
    fun `should pass error and return true`(softly: SoftAssertions) {
        every { pushMessage.userPictureUpdate } returns null
        val result = subject.handle(socketMessage, pushMessage, callback)
        softly.assertThat(result).isTrue
        verify {
            callback.onUserMessageError(socketMessage, any())
        }
    }

    private fun createSubject() = UserPictureUpdateHandler()

    private companion object {

        private const val SEQUENCE_NUMBER = 1
    }
}
