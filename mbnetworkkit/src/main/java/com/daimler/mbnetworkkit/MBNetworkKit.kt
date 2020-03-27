package com.daimler.mbnetworkkit

import com.daimler.mbnetworkkit.header.HeaderService
import com.daimler.mbnetworkkit.header.MemoryHeaderService
import com.daimler.mbnetworkkit.header.UUIDTrackingIdProvider

object MBNetworkKit {

    private lateinit var headerService: HeaderService

    fun init(config: NetworkServiceConfig) {
        headerService = MemoryHeaderService(
            config.applicationName,
            config.applicationVersion,
            config.sdkVersion,
            config.osName,
            config.osVersion,
            config.sessionId,
            config.locale,
            config.authMode,
            UUIDTrackingIdProvider()
        )
    }

    /**
     * Returns the configured [HeaderService] that is used to intercept network requests and add headers
     */
    fun headerService(): HeaderService = headerService
}
