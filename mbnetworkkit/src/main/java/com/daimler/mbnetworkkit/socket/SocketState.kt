package com.daimler.mbnetworkkit.socket

import com.daimler.mbnetworkkit.socket.message.Observables
import com.daimler.mbnetworkkit.socket.reconnect.Reconnect

/**
 * State of the socket connection to the remote server
 */
sealed class SocketState : SocketEvent {

    /**
     * Simple implements all event related methods to not force class to implement all
     */
    abstract class SimpleSocketState : SocketState() {
        override fun open(observables: Observables, socketConnection: SocketConnection) = Unit

        override fun close(socketConnection: SocketConnection) = Unit

        override fun disconnect(socketConnection: SocketConnection) = Unit

        override fun error(error: ConnectionError, cause: String, socketConnection: SocketConnection) = Unit

        protected fun registerListenerAndStartConnection(config: ConnectionConfig, connectionListener: SocketConnectionListener?, socketConnection: SocketConnection) {
            socketConnection.apply {
                addListenerIfNotAlreadyRegistered(connectionListener)
                socketState = Connecting
                onStartConnection(config)
            }
        }

        protected fun registerListenerAndNotify(connectionListener: SocketConnectionListener?, socketConnection: SocketConnection) {
            socketConnection.apply {
                addListenerIfNotAlreadyRegistered(connectionListener)
                connectionListener?.let {
                    notifyListenerStateChange(connectionState(), connectionListener)
                }
            }
        }

        protected fun disconnectAndChangeStateDisconnected(socketConnection: SocketConnection) {
            socketConnection.apply {
                onStartDisconnect()
                socketState = Disconnected
            }
        }

        protected fun disconnectAndCloseSocket(socketConnection: SocketConnection) {
            socketConnection.apply {
                onStartDisconnect()
                changeStateAndCloseSocket(this)
            }
        }

        protected fun changeStateAndCloseSocket(socketConnection: SocketConnection) {
            socketConnection.apply {
                socketState = Closed
                clearSocket()
            }
        }

        protected fun notifyErrorAndChangeStateConnectionLost(error: ConnectionError, cause: String, socketConnection: SocketConnection) {
            socketConnection.apply {
                socketState = onConnectionError(error, cause)
            }
        }

        protected fun completeConnectionAndChangeStateConnected(observables: Observables, socketConnection: SocketConnection) {
            socketConnection.apply {
                onConnectionCompleted()
                socketState = Connected(observables)
            }
        }
    }

    /**
     * Initial state of the socket connection or if connection was closed by calling ???.closeSocket()
     * In this state there are now registered listeners active and the socket will be completely cleared.
     * This should be called e.g. if the user has logged out to ensure that a new [SocketConnection]
     * will be used.
     * ### Handled Events
     * + [SocketEvent.connect]
     */
    object Closed : SimpleSocketState() {

        override fun connect(config: ConnectionConfig, connectionListener: SocketConnectionListener?, socketConnection: SocketConnection) {
            registerListenerAndStartConnection(config, connectionListener, socketConnection)
        }

        override fun connectionState(): ConnectionState = ConnectionState.Closed
    }

    /**
     * This state will be called if connection to socket was closed. Registered listener for [SocketState]
     * will still be active and not cleared. While in state Disconnected, it is only possible to
     * ### Handled Events
     * + [SocketEvent.connect]
     * + [SocketEvent.close]
     */
    object Disconnected : SimpleSocketState() {

        override fun connect(config: ConnectionConfig, connectionListener: SocketConnectionListener?, socketConnection: SocketConnection) {
            registerListenerAndStartConnection(config, connectionListener, socketConnection)
        }

        override fun close(socketConnection: SocketConnection) {
            changeStateAndCloseSocket(socketConnection)
        }

        override fun connectionState(): ConnectionState = ConnectionState.Disconnected
    }

    /**
     * Establish a connection to the socket is currently in progress
     * ### Handled Events
     * + [SocketEvent.connect]
     * + [SocketEvent.disconnect]
     * + [SocketEvent.error]
     * + [SocketEvent.close]
     * + [SocketEvent.open]
     */
    object Connecting : SimpleSocketState() {

        override fun connect(config: ConnectionConfig, connectionListener: SocketConnectionListener?, socketConnection: SocketConnection) {
            registerListenerAndNotify(connectionListener, socketConnection)
        }

        override fun disconnect(socketConnection: SocketConnection) {
            disconnectAndChangeStateDisconnected(socketConnection)
        }

        override fun error(error: ConnectionError, cause: String, socketConnection: SocketConnection) {
            notifyErrorAndChangeStateConnectionLost(error, cause, socketConnection)
        }

        override fun close(socketConnection: SocketConnection) {
            disconnectAndCloseSocket(socketConnection)
        }

        override fun open(observables: Observables, socketConnection: SocketConnection) {
            completeConnectionAndChangeStateConnected(observables, socketConnection)
        }

        override fun connectionState(): ConnectionState = ConnectionState.Connecting
    }

    /**
     * The app is currently connected to the remote server.
     * ### Handled Events
     * + [SocketEvent.connect]
     * + [SocketEvent.disconnect]
     * + [SocketEvent.error]
     * + [SocketEvent.close]
     */
    class Connected(val observables: Observables) : SocketState.SimpleSocketState() {

        override fun connect(config: ConnectionConfig, connectionListener: SocketConnectionListener?, socketConnection: SocketConnection) {
            registerListenerAndNotify(connectionListener, socketConnection)
        }

        override fun disconnect(socketConnection: SocketConnection) {
            disconnectAndChangeStateDisconnected(socketConnection)
        }

        override fun error(error: ConnectionError, cause: String, socketConnection: SocketConnection) {
            notifyErrorAndChangeStateConnectionLost(error, cause, socketConnection)
        }

        override fun close(socketConnection: SocketConnection) {
            disconnectAndCloseSocket(socketConnection)
        }

        override fun connectionState(): ConnectionState = ConnectionState.Connected(observables)
    }

    /**
     * State of connection whenever there is some issue with the socket connection like e.g. network
     * failure, invalid token or timeout.
     * ### Handled Events
     * + [SocketEvent.connect]
     * + [SocketEvent.disconnect]
     * + [SocketEvent.close]
     */
    class ConnectionLost(val error: ConnectionError, val cause: String = "") : SimpleSocketState() {
        var reconnect: Reconnect = Reconnect()
            internal set

        override fun connect(config: ConnectionConfig, connectionListener: SocketConnectionListener?, socketConnection: SocketConnection) {
            registerListenerAndStartConnection(config, connectionListener, socketConnection)
        }

        override fun disconnect(socketConnection: SocketConnection) {
            disconnectAndChangeStateDisconnected(socketConnection)
        }

        override fun close(socketConnection: SocketConnection) {
            disconnectAndCloseSocket(socketConnection)
        }

        override fun connectionState(): ConnectionState {
            return ConnectionState.ConnectionLost(error, cause).apply {
                reconnect = this@ConnectionLost.reconnect.copy()
            }
        }
    }
}
