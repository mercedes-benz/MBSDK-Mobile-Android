package com.daimler.mbprotokit.dto.car.headunit

import com.daimler.mbprotokit.generated.VehicleEvents

data class WeeklyProfile(
    // Determines whether a single time profile entry is activatable
    val singleEntriesActivatable: Boolean?,
    // Maximum number of weekly time profile slots
    val maxSlots: Int?,
    // Maximum number of time profiles
    val maxTimeProfiles: Int?,
    // Current number of time profile slots
    val currentSlots: Int?,
    // Current number of time profiles
    val currentTimeProfiles: Int?,

    // Time profiles
    val allTimeProfiles: MutableList<TimeProfile> = mutableListOf(),
    // Backup to detected which time profiles changed.
    val backupProfiles: Map<Int, TimeProfile> = mutableMapOf()
) {
    companion object {
        fun mapToWeeklyProfile(): (VehicleEvents.WeeklyProfileValue?) -> WeeklyProfile? = {
            it?.let { weeklyProfileValue ->
                WeeklyProfile(
                    singleEntriesActivatable = weeklyProfileValue.singleTimeProfileEntriesActivatable,
                    maxSlots = weeklyProfileValue.maxNumberOfWeeklyTimeProfileSlots,
                    maxTimeProfiles = weeklyProfileValue.maxNumberOfTimeProfiles,
                    currentSlots = weeklyProfileValue.currentNumberOfTimeProfileSlots,
                    currentTimeProfiles = weeklyProfileValue.currentNumberOfTimeProfiles,
                    allTimeProfiles = weeklyProfileValue.timeProfilesList?.map { timeProfile ->
                        TimeProfile.map(timeProfile)
                    }?.toMutableList() ?: mutableListOf()
                )
            }
        }
    }
}
