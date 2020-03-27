package com.daimler.mbprotokit.dto.car.vehicledata

import com.daimler.mbprotokit.dto.car.VehicleEventUtil
import com.daimler.mbprotokit.dto.car.doors.DoorLockOverallStatus
import com.daimler.mbprotokit.dto.car.doors.DoorLockState
import com.daimler.mbprotokit.dto.car.random
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.random.Random

@ExtendWith(SoftAssertionsExtension::class)
class VehicleDataTest {

    @Test
    fun `vehicle data full random mapping`(softly: SoftAssertions) {
        val batteryState = random<BatteryState>()
        val vehicleDataConnectionState = Random.nextBoolean()
        val engineHoodStatus = Random.nextBoolean()
        val filterParticleState = random<FilterParticleState>()
        val odo = Random.nextInt()
        val doorLockStateGas = Random.nextBoolean()
        val rooftopState = random<RooftopState>()
        val serviceIntervalDays = Random.nextInt()
        val lockStateOverall = random<DoorLockOverallStatus>()
        val soc = Random.nextInt()
        val vTime = Random.nextLong()
        val speedUnitFromIc = Random.nextBoolean()
        val parkBrakeState = Random.nextBoolean()
        val vehicleLockState = random<DoorLockState>()

        val attributes = mapOf(
            ApiVehicleKey.STARTER_BATTERY_STATE.id to VehicleEventUtil.createAttribute(batteryState.id),
            ApiVehicleKey.VEHICLE_DATA_CONNECTION_STATE.id to VehicleEventUtil.createAttribute(vehicleDataConnectionState),
            ApiVehicleKey.ENGINE_HOOD_STATUS.id to VehicleEventUtil.createAttribute(engineHoodStatus),
            ApiVehicleKey.FILTER_PARTICLE_LOADING.id to VehicleEventUtil.createAttribute(filterParticleState.id),
            ApiVehicleKey.ODO.id to VehicleEventUtil.createAttribute(odo),
            ApiVehicleKey.DOOR_LOCK_STATUS_GAS.id to VehicleEventUtil.createAttribute(doorLockStateGas),
            ApiVehicleKey.ROOFTOP_STATUS.id to VehicleEventUtil.createAttribute(rooftopState.id),
            ApiVehicleKey.SERVICE_INTERVAL_DAYS.id to VehicleEventUtil.createAttribute(serviceIntervalDays),
            ApiVehicleKey.DOOR_LOCK_STATUS_OVERALL.id to VehicleEventUtil.createAttribute(lockStateOverall.id),
            ApiVehicleKey.SOC.id to VehicleEventUtil.createAttribute(soc),
            ApiVehicleKey.V_TIME.id to VehicleEventUtil.createAttribute(vTime),
            ApiVehicleKey.SPEED_UNIT_FROM_IC.id to VehicleEventUtil.createAttribute(speedUnitFromIc),
            ApiVehicleKey.PARK_BRAKE_STATUS.id to VehicleEventUtil.createAttribute(parkBrakeState),
            ApiVehicleKey.DOOR_LOCK_STATUS_VEHICLE.id to VehicleEventUtil.createAttribute(vehicleLockState.id)
        )

        val vehicleData = VehicleData(attributes)
        softly.assertThat(vehicleData.batteryState.first).isEqualTo(batteryState)
        softly.assertThat(vehicleData.vehicleDataConnectionState.first).isEqualTo(vehicleDataConnectionState)
        softly.assertThat(vehicleData.engineHoodStatus.first).isEqualTo(engineHoodStatus)
        softly.assertThat(vehicleData.filterParticleState.first).isEqualTo(filterParticleState)
        softly.assertThat(vehicleData.odo.first).isEqualTo(odo)
        softly.assertThat(vehicleData.doorLockStateGas.first).isEqualTo(doorLockStateGas)
        softly.assertThat(vehicleData.rooftopState.first).isEqualTo(rooftopState)
        softly.assertThat(vehicleData.serviceIntervalDays.first).isEqualTo(serviceIntervalDays)
        softly.assertThat(vehicleData.lockStateOverall.first).isEqualTo(lockStateOverall)
        softly.assertThat(vehicleData.soc.first).isEqualTo(soc)
        softly.assertThat(vehicleData.vTime.first).isEqualTo(vTime)
        softly.assertThat(vehicleData.speedUnitFromIc.first).isEqualTo(speedUnitFromIc)
        softly.assertThat(vehicleData.parkBrakeState.first).isEqualTo(parkBrakeState)
        softly.assertThat(vehicleData.vehicleLockState.first).isEqualTo(vehicleLockState)
    }

    @ParameterizedTest
    @EnumSource(BatteryState::class)
    fun `vehicle data batteryState mapping`(state: BatteryState, softly: SoftAssertions) {
        val vehicleData = VehicleData(mapOf(ApiVehicleKey.STARTER_BATTERY_STATE.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(vehicleData.batteryState.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(FilterParticleState::class)
    fun `vehicle data filterParticleState mapping`(
        state: FilterParticleState,
        softly: SoftAssertions
    ) {
        val vehicleData = VehicleData(mapOf(ApiVehicleKey.FILTER_PARTICLE_LOADING.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(vehicleData.filterParticleState.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(RooftopState::class)
    fun `vehicle data rooftopState mapping`(
        state: RooftopState,
        softly: SoftAssertions
    ) {
        val vehicleData = VehicleData(mapOf(ApiVehicleKey.ROOFTOP_STATUS.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(vehicleData.rooftopState.first).isEqualTo(state)
    }

    @ParameterizedTest
    @EnumSource(DoorLockOverallStatus::class)
    fun `vehicle data lockStateOverall mapping`(
        state: DoorLockOverallStatus,
        softly: SoftAssertions
    ) {
        val vehicleData = VehicleData(mapOf(ApiVehicleKey.DOOR_LOCK_STATUS_OVERALL.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(vehicleData.lockStateOverall.first).isEqualTo(state)
    }

    @Test
    fun `vehicle data speedAlertConfiguration mapping`(softly: SoftAssertions) {
        val speedAlertConfiguration = SpeedAlertConfiguration(
            Random.nextLong(),
            Random.nextInt(),
            "123"
        )
        val speedAlertConfigurations = listOf(speedAlertConfiguration)
        val vehicleData = VehicleData(mapOf(ApiVehicleKey.SPEED_ALERT_CONFIGURATION.id to VehicleEventUtil.createSpeedAlert(speedAlertConfigurations)))

        softly.assertThat(vehicleData.speedAlertConfiguration.first).isNotNull
        softly.assertThat(vehicleData.speedAlertConfiguration.first?.first()).isNotNull
        val configuration = vehicleData.speedAlertConfiguration.first?.first()
        softly.assertThat(configuration?.endTimestampInSeconds).isEqualTo(speedAlertConfiguration.endTimestampInSeconds)
        softly.assertThat(configuration?.thresholdDisplayValue).isEqualTo(speedAlertConfiguration.thresholdDisplayValue)
        softly.assertThat(configuration?.thresholdInKilometersPerHour).isEqualTo(speedAlertConfiguration.thresholdInKilometersPerHour)
    }

    @ParameterizedTest
    @EnumSource(DoorLockState::class)
    fun `vehicle data vehicleLockState mapping`(
        state: DoorLockState,
        softly: SoftAssertions
    ) {
        val vehicleData = VehicleData(mapOf(ApiVehicleKey.DOOR_LOCK_STATUS_VEHICLE.id to VehicleEventUtil.createAttribute(state.id)))
        softly.assertThat(vehicleData.vehicleLockState.first).isEqualTo(state)
    }
}
