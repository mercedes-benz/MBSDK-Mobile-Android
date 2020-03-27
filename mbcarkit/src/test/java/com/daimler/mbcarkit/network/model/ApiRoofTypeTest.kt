package com.daimler.mbcarkit.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiRoofTypeTest {

    @Test
    fun `Mapping 'null' ApiRoofType should default to 'null'`() {
        Assertions.assertNull(null.toRoofType())
    }

    @ParameterizedTest
    @EnumSource(ApiRoofType::class)
    fun `Mapping from ApiRoofType to RoofType enum`(apiRoofType: ApiRoofType) {
        Assertions.assertEquals(apiRoofType.name, apiRoofType.toRoofType()?.name)
    }
}
