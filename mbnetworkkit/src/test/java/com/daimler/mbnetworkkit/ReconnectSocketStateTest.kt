package com.daimler.mbnetworkkit

import com.daimler.mbnetworkkit.common.TokenProvider
import com.daimler.mbnetworkkit.common.TokenProviderCallback
import com.daimler.mbnetworkkit.socket.ConnectionConfig
import com.daimler.mbnetworkkit.socket.ConnectionError
import com.daimler.mbnetworkkit.socket.ConnectionState
import com.daimler.mbnetworkkit.socket.MessageType
import com.daimler.mbnetworkkit.socket.SocketConnectionListener
import com.daimler.mbnetworkkit.socket.message.ChaineableMessageProcessor
import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.Notifyable
import com.daimler.mbnetworkkit.socket.message.SendMessageService
import com.daimler.mbnetworkkit.socket.message.SendableMessage
import com.daimler.mbnetworkkit.socket.reconnect.FixPeriodicReconnection
import com.daimler.mbnetworkkit.socket.reconnect.Reconnect
import com.daimler.mbnetworkkit.socket.reconnect.ReconnectableSocketConnection
import com.daimler.mbnetworkkit.socket.reconnect.Reconnection
import org.junit.Before
import org.junit.Test
import java.util.UUID

class ReconnectSocketStateTest {

    private val JWT_TOKEN = "a1b2c3d4"

    private val connectionListener = ConnectionListener()

    private var currentConnectionState: ConnectionState? = null

    private var reconnectCount = 0

    private var connectionLost = false

    private var reconnect: Reconnect? = null

    @Before
    fun setup() {
        currentConnectionState = null
        reconnectCount = 0
        connectionLost = false
    }

    @Test
    fun reconnectExecutedAfterConnectionLost() {
        val retries = 1
        val reconnect = FixPeriodicReconnection(DELAY, retries)
        val socketConnection = TestReconnactableSocketConnection(reconnect)
        socketConnection.connectToSocket(testConnectionConfig(), connectionListener)
        socketConnection.error(ConnectionError.NETWORK_FAILURE, "disconnect")
        Thread.sleep(DELAY * retries + 100)
        assertConnectionLostAndReconnectTriggerd(retries)
    }

    @Test
    fun reconnectSuccessAftereConnectionLost() {
        val retries = 1
        val reconnect = FixPeriodicReconnection(DELAY, retries)
        val socketConnection = TestReconnactableSocketConnection(reconnect)
        socketConnection.connectToSocket(testConnectionConfig(), connectionListener)
        socketConnection.error(ConnectionError.NETWORK_FAILURE, "disconnect")
        Thread.sleep(DELAY * retries + 100)
        assertConnectedAfterConnectionLost()
    }

    @Test
    fun reconnectTriggeredMultipleTimesIfFailedBecauseNetworkError() {
        val retries = 2
        val reconnect = FixPeriodicReconnection(DELAY, retries)
        val socketConnection = FailingSocketConnection(ConnectionError.NETWORK_FAILURE, reconnect)
        socketConnection.connectToSocket(testConnectionConfig(), connectionListener)
        Thread.sleep(DELAY * retries + 100)
        assertConnectionLostAndReconnectTriggered(retries)
    }

    @Test
    fun reconnectTriggeredIfFailedBecauseForbiddenWithTokenProvider() {
        val retries = 1
        val reconnect = FixPeriodicReconnection(DELAY, retries)
        val socketConnection =
            FailingSocketConnection(ConnectionError.FORBIDDEN, reconnect, TestTokenProvider())
        socketConnection.connectToSocket(testConnectionConfig(), connectionListener)
        Thread.sleep(DELAY * retries + 100)
        assertConnectionLostAndReconnectTriggered(retries)
    }

    @Test
    fun reconnectNotTriggeredIfFailedBecauseForbiddenWithoutTokenProvider() {
        val retries = 1
        val reconnect = FixPeriodicReconnection(DELAY, retries)
        val socketConnection = FailingSocketConnection(ConnectionError.FORBIDDEN, reconnect)
        socketConnection.connectToSocket(testConnectionConfig(), connectionListener)
        Thread.sleep(DELAY * retries + 100)
        assertConnectionLostAndReconnectTriggered(0)
    }

