package com.daimler.mbcarkit.utils

import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import com.daimler.mbcarkit.business.model.services.MissingAccountLinkage
import com.daimler.mbcarkit.business.model.services.MissingServiceData
import com.daimler.mbcarkit.business.model.services.ServiceAction
import com.daimler.mbcarkit.business.model.services.ServiceRight
import com.daimler.mbcarkit.business.model.services.ServiceStatus
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class ObjectsTest {

    @Test
    fun `verify createService()`(softly: SoftAssertions) {
        val missingData = MissingServiceData(MissingAccountLinkage(true, AccountType.CHARGING))
        val service = createService(
            id = INT_VALUE,
            name = STRING_VALUE,
            description = STRING_VALUE,
            shortName = STRING_VALUE,
            categoryName = STRING_VALUE,
            allowedActions = listOf(ServiceAction.ACCOUNT_LINKAGE),
            activationStatus = ServiceStatus.ACTIVATION_PENDING,
            desiredActivationStatus = ServiceStatus.ACTIVE,
            actualActivationServiceStatus = ServiceStatus.UNKNOWN,
            virtualActivationServiceStatus = ServiceStatus.DEACTIVATION_PENDING,
            prerequisiteChecks = emptyList(),
            rights = listOf(ServiceRight.EXECUTE, ServiceRight.ACTIVATE),
            missingServiceData = missingData
        )

        softly.assertThat(service.id).isEqualTo(INT_VALUE)
        softly.assertThat(service.name).isEqualTo(STRING_VALUE)
        softly.assertThat(service.description).isEqualTo(STRING_VALUE)
        softly.assertThat(service.shortName).isEqualTo(STRING_VALUE)
        softly.assertThat(service.categoryName).isEqualTo(STRING_VALUE)
        softly.assertThat(service.allowedActions).isEqualTo(listOf(ServiceAction.ACCOUNT_LINKAGE))
        softly.assertThat(service.activationStatus).isEqualTo(ServiceStatus.ACTIVATION_PENDING)
        softly.assertThat(service.desiredActivationStatus).isEqualTo(ServiceStatus.ACTIVE)
        softly.assertThat(service.actualActivationServiceStatus).isEqualTo(ServiceStatus.UNKNOWN)
        softly.assertThat(service.virtualActivationServiceStatus).isEqualTo(ServiceStatus.DEACTIVATION_PENDING)
        softly.assertThat(service.prerequisiteChecks).isEmpty()
        softly.assertThat(service.rights).isEqualTo(listOf(ServiceRight.EXECUTE, ServiceRight.ACTIVATE))
        softly.assertThat(service.missingData).isEqualTo(missingData)
    }

    @Test
    fun `verify createMissingAccountLinkage()`(softly: SoftAssertions) {
        val missingLinkage = createMissingAccountLinkage(
            true,
            AccountType.CHARGING
        )
        softly.assertThat(missingLinkage.mandatory).isTrue
        softly.assertThat(missingLinkage.type).isEqualTo(AccountType.CHARGING)
    }

    @Test
    fun `verify createMissingServiceData()`(softly: SoftAssertions) {
        val missingData = createMissingServiceData(
            MissingAccountLinkage(
                true,
                AccountType.IN_CAR_OFFICE
            )
        )
        softly.assertThat(missingData.missingAccountLinkage).isNotNull
        softly.assertThat(missingData.missingAccountLinkage?.mandatory).isTrue
        softly.assertThat(missingData.missingAccountLinkage?.type).isEqualTo(AccountType.IN_CAR_OFFICE)
    }

    private companion object {

        private const val INT_VALUE = 1
        private const val STRING_VALUE = "string"
    }
}
