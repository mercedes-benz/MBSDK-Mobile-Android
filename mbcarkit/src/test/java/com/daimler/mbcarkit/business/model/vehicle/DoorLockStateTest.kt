package com.daimler.mbcarkit.business.model.vehicle

import org.junit.Assert
import org.junit.Test

class DoorLockStateTest {

    @Test
    fun testCorrectEnumPositionsOfDoorLockStates() {
        val states = DoorLockState.values()
        Assert.assertEquals(DoorLockState.UNLOCKED, states[0])
        Assert.assertEquals(DoorLockState.LOCKED_INTERNAL, states[1])
        Assert.assertEquals(DoorLockState.LOCKED_EXTERNAL, states[2])
        Assert.assertEquals(DoorLockState.UNLOCKED_SELECTIVE, states[3])
        Assert.assertEquals(DoorLockState.UNKNOWN, states[4])
    }
}
