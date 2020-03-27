package com.daimler.mbnetworkkit

import com.daimler.mbnetworkkit.socket.message.ChaineableMessageProcessor
import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.Notifyable
import com.daimler.mbnetworkkit.socket.message.ObserverManager
import com.daimler.mbnetworkkit.socket.message.SendMessageService
import com.daimler.mbnetworkkit.socket.message.SendableMessage
import org.junit.Before
import org.junit.Test

class ChainableMessageProcessorTest {

    var receivedMassage: DataSocketMessage? = null

    var sendMessage: DataSocketMessage? = null

    var continuedReceive = false

    var continuedSend = false

    @Before
    fun setup() {
        receivedMassage = null
        continuedReceive = false
        sendMessage = null
        continuedSend = false
    }

    @Test
    fun receiveMessageStopProcessing() {
        val messageProcessor = SingleChainedMessageProcessor(false, false)
        val message = DataSocketMessage.ByteSocketMessage(System.currentTimeMillis(), ByteArray(0))
        messageProcessor.handleReceivedMessage(ObserverManager(), TestSendMessageService(), message)
        assertReceivedMessageAndStopped(message)
    }

    @Test
    fun receiveMessageContinueProcessing() {
        val messageProceccor = SingleChainedMessageProcessor(continueReceived = true, parse = false)
        val message = DataSocketMessage.ByteSocketMessage(System.currentTimeMillis(), ByteArray(0))
        messageProceccor.handleReceivedMessage(ObserverManager(), TestSendMessageService(), message)
        assertReceivedMessageAndContinued(message)
    }

    @Test
    fun sendMessageStopProcessing() {
        val messageProceccor = SingleChainedMessageProcessor(continueReceived = true, parse = false)
        messageProceccor.parseMessageToSend(TestParcelableMessage(DataSocketMessage.StringSocketMessage(System.currentTimeMillis(), "TEST")))
        assert(continuedSend.not())
    }

    @Test
    fun sendMessageContinueProcessing() {
        val message = DataSocketMessage.ByteSocketMessage(System.currentTimeMillis(), ByteArray(0))
        val messageProceccor = SingleChainedMessageProcessor(continueReceived = true, parse = true)
        messageProceccor.parseMessageToSend(TestParcelableMessage(message))
        assertSendMessageAndContinued(message)
    }

    @Test
    fun chainedMessageProcessorReachedEnd() {
        val message = DataSocketMessage.ByteSocketMessage(System.currentTimeMillis(), ByteArray(0))
        val lastProcessor = SingleChainedMessageProcessor(continueReceived = true, parse = true)
        val firstProcessor = object : ChaineableMessageProcessor(lastProcessor) {
            override fun doHandleReceivedMessage(notifyable: Notifyable, sendService: SendMessageService, dataSocketMessage: DataSocketMessage): Boolean {
                return true
            }

            override fun doHandleMessageToSend(message: SendableMessage): DataSocketMessage? {
                return null
            }
        }
        firstProcessor.parseMessageToSend(TestParcelableMessage(message))
        assertSendMessageAndContinued(message)
    }

    private fun assertSendMessageAndContinued(expectedMessage: DataSocketMessage) {
        assert(expectedMessage == sendMessage)
        assert(continuedSend)
    }

    private fun assertReceivedMessageAndStopped(expectedMessage: DataSocketMessage) {
        assert(receivedMassage == expectedMessage)
        assert(continuedReceive.not())
    }

    private fun assertReceivedMessageAndContinued(expectedMessage: DataSocketMessage) {
        assert(receivedMassage == expectedMessage)
        assert(continuedReceive)
    }

    inner class TestParcelableMessage(val data: DataSocketMessage) : SendableMessage {

        override fun parse(): DataSocketMessage {
            return data
        }
    }

    inner class SingleChainedMessageProcessor(private val continueReceived: Boolean, private val parse: Boolean) : ChaineableMessageProcessor() {
        override fun doHandleReceivedMessage(notifyable: Notifyable, sendService: SendMessageService, dataSocketMessage: DataSocketMessage): Boolean {
            receivedMassage = dataSocketMessage
            continuedReceive = continueReceived
            return continuedReceive
        }

        override fun doHandleMessageToSend(message: SendableMessage): DataSocketMessage? {
            continuedSend = parse
            return if (parse) {
                sendMessage = message.parse()
                return sendMessage
            } else {
                null
            }
        }
    }

    inner class TestSendMessageService : SendMessageService {
        override fun sendMessage(message: SendableMessage): Boolean {
            TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
        }
    }
}
