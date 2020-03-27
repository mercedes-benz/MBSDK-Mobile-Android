package com.daimler.mbprotokit.dto.car.headunit

import com.daimler.mbprotokit.dto.car.Day
import com.daimler.mbprotokit.generated.VehicleEvents
import java.util.EnumSet

// Application identifier used for entries created by the user in the app.
internal const val APPLICATION_IDENTIFIER_FOR_NEW_ENTRY = -1

data class TimeProfile(
    // a unique identifier of this time profile entry
    val identifier: Int? = null,
    // Hour after midnight range [0, 23]
    var hour: Int,
    // Minute after full hour range [0, 59]
    var minute: Int,
    // Whether this profile entry is active or not
    var active: Boolean,
    // Days for which the above time should be applied
    var days: Set<Day>,

    var applicationIdentifier: Int = APPLICATION_IDENTIFIER_FOR_NEW_ENTRY,
    var toBeRemoved: Boolean = false
) {

    companion object {
        fun map(timeProfile: VehicleEvents.VVRTimeProfile) = TimeProfile(
            timeProfile.identifier,
            timeProfile.hour,
            timeProfile.minute,
            timeProfile.active,
            EnumSet.copyOf(timeProfile.daysList.map { day -> Day.map(day.number) }),
            timeProfile.applicationIdentifier,
            false
        )
    }
}
