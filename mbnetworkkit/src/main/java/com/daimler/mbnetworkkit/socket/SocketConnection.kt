package com.daimler.mbnetworkkit.socket

import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.MessageProcessor
import com.daimler.mbnetworkkit.socket.message.ObserverManager
import com.daimler.mbnetworkkit.socket.message.SendMessageService
import com.daimler.mbnetworkkit.socket.message.SendableMessage

abstract class SocketConnection(
    private val messageProcessor: MessageProcessor
) : SendMessageService {

    private val observerManager = ObserverManager()

    private var connectionListeners = mutableListOf<SocketConnectionListener>()

    internal var socketState: SocketState = SocketState.Closed
        set(value) {
            field = value
            notifyListenersStateChange(field.connectionState())
        }

    // region Fascade methods

    fun unregisterListener(socketConnectionListener: SocketConnectionListener) {
        removeListenerIfRegistered(socketConnectionListener)
    }

    fun connectToSocket(config: ConnectionConfig, socketConnectionListener: SocketConnectionListener? = null) {
        socketState.connect(config, socketConnectionListener, this)
    }

    fun disconnectSocket() {
        socketState.disconnect(this)
    }

    fun closeSocket() {
        socketState.close(this)
    }

    internal fun error(error: ConnectionError, cause: String) {
        socketState.error(error, cause, this)
    }

    internal fun openConnection() {
        socketState.open(observerManager, this)
    }

    final override fun sendMessage(message: SendableMessage): Boolean {
        return sendMessage(messageProcessor.parseMessageToSend(message))
    }

    fun connectionState(): ConnectionState = socketState.connectionState()

    /**
     * This method must be called for all incoming messages which are received on a socket. The passed
     * message will be than processed by delegating the call to configured [MessageProcessor]
     */
    protected fun processReceivedMessage(dataMessageFromSocket: DataSocketMessage) {
        messageProcessor.handleReceivedMessage(observerManager, this, dataMessageFromSocket)
    }

    /**
     * Called when messages should be send on Socket.
     *
     * @param message
     *          If null, there was not configured MessageProcessor available to parse the related message.
     *
     * @return
     *          true if the message was send successfully, else false
     */
    protected abstract fun sendMessage(message: DataSocketMessage?): Boolean

    // endregion

    // region Actions

    internal fun addListenerIfNotAlreadyRegistered(socketConnectionListener: SocketConnectionListener?) {
        socketConnectionListener?.let {
            synchronized(this) {
                if (connectionListeners.contains(it).not()) {
                    connectionListeners.add(it)
                }
            }
        }
    }

    abstract fun notifyListenerStateChange(connectionState: ConnectionState, connectionListener: SocketConnectionListener)

    /**
     * Called directly after switching to state [SocketState.Connecting]. To finalize connection process,
     * [SocketConnection.openConnection] must be called.
     * @see SocketState
     */
    internal abstract fun onStartConnection(config: ConnectionConfig)

    /**
     * This is called if [SocketConnection.disconnectSocket] or [SocketConnection.closeSocket] was called.
     * The state will change to [SocketState.Disconnected] after method is completed.
     * @see SocketState
     */
    internal abstract fun onStartDisconnect()

    /**
     * This is called first in [SocketConnection.openConnection] and dirctly before state will switch
     * to [SocketState.Connected]
     * @see SocketState
     */
    internal abstract fun onConnectionCompleted()

    /**
     * If an error was triggered which has been notified by calling [SocketConnection.error], this
     * method will be called first. If completed the state will switch to [SocketState.ConnectionLost].
     * @see SocketState
     */
    internal abstract fun onConnectionError(error: ConnectionError, cause: String): SocketState.ConnectionLost

    /**
     * Called if the socket has been cleared to completely shutdown. This will be triggered by [SocketConnection.closeSocket]
     * @see SocketState
     */
    internal abstract fun onSocketClosed()

    internal fun clearSocket() {
        synchronized(this) {
            connectionListeners.clear()
        }
        onSocketClosed()
    }

    // endregion

    // region private helper functions

    private fun notifyListenersStateChange(connectionState: ConnectionState) {
        connectionListeners.forEach {
            notifyListenerStateChange(connectionState, it)
        }
    }

    private fun removeListenerIfRegistered(socketConnectionListener: SocketConnectionListener) {
        synchronized(this) {
            connectionListeners.remove(socketConnectionListener)
        }
    }
}
