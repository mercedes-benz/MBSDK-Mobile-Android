package com.daimler.mbcarkit.business.model.command

import com.daimler.mbcarkit.business.model.vehicle.Day
import com.daimler.mbcarkit.business.model.vehicle.TimeProfile
import com.daimler.mbcarkit.business.model.vehicle.WeeklyProfile
import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbprotokit.generated.Client
import org.junit.Assert
import org.junit.Test

class CarVehicleApiCommandTest {
    @Test
    fun testConversionOfWeeklyProfileCommand() {
        val protoMessage = CarVehicleApiCommand(
            VehicleCommand.WeekProfileConfigureV2(
                "vin",
                WeeklyProfile(
                    true,
                    0,
                    10,
                    0,
                    10,
                    mutableListOf(
                        TimeProfile(42, 4, 30, true, setOf(Day.WEDNESDAY), 11),
                        TimeProfile(null, 15, 35, false, setOf(Day.THURSDAY, Day.FRIDAY), 11),
                        TimeProfile(43, 7, 30, true, setOf(Day.TUESDAY), 11)
                    ),
                    mapOf(
                        42 to TimeProfile(42, 5, 30, true, setOf(Day.WEDNESDAY), 11),
                        43 to TimeProfile(43, 7, 30, true, setOf(Day.TUESDAY), 11)
                    )
                )
            )
        ).parse()
        val messageAsByte = (protoMessage as DataSocketMessage.ByteSocketMessage).bytes
        val commandRequest = Client.ClientMessage.parseFrom(messageAsByte).commandRequest
        Assert.assertNotNull(commandRequest)
        val weekProfileCommand = commandRequest.weekProfileConfigureV2
        Assert.assertNotNull(weekProfileCommand)
        Assert.assertEquals(3, weekProfileCommand.timeProfilesCount)
        Assert.assertEquals(true, weekProfileCommand.timeProfilesList[0].active.value)
        Assert.assertEquals(1, weekProfileCommand.timeProfilesList[0].daysCount)
        Assert.assertEquals(4, weekProfileCommand.timeProfilesList[0].hour.value)
        Assert.assertEquals(30, weekProfileCommand.timeProfilesList[0].minute.value)
        Assert.assertEquals(42, weekProfileCommand.timeProfilesList[0].identifier.value)
        Assert.assertEquals(-1, weekProfileCommand.timeProfilesList[0].applicationIdentifier)
        Assert.assertTrue(weekProfileCommand.timeProfilesList[0].hasIdentifier())
        Assert.assertEquals(false, weekProfileCommand.timeProfilesList[1].active.value)
        Assert.assertEquals(2, weekProfileCommand.timeProfilesList[1].daysCount)
        Assert.assertEquals(15, weekProfileCommand.timeProfilesList[1].hour.value)
        Assert.assertEquals(35, weekProfileCommand.timeProfilesList[1].minute.value)
        Assert.assertFalse(weekProfileCommand.timeProfilesList[1].hasIdentifier())
        Assert.assertEquals(-1, weekProfileCommand.timeProfilesList[1].applicationIdentifier)
        Assert.assertEquals(true, weekProfileCommand.timeProfilesList[2].active.value)
        Assert.assertEquals(1, weekProfileCommand.timeProfilesList[2].daysCount)
        Assert.assertEquals(7, weekProfileCommand.timeProfilesList[2].hour.value)
        Assert.assertEquals(30, weekProfileCommand.timeProfilesList[2].minute.value)
        Assert.assertEquals(43, weekProfileCommand.timeProfilesList[2].identifier.value)
        Assert.assertEquals(11, weekProfileCommand.timeProfilesList[2].applicationIdentifier)
    }
}
