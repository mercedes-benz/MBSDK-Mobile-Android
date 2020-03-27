package com.daimler.mbmobilesdk.configuration

import com.daimler.mbingresskit.common.authentication.AuthenticationType

/**
 * Provider for endpoint URLs used by the SDK.
 */
interface EndpointUrlProvider {

    /**
     * True, if the given URLs point to a productive environment.
     */
    val isProductiveEnvironment: Boolean

    /**
     * Returns the URL used for authorization token exchanges.
     */
    fun authUrl(authenticationType: AuthenticationType): String

    /**
     * Returns the URL used for the backend.
     */
    val bffUrl: String

    /**
     * Returns the URL used for socket connections.
     */
    val socketUrl: String
}