    @Test
    fun reconnectNotTriggeredIfFailedBecauseUnauthorized() {
        val retries = 1
        val reconnect = FixPeriodicReconnection(DELAY, retries)
        val socketConnection = FailingSocketConnection(ConnectionError.UNAUTHORIZED, reconnect)
        socketConnection.connectToSocket(testConnectionConfig(), connectionListener)
        Thread.sleep(DELAY * retries + 100)
        assertConnectionLostAndReconnectTriggered(0)
    }

    @Test
    fun connectionLostStateContainsReconnectWhileInProgress() {
        val retries = 1
        val reconnect = FixPeriodicReconnection(DELAY, retries)
        val socketConnection = FailingSocketConnection(ConnectionError.NETWORK_FAILURE, reconnect)
        socketConnection.connectToSocket(testConnectionConfig(), connectionListener)
        assert(this.reconnect == Reconnect(true, 1, DELAY))
    }

    @Test
    fun connectionLostStateContainsReconnectAfterLastAttempt() {
        val retries = 2
        val reconnect = FixPeriodicReconnection(DELAY, retries)
        val socketConnection = FailingSocketConnection(ConnectionError.NETWORK_FAILURE, reconnect)
        socketConnection.connectToSocket(testConnectionConfig(), connectionListener)
        Thread.sleep(DELAY * retries + 100)
        assert(this.reconnect == Reconnect(false, retries, 0))
    }

    private fun assertConnectionLostAndReconnectTriggerd(expectedReconnectCount: Int) {
        assert(reconnectCount == expectedReconnectCount)
        assert(connectionLost)
    }

    private fun assertConnectedAfterConnectionLost() {
        assert(connectionLost)
        assert(currentConnectionState is ConnectionState.Connected)
    }

    private fun assertConnectionLostAndReconnectTriggered(expectedReconnectCount: Int) {
        assert(reconnectCount == expectedReconnectCount)
        assert(currentConnectionState is ConnectionState.ConnectionLost)
    }

    private fun testConnectionConfig(): ConnectionConfig {
        return ConnectionConfig(JWT_TOKEN, UUID.randomUUID().toString(), MessageType.PROTO)
    }

    inner class ConnectionListener : SocketConnectionListener {

        override fun connectionStateChanged(connectionState: ConnectionState) {
            this@ReconnectSocketStateTest.currentConnectionState = connectionState
            if (connectionState is ConnectionState.ConnectionLost) {
                connectionLost = true
                reconnect = connectionState.reconnect
            }
        }
    }

    inner class TestMessageProcessor : ChaineableMessageProcessor() {

        override fun doHandleReceivedMessage(
            notifyable: Notifyable,
            sendService: SendMessageService,
            dataSocketMessage: DataSocketMessage
        ): Boolean {
            return true
        }

        override fun doHandleMessageToSend(message: SendableMessage): DataSocketMessage? {
            return null
        }
    }

    inner class TestReconnactableSocketConnection(reconnection: Reconnection) :
        ReconnectableSocketConnection(reconnection, TestMessageProcessor(), TestTokenProvider()) {

        override fun notifyListenerStateChange(
            connectionState: ConnectionState,
            connectionListener: SocketConnectionListener
        ) {
            connectionListener.connectionStateChanged(connectionState)
        }

        override fun onStartConnection(config: ConnectionConfig) {
            super.onStartConnection(config)
            openConnection()
        }

        override fun onStartReconnect(connectionConfig: ConnectionConfig) {
            super.onStartReconnect(connectionConfig)
            reconnectCount++
        }

        override fun sendMessage(message: DataSocketMessage?): Boolean {
            return false
        }
    }

    inner class FailingSocketConnection(
        private val connectionError: ConnectionError,
        reconnection: Reconnection,
        tokenProvider: TokenProvider? = null
    ) : ReconnectableSocketConnection(reconnection, TestMessageProcessor(), tokenProvider) {

        override fun notifyListenerStateChange(
            connectionState: ConnectionState,
            connectionListener: SocketConnectionListener
        ) {
            connectionListener.connectionStateChanged(connectionState)
        }

        override fun onStartConnection(config: ConnectionConfig) {
            super.onStartConnection(config)
            error(connectionError, "error")
        }

        override fun onStartReconnect(connectionConfig: ConnectionConfig) {
            super.onStartReconnect(connectionConfig)
            reconnectCount++
        }

        override fun sendMessage(message: DataSocketMessage?): Boolean {
            return false
        }
    }

    private class TestTokenProvider : TokenProvider {

        override fun requestToken(callback: TokenProviderCallback) {
            callback.onTokenReceived("123")
        }
    }

    companion object {
        const val DELAY = 50L
    }
}
