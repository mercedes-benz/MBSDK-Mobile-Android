package com.daimler.mbcarkit.business.model.vehicle

import org.junit.Assert
import org.junit.Test

class ActiveStateTest {

    @Test
    fun testCorrectEnumPositionsOfActiveState() {
        val states = ActiveState.values()
        Assert.assertEquals(ActiveState.INACTIVE, states[0])
        Assert.assertEquals(ActiveState.ACTIVE, states[1])
        Assert.assertEquals(ActiveState.UNKNOWN, states[2])
    }
}
