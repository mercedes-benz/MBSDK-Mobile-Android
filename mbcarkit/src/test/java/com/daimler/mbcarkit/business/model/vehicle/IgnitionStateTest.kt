package com.daimler.mbcarkit.business.model.vehicle

import org.junit.Assert
import org.junit.Test

class IgnitionStateTest {

    @Test
    fun testCorrectEnumPositionsOfIgnitionState() {
        val states = IgnitionState.values()
        Assert.assertEquals(IgnitionState.LOCK, states[0])
        Assert.assertEquals(IgnitionState.OFF, states[1])
        Assert.assertEquals(IgnitionState.ACCESSORY, states[2])
        Assert.assertEquals(IgnitionState.ON, states[3])
        Assert.assertEquals(IgnitionState.START, states[4])
        Assert.assertEquals(IgnitionState.UNKNOWN, states[5])
    }
}
