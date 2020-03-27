package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.GeofencingService
import com.daimler.mbcarkit.business.model.geofencing.ActiveTimes
import com.daimler.mbcarkit.business.model.geofencing.Fence
import com.daimler.mbcarkit.business.model.geofencing.GeofenceViolation
import com.daimler.mbcarkit.business.model.geofencing.Shape
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.TaskObject
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CachedGeofencingServiceTest {

    private val geofencingService: GeofencingService = mockk()

    private val fencesTask = TaskObject<List<Fence>, ResponseError<out RequestError>?>()
    private val fenceTask = TaskObject<Fence, ResponseError<out RequestError>?>()
    private val unitTask = TaskObject<Unit, ResponseError<out RequestError>?>()
    private val violationsTask = TaskObject<List<GeofenceViolation>, ResponseError<out RequestError>?>()

    private val subject: CachedGeofencingService = CachedGeofencingService(geofencingService)

    private val fence = Fence(
        null,
        null,
        "",
        ActiveTimes(emptyList(), 0, 0),
        null,
        Shape(null, null)
    )

    @BeforeEach
    fun setup() {
        every { geofencingService.fetchAllFences(any(), any()) } returns fencesTask
        every { geofencingService.createFence(any(), any()) } returns fenceTask
        every { geofencingService.fetchFenceById(any(), any()) } returns fenceTask
        every { geofencingService.updateFence(any(), any(), any()) } returns unitTask
        every { geofencingService.deleteFence(any(), any()) } returns unitTask
        every { geofencingService.fetchAllVehicleFences(any(), any()) } returns fencesTask
        every { geofencingService.activateVehicleFence(any(), any(), any()) } returns unitTask
        every { geofencingService.deleteVehicleFence(any(), any(), any()) } returns unitTask
        every { geofencingService.fetchAllViolations(any(), any()) } returns violationsTask
        every { geofencingService.deleteAllViolations(any(), any()) } returns unitTask
        every { geofencingService.deleteViolation(any(), any(), any()) } returns unitTask
    }

    @Test
    fun `fetchAllFences should return retrofit response`() {
        Assertions.assertEquals(fencesTask, subject.fetchAllFences("", ""))
    }

    @Test
    fun `createFence should return retrofit response`() {
        Assertions.assertEquals(
            fenceTask,
            subject.createFence(
                "",
                Fence(
                    null,
                    null,
                    "",
                    ActiveTimes(emptyList(), 0, 0),
                    null,
                    Shape(null, null)
                )
            )
        )
    }

    @Test
    fun `fetchFenceById should return retrofit response`() {
        Assertions.assertEquals(fenceTask, subject.fetchFenceById("", 0))
    }

    @Test
    fun `updateFence should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.updateFence("", 0, fence))
    }

    @Test
    fun `deleteFence should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.deleteFence("", 0))
    }

    @Test
    fun `fetchAllVehicleFences should return retrofit response`() {
        Assertions.assertEquals(fencesTask, subject.fetchAllVehicleFences("", ""))
    }

    @Test
    fun `activateVehicleFence should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.activateVehicleFence("", "", 0))
    }

    @Test
    fun `deleteVehicleFence should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.deleteVehicleFence("", "", 0))
    }

    @Test
    fun `fetchAllViolations should return retrofit response`() {
        Assertions.assertEquals(violationsTask, subject.fetchAllViolations("", ""))
    }

    @Test
    fun `deleteAllViolations should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.deleteAllViolations("", ""))
    }

    @Test
    fun `deleteViolation should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.deleteViolation("", "", 0))
    }
}
