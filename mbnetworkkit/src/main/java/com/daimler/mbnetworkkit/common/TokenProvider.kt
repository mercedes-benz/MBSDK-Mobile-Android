package com.daimler.mbnetworkkit.common

/**
 * Interface for implementations that provide an authentication token for this module.
 */
interface TokenProvider {

    /**
     * Requests and returns the authentication token.
     */
    fun requestToken(callback: TokenProviderCallback)
}
