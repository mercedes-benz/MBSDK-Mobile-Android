package com.daimler.mbnetworkkit.socket

data class ConnectionConfig(
    val token: String,
    val sessionId: String,
    val messageType: MessageType
)
