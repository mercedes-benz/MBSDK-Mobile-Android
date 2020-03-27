package com.daimler.mbnetworkkit.socket

interface SocketConnectionListener {
    fun connectionStateChanged(connectionState: ConnectionState)
}
