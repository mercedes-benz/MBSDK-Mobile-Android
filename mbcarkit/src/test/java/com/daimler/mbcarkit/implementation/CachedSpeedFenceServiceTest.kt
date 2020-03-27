package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.SpeedfenceService
import com.daimler.mbcarkit.business.model.speedfence.SpeedFence
import com.daimler.mbcarkit.business.model.speedfence.SpeedFenceViolation
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.TaskObject
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CachedSpeedFenceServiceTest {

    private val speedfenceService: SpeedfenceService = mockk()

    private val speedFencesTask = TaskObject<List<SpeedFence>, ResponseError<out RequestError>?>()
    private val speedFenceViolationsTask = TaskObject<List<SpeedFenceViolation>, ResponseError<out RequestError>?>()
    private val unitTask = TaskObject<Unit, ResponseError<out RequestError>?>()

    private val subject = CachedSpeedfenceService(speedfenceService)

    @BeforeEach
    fun setup() {
        every { speedfenceService.createSpeedFences(any(), any(), any()) } returns unitTask
        every { speedfenceService.updateSpeedFences(any(), any(), any()) } returns unitTask
        every { speedfenceService.fetchSpeedFences(any(), any()) } returns speedFencesTask
        every { speedfenceService.deleteSpeedFences(any(), any()) } returns unitTask
        every { speedfenceService.deleteAllSpeedFences(any(), any()) } returns unitTask
        every { speedfenceService.deleteSpeedFence(any(), any(), any()) } returns unitTask
        every { speedfenceService.fetchSpeedFenceViolations(any(), any()) } returns speedFenceViolationsTask
        every { speedfenceService.deleteSpeedFenceViolations(any(), any()) } returns unitTask
    }

    @Test
    fun `createSpeedFences should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.createSpeedFences("", "", emptyList()))
    }

    @Test
    fun `updateSpeedFences should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.updateSpeedFences("", "", emptyList()))
    }

    @Test
    fun `fetchSpeedFences should return retrofit response`() {
        Assertions.assertEquals(speedFencesTask, subject.fetchSpeedFences("", ""))
    }

    @Test
    fun `deleteSpeedFences should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.deleteSpeedFences("", "", emptyList()))
    }

    @Test
    fun `deleteAllSpeedFences should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.deleteAllSpeedFences("", ""))
    }

    @Test
    fun `deleteSpeedFence should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.deleteSpeedFence("", "", 0))
    }

    @Test
    fun `fetchSpeedFenceViolations should return retrofit response`() {
        Assertions.assertEquals(speedFenceViolationsTask, subject.fetchSpeedFenceViolations("", ""))
    }

    @Test
    fun `deleteSpeedFenceViolations should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.deleteSpeedFenceViolations("", "", emptyList()))
    }
}
