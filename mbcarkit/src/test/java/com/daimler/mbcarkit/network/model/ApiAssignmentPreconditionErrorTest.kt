package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiAssignmentPreconditionErrorTest {

    @Test
    fun `map ApiAssignmentPreconditionError to AssignmentPreconditionError`(softly: SoftAssertions) {
        val apiAssignmentPreconditionError = ApiAssignmentPreconditionError(
            "vin",
            ApiAssignmentType.OWNER,
            true,
            true,
            "designation",
            "baumuster",
            "description"
        )
        val assignmentPreconditionError = apiAssignmentPreconditionError.toAssignmentPreconditionError()

        softly.assertThat(assignmentPreconditionError.finOrVin).isEqualTo(apiAssignmentPreconditionError.finOrVin)
        softly.assertThat(assignmentPreconditionError.assignmentType.name).isEqualTo(apiAssignmentPreconditionError.assignmentType?.name)
        softly.assertThat(assignmentPreconditionError.termsOfUseRequired).isEqualTo(apiAssignmentPreconditionError.termsOfUseRequired)
        softly.assertThat(assignmentPreconditionError.mePinRequired).isEqualTo(apiAssignmentPreconditionError.mePinRequired)
        softly.assertThat(assignmentPreconditionError.salesDesignation).isEqualTo(apiAssignmentPreconditionError.salesDesignation)
        softly.assertThat(assignmentPreconditionError.baumuster).isEqualTo(apiAssignmentPreconditionError.baumuster)
        softly.assertThat(assignmentPreconditionError.baumusterDescription).isEqualTo(apiAssignmentPreconditionError.baumusterDescription)
    }
}
