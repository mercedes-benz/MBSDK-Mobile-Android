package com.daimler.mbnetworkkit.socket.reconnect

/**
 * A Reconnect will be done with a fix period between the attempt until the max number of attempts
 * is reached.
 */
class FixPeriodicReconnection(private val fixPeriodMillis: Long, maxRetries: Int) : DelayedReconnection(maxRetries) {

    override fun retryDelayInMillis(numberOfRetry: Int): Long {
        return fixPeriodMillis
    }
}
