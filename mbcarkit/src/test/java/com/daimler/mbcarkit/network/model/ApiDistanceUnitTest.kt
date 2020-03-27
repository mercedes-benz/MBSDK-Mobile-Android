package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.valetprotect.DistanceUnit
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiDistanceUnitTest {

    @Test
    fun `Mapping 'null' ApiDistanceUnit should default to UNKNOWN`() {
        Assertions.assertEquals(DistanceUnit.UNKNOWN, null.toDistanceUnit())
    }

    @ParameterizedTest
    @EnumSource(ApiDistanceUnit::class)
    fun `Mapping from ApiDistanceUnit to DistanceUnit enum`(apiDistanceUnit: ApiDistanceUnit) {
        Assertions.assertEquals(apiDistanceUnit.name, apiDistanceUnit.toDistanceUnit().name)
    }

    @ParameterizedTest
    @EnumSource(DistanceUnit::class)
    fun `Mapping from DistanceUnit to ApiDistanceUnit enum`(distanceUnit: DistanceUnit) {
        if (distanceUnit == DistanceUnit.UNKNOWN) {
            Assertions.assertEquals(ApiDistanceUnit.KM.name, ApiDistanceUnit.fromDistanceUnit(distanceUnit).name)
        } else {
            Assertions.assertEquals(distanceUnit.name, ApiDistanceUnit.fromDistanceUnit(distanceUnit).name)
        }
    }
}
