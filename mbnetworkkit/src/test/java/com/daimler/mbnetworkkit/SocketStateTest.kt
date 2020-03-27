package com.daimler.mbnetworkkit

import com.daimler.mbnetworkkit.socket.ConnectionConfig
import com.daimler.mbnetworkkit.socket.ConnectionError
import com.daimler.mbnetworkkit.socket.ConnectionState
import com.daimler.mbnetworkkit.socket.MessageType
import com.daimler.mbnetworkkit.socket.SocketConnection
import com.daimler.mbnetworkkit.socket.SocketConnectionListener
import com.daimler.mbnetworkkit.socket.SocketState
import com.daimler.mbnetworkkit.socket.message.ChaineableMessageProcessor
import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.Notifyable
import com.daimler.mbnetworkkit.socket.message.SendMessageService
import com.daimler.mbnetworkkit.socket.message.SendableMessage
import org.junit.Before
import org.junit.Test
import java.util.UUID

class SocketStateTest {

    lateinit var socketConnection: SocketConnection

    private val connectionListner = ConnectionListener()

    var connectionState: ConnectionState? = null

    var connectionConfig: ConnectionConfig? = null

    var connected = false

    var connectionError = false

    var socketCleared = false

    var sendMessage: DataSocketMessage? = null

    @Before
    fun setup() {
        connectionState = null
        sendMessage = null
    }

    @Test
    fun connectionCompleted() {
        val connectionConfig = testConnectionConfig()
        socketConnection = TestSocketConnection(emptyMessageProcessor())
        socketConnection.connectToSocket(connectionConfig, connectionListner)
        assertConnected()
    }

    @Test
    fun connectionDisconnectedAfterCompleted() {
        val config = testConnectionConfig()
        socketConnection = TestSocketConnection(emptyMessageProcessor())
        socketConnection.connectToSocket(config, connectionListner)
        socketConnection.disconnectSocket()
        assertDisconnected()
    }

    @Test
    fun connectionDisconnectedWhileConnectonLost() {
        val config = testConnectionConfig()
        val error = ConnectionError.NETWORK_FAILURE
        val cause = "DISCONNECT"
        socketConnection = TestSocketConnection(emptyMessageProcessor())
        socketConnection.connectToSocket(config, connectionListner)
        socketConnection.error(error, cause)
        socketConnection.disconnectSocket()
        assertDisconnected()
    }

    @Test
    fun connectionErrorWhileConnected() {
        val config = testConnectionConfig()
        val error = ConnectionError.NETWORK_FAILURE
        val cause = "DISCONNECT"
        socketConnection = TestSocketConnection(emptyMessageProcessor())
        socketConnection.connectToSocket(config, connectionListner)
        socketConnection.error(error, cause)
        assertConnectionLost(error, cause)
    }

    @Test
    fun closeConnectionWhileConnected() {
        val config = testConnectionConfig()
        socketConnection = TestSocketConnection(emptyMessageProcessor())
        socketConnection.connectToSocket(config, connectionListner)
        socketConnection.closeSocket()
        assertConnectionClosed()
    }

    @Test
    fun closeConnectionWhileDisconnected() {
        val config = testConnectionConfig()
        socketConnection = TestSocketConnection(emptyMessageProcessor())
        socketConnection.connectToSocket(config, connectionListner)
        socketConnection.disconnectSocket()
        socketConnection.closeSocket()
        assertConnectionClosed()
    }

    @Test
    fun closeConnectionWhileConnectionLost() {
        val config = testConnectionConfig()
        val error = ConnectionError.NETWORK_FAILURE
        val cause = "DISCONNECT"
        socketConnection = TestSocketConnection(emptyMessageProcessor())
        socketConnection.connectToSocket(config, connectionListner)
        socketConnection.error(error, cause)
        socketConnection.closeSocket()
        assertConnectionClosed()
    }

    @Test
    fun connectionStateNotTriggeredAfterListenerUnregistered() {
        val config = testConnectionConfig()
        socketConnection = TestSocketConnection(emptyMessageProcessor())
        socketConnection.connectToSocket(config, connectionListner)
        socketConnection.unregisterListener(connectionListner)
        socketConnection.closeSocket()
        assert(connectionState is ConnectionState.Connected)
    }

    private fun assertConnected() {
        assert(connectionState is ConnectionState.Connected)
        assert(connected)
    }

    private fun assertDisconnected() {
        assert(connectionState == ConnectionState.Disconnected)
        assert(connected.not())
    }

    private fun assertConnectionLost(error: ConnectionError, cause: String) {
        assert(connected.not())
        assert(connectionState == ConnectionState.ConnectionLost(error, cause))
        assert(connectionError)
    }

    private fun assertConnectionClosed() {
        assert(connectionState == ConnectionState.Closed)
        assert(connected.not())
        assert(socketCleared)
    }

    private fun testConnectionConfig(): ConnectionConfig {
        val session = UUID.randomUUID().toString()
        return ConnectionConfig("", session, MessageType.PROTO)
    }

    private fun emptyMessageProcessor(): ChaineableMessageProcessor {
        return object : ChaineableMessageProcessor() {
            override fun doHandleReceivedMessage(notifyable: Notifyable, sendService: SendMessageService, dataSocketMessage: DataSocketMessage): Boolean {
                return true
            }

            override fun doHandleMessageToSend(message: SendableMessage): DataSocketMessage? {
                return null
            }
        }
    }

    inner class ConnectionListener : SocketConnectionListener {
        override fun connectionStateChanged(connectionState: ConnectionState) {
            this@SocketStateTest.connectionState = connectionState
        }
    }

    // TODO: 10.02.19 - 11:47 test public methods for received and send messages

    inner class TestSocketConnection(messageProcessor: ChaineableMessageProcessor) : SocketConnection(messageProcessor) {

        override fun notifyListenerStateChange(connectionState: ConnectionState, connectionListener: SocketConnectionListener) {
            connectionListener.connectionStateChanged(connectionState)
        }

        override fun onStartConnection(config: ConnectionConfig) {
            connectionConfig = config
            socketConnection.openConnection()
        }

        override fun onStartDisconnect() {
            connected = false
        }

        override fun onConnectionCompleted() {
            connected = true
        }

        override fun onConnectionError(error: ConnectionError, cause: String): SocketState.ConnectionLost {
            connected = false
            connectionError = true
            return SocketState.ConnectionLost(error, cause)
        }

        override fun onSocketClosed() {
            socketCleared = true
        }

        override fun sendMessage(message: DataSocketMessage?): Boolean {
            // TODO: 10.02.19 - 14:29
            return true
        }
    }
}
