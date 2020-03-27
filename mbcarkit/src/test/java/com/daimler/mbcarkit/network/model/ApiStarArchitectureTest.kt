package com.daimler.mbcarkit.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiStarArchitectureTest {

    @Test
    fun `Mapping 'null' ApiStarArchitecture should default to 'null'`() {
        Assertions.assertNull(null.toStarArchitecture())
    }

    @ParameterizedTest
    @EnumSource(ApiStarArchitecture::class)
    fun `Mapping from ApiStarArchitecture to StarArchitecture enum`(apiStarArchitecture: ApiStarArchitecture) {
        Assertions.assertEquals(apiStarArchitecture.name, apiStarArchitecture.toStarArchitecture()?.name)
    }
}
