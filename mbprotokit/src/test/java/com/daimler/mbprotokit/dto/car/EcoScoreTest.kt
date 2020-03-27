package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.random.Random

@ExtendWith(SoftAssertionsExtension::class)
class EcoScoreTest {

    @Test
    fun `ecoScore full random mapping`(softly: SoftAssertions) {
        val accel = Random.nextInt()
        val bonusRange = Random.nextDouble()
        val freeWhl = Random.nextInt()
        val const = Random.nextInt()
        val total = Random.nextInt()

        val attribute = mapOf(
            ApiVehicleKey.ECO_SCORE_ACCELL.id to VehicleEventUtil.createAttribute(accel),
            ApiVehicleKey.ECO_SCORE_BONUS_RANGE.id to VehicleEventUtil.createAttribute(bonusRange),
            ApiVehicleKey.ECO_SCORE_FREE_WHL.id to VehicleEventUtil.createAttribute(freeWhl),
            ApiVehicleKey.ECO_SCORE_CONST.id to VehicleEventUtil.createAttribute(const),
            ApiVehicleKey.ECO_SCORE_TOTAL.id to VehicleEventUtil.createAttribute(total)
        )

        val ecoScore = EcoScore(attribute)

        softly.assertThat(ecoScore.accel.first).isEqualTo(accel)
        softly.assertThat(ecoScore.bonusRange.first).isEqualTo(bonusRange)
        softly.assertThat(ecoScore.freeWhl.first).isEqualTo(freeWhl)
        softly.assertThat(ecoScore.const.first).isEqualTo(const)
        softly.assertThat(ecoScore.total.first).isEqualTo(total)
    }
}
