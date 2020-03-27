package com.daimler.mbnetworkkit.socket

enum class ConnectionError {
    /**
     * Returned by Socket if invalid Token was passed
     */
    UNAUTHORIZED,

    /**
     * Returned by Socket if passed Token has been expired
     */
    FORBIDDEN,

    /**
     * Returned if there are some connection issues
     */
    NETWORK_FAILURE
}
