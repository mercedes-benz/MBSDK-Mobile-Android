package com.daimler.mbprotokit

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage

interface ServiceMessageParser {

    fun parseReceivedMessage(socketMessage: DataSocketMessage, callback: ReceivedServiceMessageCallback): Boolean
}
