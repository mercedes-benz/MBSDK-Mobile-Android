package com.daimler.mbcarkit.business.model.vehicle

import org.junit.Assert
import org.junit.Test

class ChargingErrorTest {

    @Test
    fun testCorrectEnumPositionsOfChargingErrors() {
        val states = ChargingError.values()
        Assert.assertEquals(ChargingError.NONE, states[0])
        Assert.assertEquals(ChargingError.CABLE, states[1])
        Assert.assertEquals(ChargingError.CHARGING_DISORDER, states[2])
        Assert.assertEquals(ChargingError.CHARGING_STATION, states[3])
        Assert.assertEquals(ChargingError.CHARGING_TYPE, states[4])
        Assert.assertEquals(ChargingError.UNKNOWN, states[5])
    }
}
