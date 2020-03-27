package com.daimler.mbnetworkkit.socket.message

interface SendMessageService {

    fun sendMessage(message: SendableMessage): Boolean
}
