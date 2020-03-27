package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.ServiceService
import com.daimler.mbcarkit.business.model.services.ServiceFilter
import com.daimler.mbcarkit.business.model.services.ServiceGroup
import com.daimler.mbcarkit.business.model.services.ServiceGroupOption
import com.daimler.mbcarkit.business.model.services.ServiceRight
import com.daimler.mbcarkit.utils.ResponseTaskObject
import com.daimler.mbcarkit.utils.createService
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(SoftAssertionsExtension::class)
internal class FilteredServiceServiceTest {

    private val service = mockk<ServiceService>()

    private lateinit var serviceTask: ResponseTaskObject<List<ServiceGroup>>

    private val serviceRead = createService(rights = listOf(ServiceRight.READ))
    private val serviceReadActivate = createService(rights = listOf(ServiceRight.READ, ServiceRight.ACTIVATE))
    private val serviceReadManage = createService(rights = listOf(ServiceRight.MANAGE, ServiceRight.READ))
    private val serviceNoRights = createService(rights = listOf())
    private val serviceUnknown = createService(rights = listOf(ServiceRight.UNKNOWN))

    private val group1 = ServiceGroup(
        "group1",
        listOf(serviceRead, serviceReadActivate, serviceNoRights, serviceReadManage)
    )

    private val group2 = ServiceGroup(
        "group2",
        listOf(serviceUnknown)
    )

    private lateinit var subject: FilteredServiceService

    @BeforeEach
    fun setup() {
        serviceTask = ResponseTaskObject()
        service.apply {
            every { fetchServices(any(), any(), any<ServiceGroupOption>(), any(), any(), any()) } returns serviceTask.futureTask()
            every { fetchServices(any(), any(), any(), any(), any(), any(), any()) } returns serviceTask.futureTask()
        }

        subject = createSubject()
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should return original result for filter None`(withIds: Boolean, softly: SoftAssertions) {
        var result: List<ServiceGroup>? = null
        getFetchServicesCall(withIds, ServiceFilter.None)
            .onComplete {
                result = it
            }
        serviceTask.complete(listOf(group1, group2))
        softly.assertThat(result).isEqualTo(listOf(group1, group2))
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should return filtered result for filter ReadOnly`(withIds: Boolean, softly: SoftAssertions) {
        var result: List<ServiceGroup>? = null
        getFetchServicesCall(withIds, ServiceFilter.ReadOnly)
            .onComplete {
                result = it
            }
        serviceTask.complete(listOf(group1, group2))
        softly.assertThat(result).isNotNull
        softly.assertThat(result?.size).isEqualTo(1)
        val group = result?.firstOrNull()
        softly.assertThat(group?.services).isEqualTo(listOf(serviceRead, serviceReadActivate, serviceReadManage))
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should return original result for filter Rights`(withIds: Boolean, softly: SoftAssertions) {
        var result: List<ServiceGroup>? = null
        val rights = listOf(ServiceRight.READ, ServiceRight.ACTIVATE)
        getFetchServicesCall(withIds, ServiceFilter.Rights(rights))
            .onComplete {
                result = it
            }
        serviceTask.complete(listOf(group1, group2))
        softly.assertThat(result).isNotNull
        softly.assertThat(result?.size).isEqualTo(1)
        val group = result?.firstOrNull()
        softly.assertThat(group?.services).isEqualTo(listOf(serviceReadActivate))
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should delegate error`(withIds: Boolean, softly: SoftAssertions) {
        val expected = ResponseError.httpError(500)
        var error: ResponseError<out RequestError>? = null
        getFetchServicesCall(withIds, ServiceFilter.None)
            .onFailure {
                error = it
            }
        serviceTask.fail(expected)
        softly.assertThat(error).isEqualTo(expected)
    }

    private fun getFetchServicesCall(withIds: Boolean, filter: ServiceFilter) =
        if (withIds) {
            subject.fetchServices(
                "",
                "",
                ServiceGroupOption.CATEGORY,
                null,
                false,
                filter
            )
        } else {
            subject.fetchServices(
                "",
                "",
                emptyList(),
                ServiceGroupOption.CATEGORY,
                null,
                false,
                filter
            )
        }

    private fun createSubject() = FilteredServiceService(service)
}
