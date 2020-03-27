package com.daimler.mbcarkit.network.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiTirePressureMonitoringStateTest {

    @Test
    fun `Mapping 'null' ApiTirePressureMonitoringState should default to 'null'`() {
        Assertions.assertNull(null.toTirePressureMonitoringState())
    }

    @ParameterizedTest
    @EnumSource(ApiTirePressureMonitoringState::class)
    fun `Mapping from ApiTirePressureMonitoringState to TirePressureMonitoringState enum`(apiTirePressureMonitoringState: ApiTirePressureMonitoringState) {
        Assertions.assertEquals(apiTirePressureMonitoringState.name, apiTirePressureMonitoringState.toTirePressureMonitoringState()?.name)
    }
}
