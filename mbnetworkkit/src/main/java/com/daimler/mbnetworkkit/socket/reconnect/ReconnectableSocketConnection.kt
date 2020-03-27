package com.daimler.mbnetworkkit.socket.reconnect

import com.daimler.mbnetworkkit.common.TokenProvider
import com.daimler.mbnetworkkit.common.TokenProviderCallback
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.socket.ConnectionConfig
import com.daimler.mbnetworkkit.socket.ConnectionError
import com.daimler.mbnetworkkit.socket.SocketConnection
import com.daimler.mbnetworkkit.socket.SocketState
import com.daimler.mbnetworkkit.socket.message.MessageProcessor

/**
 * It provides the possibility to do a reconnect, if the connection was lost. All classes extending
 * this implementation must call super method of the related action.
 */
abstract class ReconnectableSocketConnection(
    private var reconnection: Reconnection,
    messageProcessor: MessageProcessor,
    private val tokenProvider: TokenProvider?
) : SocketConnection(messageProcessor), ReconnectListener {

    private var lastConnectionError: ConnectionError? = null

    override fun onStartConnection(config: ConnectionConfig) {
        reconnection.connectingStarted(config)
    }

    override fun onConnectionCompleted() {
        reconnection.reconnectSuccess()
    }

    override fun onStartDisconnect() {
        resetReconnection()
    }

    /**
     * When overriden, the super [ReconnectableSocketConnection.onConnectionError] should be called
     * to return the related [SocketState.ConnectionLost]
     */
    override fun onConnectionError(error: ConnectionError, cause: String): SocketState.ConnectionLost {
        val connectionLostState = SocketState.ConnectionLost(error, cause)

        if (canReconnect(error)) {
            lastConnectionError = error
            connectionLostState.reconnect = reconnection.reconnect(this)
        } else {
            resetReconnection()
        }

        return connectionLostState
    }

    private fun canReconnect(error: ConnectionError): Boolean = error.isReconnectableError() || error.requiresTokenRefresh().and(tokenProvider != null)

    override fun onSocketClosed() {
        resetReconnection()
    }

    // region ReconnectListener

    override fun onStartReconnect(connectionConfig: ConnectionConfig) {
        if (lastConnectionError?.requiresTokenRefresh() == true) {
            connectWithTokenOrReset(connectionConfig)
        } else {
            connectToSocket(connectionConfig)
        }
        lastConnectionError = null
    }

    override fun onReconnectCancelled() = Unit

    // endregion

    private fun resetReconnection() {
        reconnection.reset()
    }

    private fun connectWithTokenOrReset(connectionConfig: ConnectionConfig) {
        tokenProvider?.requestToken(
            object : TokenProviderCallback {

                override fun onTokenReceived(token: String) {
                    connectToSocket(connectionConfig.copy(token = token))
                }

                override fun onRequestFailed(error: ResponseError<out RequestError>?) {
                    resetReconnection()
                }
            }
        ) ?: resetReconnection()
    }

    private fun ConnectionError.isReconnectableError() =
        this == ConnectionError.NETWORK_FAILURE

    private fun ConnectionError.requiresTokenRefresh() =
        this == ConnectionError.FORBIDDEN
}
