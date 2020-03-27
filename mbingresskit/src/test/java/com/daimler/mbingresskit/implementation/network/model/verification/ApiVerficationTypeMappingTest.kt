package com.daimler.mbingresskit.implementation.network.model.verification

import com.daimler.mbingresskit.common.VerificationType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class ApiVerficationTypeMappingTest {

    @ParameterizedTest
    @EnumSource(value = VerificationType::class)
    fun `map ApiVerificationType from VerificationType`(value: VerificationType) {
        Assertions.assertEquals(value.name, ApiVerificationType.fromVerificationType(value).name)
    }
}
