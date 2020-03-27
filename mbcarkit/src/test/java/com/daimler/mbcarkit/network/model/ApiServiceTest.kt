package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiServiceTest {

    @Test
    fun `map ApiService to Service`(softly: SoftAssertions) {
        val apiService = ApiService(
            0,
            "name",
            "description",
            "shortDescription",
            "shortName",
            "categoryName",
            listOf(ApiAllowedServiceActions.EDIT_USER_PROFILE),
            ApiServiceStatus.ACTIVATION_PENDING,
            ApiServiceStatus.ACTIVE,
            ApiServiceStatus.ACTIVE,
            ApiServiceStatus.ACTIVE,
            listOf(ApiPrerequisiteCheck(ApiPrerequisiteType.CONSENT, emptyList(), emptyList())),
            listOf(ApiServiceRight.ACTIVATE),
            ApiMissingServiceData(ApiMissingAccountLinkage(true, ApiAccountType.CHARGING))
        )
        val service = apiService.toService()

        softly.assertThat(service.id).isEqualTo(apiService.id)
        softly.assertThat(service.name).isEqualTo(apiService.name)
        softly.assertThat(service.description).isEqualTo(apiService.shortDescription)
        softly.assertThat(service.shortName).isEqualTo(apiService.shortName)
        softly.assertThat(service.categoryName).isEqualTo(apiService.categoryName)
        softly.assertThat(service.allowedActions[0].name).isEqualTo(apiService.allowedActions[0]?.name)
        softly.assertThat(service.activationStatus.name).isEqualTo(apiService.activationStatus?.name)
        softly.assertThat(service.desiredActivationStatus.name).isEqualTo(apiService.desiredServiceStatus?.name)
        softly.assertThat(service.actualActivationServiceStatus.name).isEqualTo(apiService.actualActivationServiceStatus?.name)
        softly.assertThat(service.virtualActivationServiceStatus.name).isEqualTo(apiService.virtualActivationServiceStatus?.name)
        softly.assertThat(service.prerequisiteChecks[0].name).isEqualTo(apiService.prerequisiteCheck[0]?.name?.name)
        softly.assertThat(service.rights[0].name).isEqualTo(apiService.rights[0]?.name)
        softly.assertThat(service.missingData?.missingAccountLinkage?.mandatory).isTrue
        softly.assertThat(service.missingData?.missingAccountLinkage?.type).isEqualTo(AccountType.CHARGING)
    }
}
