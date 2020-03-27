package com.daimler.mbnetworkkit.socket.reconnect

import com.daimler.mbnetworkkit.socket.ConnectionConfig

interface Reconnection {

    /**
     * Called directly before a new connection attempt will be started on [com.daimler.mbnetworkkit.socket.SocketService]
     */
    fun connectingStarted(connectionConfig: ConnectionConfig)

    /**
     * Should return true if there is currently a reconnect pending
     */
    fun isReconnecting(): Boolean

    /**
     * Called when connection has been established
     */
    fun reconnectSuccess()

    /**
     * Should start a reconnect immediatelly or after some time. To start the reconnect, just call
     * related method [ReconnectListener.onStartReconnect]
     */
    fun reconnect(reconnectListener: ReconnectListener): Reconnect

    /**
     * Called it connection was completelly disconnected
     */
    fun reset()
}
