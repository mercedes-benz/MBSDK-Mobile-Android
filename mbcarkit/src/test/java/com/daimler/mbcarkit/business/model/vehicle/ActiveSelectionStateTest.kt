package com.daimler.mbcarkit.business.model.vehicle

import org.junit.Assert
import org.junit.Test

class ActiveSelectionStateTest {

    @Test
    fun testCorrectEnumPositionsOfActiveSelectionState() {
        val states = ActiveSelectionState.values()
        Assert.assertEquals(ActiveSelectionState.NOT_ACTIVE_SELECTED, states[0])
        Assert.assertEquals(ActiveSelectionState.NOT_ACTIVE_UNSELECTED, states[1])
        Assert.assertEquals(ActiveSelectionState.ACTIVE, states[2])
        Assert.assertEquals(ActiveSelectionState.UNKNOWN, states[3])
    }
}
