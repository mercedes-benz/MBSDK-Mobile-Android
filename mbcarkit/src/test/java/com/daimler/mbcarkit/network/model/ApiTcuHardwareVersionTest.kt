package com.daimler.mbcarkit.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiTcuHardwareVersionTest {

    @Test
    fun `Mapping 'null' ApiTcuHardwareVersion should default to 'null'`() {
        Assertions.assertNull(null.toTcuHardwareVersion())
    }

    @ParameterizedTest
    @EnumSource(ApiTcuHardwareVersion::class)
    fun `Mapping from ApiTcuHardwareVersion to TcuHardwareVersion enum`(apiTcuHardwareVersion: ApiTcuHardwareVersion) {
        Assertions.assertEquals(apiTcuHardwareVersion.name, apiTcuHardwareVersion.toTcuHardwareVersion()?.name)
    }
}
