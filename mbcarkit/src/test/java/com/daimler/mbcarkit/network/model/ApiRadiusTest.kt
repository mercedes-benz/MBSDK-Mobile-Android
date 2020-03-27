package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.merchants.Radius
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiRadiusTest {

    @Test
    fun `Mapping from Radius to ApiRadius`(softly: SoftAssertions) {
        val radius = Radius("50", "km")
        val apiRadius = ApiRadius.fromRadius(radius)

        softly.assertThat(apiRadius.value).isEqualTo(radius.value)
        softly.assertThat(apiRadius.unit).isEqualTo(radius.unit)
    }
}
