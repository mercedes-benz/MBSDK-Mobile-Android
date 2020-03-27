package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.ClockHourUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.DisplayUnitCase
import org.junit.Assert
import org.junit.Test
import java.util.Date

class VehicleStatusTest {

    var myunit = VehicleAttribute.Unit("", DisplayUnitCase.CLOCK_HOUR_UNIT, ClockHourUnit.UNSPECIFIED_CLOCK_HOUR_UNIT)

    @Test
    fun testIfBatteryStateIsMappedCorrectly_From0ToGreen() {
        val vehicleAttribute = VehicleAttribute(StatusEnum.VALID, Date(), 0, myunit)
        val batteryState = VehicleStatus.batteryStateFromInt(vehicleAttribute)
        Assert.assertEquals(BatteryState.GREEN, batteryState.value)
    }

    @Test
    fun testIfBatteryStateIsMappedCorrectly_From1ToYELLOW() {
        val vehicleAttribute = VehicleAttribute(StatusEnum.VALID, Date(), 1, myunit)
        val batteryState = VehicleStatus.batteryStateFromInt(vehicleAttribute)
        Assert.assertEquals(BatteryState.YELLOW, batteryState.value)
    }

    @Test
    fun testIfBatteryStateIsMappedCorrectly_From2ToRED() {
        val vehicleAttribute = VehicleAttribute(StatusEnum.VALID, Date(), 2, myunit)
        val batteryState = VehicleStatus.batteryStateFromInt(vehicleAttribute)
        Assert.assertEquals(BatteryState.RED, batteryState.value)
    }

    @Test
    fun testIfBatteryStateIsMappedCorrectly_From3toServiceDisabled() {
        val vehicleAttribute = VehicleAttribute(StatusEnum.VALID, Date(), 3, myunit)
        val batteryState = VehicleStatus.batteryStateFromInt(vehicleAttribute)
        Assert.assertEquals(BatteryState.SERVICE_DISABLED, batteryState.value)
    }

    @Test
    fun testIfKeyActivationStateIsMappedCorrectly_From1ToActive() {
        val vehicleAttribute = VehicleAttribute(StatusEnum.VALID, Date(), 1, myunit)
        val kas = VehicleStatus.keyActivationStateFromInt(vehicleAttribute)
        Assert.assertEquals(KeyActivationState.ALL_KEYS_ACTIVE, kas.value)
    }

    @Test
    fun testIfKeyActivationStateIsMappedCorrectly_From0ToInactive() {
        val vehicleAttribute = VehicleAttribute(StatusEnum.VALID, Date(), 0, myunit)
        val kas = VehicleStatus.keyActivationStateFromInt(vehicleAttribute)
        Assert.assertEquals(KeyActivationState.ALL_KEYS_INACTIVE, kas.value)
    }
}
