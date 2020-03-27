package com.daimler.mbcarkit.business.model.vehicle

data class WeeklyProfile(
    // Determines whether a single time profile entry is activatable
    val singleEntriesActivatable: Boolean,
    // Maximum number of weekly time profile slots
    val maxSlots: Int,
    // Maximum number of time profiles
    val maxTimeProfiles: Int,
    // Current number of time profile slots
    val currentSlots: Int,
    // Current number of time profiles
    val currentTimeProfiles: Int,

    // Time profiles
    internal val allTimeProfiles: MutableList<TimeProfile> = mutableListOf(),
    // Backup to detected which time profiles changed.
    internal val backupProfiles: Map<Int, TimeProfile> = mutableMapOf()
) {
    // Time profiles
    val timeProfiles: List<TimeProfile>
        get() = allTimeProfiles.filter { !it.toBeRemoved }

    fun updateTimeProfile(index: Int, timeProfile: TimeProfile): WeeklyProfile {
        mapFilteredIndexToFullIndex(index)?.let {
            allTimeProfiles[it] = allTimeProfiles[it].copy(
                hour = timeProfile.hour,
                minute = timeProfile.minute,
                active = timeProfile.active,
                days = timeProfile.days
            )
        }
        return this
    }

    fun removeTimeProfile(index: Int): WeeklyProfile? {
        mapFilteredIndexToFullIndex(index)?.let {
            if (allTimeProfiles[it].identifier == null) {
                allTimeProfiles.removeAt(it)
            } else {
                allTimeProfiles[it].toBeRemoved = true
            }
        } ?: return null
        return this
    }

    fun addTimeProfile(timeProfile: TimeProfile) = this.apply {
        allTimeProfiles.add(timeProfile)
    }

    private fun mapFilteredIndexToFullIndex(filteredIndex: Int): Int? {
        var tmpFilteredIndex = -1
        for (fullIndex in 0 until allTimeProfiles.size) {

            if (!allTimeProfiles[fullIndex].toBeRemoved) {
                tmpFilteredIndex += 1
            }

            if (tmpFilteredIndex == filteredIndex) {
                return fullIndex
            }
        }
        return null
    }

    fun copy(
        singleEntriesActivatable: Boolean = this.singleEntriesActivatable,
        maxSlots: Int = this.maxSlots,
        maxTimeProfiles: Int = this.maxTimeProfiles,
        currentSlots: Int = this.currentSlots,
        currentTimeProfiles: Int = this.currentTimeProfiles
    ): WeeklyProfile = WeeklyProfile(
        singleEntriesActivatable,
        maxSlots,
        maxTimeProfiles,
        currentSlots,
        currentTimeProfiles,
        mutableListOf(*allTimeProfiles.toTypedArray()),
        backupProfiles.toMap()
    )
}
