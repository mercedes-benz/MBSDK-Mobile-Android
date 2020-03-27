package com.daimler.mbnetworkkit.socket.reconnect

/**
 * This implementation of [DelayedReconnection] never tries to reconnect as the number of retries is
 * set to 0.
 */
class NoReconnection : DelayedReconnection(0) {
    override fun retryDelayInMillis(numberOfRetry: Int): Long {
        return 0
    }
}
