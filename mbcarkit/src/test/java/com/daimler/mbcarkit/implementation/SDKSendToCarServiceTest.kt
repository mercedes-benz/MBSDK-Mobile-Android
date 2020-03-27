package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.bluetooth.BluetoothSendToCarService
import com.daimler.mbcarkit.business.SendToCarProvider
import com.daimler.mbcarkit.business.model.sendtocar.RouteType
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapabilities
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapability
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarPoi
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarPrecondition
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarRoute
import com.daimler.mbcarkit.implementation.exceptions.SendToCarNotPossibleError
import com.daimler.mbcarkit.utils.ResponseTaskObject
import com.daimler.mbcarkit.utils.ResponseTaskObjectUnit
import com.daimler.mbnetworkkit.networking.NetworkError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

@ExtendWith(SoftAssertionsExtension::class)
internal class SDKSendToCarServiceTest {

    private val mockSendToCarProvider: SendToCarProvider = mockk(relaxed = true)
    private val mockRoute: SendToCarRoute = mockk(relaxed = true)
    private val mockPoi: SendToCarPoi = mockk(relaxed = true)
    private val mockBluetoothSendToCarService: BluetoothSendToCarService = mockk(relaxed = true)

    private lateinit var subject: SDKSendToCarService
    private lateinit var capabilitiesTask: ResponseTaskObject<SendToCarCapabilities>
    private lateinit var sendRoutePoiTask: ResponseTaskObjectUnit

    private val sampleCapabilities = SendToCarCapabilities(
        listOf(SendToCarCapability.SINGLE_POI_BACKEND, SendToCarCapability.DYNAMIC_ROUTE_BACKEND),
        listOf(SendToCarPrecondition.MBAPPS_REGISTER_USER)
    )

