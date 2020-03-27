package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.valetprotect.ValetprotectViolationType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiValetprotectViolationTypeTest {

    @Test
    fun `Mapping 'null' ApiValetprotectViolationType should default to UNKNOWN`() {
        Assertions.assertEquals(ValetprotectViolationType.UNKNOWN, null.toValetprotectViolationType())
    }

    @ParameterizedTest
    @EnumSource(ApiValetprotectViolationType::class)
    fun `Mapping from ApiValetprotectViolationType to ValetprotectViolationType enum`(apiValetprotectViolationType: ApiValetprotectViolationType) {
        Assertions.assertEquals(apiValetprotectViolationType.name, apiValetprotectViolationType.toValetprotectViolationType().name)
    }

    @ParameterizedTest
    @EnumSource(ValetprotectViolationType::class)
    fun `Mapping from ValetprotectViolationType to ApiValetprotectViolationType enum`(valetprotectViolationType: ValetprotectViolationType) {
        if (valetprotectViolationType == ValetprotectViolationType.UNKNOWN) {
            Assertions.assertEquals(ApiValetprotectViolationType.FENCE.name, ApiValetprotectViolationType.fromValetprotectViolationType(valetprotectViolationType).name)
        } else {
            Assertions.assertEquals(valetprotectViolationType.name, ApiValetprotectViolationType.fromValetprotectViolationType(valetprotectViolationType).name)
        }
    }
}
