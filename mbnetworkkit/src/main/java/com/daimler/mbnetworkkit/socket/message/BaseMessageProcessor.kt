package com.daimler.mbnetworkkit.socket.message

/**
 * Implementation of [MessageProcessor] which already tries to parse the message by delegating it to
 * [SendableMessage.parse]
 */
abstract class BaseMessageProcessor : MessageProcessor {
    final override fun parseMessageToSend(message: SendableMessage): DataSocketMessage? {
        return message.parse()
    }
}
