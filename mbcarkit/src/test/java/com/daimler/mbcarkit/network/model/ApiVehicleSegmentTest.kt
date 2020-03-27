package com.daimler.mbcarkit.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiVehicleSegmentTest {

    @Test
    fun `Mapping 'null' ApiVehicleSegment should default to 'null'`() {
        Assertions.assertNull(null.toVehicleSegment())
    }

    @ParameterizedTest
    @EnumSource(ApiVehicleSegment::class)
    fun `Mapping from ApiVehicleSegment to VehicleSegment enum`(apiVehicleSegment: ApiVehicleSegment) {
        Assertions.assertEquals(apiVehicleSegment.name, apiVehicleSegment.toVehicleSegment()?.name)
    }
}
