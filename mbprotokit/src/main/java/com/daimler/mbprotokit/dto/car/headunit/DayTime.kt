package com.daimler.mbprotokit.dto.car.headunit

import com.daimler.mbprotokit.dto.car.Day
import com.daimler.mbprotokit.generated.VehicleEvents

data class DayTime(
    /**
     * Day of week
     */
    val day: Day,

    /**
     * Time in minutes since midnight
     * Range: 0..1439
     */
    val time: Int
) {

    companion object {
        fun mapToDayTime(): (VehicleEvents.WeeklySettingsHeadUnitValue?) -> List<DayTime>? = {
            it?.weeklySettingsList?.map { setting ->
                DayTime(
                    Day.map(setting.day) ?: Day.MONDAY,
                    setting.minutesSinceMidnight
                )
            }
        }
    }
}
