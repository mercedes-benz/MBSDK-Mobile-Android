package com.daimler.mbcarkit.implementation

/*@ExtendWith(SoftAssertionsExtension::class)
class CachedSendToCarServiceTest {

    private val service: SendToCarService = mockk()
    private val cache: SendToCarCache = mockk()
    private val bluetoothSendToCarService: BluetoothSendToCarService = mockk()

    private lateinit var sendToCarCapabilitiesTask: ResponseTaskObject<List<SendToCarCapability>>
    private lateinit var unitTask: ResponseTaskObjectUnit

    private lateinit var subject: CachedSendToCarProvider

    @BeforeEach
    fun setup() {
        sendToCarCapabilitiesTask = ResponseTaskObject()
        unitTask = ResponseTaskObjectUnit()
        subject = CachedSendToCarProvider(service, cache)
        every { service.fetchCapabilities(any(), any()) } returns sendToCarCapabilitiesTask
        every { service.sendRoute(any(), any(), any()) } returns unitTask

        every { cache.saveCapabilities(any(), any()) } returns Unit

        mockkObject(MBCarKit)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `fetchCapabilities with cached values should return cached values and fetch fresh values`(softly: SoftAssertions) {
        var success: List<SendToCarCapability>? = null
        var failure: ResponseError<out RequestError>? = null

        val capabilities = listOf(SendToCarCapability.SINGLE_POI_BACKEND)
        every { cache.loadCapabilities(any()) } returns capabilities

        subject.fetchCapabilities("", "")
            .onComplete { success = it }
            .onFailure { failure = it }

        verify { service.fetchCapabilities(any(), any()) }

        softly.assertThat(success).isEqualTo(capabilities)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchCapabilities with bluetooth enabled should return unfiltered capabilities`(softly: SoftAssertions) {
        var success: List<SendToCarCapability>? = null
        var failure: ResponseError<out RequestError>? = null

        every { MBCarKit.bluetoothSendToCarService } returns bluetoothSendToCarService
        subject.enableBluetooth(true)

        val capabilities = listOf(
            SendToCarCapability.SINGLE_POI_BLUETOOTH,
            SendToCarCapability.SINGLE_POI_BACKEND
        )
        every { cache.loadCapabilities(any()) } returns capabilities

        subject.fetchCapabilities("", "")
            .onComplete { success = it }
            .onFailure { failure = it }

        softly.assertThat(success).isEqualTo(capabilities)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchCapabilities with bluetooth disabled should return filtered capabilities`(softly: SoftAssertions) {
        var success: List<SendToCarCapability>? = null
        var failure: ResponseError<out RequestError>? = null

        every { MBCarKit.bluetoothSendToCarService } returns bluetoothSendToCarService
        subject.enableBluetooth(false)

        val capabilities = listOf(
            SendToCarCapability.SINGLE_POI_BLUETOOTH,
            SendToCarCapability.SINGLE_POI_BACKEND
        )
        every { cache.loadCapabilities(any()) } returns capabilities

        subject.fetchCapabilities("", "")
            .onComplete { success = it }
            .onFailure { failure = it }

        softly.assertThat(success).containsExactly(SendToCarCapability.SINGLE_POI_BACKEND)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchCapabilities without cached values should fetch capabilities and update cache`(softly: SoftAssertions) {
        var success: List<SendToCarCapability>? = null
        var failure: ResponseError<out RequestError>? = null

        every { cache.loadCapabilities(any()) } returns null

        subject.fetchCapabilities("", "")
            .onComplete { success = it }
            .onFailure { failure = it }

        verify { service.fetchCapabilities(any(), any()) }

        val capabilities = listOf(SendToCarCapability.SINGLE_POI_BACKEND)
        sendToCarCapabilitiesTask.complete(capabilities)

        verify { cache.saveCapabilities(any(), any()) }

        softly.assertThat(success).isEqualTo(capabilities)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchCapabilities without cached values and service failure should fail task`(softly: SoftAssertions) {
        var success: List<SendToCarCapability>? = null
        var failure: ResponseError<out RequestError>? = null

        every { cache.loadCapabilities(any()) } returns null

        subject.fetchCapabilities("", "")
            .onComplete { success = it }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        sendToCarCapabilitiesTask.fail(responseError)

        softly.assertThat(success).isNull()
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `sendRoute with bluetooth enabled and only bluetooth capabilities should send route via bluetooth with caching`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        every { MBCarKit.bluetoothSendToCarService } returns bluetoothSendToCarService
        every { bluetoothSendToCarService.sendPoi(any(), any(), any()) } returns unitTask
        every { cache.loadCapabilities(any()) } returns listOf(SendToCarCapability.SINGLE_POI_BLUETOOTH)
        subject.enableBluetooth(true)

        subject.sendRoute("", "", SendToCarRoute(RouteType.SINGLE_POI, listOf(SendToCarWaypoint(0.0, 0.0))))
            .onComplete { success = true }
            .onFailure { failure = it }

        unitTask.complete(Unit)

        verify { bluetoothSendToCarService.sendPoi(any(), any(), eq(true)) }

        softly.assertThat(success).isTrue
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `sendRoute with bluetooth enabled and only bluetooth capabilities and bluetooth failure should fail task`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        every { MBCarKit.bluetoothSendToCarService } returns bluetoothSendToCarService
        every { bluetoothSendToCarService.sendPoi(any(), any(), any()) } returns unitTask
        every { cache.loadCapabilities(any()) } returns listOf(SendToCarCapability.SINGLE_POI_BLUETOOTH)
        subject.enableBluetooth(true)

        subject.sendRoute("", "", SendToCarRoute(RouteType.SINGLE_POI, listOf(SendToCarWaypoint(0.0, 0.0))))
            .onComplete { success = true }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        unitTask.fail(responseError)

        softly.assertThat(success).isFalse
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `sendRoute with bluetooth enabled and only bluetooth capabilities and no waypoints should fail task`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        every { MBCarKit.bluetoothSendToCarService } returns bluetoothSendToCarService
        every { bluetoothSendToCarService.sendPoi(any(), any(), any()) } returns unitTask
        every { cache.loadCapabilities(any()) } returns listOf(SendToCarCapability.SINGLE_POI_BLUETOOTH)
        subject.enableBluetooth(true)

        subject.sendRoute("", "", SendToCarRoute(RouteType.SINGLE_POI, emptyList()))
            .onComplete { success = true }
            .onFailure { failure = it }

        softly.assertThat(success).isFalse
        softly.assertThat(failure?.requestError).isInstanceOf(SendToCarNotPossibleError::class.java)
    }

    @Test
    fun `sendRoute with bluetooth enabled and established bluetooth connection should send route via bluetooth without caching`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        every { MBCarKit.bluetoothSendToCarService } returns bluetoothSendToCarService
        every { bluetoothSendToCarService.sendPoi(any(), any(), any()) } returns unitTask
        every { bluetoothSendToCarService.isBluetoothConnectionAvailable(any()) } returns true
        every { cache.loadCapabilities(any()) } returns listOf(
            SendToCarCapability.SINGLE_POI_BLUETOOTH,
            SendToCarCapability.SINGLE_POI_BACKEND
        )
        subject.enableBluetooth(true)

        subject.sendRoute("", "", SendToCarRoute(RouteType.SINGLE_POI, listOf(SendToCarWaypoint(0.0, 0.0))))
            .onComplete { success = true }
            .onFailure { failure = it }

        unitTask.complete(Unit)

        verify { bluetoothSendToCarService.sendPoi(any(), any(), eq(false)) }

        softly.assertThat(success).isTrue
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `sendRoute with bluetooth enabled and no established bluetooth connection should send route via network`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        every { MBCarKit.bluetoothSendToCarService } returns bluetoothSendToCarService
        every { bluetoothSendToCarService.sendPoi(any(), any(), any()) } returns unitTask
        every { bluetoothSendToCarService.isBluetoothConnectionAvailable(any()) } returns false
        every { cache.loadCapabilities(any()) } returns listOf(
            SendToCarCapability.SINGLE_POI_BLUETOOTH,
            SendToCarCapability.SINGLE_POI_BACKEND
        )
        subject.enableBluetooth(true)

        subject.sendRoute("", "", SendToCarRoute(RouteType.SINGLE_POI, listOf(SendToCarWaypoint(0.0, 0.0))))
            .onComplete { success = true }
            .onFailure { failure = it }

        unitTask.complete(Unit)

        verify { service.sendRoute(any(), any(), any()) }

        softly.assertThat(success).isTrue
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `sendRoute with bluetooth enabled and service failure should fail task`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        every { MBCarKit.bluetoothSendToCarService } returns bluetoothSendToCarService
        every { cache.loadCapabilities(any()) } returns null
        subject.enableBluetooth(true)

        subject.sendRoute("", "", SendToCarRoute(RouteType.SINGLE_POI, emptyList()))
            .onComplete { success = true }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        sendToCarCapabilitiesTask.fail(responseError)

        softly.assertThat(success).isFalse
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `sendRoute with bluetooth disabled should send route via network`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.enableBluetooth(false)

        subject.sendRoute("", "", SendToCarRoute(RouteType.SINGLE_POI, emptyList()))
            .onComplete { success = true }
            .onFailure { failure = it }

        unitTask.complete(Unit)

        softly.assertThat(success).isTrue
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `sendRoute with bluetooth disabled with service failure should fail task`(softly: SoftAssertions) {
        var success = false
        var failure: ResponseError<out RequestError>? = null

        subject.enableBluetooth(false)

        subject.sendRoute("", "", SendToCarRoute(RouteType.SINGLE_POI, emptyList()))
            .onComplete { success = true }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        unitTask.fail(responseError)

        softly.assertThat(success).isFalse
        softly.assertThat(failure).isEqualTo(responseError)
    }
}*/
