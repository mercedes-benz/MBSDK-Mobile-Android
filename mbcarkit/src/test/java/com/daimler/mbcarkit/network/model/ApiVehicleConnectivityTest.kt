package com.daimler.mbcarkit.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiVehicleConnectivityTest {

    @Test
    fun `Mapping 'null' ApiVehicleConnectivity should default to 'null'`() {
        Assertions.assertNull(null.toVehicleConnectivity())
    }

    @ParameterizedTest
    @EnumSource(ApiVehicleConnectivity::class)
    fun `Mapping from ApiVehicleConnectivity to VehicleConnectivity enum`(apiVehicleConnectivity: ApiVehicleConnectivity) {
        Assertions.assertEquals(apiVehicleConnectivity.name, apiVehicleConnectivity.toVehicleConnectivity()?.name)
    }
}
