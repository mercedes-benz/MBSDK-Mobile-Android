package com.daimler.mbcarkit.business.model.vehicle

import org.junit.Assert
import org.junit.Test

class WeeklyProfileTest {

    @Test
    fun testAddTimeProfile() {
        var weeklyProfileModel = WeeklyProfile(
            singleEntriesActivatable = true,
            maxSlots = 0,
            maxTimeProfiles = 0,
            currentSlots = 0,
            currentTimeProfiles = 0
        )
        weeklyProfileModel = weeklyProfileModel.addTimeProfile(TimeProfile(hour = 1337, minute = 0, active = true, days = emptySet(), applicationIdentifier = 11))

        Assert.assertEquals(1337, weeklyProfileModel.timeProfiles[0].hour)
    }

    @Test
    fun testRemoveTimeProfile() {
        val weeklyProfileModel = WeeklyProfile(
            singleEntriesActivatable = true,
            maxSlots = 0,
            maxTimeProfiles = 0,
            currentSlots = 0,
            currentTimeProfiles = 0,
            allTimeProfiles = mutableListOf(TimeProfile(identifier = 4, hour = 0, minute = 0, active = true, days = setOf(), applicationIdentifier = 11))
        )

        Assert.assertEquals(1, weeklyProfileModel.timeProfiles.size)
        val removed = weeklyProfileModel.removeTimeProfile(0)
        Assert.assertEquals(0, removed?.timeProfiles?.size)
        Assert.assertEquals(1, weeklyProfileModel.allTimeProfiles.size)
    }

    @Test
    fun testRemoveLocallyCreatedTimeProfile() {
        var weeklyProfileModel = WeeklyProfile(
            singleEntriesActivatable = true,
            maxSlots = 0,
            maxTimeProfiles = 0,
            currentSlots = 0,
            currentTimeProfiles = 0,
            allTimeProfiles = mutableListOf(TimeProfile(identifier = 1, hour = 1, minute = 1, active = true, days = setOf(), applicationIdentifier = 11))
        )
        weeklyProfileModel.addTimeProfile(TimeProfile(hour = 1, minute = 1, active = true, days = setOf(), applicationIdentifier = 11))
        Assert.assertEquals(2, weeklyProfileModel.timeProfiles.size)

        weeklyProfileModel.removeTimeProfile(1)
        Assert.assertEquals(1, weeklyProfileModel.timeProfiles.size)
        Assert.assertEquals(1, weeklyProfileModel.allTimeProfiles.size)
    }

    @Test
    fun testAddRemoveMixTimeProfile() {
        var weeklyProfileModel = WeeklyProfile(
            singleEntriesActivatable = true,
            maxSlots = 0,
            maxTimeProfiles = 0,
            currentSlots = 0,
            currentTimeProfiles = 0,
            allTimeProfiles = mutableListOf(TimeProfile(identifier = 1, hour = 1, minute = 1, active = true, days = setOf(), applicationIdentifier = 11))
        )

        weeklyProfileModel = weeklyProfileModel.addTimeProfile(TimeProfile(identifier = 2, hour = 2, minute = 2, active = true, days = setOf(), applicationIdentifier = 11))
        weeklyProfileModel = weeklyProfileModel.addTimeProfile(TimeProfile(identifier = 3, hour = 3, minute = 3, active = true, days = setOf(), applicationIdentifier = 11))

        Assert.assertEquals(3, weeklyProfileModel.timeProfiles.size)
        val removed = weeklyProfileModel.removeTimeProfile(1)
        Assert.assertNotNull(removed)
        if (removed != null) {
            weeklyProfileModel = removed
        }

        Assert.assertEquals(2, weeklyProfileModel.timeProfiles.size)
        weeklyProfileModel = weeklyProfileModel.updateTimeProfile(1, TimeProfile(identifier = 3, hour = 4, minute = 4, active = true, days = setOf(), applicationIdentifier = 11))
        Assert.assertEquals(4, weeklyProfileModel.timeProfiles[1].hour)
    }

    @Test
    fun testNilForInvalidIndexTimeProfile() {
        var weeklyProfileModel = WeeklyProfile(
            singleEntriesActivatable = true,
            maxSlots = 0,
            maxTimeProfiles = 0,
            currentSlots = 0,
            currentTimeProfiles = 0,
            allTimeProfiles = mutableListOf(TimeProfile(identifier = 1, hour = 1, minute = 1, active = true, days = setOf(), applicationIdentifier = 11))
        )

        weeklyProfileModel = weeklyProfileModel.addTimeProfile(TimeProfile(identifier = 2, hour = 2, minute = 2, active = true, days = setOf(), applicationIdentifier = 11))

        Assert.assertNull(weeklyProfileModel.removeTimeProfile(index = 341))
    }
}
