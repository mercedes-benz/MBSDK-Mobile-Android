package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.OutletsService
import com.daimler.mbcarkit.business.model.merchants.Merchants
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.TaskObject
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CachedMerchantsServiceTest {

    private val outletsService: OutletsService = mockk()

    private val fetchOutletsTask = TaskObject<Merchants, ResponseError<out RequestError>?>()

    private val subject = CachedMerchantsService(outletsService)

    @BeforeEach
    fun setup() {
        every { outletsService.fetchOutlets(any(), any(), any(), any(), any()) } returns fetchOutletsTask
    }

    @Test
    fun `fetchOutlets should return retrofit response`() {
        Assertions.assertEquals(fetchOutletsTask, subject.fetchOutlets("", "", "", null, outletActivity = null))
    }
}
