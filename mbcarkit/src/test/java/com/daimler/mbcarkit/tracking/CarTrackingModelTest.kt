package com.daimler.mbcarkit.tracking

import com.daimler.mbcarkit.business.model.command.CommandCondition
import com.daimler.mbcarkit.business.model.command.VehicleCommandStatus
import org.junit.Assert
import org.junit.Test

class CarTrackingModelTest {

    @Test
    fun `tracking model to map with null`() {
        val model = CarTrackingModel(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val expectedMap = mapOf(
            "id" to "null",
            "carSeries" to "null",
            "timestamp" to "null",
            "state" to "null",
            "condition" to "null",
            "responseCode" to "null",
            "sdkVersion" to "null",
            "intValue" to "null",
            "boolValue" to "null",
            "commandDuration" to "null",
            "stateType" to "null"
        )
        val result = model.toTrackingMap()
        result.keys.forEach {
            Assert.assertEquals(expectedMap[it], result[it])
        }
    }

    @Test
    fun `tracking model to map with data`() {
        val model = CarTrackingModel(
            null,
            null,
            null,
            null,
            CommandCondition.NONE,
            null,
            null,
            1,
            true,
            123L,
            VehicleCommandStatus.ABOUT_TO_SEND
        )
        val expectedMap = mapOf(
            "id" to "null",
            "carSeries" to "null",
            "timestamp" to "null",
            "state" to "null",
            "condition" to "NONE",
            "responseCode" to "null",
            "sdkVersion" to "null",
            "intValue" to "1",
            "boolValue" to "true",
            "commandDuration" to "123",
            "stateType" to "ABOUT_TO_SEND"
        )
        val result = model.toTrackingMap()
        result.keys.forEach {
            Assert.assertEquals(expectedMap[it], result[it])
        }
    }
}
