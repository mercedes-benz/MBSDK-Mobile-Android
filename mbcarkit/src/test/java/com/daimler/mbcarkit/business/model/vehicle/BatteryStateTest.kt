package com.daimler.mbcarkit.business.model.vehicle

import org.junit.Assert.assertEquals
import org.junit.Test

class BatteryStateTest {

    @Test
    fun testCorrectEnumPositionsOfBatteryStates() {
        val states = BatteryState.values()
        assertEquals(BatteryState.GREEN, states[0])
        assertEquals(BatteryState.YELLOW, states[1])
        assertEquals(BatteryState.RED, states[2])
        assertEquals(BatteryState.SERVICE_DISABLED, states[3])
        assertEquals(BatteryState.VEHICLE_NOT_AVAILABLE, states[4])
        assertEquals(BatteryState.UNKNOWN, states[5])
    }
}
