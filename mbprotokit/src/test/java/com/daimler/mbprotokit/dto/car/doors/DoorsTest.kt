package com.daimler.mbprotokit.dto.car.doors

import com.daimler.mbprotokit.dto.car.VehicleEventUtil
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.random.Random

@ExtendWith(SoftAssertionsExtension::class)
class DoorsTest {

    @Test
    fun `doors full random mapping`(softly: SoftAssertions) {
        val lockStateFrontLeft = Random.nextBoolean()
        val lockStateFrontRight = Random.nextBoolean()
        val lockStateRearLeft = Random.nextBoolean()
        val lockStateRearRight = Random.nextBoolean()
        val stateFrontLeft = Random.nextBoolean()
        val stateFrontRight = Random.nextBoolean()
        val stateRearLeft = Random.nextBoolean()
        val stateRearRight = Random.nextBoolean()

        val attributes = mapOf(
            ApiVehicleKey.DOOR_LOCK_STATE_FRONT_LEFT.id to VehicleEventUtil.createAttribute(lockStateFrontLeft),
            ApiVehicleKey.DOOR_LOCK_STATE_FRONT_RIGHT.id to VehicleEventUtil.createAttribute(lockStateFrontRight),
            ApiVehicleKey.DOOR_LOCK_STATE_REAR_LEFT.id to VehicleEventUtil.createAttribute(lockStateRearLeft),
            ApiVehicleKey.DOOR_LOCK_STATE_REAR_RIGHT.id to VehicleEventUtil.createAttribute(lockStateRearRight),
            ApiVehicleKey.DOOR_STATE_FRONT_LEFT.id to VehicleEventUtil.createAttribute(stateFrontLeft),
            ApiVehicleKey.DOOR_STATE_FRONT_RIGHT.id to VehicleEventUtil.createAttribute(stateFrontRight),
            ApiVehicleKey.DOOR_STATE_REAR_LEFT.id to VehicleEventUtil.createAttribute(stateRearLeft),
            ApiVehicleKey.DOOR_STATE_REAR_RIGHT.id to VehicleEventUtil.createAttribute(stateRearRight)
        )

        val doors = Doors(attributes)

        softly.assertThat(doors.doorLockStateFrontLeft.first).isEqualTo(lockStateFrontLeft)
        softly.assertThat(doors.doorLockStateFrontRight.first).isEqualTo(lockStateFrontRight)
        softly.assertThat(doors.doorLockStateRearLeft.first).isEqualTo(lockStateRearLeft)
        softly.assertThat(doors.doorLockStateRearRight.first).isEqualTo(lockStateRearRight)
        softly.assertThat(doors.doorStateFrontLeft.first).isEqualTo(stateFrontLeft)
        softly.assertThat(doors.doorStateFrontRight.first).isEqualTo(stateFrontRight)
        softly.assertThat(doors.doorStateRearLeft.first).isEqualTo(stateRearLeft)
        softly.assertThat(doors.doorStateRearRight.first).isEqualTo(stateRearRight)
    }
}
