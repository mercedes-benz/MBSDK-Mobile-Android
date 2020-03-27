package com.daimler.mbcarkit.business.model.vehicle

import org.junit.Assert
import org.junit.Test

class FuelTypeTest {

    @Test
    fun testCorrectEnumPositionsOfFuelType() {
        val states = FuelType.values()
        Assert.assertEquals(FuelType.GAS, states[0])
        Assert.assertEquals(FuelType.ELECTRIC, states[1])
        Assert.assertEquals(FuelType.FUEL_CELL_PLUGIN, states[2])
        Assert.assertEquals(FuelType.HYBRID, states[3])
        Assert.assertEquals(FuelType.PLUGIN, states[4])
        Assert.assertEquals(FuelType.COMBUSTION, states[5])
    }
}
