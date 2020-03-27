package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiPrerequisiteCheckTest {

    @Test
    fun `map ApiPrerequisiteCheck to PrerequisiteCheck`(softly: SoftAssertions) {
        val apiPrerequisiteCheck = ApiPrerequisiteCheck(
            ApiPrerequisiteType.CONSENT,
            listOf(ApiServiceMissingFields.LICENSE_PLATE),
            listOf(ApiAllowedServiceActions.EDIT_USER_PROFILE)
        )
        val prerequisiteCheck = apiPrerequisiteCheck.toPrerequisiteCheck()

        softly.assertThat(prerequisiteCheck.name).isEqualTo(apiPrerequisiteCheck.name.name)
        softly.assertThat(prerequisiteCheck.missingInformation[0].name).isEqualTo(apiPrerequisiteCheck.missingFields?.get(0)?.name)
        softly.assertThat(prerequisiteCheck.actions[0].name).isEqualTo(apiPrerequisiteCheck.actions?.get(0)?.name)
    }
}
