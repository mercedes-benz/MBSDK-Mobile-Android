package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.customerfence.CustomerFenceViolationType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.fail
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiCustomerFenceViolationTypeTest {

    @ParameterizedTest
    @EnumSource(ApiCustomerFenceViolationType::class)
    fun `Mapping from ApiCustomerFenceViolationType to CustomerFenceViolationType enum`(apiCustomerFenceViolationType: ApiCustomerFenceViolationType) {
        val actual = apiCustomerFenceViolationType.toCustomerFenceViolationType()
        val expected = when (apiCustomerFenceViolationType) {
            ApiCustomerFenceViolationType.ENTER -> CustomerFenceViolationType.ENTER
            ApiCustomerFenceViolationType.LEAVE -> CustomerFenceViolationType.LEAVE
            ApiCustomerFenceViolationType.LEAVE_AND_ENTER -> CustomerFenceViolationType.LEAVE_AND_ENTER
            else -> fail("Not all conditions covered!")
        }
        Assertions.assertEquals(expected, actual)
    }
}
