package com.daimler.mbcarkit.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiDataCollectorVersionTest {

    @Test
    fun `Mapping 'null' ApiDataCollectorVersion should default to 'null'`() {
        Assertions.assertNull(null.toDataCollectorVersion())
    }

    @ParameterizedTest
    @EnumSource(ApiDataCollectorVersion::class)
    fun `Mapping from ApiDataCollectorVersion to DataCollectorVersion enum`(apiDataCollectorVersion: ApiDataCollectorVersion) {
        Assertions.assertEquals(apiDataCollectorVersion.name, apiDataCollectorVersion.toDataCollectorVersion()?.name)
    }
}
