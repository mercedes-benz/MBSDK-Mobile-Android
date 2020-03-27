package com.daimler.mbnetworkkit.socket.reconnect

import com.daimler.mbnetworkkit.socket.ConnectionConfig
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.schedule

/**
 * Basic implementation which tries to reconnect until the specified number of attempts is reached.
 * If Reconnect was successfull, triggered by calling [reconnectSuccess], the number of attempts will
 * be reset. The reconnect will start when [reconnect] is called and stop when connection was established
 * or max retries are reached.
 * To do reconnects, the related [ConnectionConfig] must be set once when related connection should
 * be established. Instead [reconnectSuccess] should only be called when the connection could be
 * established.
 */
abstract class DelayedReconnection(internal val maxRetries: Int) : Reconnection {

    private var connectionConfig: ConnectionConfig? = null

    internal var numberOfRetries = 0
        private set

    private var nextRetryTimer: TimerTask? = null

    init {
        if (maxRetries < 0) {
            throw IllegalArgumentException("Number of max retries must not be smaller than 0")
        }
    }

    final override fun connectingStarted(connectionConfig: ConnectionConfig) {
        if (nextRetryTimer != null) {
            reset()
        }
        this.connectionConfig = connectionConfig
    }

    final override fun isReconnecting(): Boolean {
        return nextRetryTimer != null
    }

    final override fun reconnectSuccess() {
        resetRetryCount()
    }

    /**
     * [ReconnectListener.onReconnectCancelled] will be called if reconnect was cancelled because max attempts
     * are already reached, or [ReconnectListener.onStartReconnect] if reconnect will be done. The listener
     * is only triggered after the delay time has finished
     */
    final override fun reconnect(reconnectListener: ReconnectListener): Reconnect {
        val delay = retryDelayInMillis(numberOfRetries)
        return when {
            reachedMaxRetries(numberOfRetries) -> cancelReconnect(reconnectListener)
            isConnectionConfigAvailable().not() -> cancelReconnect(reconnectListener)
            validDelay(delay).not() -> throw IllegalArgumentException("Invalid delay for reconnect: $delay")
            else -> doNextRetry(delay, reconnectListener)
        }
    }

    private fun reachedMaxRetries(numberOfRetries: Int): Boolean {
        return numberOfRetries >= maxRetries
    }

    private fun isConnectionConfigAvailable(): Boolean {
        return connectionConfig != null
    }

    private fun doNextRetry(retryDelay: Long, reconnectListener: ReconnectListener): Reconnect {
        numberOfRetries++
        return doNextReconnect(retryDelay, reconnectListener)
    }

    final override fun reset() {
        nextRetryTimer?.cancel()
        connectionConfig = null
        resetRetryCount()
    }

    private fun validDelay(delay: Long): Boolean {
        return delay >= 0
    }

    private fun doNextReconnect(delay: Long, reconnectListener: ReconnectListener): Reconnect {
        val reconnecting = connectionConfig?.let {
            nextRetryTimer = Timer().schedule(delay) {
                nextRetryTimer = null
                reconnectListener.onStartReconnect(it)
            }
            true
        } ?: false
        return Reconnect(reconnecting, numberOfRetries, delay)
    }

    private fun cancelReconnect(reconnectListener: ReconnectListener): Reconnect {
        val currentNumberOfRetries = numberOfRetries
        reset()
        reconnectListener.onReconnectCancelled()
        return Reconnect(false, currentNumberOfRetries, 0)
    }

    private fun resetRetryCount() {
        numberOfRetries = 0
    }

    /**
     * The time until next period in milliseconds. The returned value must be larger than 0. If not
     * an [IllegalStateException] will be thrown when [reconnect] is called
     */
    abstract fun retryDelayInMillis(numberOfRetry: Int): Long
}
