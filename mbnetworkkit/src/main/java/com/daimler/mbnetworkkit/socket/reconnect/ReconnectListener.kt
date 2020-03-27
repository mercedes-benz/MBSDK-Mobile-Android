package com.daimler.mbnetworkkit.socket.reconnect

import com.daimler.mbnetworkkit.socket.ConnectionConfig

interface ReconnectListener {
    fun onStartReconnect(connectionConfig: ConnectionConfig)
    fun onReconnectCancelled()
}
