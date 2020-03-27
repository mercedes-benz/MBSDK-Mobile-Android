package com.daimler.mbnetworkkit.socket

import com.daimler.mbnetworkkit.socket.message.Observables

interface SocketEvent {
    fun connect(config: ConnectionConfig, connectionListener: SocketConnectionListener?, socketConnection: SocketConnection)
    fun open(observables: Observables, socketConnection: SocketConnection)
    fun close(socketConnection: SocketConnection)
    fun disconnect(socketConnection: SocketConnection)
    fun error(error: ConnectionError, cause: String, socketConnection: SocketConnection)
    fun connectionState(): ConnectionState
}
