package com.daimler.mbnetworkkit.socket

import com.daimler.mbnetworkkit.SocketServiceConfig
import com.daimler.mbnetworkkit.socket.message.SendableMessage

object SocketService {

    private lateinit var socketConnection: SocketConnection

    private lateinit var sessionId: String

    fun init(serviceConfig: SocketServiceConfig) {
        sessionId = serviceConfig.sessionId
        socketConnection = serviceConfig.socketConnection
    }

    /**
     * Establishes a connection to the socket if the current [ConnectionState] is in [ConnectionState.ConnectionLost]
     * or [ConnectionState.Disconnected].<br>
     *
     * If current connection is already in state [ConnectionState.Connecting] it will directly call the
     * passed [SocketConnectionListener]. If the connection has already been established successfully,
     * it will also directly call the passed listener.<br>
     *
     * This method can be called multiple times e.g. if the state of connection should be observed in
     * different views or screens.
     *
     * @param [jwtToken]
     *                  The Token which is required to establish a socket connection to remote server.
     *                  If an invalid or outdated Token was passed, the [ConnectionState] will be set
     *                  to [ConnectionState.ConnectionLost].
     *
     * @param [socketConnectionListener]
     *                  The lister which should be notified whenever the state of the socket connection
     *                  to remote server changes. All listeners will we be hold permanently. To avoid
     *                  memory leaks, the listener should be removed by calling [unregisterFromSocket].
     */
    fun connectToWebSocket(jwtToken: String, socketConnectionListener: SocketConnectionListener) {
        socketConnection.connectToSocket(ConnectionConfig(jwtToken, sessionId, MessageType.PROTO), socketConnectionListener)
    }

    /**
     * The socket connection to the remote server will be cleared completely, no matter independent from
     * current state of the connection. After finished, the [ConnectionState] will be set to
     * [ConnectionState.Disconnected]. All registered [SocketConnectionListener]s will be notified
     * before connection will be disconnected. Currently registered listeners will not be deleted.
     */
    fun disconnectFromWebSocket() {
        socketConnection.disconnectSocket()
    }

    /**
     * Only removes the passed listener from changes. This will not change the state of the socket
     * connection.
     */
    fun unregisterFromSocket(socketConnectionListener: SocketConnectionListener) {
        socketConnection.unregisterListener(socketConnectionListener)
    }

    /**
     * All registered listeners will be unregistered and the connection will closed. This
     * should only be called if the socket connection should be closed completelly and all listeners
     * have to be cleared.
     */
    fun closeSocket() {
        socketConnection.closeSocket()
    }

    /**
     * Send the parsed message on Socket
     */
    fun sendMessage(message: SendableMessage): Boolean {
        return socketConnection.sendMessage(message)
    }

    /**
     * Returns the current state of the Socket
     */
    fun connectionState(): ConnectionState = socketConnection.connectionState()

    /**
     * Returns true if socket is either disconnected or closed
     */
    fun isSocketDisposed(): Boolean {
        val connectionState = connectionState()
        return connectionState is ConnectionState.Disconnected ||
            connectionState is ConnectionState.Closed
    }
}
