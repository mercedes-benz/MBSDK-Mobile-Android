package com.daimler.mbnetworkkit.common

import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError

/**
 * Callback for token providing requests.
 */
interface TokenProviderCallback {

    /**
     * Call this when the token was received.
     */
    fun onTokenReceived(token: String)

    /**
     * Call this when the token request failed.
     */
    fun onRequestFailed(error: ResponseError<out RequestError>?)
}
