package com.daimler.mbcarkit.business.model.vehicle

import org.junit.Assert
import org.junit.Test

class LightTypeTest {

    @Test
    fun testCorrectEnumPositionsOfLightType() {
        val states = LightType.values()
        Assert.assertEquals(LightType.LIGHT_OFF, states[0])
        Assert.assertEquals(LightType.DIPPED_HEAD_LIGHT, states[1])
        Assert.assertEquals(LightType.WARNING_LIGHT, states[2])
    }
}
