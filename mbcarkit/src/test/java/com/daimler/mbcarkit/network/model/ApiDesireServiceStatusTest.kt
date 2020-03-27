package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.services.ServiceStatusDesire
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(SoftAssertionsExtension::class)
class ApiDesireServiceStatusTest {

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `map ApiDesireServiceStatus from ServiceStatusDesire`(activate: Boolean, softly: SoftAssertions) {
        val expectedApiServiceDesire = if (activate) ApiServiceDesire.ACTIVE else ApiServiceDesire.INACTIVE
        val serviceStatusDesire = ServiceStatusDesire(1, activate)
        val apiDesireServiceStatus = ApiDesireServiceStatus.fromServiceStatusDesire(serviceStatusDesire)

        softly.assertThat(apiDesireServiceStatus.serviceId).isEqualTo(serviceStatusDesire.serviceId)
        softly.assertThat(apiDesireServiceStatus.status).isEqualTo(expectedApiServiceDesire)
    }
}
