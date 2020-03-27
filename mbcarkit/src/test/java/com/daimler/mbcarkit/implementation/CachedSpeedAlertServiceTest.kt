package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.SpeedAlertService
import com.daimler.mbcarkit.business.model.speedalert.SpeedAlertViolation
import com.daimler.mbcarkit.business.model.speedalert.SpeedUnit
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.TaskObject
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CachedSpeedAlertServiceTest {

    private val speedAlertService: SpeedAlertService = mockk()

    private val speedAlertViolationsTask = TaskObject<List<SpeedAlertViolation>, ResponseError<out RequestError>?>()
    private val unitTask = TaskObject<Unit, ResponseError<out RequestError>?>()

    private val subject = CachedSpeedAlertService(speedAlertService)

    @BeforeEach
    fun setup() {
        every { speedAlertService.fetchViolations(any(), any(), any()) } returns speedAlertViolationsTask
        every { speedAlertService.deleteViolation(any(), any(), any()) } returns unitTask
        every { speedAlertService.deleteViolations(any(), any()) } returns unitTask
    }

    @Test
    fun `fetchViolations should return retrofit response`() {
        Assertions.assertEquals(speedAlertViolationsTask, subject.fetchViolations("", "", SpeedUnit.KMH))
    }

    @Test
    fun `deleteViolation should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.deleteViolation("", "", ""))
    }

    @Test
    fun `deleteViolations should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.deleteViolations("", ""))
    }
}
