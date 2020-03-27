package com.daimler.mbnetworkkit.socket.message

interface SendableMessage {

    fun parse(): DataSocketMessage
}
