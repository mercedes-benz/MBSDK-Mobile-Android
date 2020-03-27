package com.daimler.mbnetworkkit.socket.message

/**
 * Implementation of [ChaineableMessageProcessor] which already trys to parse the message to send by
 * delegating to [SendableMessage.parse]
 */
abstract class BaseChainableMessageProcessor(nextProcessor: MessageProcessor? = null) : ChaineableMessageProcessor(nextProcessor) {
    final override fun doHandleMessageToSend(message: SendableMessage): DataSocketMessage? {
        return message.parse()
    }
}
