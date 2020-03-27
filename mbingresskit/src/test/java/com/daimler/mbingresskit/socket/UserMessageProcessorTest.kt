package com.daimler.mbingresskit.socket

import com.daimler.mbingresskit.socket.observable.ProfileDataUpdate
import com.daimler.mbingresskit.socket.observable.ProfilePictureUpdate
import com.daimler.mbingresskit.socket.observable.ProfilePinUpdate
import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.MessageProcessor
import com.daimler.mbnetworkkit.socket.message.Notifyable
import com.daimler.mbnetworkkit.socket.message.SendMessageService
import com.daimler.mbnetworkkit.socket.message.SendableMessage
import com.daimler.mbprotokit.ReceivedUserMessageCallback
import com.daimler.mbprotokit.UserMessageParser
import com.daimler.mbprotokit.dto.user.UserDataUpdate
import com.daimler.mbprotokit.dto.user.UserPictureUpdate
import com.daimler.mbprotokit.dto.user.UserPinUpdate
import com.daimler.testutils.coroutines.CoroutineTest
import com.daimler.testutils.coroutines.TestCoroutineExtension
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
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
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExperimentalCoroutinesApi
@ExtendWith(SoftAssertionsExtension::class, TestCoroutineExtension::class)
internal class UserMessageProcessorTest : CoroutineTest {

    private val parser = mockk<UserMessageParser>()
    private val notifyable = mockk<Notifyable>(relaxUnitFun = true)
    private val sendService = mockk<SendMessageService>()
    private val nextProcessor = mockk<MessageProcessor>(relaxed = true)
    private val dataMessage = mockk<DataSocketMessage>()
    private val userUpdateAck = mockk<SendableMessage>()
    private val userPictureAck = mockk<SendableMessage>()
    private val userPinAck = mockk<SendableMessage>()

    private lateinit var scope: CoroutineScope

    private lateinit var subject: UserMessageProcessor

    override fun attachScope(scope: CoroutineScope) {
        this.scope = scope
    }

    @BeforeEach
    fun setup() {
        every { sendService.sendMessage(any()) } returns true

        parser.apply {
            every { parseReceivedMessage(any(), any()) } returns true
            every { parseUserUpdateAcknowledgement(any()) } returns userUpdateAck
            every { parsePictureUpdateAcknowledgement(any()) } returns userPictureAck
            every { parsePinUpdateAcknowledgement(any()) } returns userPinAck
        }

        subject = createSubject()
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `doHandleReceivedMessage() should return parser's result`(
        result: Boolean,
        softly: SoftAssertions
    ) {
        every { parser.parseReceivedMessage(any(), any()) } returns result
        softly.assertThat(subject.doHandleReceivedMessage(notifyable, sendService, dataMessage))
            .isEqualTo(result)
    }

    @Test
    fun `nextProcessor should be called if doHandleReceivedMessage() returns false`(
        softly: SoftAssertions
    ) {
        every { parser.parseReceivedMessage(any(), any()) } returns false
        subject.handleReceivedMessage(notifyable, sendService, dataMessage)
        verify {
            nextProcessor.handleReceivedMessage(notifyable, sendService, dataMessage)
        }
    }

    @Test
    fun `should send ACK and notify transformed result for UserDataUpdate`(scope: TestCoroutineScope) {
        simpleSocketMessageTest(
            userUpdateAck,
            ProfileDataUpdate::class.java,
            ProfileDataUpdate(SEQUENCE_NUMBER),
            scope
        ) {
            onUserDataUpdated(UserDataUpdate(SEQUENCE_NUMBER))
        }
    }

    @Test
    fun `should send ACK and notify transformed result for UserPictureUpdate`(scope: TestCoroutineScope) {
        simpleSocketMessageTest(
            userPictureAck,
            ProfilePictureUpdate::class.java,
            ProfilePictureUpdate(SEQUENCE_NUMBER),
            scope
        ) {
            onUserPictureUpdated(UserPictureUpdate(SEQUENCE_NUMBER))
        }
    }

    @Test
    fun `should send ACK and notify transformed result for UserPinUpdate`(scope: TestCoroutineScope) {
        simpleSocketMessageTest(
            userPinAck,
            ProfilePinUpdate::class.java,
            ProfilePinUpdate(SEQUENCE_NUMBER),
            scope
        ) {
            onUserPinUpdated(UserPinUpdate(SEQUENCE_NUMBER))
        }
    }

    private fun <T> simpleSocketMessageTest(
        ackMessage: SendableMessage,
        notifyType: Class<T>,
        notifyData: T,
        scope: TestCoroutineScope,
        callbackAnswer: ReceivedUserMessageCallback.() -> Unit
    ) {
        every { parser.parseReceivedMessage(any(), any()) } answers {
            secondArg<ReceivedUserMessageCallback>().callbackAnswer()
            true
        }
        scope.runBlockingTest {
            subject.doHandleReceivedMessage(notifyable, sendService, dataMessage)
            coVerify {
                sendService.sendMessage(ackMessage)
                notifyable.notifyChange(
                    notifyType,
                    notifyData
                )
            }
        }
    }

    private fun createSubject() =
        UserMessageProcessor(parser, nextProcessor, scope)

    private companion object {

        private const val SEQUENCE_NUMBER = 1
    }
}
