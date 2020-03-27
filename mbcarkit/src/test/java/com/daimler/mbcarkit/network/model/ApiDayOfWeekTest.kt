package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.Day
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiDayOfWeekTest {

    @ParameterizedTest
    @EnumSource(ApiDayOfWeek::class)
    fun `Mapping from ApiDayOfWeek to DayOfWeek enum`(apiDayOfWeek: ApiDayOfWeek) {
        Assertions.assertEquals(apiDayOfWeek.name, apiDayOfWeek.toDay()?.name)
    }

    @ParameterizedTest
    @EnumSource(Day::class)
    fun `Mapping ApiDayOfWeek from DayOfWeek enum`(day: Day) {
        if (day == Day.UNKNOWN) {
            Assertions.assertEquals(null, ApiDayOfWeek.fromDay(day)?.name)
        } else {
            Assertions.assertEquals(day.name, ApiDayOfWeek.fromDay(day)?.name)
        }
    }
}
