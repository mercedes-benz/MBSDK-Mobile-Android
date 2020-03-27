package com.daimler.mbmobilesdk.implementation

import com.daimler.mbingresskit.common.authentication.AuthenticationType
import com.daimler.mbmobilesdk.configuration.Region
import com.daimler.mbmobilesdk.configuration.Stage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class StageBasedEndpointUrlProviderTest {

    @Test
    fun test_stageBasedEndpointUrlProvider_usePROD_shouldProvideCorrectValues() {
        val expectedAuthUrl = "https://keycloak.risingstars.daimler.com"
        val expectedBffUrl = "https://bff-prod.risingstars.daimler.com"
        val expectedSocketUrl = "wss://websocket-prod.risingstars.daimler.com/ws"
        val urlProvider = StageBasedEndpointUrlProvider(Region.ECE, Stage.PROD)

        assertEquals(expectedAuthUrl, urlProvider.authUrl(AuthenticationType.KEYCLOAK))
        assertEquals(expectedBffUrl, urlProvider.bffUrl)
        assertEquals(expectedSocketUrl, urlProvider.socketUrl)
        assertTrue(urlProvider.isProductiveEnvironment)
    }

    @Test
    fun test_stageBasedEndpointUrlProvider_useMOCK_shouldProvideCorrectValues() {
        val expectedAuthUrl = "https://keycloak.risingstars-mock.daimler.com"
        val expectedBffUrl = "https://bff-mock.risingstars-mock.daimler.com"
        val expectedSocketUrl = "wss://websocket-mock.risingstars-mock.daimler.com/ws"
        val urlProvider = StageBasedEndpointUrlProvider(Region.ECE, Stage.MOCK)

        assertEquals(expectedAuthUrl, urlProvider.authUrl(AuthenticationType.KEYCLOAK))
        assertEquals(expectedBffUrl, urlProvider.bffUrl)
        assertEquals(expectedSocketUrl, urlProvider.socketUrl)
        assertFalse(urlProvider.isProductiveEnvironment)
    }
}
