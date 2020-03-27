package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiQrAssignmentResponseTest {

    @Test
    fun `map ApiQrAssignmentResponse to QrAssignmentResponse`(softly: SoftAssertions) {
        val apiQrAssignmentResponse = ApiQrAssignmentResponse("", ApiAssignmentType.OWNER, "")
        val qrAssignmentResponse = apiQrAssignmentResponse.toQRAssignment()

        softly.assertThat(qrAssignmentResponse.finOrVin).isEqualTo(apiQrAssignmentResponse.vin)
        softly.assertThat(qrAssignmentResponse.type.name).isEqualTo(apiQrAssignmentResponse.assignmentType?.name)
        softly.assertThat(qrAssignmentResponse.model).isEqualTo(apiQrAssignmentResponse.model)
    }
}
