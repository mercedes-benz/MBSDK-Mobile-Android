package com.daimler.mbnetworkkit.socket.message

/**
 * Based on Chain of Responsability, this implementation can handle calls or simply passes them to the
 * next instance. If there is no next instance configurated, the processing will stop.
 * Incoming messages will be processed in [doHandleReceivedMessage]. If this returnes true, the
 * processing will stop. If false, the message will be passed to next [MessageProcessor] if set.
 * For messages to send, the processor will stop if the parsed message is not null.
 */
abstract class ChaineableMessageProcessor(private val nextProcessor: MessageProcessor? = null) : MessageProcessor {

    final override fun handleReceivedMessage(notifyable: Notifyable, sendService: SendMessageService, dataSocketMessage: DataSocketMessage) {
        if (doHandleReceivedMessage(notifyable, sendService, dataSocketMessage).not()) {
            nextProcessor?.handleReceivedMessage(notifyable, sendService, dataSocketMessage)
        }
    }

    final override fun parseMessageToSend(message: SendableMessage): DataSocketMessage? {
        return doHandleMessageToSend(message) ?: nextProcessor?.parseMessageToSend(message)
    }

    /**
     * This is called if there was a received incoming message. If true was returned, the processing
     * of the related message will stop here. If false, the message will be passed to the next
     * configured [MessageProcessor]
     */
    abstract fun doHandleReceivedMessage(notifyable: Notifyable, sendService: SendMessageService, dataSocketMessage: DataSocketMessage): Boolean

    /**
     * This is called if there is a message that should be parsed to send. If null was returned, the
     * message will be passed to the next configured [MessageProcessor]. Else, the processing will stop
     * and the result is returned
     */
    abstract fun doHandleMessageToSend(message: SendableMessage): DataSocketMessage?
}
