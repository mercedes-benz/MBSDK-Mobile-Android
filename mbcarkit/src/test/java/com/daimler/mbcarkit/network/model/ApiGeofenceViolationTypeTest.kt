package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.geofencing.GeofenceViolationType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiGeofenceViolationTypeTest {

    @Test
    fun `Mapping 'null' ApiGeofenceViolationType should default to LEAVE_AND_ENTER`() {
        Assertions.assertEquals(GeofenceViolationType.LEAVE_AND_ENTER, null.toGeofenceViolationType())
    }

    @ParameterizedTest
    @EnumSource(ApiGeofenceViolationType::class)
    fun `Mapping from ApiGeofenceViolationType to GeofenceViolationType enum`(apiGeofenceViolationType: ApiGeofenceViolationType) {
        Assertions.assertEquals(apiGeofenceViolationType.name, apiGeofenceViolationType.toGeofenceViolationType().name)
    }

    @ParameterizedTest
    @EnumSource(GeofenceViolationType::class)
    fun `Mapping from GeofenceViolationType to ApiGeofenceViolationType enum`(geofenceViolationType: GeofenceViolationType) {
        Assertions.assertEquals(geofenceViolationType.name, ApiGeofenceViolationType.fromGeofenceViolationType(geofenceViolationType).name)
    }
}
