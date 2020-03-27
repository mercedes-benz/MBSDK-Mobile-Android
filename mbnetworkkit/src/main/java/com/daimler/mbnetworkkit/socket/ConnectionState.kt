package com.daimler.mbnetworkkit.socket

import com.daimler.mbnetworkkit.socket.message.Observables
import com.daimler.mbnetworkkit.socket.reconnect.Reconnect

/**
 * This class is used for being passed back from SDK. This is required to not make methods from
 * [SocketEvent] accessible
 */
sealed class ConnectionState {
    object Closed : ConnectionState()
    object Disconnected : ConnectionState()
    object Connecting : ConnectionState()
    class Connected(val observables: Observables) : ConnectionState()
    data class ConnectionLost(val error: ConnectionError, val cause: String) : ConnectionState() {
        var reconnect: Reconnect = Reconnect()
            internal set
    }
}
