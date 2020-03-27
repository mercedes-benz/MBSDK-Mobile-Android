package com.daimler.mbnetworkkit.socket.message

interface MessageProcessor {

    /**
     * This is called whenever there is an incoming message received on Socket. Be careful that this
     * message might be called on a worker thread and not on Main-Thread.
     */
    fun handleReceivedMessage(notifyable: Notifyable, sendService: SendMessageService, dataSocketMessage: DataSocketMessage)

    fun parseMessageToSend(message: SendableMessage): DataSocketMessage?
}
