package com.daimler.mbcarkit.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiFuelTypeTest {

    @Test
    fun `Mapping 'null' ApiFuelType should default to 'null'`() {
        Assertions.assertNull(null.toFuelType())
    }

    @ParameterizedTest
    @EnumSource(ApiFuelType::class)
    fun `Mapping from ApiFuelType to FuelType enum`(apiFuelType: ApiFuelType) {
        Assertions.assertEquals(apiFuelType.name, apiFuelType.toFuelType()?.name)
    }
}
