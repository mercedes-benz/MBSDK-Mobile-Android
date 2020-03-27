package com.daimler.mbprotokit.messagehandler.handler.user

import com.daimler.mbprotokit.generated.VehicleEvents
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

@ExtendWith(SoftAssertionsExtension::class)
internal class UserMessageHandlerFactoryTest {

    private val message = mockk<VehicleEvents.PushMessage>()

    private lateinit var subject: UserMessageHandlerFactory

    @BeforeEach
    fun setup() {
        subject = createSubject()
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @ParameterizedTest
    @EnumSource(VehicleEvents.PushMessage.MsgCase::class)
    fun `should return correct handler instance`(msgCase: VehicleEvents.PushMessage.MsgCase, softly: SoftAssertions) {
        val expected = when (msgCase) {
            VehicleEvents.PushMessage.MsgCase.USER_DATA_UPDATE -> UserDataUpdateHandler::class.java
            VehicleEvents.PushMessage.MsgCase.USER_PICTURE_UPDATE -> UserPictureUpdateHandler::class.java
            VehicleEvents.PushMessage.MsgCase.USER_PIN_UPDATE -> UserPinUpdateHandler::class.java
            else -> null
        }
        every { message.msgCase } returns msgCase
        val handler = subject.handlerForMessage(message)
        softly.assertThat(handler?.javaClass).isEqualTo(expected)
    }

    private fun createSubject() = UserMessageHandlerFactory
}
