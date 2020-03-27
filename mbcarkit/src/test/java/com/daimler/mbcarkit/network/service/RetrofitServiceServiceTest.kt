package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.model.services.ServiceAction
import com.daimler.mbcarkit.business.model.services.ServiceGroup
import com.daimler.mbcarkit.business.model.services.ServiceGroupOption
import com.daimler.mbcarkit.business.model.services.ServiceStatus
import com.daimler.mbcarkit.network.model.ApiAllowedServiceActions
import com.daimler.mbcarkit.network.model.ApiService
import com.daimler.mbcarkit.network.model.ApiServiceResponse
import com.daimler.mbcarkit.network.model.ApiServiceStatus
import com.daimler.mbcarkit.utils.ApiServiceFactory
import com.daimler.mbcarkit.utils.ResponseTaskTestCase
import com.daimler.mbnetworkkit.networking.HttpError
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
internal class RetrofitServiceServiceTest : BaseRetrofitServiceTest<RetrofitServiceService>() {

    private val servicesResponse = mockk<Response<List<ApiServiceResponse>>>()

    override fun createSubject(scope: CoroutineScope): RetrofitServiceService =
        RetrofitServiceService(vehicleApi, headerService, scope)

    @BeforeEach
    fun setup() {
        vehicleApi.apply {
            coEvery {
                fetchServices(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns servicesResponse
            coEvery {
                fetchServices(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns servicesResponse
            coEvery {
                requestServiceUpdate(
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns noContentResponse
        }
    }

    @Test
    fun `fetchServices() should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val apiService = ApiServiceFactory.createService(
            id = 1,
            name = "name",
            description = "desc",
            shortDescription = "shortDesc",
            shortName = "shortName",
            categoryName = "group",
            allowedActions = listOf(ApiAllowedServiceActions.PURCHASE_LICENSE),
            activationStatus = ApiServiceStatus.ACTIVE,
            desiredServiceStatus = ApiServiceStatus.INACTIVE
        )
        val api = ApiServiceResponse(
            "group",
            listOf(apiService)
        )
        val case = ResponseTaskTestCase(servicesResponse) {
            subject.fetchServices("", "", ServiceGroupOption.CATEGORY, null, false)
        }
        scope.runBlockingTest {
            case.finish(listOf(api))
            assertServicesResponse(apiService, case, softly)
        }
    }

    @Test
    fun `fetchServices() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        servicesResponse.mockError()
        val case = ResponseTaskTestCase(servicesResponse) {
            subject.fetchServices("", "", ServiceGroupOption.CATEGORY, null, false)
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `fetchServices() by ids should return transformed result`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val apiService = ApiServiceFactory.createService(
            id = 1,
            name = "name",
            description = "desc",
            shortDescription = "shortDesc",
            shortName = "shortName",
            categoryName = "group",
            allowedActions = listOf(ApiAllowedServiceActions.PURCHASE_LICENSE),
            activationStatus = ApiServiceStatus.ACTIVE,
            desiredServiceStatus = ApiServiceStatus.INACTIVE
        )
        val api = ApiServiceResponse(
            "group",
            listOf(apiService)
        )
        val case = ResponseTaskTestCase(servicesResponse) {
            subject.fetchServices("", "", listOf(1), ServiceGroupOption.CATEGORY, null, false)
        }
        scope.runBlockingTest {
            case.finish(listOf(api))
            assertServicesResponse(apiService, case, softly)
        }
    }

    @Test
    fun `fetchServices() by ids should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        servicesResponse.mockError()
        val case = ResponseTaskTestCase(servicesResponse) {
            subject.fetchServices("", "", listOf(1), ServiceGroupOption.CATEGORY, null, false)
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `requestServiceUpdate() should return successfully`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.requestServiceUpdate("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
        }
    }

    @Test
    fun `requestServiceUpdate() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.requestServiceUpdate("", "", emptyList())
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `activateAllServices() should return successfully`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.activateAllServices("", "")
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
        }
    }

    @Test
    fun `activateAllServices() should return transformed error`(
        softly: SoftAssertions,
        scope: TestCoroutineScope
    ) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.activateAllServices("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    private fun assertServicesResponse(
        api: ApiService,
        case: ResponseTaskTestCase<List<ApiServiceResponse>, List<ServiceGroup>, *>,
        softly: SoftAssertions
    ) {
        softly.assertThat(case.error).isNull()
        softly.assertThat(case.success).isNotNull
        softly.assertThat(case.success?.size).isEqualTo(1)
        val group = case.success?.firstOrNull()
        val service = group?.services?.firstOrNull()
        softly.assertThat(service?.id).isEqualTo(api.id)
        softly.assertThat(service?.name).isEqualTo(api.name)
        softly.assertThat(service?.shortName).isEqualTo(api.shortName)
        softly.assertThat(service?.categoryName).isEqualTo(api.categoryName)
        softly.assertThat(service?.allowedActions).isEqualTo(listOf(ServiceAction.PURCHASE_LICENSE))
        softly.assertThat(service?.activationStatus).isEqualTo(ServiceStatus.ACTIVE)
        softly.assertThat(service?.desiredActivationStatus).isEqualTo(ServiceStatus.INACTIVE)
        softly.assertThat(service?.actualActivationServiceStatus).isEqualTo(ServiceStatus.UNKNOWN)
        softly.assertThat(service?.virtualActivationServiceStatus).isEqualTo(ServiceStatus.UNKNOWN)
    }
}
