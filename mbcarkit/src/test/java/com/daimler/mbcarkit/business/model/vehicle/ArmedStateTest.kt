package com.daimler.mbcarkit.business.model.vehicle

import org.junit.Assert
import org.junit.Test

class ArmedStateTest {

    @Test
    fun testCorrectEnumPositionsOfArmedState() {
        val states = ArmedState.values()
        Assert.assertEquals(ArmedState.NOT_ARMED, states[0])
        Assert.assertEquals(ArmedState.ARMED, states[1])
        Assert.assertEquals(ArmedState.UNKNOWN, states[2])
    }
}
