package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.onboardfence.FenceType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.fail
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiFenceTypeTest {

    @ParameterizedTest
    @EnumSource(ApiFenceType::class)
    fun `Mapping from ApiFenceType to FenceType enum`(apiFenceType: ApiFenceType) {
        val actual = apiFenceType.toFenceType()
        val expected = when (apiFenceType) {
            ApiFenceType.CIRCLE -> FenceType.CIRCLE
            ApiFenceType.POLYGON -> FenceType.POLYGON
            else -> fail("Not all conditions covered!")
        }
        Assertions.assertEquals(expected, actual)
    }
}
