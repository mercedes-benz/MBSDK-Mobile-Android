package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapability
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiSendToCarCapabilityTest {

    @Test
    fun `Mapping 'null' ApiSendToCarCapability should default to DYNAMIC_ROUTE_BACKEND`() {
        Assertions.assertEquals(SendToCarCapability.DYNAMIC_ROUTE_BACKEND, null.toSendToCarCapability())
    }

    @ParameterizedTest
    @EnumSource(ApiSendToCarCapability::class)
    fun `Mapping from ApiSendToCarCapability to SendToCarCapability enum`(apiSendToCarCapability: ApiSendToCarCapability) {
        Assertions.assertEquals(apiSendToCarCapability.name, apiSendToCarCapability.toSendToCarCapability().name)
    }
}
