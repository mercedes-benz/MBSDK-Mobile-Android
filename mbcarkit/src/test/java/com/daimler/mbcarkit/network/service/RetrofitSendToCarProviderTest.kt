package com.daimler.mbcarkit.network.service

import com.daimler.mbcarkit.business.model.sendtocar.RouteType
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapability
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarPoi
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarPrecondition
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarRoute
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarWaypoint
import com.daimler.mbcarkit.network.model.ApiSendToCarCapabilities
import com.daimler.mbcarkit.network.model.ApiSendToCarCapability
import com.daimler.mbcarkit.network.model.ApiSendToCarPrecondition
import com.daimler.mbcarkit.utils.ResponseTaskTestCase
import com.daimler.mbnetworkkit.networking.HttpError
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
internal class RetrofitSendToCarProviderTest : BaseRetrofitServiceTest<RetrofitSendToCarProvider>() {
    private val capabilitiesResponse = mockk<Response<ApiSendToCarCapabilities>>()

    override fun createSubject(scope: CoroutineScope): RetrofitSendToCarProvider =
        RetrofitSendToCarProvider(vehicleApi, headerService, scope)

    @BeforeEach
    fun setup() {
        vehicleApi.apply {
            coEvery { fetchSendToCarCapabilities(any(), any(), any()) } returns capabilitiesResponse
            coEvery { sendRoute(any(), any(), any()) } returns noContentResponse
        }
    }

    @Test
    fun `fetchCapabilities() should return transformed data`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val api = ApiSendToCarCapabilities(
            listOf(
                ApiSendToCarCapability.DYNAMIC_ROUTE_BACKEND,
                ApiSendToCarCapability.SINGLE_POI_BACKEND,
                ApiSendToCarCapability.SINGLE_POI_BLUETOOTH
            ),
            listOf(
                ApiSendToCarPrecondition.MBAPPS_ENABLE_MBAPPS
            )
        )
        val case = ResponseTaskTestCase(capabilitiesResponse) {
            subject.fetchCapabilities("", "")
        }
        scope.runBlockingTest {
            case.finish(api)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
            softly.assertThat(case.success?.capabilities?.contains(SendToCarCapability.DYNAMIC_ROUTE_BACKEND))
            softly.assertThat(case.success?.capabilities?.contains(SendToCarCapability.SINGLE_POI_BACKEND))
            softly.assertThat(case.success?.capabilities?.contains(SendToCarCapability.SINGLE_POI_BLUETOOTH))
            softly.assertThat(case.success?.preconditions?.contains(SendToCarPrecondition.MBAPPS_ENABLE_MBAPPS))
        }
    }

    @Test
    fun `fetchCapabilities() should return transformed error`(softly: SoftAssertions, scope: TestCoroutineScope) {
        capabilitiesResponse.mockError()
        val case = ResponseTaskTestCase(capabilitiesResponse) {
            subject.fetchCapabilities("", "")
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `sendRoute() should return successfully`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.sendRoute("", "", SendToCarRoute(RouteType.DYNAMIC_ROUTE, emptyList()))
        }
        scope.runBlockingTest {
            case.finish(Unit)
            softly.assertThat(case.error).isNull()
            softly.assertThat(case.success).isNotNull
        }
    }

    @Test
    fun `sendRoute() should return transformed error`(softly: SoftAssertions, scope: TestCoroutineScope) {
        noContentResponse.mockError()
        val case = ResponseTaskTestCase(noContentResponse) {
            subject.sendRoute("", "", SendToCarRoute(RouteType.DYNAMIC_ROUTE, emptyList()))
        }
        scope.runBlockingTest {
            case.finishRaw()
            softly.assertThat(case.success).isNull()
            softly.assertThat(case.error).isNotNull
            softly.assertThat(case.error?.requestError).isInstanceOf(HttpError::class.java)
        }
    }

    @Test
    fun `sendPoi() should call sendRoute() with params`(softly: SoftAssertions, scope: TestCoroutineScope) {
        val token = "1234"
        val vin = "WDDXXXXXXXXXX"
        val poi = SendToCarPoi(
            SendToCarWaypoint(1.0, 1.2)
        )
        val expectedRoute = SendToCarRoute(RouteType.SINGLE_POI, listOf(poi.location), poi.title, poi.notificationText)
        val serviceSpyk = spyk(subject)
        serviceSpyk.sendPoi(token, vin, poi)
        verify { serviceSpyk.sendRoute(token, vin, expectedRoute) }
    }
}