    @BeforeEach
    fun setup() {
        subject = SDKSendToCarService(mockSendToCarProvider)

        capabilitiesTask = ResponseTaskObject()
        every { mockSendToCarProvider.fetchCapabilities(any(), any()) } returns capabilitiesTask

        sendRoutePoiTask = ResponseTaskObjectUnit()
        every { mockSendToCarProvider.sendRoute(any(), any(), any()) } returns sendRoutePoiTask
        every { mockSendToCarProvider.sendPoi(any(), any(), any()) } returns sendRoutePoiTask

        mockkObject(MBCarKit)
        every { MBCarKit.bluetoothSendToCarService } returns null
        every {
            mockBluetoothSendToCarService.sendPoi(
                any(),
                any(),
                any()
            )
        } returns sendRoutePoiTask
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should fetch capabilities from provider`(softly: SoftAssertions) {
        var success: SendToCarCapabilities? = null
        var failure: ResponseError<out RequestError>? = null
        subject.fetchCapabilities("", "")
            .onComplete { success = it }
            .onFailure { failure = it }
        capabilitiesTask.complete(sampleCapabilities)

        softly.assertThat(
            success?.capabilities?.containsAll(
                listOf(
                    SendToCarCapability.SINGLE_POI_BACKEND,
                    SendToCarCapability.DYNAMIC_ROUTE_BACKEND
                )
            )
        )
        softly.assertThat(success?.preconditions?.contains(SendToCarPrecondition.MBAPPS_REGISTER_USER))
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `should handle fail on capability fetch`(softly: SoftAssertions) {
        var success: SendToCarCapabilities? = null
        var failure: ResponseError<out RequestError>? = null
        subject.fetchCapabilities("", "")
            .onComplete { success = it }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> =
            ResponseError.networkError(NetworkError.NO_CONNECTION)
        capabilitiesTask.fail(responseError)

        softly.assertThat(success).isNull()
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `sendRoute() should fail sending when preconditions not met`(softly: SoftAssertions) {
        var success: Unit? = null
        var failure: ResponseError<out RequestError>? = null
        subject.sendRoute("", "", mockRoute)
            .onComplete { success = it }
            .onFailure { failure = it }

        capabilitiesTask.complete(
            SendToCarCapabilities(
                emptyList(),
                listOf(SendToCarPrecondition.MBAPPS_REGISTER_USER)
            )
        )

        softly.assertThat(success).isNull()
        softly.assertThat(failure?.requestError is SendToCarNotPossibleError.Preconditions).isTrue
    }

    @Test
    fun `sendRoute() should fail when vehicle not capable`(softly: SoftAssertions) {
        var success: Unit? = null
        var failure: ResponseError<out RequestError>? = null
        subject.sendRoute("", "", mockRoute)
            .onComplete { success = it }
            .onFailure { failure = it }

        capabilitiesTask.complete(
            SendToCarCapabilities(
                listOf(SendToCarCapability.SINGLE_POI_BACKEND),
                emptyList()
            )
        )

        softly.assertThat(success).isNull()
        softly.assertThat(failure?.requestError is SendToCarNotPossibleError.Capability).isTrue
    }

    @Test
    fun `sendRoute() should fail when vehicle not capable (2)`(softly: SoftAssertions) {
        var success: Unit? = null
        var failure: ResponseError<out RequestError>? = null
        every { mockRoute.routeType } returns RouteType.DYNAMIC_ROUTE
        subject.sendRoute("", "", mockRoute)
            .onComplete { success = it }
            .onFailure { failure = it }

        capabilitiesTask.complete(
            SendToCarCapabilities(
                listOf(SendToCarCapability.STATIC_ROUTE_BACKEND),
                emptyList()
            )
        )

        softly.assertThat(success).isNull()
        softly.assertThat(failure?.requestError is SendToCarNotPossibleError.Capability).isTrue
    }

    @Test
    fun `sendRoute() should fail when vehicle not capable (3)`(softly: SoftAssertions) {
        var success: Unit? = null
        var failure: ResponseError<out RequestError>? = null
        every { mockRoute.routeType } returns RouteType.STATIC_ROUTE
        subject.sendRoute("", "", mockRoute)
            .onComplete { success = it }
            .onFailure { failure = it }

        capabilitiesTask.complete(
            SendToCarCapabilities(
                listOf(SendToCarCapability.DYNAMIC_ROUTE_BACKEND),
                emptyList()
            )
        )

        softly.assertThat(success).isNull()
        softly.assertThat(failure?.requestError is SendToCarNotPossibleError.Capability).isTrue
    }

    @Test
    fun `sendRoute() should send route to car`(softly: SoftAssertions) {
        var success: Unit? = null
        var failure: ResponseError<out RequestError>? = null
        every { mockRoute.routeType } returns RouteType.STATIC_ROUTE
        subject.sendRoute("", "", mockRoute)
            .onComplete { success = it }
            .onFailure { failure = it }

        capabilitiesTask.complete(
            SendToCarCapabilities(
                listOf(SendToCarCapability.STATIC_ROUTE_BACKEND),
                emptyList()
            )
        )

        sendRoutePoiTask.complete(Unit)

        softly.assertThat(success).isNotNull
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `sendRoute() should pass send route error to caller`(softly: SoftAssertions) {
        var success: Unit? = null
        var failure: ResponseError<out RequestError>? = null
        every { mockRoute.routeType } returns RouteType.STATIC_ROUTE
        subject.sendRoute("", "", mockRoute)
            .onComplete { success = it }
            .onFailure { failure = it }

        capabilitiesTask.complete(
            SendToCarCapabilities(
                listOf(SendToCarCapability.STATIC_ROUTE_BACKEND),
                emptyList()
            )
        )

        val responseError: ResponseError<out RequestError> =
            ResponseError.networkError(NetworkError.NO_CONNECTION)
        sendRoutePoiTask.fail(responseError)

        softly.assertThat(success).isNull()
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `sendPoi() should fail sending when preconditions not met`(softly: SoftAssertions) {
        var success: Unit? = null
        var failure: ResponseError<out RequestError>? = null
        subject.sendPoi("", "", mockPoi)
            .onComplete { success = it }
            .onFailure { failure = it }

        capabilitiesTask.complete(
            SendToCarCapabilities(
                emptyList(),
                listOf(SendToCarPrecondition.MBAPPS_REGISTER_USER)
            )
        )

        softly.assertThat(success).isNull()
        softly.assertThat(failure?.requestError is SendToCarNotPossibleError.Preconditions).isTrue
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `sendPoi() should fail when vehicle not capable - bluetooth`(
        isBluetoothEnabled: Boolean,
        softly: SoftAssertions
    ) {
        subject.enableBluetooth(isBluetoothEnabled)
        var failure: ResponseError<out RequestError>? = null
        subject.sendPoi("", "", mockPoi)
            .onFailure { failure = it }

        capabilitiesTask.complete(
            SendToCarCapabilities(
                listOf(SendToCarCapability.SINGLE_POI_BLUETOOTH),
                emptyList()
            )
        )

        softly.assertThat(failure?.requestError is SendToCarNotPossibleError.Capability)
            .isEqualTo(!isBluetoothEnabled)
    }

    @Test
    fun `sendPoi() should send via Backend, only BE available, no Bluetooth`(softly: SoftAssertions) {
        var success: Unit? = null
        var failure: ResponseError<out RequestError>? = null

        subject.sendPoi("", "", mockPoi)
            .onComplete { success = it }
            .onFailure { failure = it }

        capabilitiesTask.complete(
            SendToCarCapabilities(
                listOf(SendToCarCapability.SINGLE_POI_BACKEND),
                emptyList()
            )
        )
        sendRoutePoiTask.complete(Unit)

        verify { mockSendToCarProvider.sendPoi(any(), any(), any()) }

        softly.assertThat(success).isNotNull
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `sendPoi() should send via Bluetooth, BE and Bluetooth available in vehicle`(softly: SoftAssertions) {
        subject.enableBluetooth(true)
        every { MBCarKit.bluetoothSendToCarService } returns mockBluetoothSendToCarService

        var success: Unit? = null
        var failure: ResponseError<out RequestError>? = null

        subject.sendPoi("", "", mockPoi)
            .onComplete { success = it }
            .onFailure { failure = it }

        capabilitiesTask.complete(
            SendToCarCapabilities(
                listOf(
                    SendToCarCapability.SINGLE_POI_BACKEND,
                    SendToCarCapability.SINGLE_POI_BLUETOOTH
                ),
                emptyList()
            )
        )
        sendRoutePoiTask.complete(Unit)

        verify(exactly = 0) { mockSendToCarProvider.sendPoi(any(), any(), any()) }
        verify { mockBluetoothSendToCarService.sendPoi(any(), any(), any()) }

        softly.assertThat(success).isNotNull
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `sendPoi() should send via BE, Bluetooth available in vehicle, Bluetooth enabled`(softly: SoftAssertions) {
        subject.enableBluetooth(true)
        every { MBCarKit.bluetoothSendToCarService } returns mockBluetoothSendToCarService

        var success: Unit? = null
        var failure: ResponseError<out RequestError>? = null

        subject.sendPoi("", "", mockPoi)
            .onComplete { success = it }
            .onFailure { failure = it }

        capabilitiesTask.complete(
            SendToCarCapabilities(
                listOf(SendToCarCapability.SINGLE_POI_BACKEND),
                emptyList()
            )
        )
        sendRoutePoiTask.complete(Unit)

        verify { mockSendToCarProvider.sendPoi(any(), any(), any()) }
        verify(exactly = 0) { mockBluetoothSendToCarService.sendPoi(any(), any(), any()) }

        softly.assertThat(success).isNotNull
        softly.assertThat(failure).isNull()
    }

    @ParameterizedTest
    @ArgumentsSource(PreconditionsProvider::class)
    fun `sendPoi() should not fail if BT enabled and preconditions for BE present`(
        preconditions: List<SendToCarPrecondition>,
        isBluetoothEnabled: Boolean,
        softly: SoftAssertions
    ) {
        subject.enableBluetooth(isBluetoothEnabled)
        every { MBCarKit.bluetoothSendToCarService } returns mockBluetoothSendToCarService

        var success: Unit? = null
        var failure: ResponseError<out RequestError>? = null

        subject.sendPoi("", "", mockPoi)
            .onComplete { success = it }
            .onFailure { failure = it }

        capabilitiesTask.complete(
            SendToCarCapabilities(
                listOf(SendToCarCapability.SINGLE_POI_BLUETOOTH),
                preconditions
            )
        )
        sendRoutePoiTask.complete(Unit)

        verify(exactly = 0) { mockSendToCarProvider.sendPoi(any(), any(), any()) }

        if (isBluetoothEnabled) {
            verify { mockBluetoothSendToCarService.sendPoi(any(), any(), any()) }

            softly.assertThat(success).isNotNull
            softly.assertThat(failure).isNull()
        } else {
            verify(exactly = 0) { mockBluetoothSendToCarService.sendPoi(any(), any(), any()) }
            softly.assertThat(success).isNull()
            softly.assertThat(failure?.requestError is SendToCarNotPossibleError.Preconditions).isTrue
            softly.assertThat((failure?.requestError as SendToCarNotPossibleError.Preconditions).preconditions)
                .isEqualTo(preconditions)
        }
    }

    internal class PreconditionsProvider : ArgumentsProvider {

        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> =
            listOf(
                listOf(SendToCarPrecondition.MBAPPS_ENABLE_MBAPPS) to true,
                listOf(SendToCarPrecondition.MBAPPS_REGISTER_USER) to true,
                listOf(
                    SendToCarPrecondition.MBAPPS_ENABLE_MBAPPS,
                    SendToCarPrecondition.MBAPPS_REGISTER_USER
                ) to true,
                listOf(SendToCarPrecondition.MBAPPS_ENABLE_MBAPPS) to false,
                listOf(SendToCarPrecondition.MBAPPS_REGISTER_USER) to false,
                listOf(
                    SendToCarPrecondition.MBAPPS_ENABLE_MBAPPS,
                    SendToCarPrecondition.MBAPPS_REGISTER_USER
                ) to false
            ).map {
                Arguments { arrayOf(it.first, it.second) }
            }.stream()
    }
}
