package com.daimler.mbcarkit.business.model.vehicle

import org.junit.Assert
import org.junit.Test

class DoorLockOverallStatusTest {

    @Test
    fun testCorrectEnumPositionsOfDoorLockOverallStatus() {
        val states = DoorLockOverallStatus.values()
        Assert.assertEquals(DoorLockOverallStatus.LOCKED, states[0])
        Assert.assertEquals(DoorLockOverallStatus.UNLOCKED, states[1])
        Assert.assertEquals(DoorLockOverallStatus.NOT_EXISTING, states[2])
        Assert.assertEquals(DoorLockOverallStatus.UNKNOWN, states[3])
    }
}
