package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.random.Random

@ExtendWith(SoftAssertionsExtension::class)
class DecklidTest {

    @Test
    fun `decklid full random mapping`(softly: SoftAssertions) {
        val lockState = Random.nextBoolean()
        val state = Random.nextBoolean()

        val attributes = mapOf(
            ApiVehicleKey.DOOR_LOCK_STATUS_DECKLID.id to VehicleEventUtil.createAttribute(lockState),
            ApiVehicleKey.DECKLID_STATUS.id to VehicleEventUtil.createAttribute(state)
        )

        val decklid = Decklid(attributes)
        softly.assertThat(decklid.lockState.first).isEqualTo(lockState)
        softly.assertThat(decklid.state.first).isEqualTo(state)
    }
}
