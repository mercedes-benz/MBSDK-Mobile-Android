package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.sendtocar.RouteType
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapabilities
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapability
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarPoi
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarPrecondition
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarRoute
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarWaypoint
import com.daimler.mbcarkit.implementation.CachedSendToCarProvider
import com.daimler.mbcarkit.utils.ResponseTaskObject
import com.daimler.mbcarkit.utils.ResponseTaskObjectUnit
import com.daimler.mbnetworkkit.networking.NetworkError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class CachedSendToCarProviderTest {

    lateinit var subject: CachedSendToCarProvider

    @MockK
    lateinit var cacheMock: SendToCarCache

    @MockK
    lateinit var retrofitServiceMock: SendToCarProvider

    private lateinit var sendToCarCapabilitiesTask: ResponseTaskObject<SendToCarCapabilities>

    private lateinit var sendPoiRouteTask: ResponseTaskObjectUnit

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        subject = CachedSendToCarProvider(retrofitServiceMock, cacheMock)

        sendToCarCapabilitiesTask = ResponseTaskObject()
        sendPoiRouteTask = ResponseTaskObjectUnit()

        every { cacheMock.saveCapabilities(any(), any()) } returns Unit
        every { retrofitServiceMock.fetchCapabilities(any(), any()) } returns sendToCarCapabilitiesTask
        every { retrofitServiceMock.sendRoute(any(), any(), any()) } returns sendPoiRouteTask
        every { retrofitServiceMock.sendPoi(any(), any(), any()) } returns sendPoiRouteTask
    }

    @AfterEach
    fun after() {
        clearAllMocks()
    }

    @Test
    fun `should return cached capabilities`(softly: SoftAssertions) {
        var success: SendToCarCapabilities? = null
        var failure: ResponseError<out RequestError>? = null

        val cachedCapabilities = SendToCarCapabilities(
            listOf(SendToCarCapability.SINGLE_POI_BLUETOOTH),
            listOf(SendToCarPrecondition.MBAPPS_REGISTER_USER)
        )

        every { cacheMock.loadCapabilities(any()) } returns cachedCapabilities
        subject.fetchCapabilities("", "").onComplete {
            success = it
        }.onFailure {
            failure = it
        }

        softly.assertThat(success).isEqualTo(cachedCapabilities)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `should return request capabilities from BE when not cached`(softly: SoftAssertions) {
        var success: SendToCarCapabilities? = null
        var failure: ResponseError<out RequestError>? = null

        val carCapabilities = SendToCarCapabilities(
            listOf(SendToCarCapability.SINGLE_POI_BACKEND),
            listOf(SendToCarPrecondition.MBAPPS_ENABLE_MBAPPS)
        )

        every { cacheMock.loadCapabilities(any()) } returns null
        subject.fetchCapabilities("", "").onComplete {
            success = it
        }.onFailure {
            failure = it
        }

        verify { retrofitServiceMock.fetchCapabilities(any(), any()) }

        sendToCarCapabilitiesTask.complete(carCapabilities)

        verify { cacheMock.saveCapabilities(any(), any()) }

        softly.assertThat(success).isEqualTo(carCapabilities)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `should fail without cached values and service failure`(softly: SoftAssertions) {
        var success: SendToCarCapabilities? = null
        var failure: ResponseError<out RequestError>? = null

        every { cacheMock.loadCapabilities(any()) } returns null

        subject.fetchCapabilities("", "")
            .onComplete { success = it }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        sendToCarCapabilitiesTask.fail(responseError)

        softly.assertThat(success).isNull()
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `should send POI via BE successfully`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.sendPoi(
            "", "",
            SendToCarPoi(
                SendToCarWaypoint(1.0, 1.2)
            )
        )
            .onComplete { success = true }
            .onFailure { failure = it }

        sendPoiRouteTask.complete(Unit)

        verify { retrofitServiceMock.sendPoi(any(), any(), any()) }

        softly.assertThat(success).isTrue
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `should handle send POI failure`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.sendPoi(
            "", "",
            SendToCarPoi(
                SendToCarWaypoint(1.0, 1.2)
            )
        )
            .onComplete { success = true }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        sendPoiRouteTask.fail(responseError)

        verify { retrofitServiceMock.sendPoi(any(), any(), any()) }

        softly.assertThat(success).isFalse
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `should send route via BE successfully`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.sendRoute(
            "", "",
            SendToCarRoute(
                RouteType.STATIC_ROUTE,
                listOf(SendToCarWaypoint(1.0, 1.2))
            )
        )
            .onComplete { success = true }
            .onFailure { failure = it }

        sendPoiRouteTask.complete(Unit)

        verify { retrofitServiceMock.sendRoute(any(), any(), any()) }

        softly.assertThat(success).isTrue
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `should handle send route failure`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.sendRoute(
            "", "",
            SendToCarRoute(
                RouteType.STATIC_ROUTE,
                listOf(SendToCarWaypoint(1.0, 1.2))
            )
        )
            .onComplete { success = true }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        sendPoiRouteTask.fail(responseError)

        verify { retrofitServiceMock.sendRoute(any(), any(), any()) }

        softly.assertThat(success).isFalse
        softly.assertThat(failure).isEqualTo(responseError)
    }
}
