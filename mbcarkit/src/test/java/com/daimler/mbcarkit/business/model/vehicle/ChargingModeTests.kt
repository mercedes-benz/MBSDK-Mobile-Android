package com.daimler.mbcarkit.business.model.vehicle

import org.junit.Assert
import org.junit.Test

class ChargingModeTests {

    @Test
    fun testCorrectEnumPositionsOfChargingMode() {
        val states = ChargingMode.values()
        Assert.assertEquals(ChargingMode.NONE, states[0])
        Assert.assertEquals(ChargingMode.COUNDUCTIVE_AC, states[1])
        Assert.assertEquals(ChargingMode.INDUCTIVE, states[2])
        Assert.assertEquals(ChargingMode.CONDUCTIVE_AC_INDUCTIVE, states[3])
        Assert.assertEquals(ChargingMode.CONDUCTIVE_DC, states[4])
        Assert.assertEquals(ChargingMode.UNKNOWN, states[5])
    }
}
