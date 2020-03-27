package com.daimler.mbprotokit

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.messagehandler.handler.user.UserMessageHandlerFactory
import com.daimler.mbprotokit.messagehandler.handler.user.UserPushMessageHandler
import com.daimler.mbprotokit.send.user.UserDataUpdateAcknowledgement
import com.daimler.mbprotokit.send.user.UserPictureUpdateAcknowledgement
import com.daimler.mbprotokit.send.user.UserPinUpdateAcknowledgement
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.verify
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(SoftAssertionsExtension::class)
internal class UserMessageParserImplTest {

    private val byteSocketMessage = mockk<DataSocketMessage.ByteSocketMessage>()
    private val pushMessage = mockk<VehicleEvents.PushMessage>()
    private val handler = mockk<UserPushMessageHandler>(relaxUnitFun = true)
    private val callback = mockk<ReceivedUserMessageCallback>(relaxUnitFun = true)

    private lateinit var subject: UserMessageParserImpl

    @BeforeEach
    fun setup() {
        every { byteSocketMessage.bytes } returns ByteArray(1)

        mockkStatic(VehicleEvents.PushMessage::class)
        every { VehicleEvents.PushMessage.parseFrom(any<ByteArray>()) } returns pushMessage

        mockkObject(UserMessageHandlerFactory)
        every { UserMessageHandlerFactory.handlerForMessage(any()) } returns handler

        subject = createSubject()
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should return handler's result`(result: Boolean, softly: SoftAssertions) {
        every { handler.handle(any(), any(), any()) } returns result
        softly.assertThat(subject.parseReceivedMessage(byteSocketMessage, callback))
            .isEqualTo(result)
    }

    @Test
    fun `should return false if no handler available`(softly: SoftAssertions) {
        every { UserMessageHandlerFactory.handlerForMessage(any()) } returns null
        softly.assertThat(subject.parseReceivedMessage(byteSocketMessage, callback)).isFalse
    }

    @Test
    fun `should return false and notify error if exception is thrown`(softly: SoftAssertions) {
        every { handler.handle(any(), any(), any()) } throws RuntimeException()
        val result = subject.parseReceivedMessage(byteSocketMessage, callback)
        softly.assertThat(result).isFalse
        verify {
            callback.onUserMessageError(byteSocketMessage, any())
        }
    }

    @Test
    fun `should return false if DataSocketMessage is not a ByteSocketMessage`(softly: SoftAssertions) {
        val message = mockk<DataSocketMessage.StringSocketMessage>()
        softly.assertThat(subject.parseReceivedMessage(message, callback)).isFalse
    }

    @Test
    fun `parseUserUpdateAcknowledgement() should return correct instance`(softly: SoftAssertions) {
        softly.assertThat(subject.parseUserUpdateAcknowledgement(mockk()))
            .isInstanceOf(UserDataUpdateAcknowledgement::class.java)
    }

    @Test
    fun `parsePictureUpdateAcknowledgement() should return correct instance`(softly: SoftAssertions) {
        softly.assertThat(subject.parsePictureUpdateAcknowledgement(mockk()))
            .isInstanceOf(UserPictureUpdateAcknowledgement::class.java)
    }

    @Test
    fun `parsePinUpdateAcknowledgement() should return correct instance`(softly: SoftAssertions) {
        softly.assertThat(subject.parsePinUpdateAcknowledgement(mockk()))
            .isInstanceOf(UserPinUpdateAcknowledgement::class.java)
    }

    private fun createSubject() = UserMessageParserImpl()
}
