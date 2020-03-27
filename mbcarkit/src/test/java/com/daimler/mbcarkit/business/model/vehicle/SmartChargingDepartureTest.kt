package com.daimler.mbcarkit.business.model.vehicle

import org.junit.Assert
import org.junit.Test

class SmartChargingDepartureTest {

    @Test
    fun testCorrectEnumPositionsOfSmartChargingDeparture() {
        val states = SmartChargingDeparture.values()
        Assert.assertEquals(SmartChargingDeparture.INACTIVE, states[0])
        Assert.assertEquals(SmartChargingDeparture.REQUESTED, states[1])
        Assert.assertEquals(SmartChargingDeparture.UNKNOWN, states[2])
    }
}
