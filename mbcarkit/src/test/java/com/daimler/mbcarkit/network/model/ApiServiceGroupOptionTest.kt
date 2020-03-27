package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.services.ServiceGroupOption
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiServiceGroupOptionTest {

    @ParameterizedTest
    @EnumSource(ServiceGroupOption::class)
    fun `Mapping from ServiceGroupOption to ApiServiceGroupOption enum`(serviceGroupOption: ServiceGroupOption) {
        if (serviceGroupOption == ServiceGroupOption.NONE) {
            Assertions.assertNull(ApiServiceGroupOption.fromServiceGroupOption(serviceGroupOption)?.name)
        } else {
            Assertions.assertEquals(serviceGroupOption.name, ApiServiceGroupOption.fromServiceGroupOption(serviceGroupOption)?.name)
        }
    }
}
