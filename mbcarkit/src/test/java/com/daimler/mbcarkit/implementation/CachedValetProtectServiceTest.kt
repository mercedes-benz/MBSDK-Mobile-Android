package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.ValetProtectService
import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.daimler.mbcarkit.business.model.valetprotect.DistanceUnit
import com.daimler.mbcarkit.business.model.valetprotect.ValetprotectItem
import com.daimler.mbcarkit.business.model.valetprotect.ValetprotectRadius
import com.daimler.mbcarkit.business.model.valetprotect.ValetprotectViolation
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.TaskObject
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CachedValetProtectServiceTest {

    private val valetProtectService: ValetProtectService = mockk()

    private val valetProtectItemTask = TaskObject<ValetprotectItem, ResponseError<out RequestError>?>()
    private val valetProtectViolationsTask = TaskObject<List<ValetprotectViolation>, ResponseError<out RequestError>?>()
    private val valetProtectViolationTask = TaskObject<ValetprotectViolation, ResponseError<out RequestError>?>()
    private val unitTask = TaskObject<Unit, ResponseError<out RequestError>?>()

    private val subject = CachedValetProtectService(valetProtectService)

    @BeforeEach
    fun setup() {
        every { valetProtectService.fetchValetprotectItem(any(), any(), any()) } returns valetProtectItemTask
        every { valetProtectService.createValetprotectItem(any(), any(), any()) } returns valetProtectItemTask
        every { valetProtectService.deleteValetprotectItem(any(), any()) } returns unitTask
        every { valetProtectService.fetchAllValetprotectViolations(any(), any(), any()) } returns valetProtectViolationsTask
        every { valetProtectService.deleteAllValetprotectViolations(any(), any()) } returns unitTask
        every { valetProtectService.fetchValetprotectViolation(any(), any(), any(), any()) } returns valetProtectViolationTask
        every { valetProtectService.deleteValetprotectViolation(any(), any(), any()) } returns unitTask
    }

    @Test
    fun `fetchValetprotectItem should return retrofit response`() {
        Assertions.assertEquals(valetProtectItemTask, subject.fetchValetprotectItem("", "", DistanceUnit.KM))
    }

    @Test
    fun `createValetprotectItem should return retrofit response`() {
        Assertions.assertEquals(
            valetProtectItemTask,
            subject.createValetprotectItem(
                "",
                "",
                ValetprotectItem("", emptyList(), GeoCoordinates(null, null), ValetprotectRadius(0.0, DistanceUnit.KM))
            )
        )
    }

    @Test
    fun `deleteValetprotectItem should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.deleteValetprotectItem("", ""))
    }

    @Test
    fun `fetchAllValetprotectViolations should return retrofit response`() {
        Assertions.assertEquals(valetProtectViolationsTask, subject.fetchAllValetprotectViolations("", "", DistanceUnit.KM))
    }

    @Test
    fun `deleteAllValetprotectViolations should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.deleteAllValetprotectViolations("", ""))
    }

    @Test
    fun `fetchValetprotectViolation should return retrofit response`() {
        Assertions.assertEquals(valetProtectViolationTask, subject.fetchValetprotectViolation("", "", 0, DistanceUnit.KM))
    }

    @Test
    fun `deleteValetprotectViolation should return retrofit response`() {
        Assertions.assertEquals(unitTask, subject.deleteValetprotectViolation("", "", 0))
    }
}
