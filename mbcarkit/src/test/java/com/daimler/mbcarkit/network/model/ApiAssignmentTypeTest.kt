package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.assignment.AssignmentType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiAssignmentTypeTest {

    @Test
    fun `Mapping 'null' ApiAssignmentType should default to UNKNOWN`() {
        Assertions.assertEquals(AssignmentType.UNKNOWN, null.toAssignmentType())
    }

    @ParameterizedTest
    @EnumSource(ApiAssignmentType::class)
    fun `Mapping from ApiAssignmentType to AssignmentType enum`(apiAssignmentType: ApiAssignmentType) {
        Assertions.assertEquals(apiAssignmentType.name, apiAssignmentType.toAssignmentType().name)
    }
}
