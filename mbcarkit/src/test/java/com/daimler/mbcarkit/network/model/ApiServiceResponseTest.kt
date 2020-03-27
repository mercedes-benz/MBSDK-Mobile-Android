package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiServiceResponseTest {

    @Test
    fun `map ApiServiceResponse to ServiceGroup`(softly: SoftAssertions) {
        val apiServiceResponse = ApiServiceResponse(
            "group",
            listOf(
                ApiService(
                    2,
                    null,
                    null,
                    null,
                    null,
                    null,
                    emptyList(),
                    null,
                    null,
                    null,
                    null,
                    emptyList(),
                    emptyList(),
                    null
                )
            )
        )
        val serviceGroup = apiServiceResponse.toServiceGroup()

        softly.assertThat(serviceGroup.group).isEqualTo(apiServiceResponse.group)
        softly.assertThat(serviceGroup.services[0].id).isEqualTo(apiServiceResponse.services[0].id)
    }
}
