package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.ServiceActivationStateProcessor
import com.daimler.mbcarkit.business.ServiceCache
import com.daimler.mbcarkit.business.ServiceService
import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbcarkit.business.model.services.ServiceGroup
import com.daimler.mbcarkit.business.model.services.ServiceGroupOption
import com.daimler.mbcarkit.business.model.services.ServiceStatus
import com.daimler.mbcarkit.business.model.services.ServiceStatusDesire
import com.daimler.mbcarkit.utils.ResponseTaskObject
import com.daimler.mbcarkit.utils.ResponseTaskObjectUnit
import com.daimler.mbcarkit.utils.createService
import com.daimler.mbnetworkkit.networking.NetworkError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(SoftAssertionsExtension::class)
class CachedServiceServiceTest {

    private val service: ServiceService = mockk()
    private val cache: ServiceCache = mockk()
    private val stateProcessor: ServiceActivationStateProcessor = mockk()

    private lateinit var serviceGroupTask: ResponseTaskObject<List<ServiceGroup>>
    private lateinit var unitTask: ResponseTaskObjectUnit

    private val subject = CachedServiceService(service, cache, stateProcessor)

    private val updateServicesSlot = slot<List<Service>>()

    private val service1 = createService(
        id = 1,
        name = "Service_1",
        categoryName = "Category_1",
        activationStatus = ServiceStatus.ACTIVE
    )
    private val service2 = createService(
        id = 2,
        name = "Service_2",
        categoryName = "Category_1",
        activationStatus = ServiceStatus.ACTIVE
    )
    private val service3 = createService(
        id = 3,
        name = "Service_3",
        categoryName = "Category_2",
        activationStatus = ServiceStatus.ACTIVE
    )

    private val statusDesire1 = ServiceStatusDesire(1, true)
    private val statusDesire2 = ServiceStatusDesire(2, false)
    private val statusDesire3 = ServiceStatusDesire(3, true)

    private val serviceGroups = listOf(
        ServiceGroup("Group_1", listOf(service1)),
        ServiceGroup("Group_2", listOf(service2))
    )

    @BeforeEach
    fun setup() {
        serviceGroupTask = ResponseTaskObject()
        unitTask = ResponseTaskObjectUnit()
        every { service.fetchServices(any(), any(), any<ServiceGroupOption>(), any(), any(), any()) } returns serviceGroupTask
        every { service.fetchServices(any(), any(), any(), any(), any(), any(), any()) } returns serviceGroupTask
        every { service.requestServiceUpdate(any(), any(), any()) } returns unitTask
        every { service.activateAllServices(any(), any()) } returns unitTask

        every { cache.loadServices(any()) } returns listOf(service1, service2, service3)
        every { cache.updateServices(any(), capture(updateServicesSlot), any()) } returns Unit
        every { cache.loadServicesById(any(), any()) } returns listOf(service1, service2, service3)
        every { cache.deleteServices(any(), any()) } returns Unit

        every { stateProcessor.processActivationStates(any(), any()) } returns listOf(service1, service2)
    }

    @AfterEach
    fun clearMocks() {
        clearAllMocks()
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `fetchServices with service response should update service cache`(checkPreconditions: Boolean, softly: SoftAssertions) {
        var success: List<ServiceGroup>? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchServices("", "", ServiceGroupOption.CATEGORY, "", checkPreconditions)
            .onComplete { success = it }
            .onFailure { failure = it }

        serviceGroupTask.complete(serviceGroups)

        softly.assertThat(success).isEqualTo(serviceGroups)
        softly.assertThat(failure).isNull()

        verify { cache.deleteServices(any(), listOf(service3)) }
        verify { cache.updateServices(any(), listOf(service1, service2), checkPreconditions) }
    }

    @Test
    fun `fetchServices with service failure should return cached values`(softly: SoftAssertions) {
        var success: List<ServiceGroup>? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchServices("", "", ServiceGroupOption.CATEGORY, "", false)
            .onComplete { success = it }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        serviceGroupTask.fail(responseError)

        softly.assertThat(success?.size).isEqualTo(2)
        softly.assertThat(success?.get(0)?.services).isEqualTo(listOf(service1, service2))
        softly.assertThat(success?.get(1)?.services).isEqualTo(listOf(service3))
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchServices with service failure without cache should fail`(softly: SoftAssertions) {
        var success: List<ServiceGroup>? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchServices("", "", ServiceGroupOption.CATEGORY, "", false)
            .onComplete { success = it }
            .onFailure { failure = it }

        every { cache.loadServices(any()) } returns null

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        serviceGroupTask.fail(responseError)

        softly.assertThat(success).isNull()
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `fetchServices with id with service response should update service cache`(checkPreconditions: Boolean, softly: SoftAssertions) {
        var success: List<ServiceGroup>? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchServices("", "", emptyList(), ServiceGroupOption.CATEGORY, "", checkPreconditions)
            .onComplete { success = it }
            .onFailure { failure = it }

        serviceGroupTask.complete(serviceGroups)

        softly.assertThat(success).isEqualTo(serviceGroups)
        softly.assertThat(failure).isNull()

        verify { cache.updateServices(any(), listOf(service1, service2), checkPreconditions) }
    }

    @Test
    fun `fetchServices with id with service failure should return cached values`(softly: SoftAssertions) {
        var success: List<ServiceGroup>? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchServices("", "", emptyList(), ServiceGroupOption.CATEGORY, "", false)
            .onComplete { success = it }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        serviceGroupTask.fail(responseError)

        softly.assertThat(success?.size).isEqualTo(2)
        softly.assertThat(success?.get(0)?.services).isEqualTo(listOf(service1, service2))
        softly.assertThat(success?.get(1)?.services).isEqualTo(listOf(service3))
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchServices with id with service failure without cache should fail`(softly: SoftAssertions) {
        var success: List<ServiceGroup>? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchServices("", "", emptyList(), ServiceGroupOption.CATEGORY, "", false)
            .onComplete { success = it }
            .onFailure { failure = it }

        every { cache.loadServicesById(any(), any()) } returns null

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        serviceGroupTask.fail(responseError)

        softly.assertThat(success).isNull()
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `requestServiceUpdate with service response should update cache`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.requestServiceUpdate("", "", listOf(statusDesire1, statusDesire2, statusDesire3))
            .onComplete { success = true }
            .onFailure { failure = it }

        every { cache.loadServicesById(any(), any()) } returns listOf(service1, service2)

        unitTask.complete(Unit)

        softly.assertThat(success).isTrue
        softly.assertThat(failure).isNull()

        verify { cache.updateServices(any(), any(), false) }
        softly.assertThat(updateServicesSlot.isCaptured).isTrue
        softly.assertThat(updateServicesSlot.captured.size).isEqualTo(2)
        softly.assertThat(updateServicesSlot.captured[0].activationStatus).isEqualTo(ServiceStatus.ACTIVATION_PENDING)
        softly.assertThat(updateServicesSlot.captured[1].activationStatus).isEqualTo(ServiceStatus.DEACTIVATION_PENDING)
    }

    @Test
    fun `requestServiceUpdate with service failure should fail`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.requestServiceUpdate("", "", listOf(statusDesire1, statusDesire2, statusDesire3))
            .onComplete { success = true }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        unitTask.fail(responseError)

        softly.assertThat(success).isFalse
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `activateAllServices should return retrofit service task`() {
        Assertions.assertEquals(unitTask, subject.activateAllServices("", ""))
    }
}
