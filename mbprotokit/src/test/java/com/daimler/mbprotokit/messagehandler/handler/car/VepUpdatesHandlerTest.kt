package com.daimler.mbprotokit.messagehandler.handler.car

import com.daimler.mbprotokit.ReceivedCarMessageCallback
import com.daimler.mbprotokit.dto.car.VehicleStatusUpdates
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.testutils.coroutines.CoroutineTest
import com.daimler.testutils.coroutines.TestCoroutineExtension
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(SoftAssertionsExtension::class, TestCoroutineExtension::class)
internal class VepUpdatesHandlerTest : CoroutineTest {

    private val callback = mockk<ReceivedCarMessageCallback>(relaxUnitFun = true)

    private lateinit var scope: CoroutineScope

    private lateinit var subject: VepUpdatesHandler

    override fun attachScope(scope: CoroutineScope) {
        this.scope = scope
    }

    @BeforeEach
    fun setup() {
        subject = createSubject()
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @Test
    fun `should notify with result`(softly: SoftAssertions, scope: TestCoroutineScope) {
        scope.runBlockingTest {
            val vep = mutableMapOf<String, VehicleEvents.VEPUpdate>().apply {
                repeat(2) {
                    put("fin_$it", VehicleEvents.VEPUpdate.getDefaultInstance())
                }
            }
            val vepUpdates = VehicleEvents.VEPUpdatesByVIN.newBuilder()
                .putAllUpdates(vep)
                .build()
            val message = mockk<VehicleEvents.PushMessage>().also {
                every { it.vepUpdates } returns vepUpdates
            }
            val updatesSlot = slot<VehicleStatusUpdates>()
            every { callback.onVehicleStatusUpdate(capture(updatesSlot)) } just runs
            subject.handle(mockk(), message, callback)
            softly.assertThat(updatesSlot.isCaptured)
            softly.assertThat(updatesSlot.captured.vehiclesByVin.size).isEqualTo(vep.size)
        }
    }

    @Test
    fun `should notify with error`(softly: SoftAssertions) {
        val message = mockk<VehicleEvents.PushMessage>().also {
            every { it.vepUpdates } returns null
        }
        subject.handle(mockk(), message, callback)
        verify {
            callback.onError(any(), any())
        }
    }

    private fun createSubject() = VepUpdatesHandler(scope)
}
