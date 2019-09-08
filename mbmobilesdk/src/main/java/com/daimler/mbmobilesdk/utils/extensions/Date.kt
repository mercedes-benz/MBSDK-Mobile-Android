package com.daimler.mbmobilesdk.utils.extensions

import java.util.*

internal fun Date.toCalendar(
    calendar: Calendar = Calendar.getInstance(),
    yearOffset: Int = 0,
    monthOffset: Int = 0,
    dayOffset: Int = 0
) = calendar.apply {
    time = this@toCalendar
    add(Calendar.YEAR, yearOffset)
    add(Calendar.MONTH, monthOffset)
    add(Calendar.DAY_OF_YEAR, dayOffset)
}