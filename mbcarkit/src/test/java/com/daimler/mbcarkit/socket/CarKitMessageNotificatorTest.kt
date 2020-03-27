package com.daimler.mbcarkit.socket

import com.daimler.mbcarkit.business.model.vehicle.Auxheat
import com.daimler.mbcarkit.business.model.vehicle.Doors
import com.daimler.mbcarkit.business.model.vehicle.DrivingModes
import com.daimler.mbcarkit.business.model.vehicle.EcoScore
import com.daimler.mbcarkit.business.model.vehicle.Engine
import com.daimler.mbcarkit.business.model.vehicle.HeadUnit
import com.daimler.mbcarkit.business.model.vehicle.Location
import com.daimler.mbcarkit.business.model.vehicle.Statistics
import com.daimler.mbcarkit.business.model.vehicle.Tank
import com.daimler.mbcarkit.business.model.vehicle.Theft
import com.daimler.mbcarkit.business.model.vehicle.Tires
import com.daimler.mbcarkit.business.model.vehicle.VehicleData
import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbcarkit.business.model.vehicle.VehicleWarnings
import com.daimler.mbcarkit.business.model.vehicle.Windows
import com.daimler.mbcarkit.business.model.vehicle.Zev
import com.daimler.mbcarkit.socket.observable.AuxheatObservableMessage
import com.daimler.mbcarkit.socket.observable.DoorsObservableMessage
import com.daimler.mbcarkit.socket.observable.DrivingModesObservableMessage
import com.daimler.mbcarkit.socket.observable.EcoScoreObservableMessage
import com.daimler.mbcarkit.socket.observable.EngineObservableMessage
import com.daimler.mbcarkit.socket.observable.HeadUnitObservableMessage
import com.daimler.mbcarkit.socket.observable.LocationObservableMessage
import com.daimler.mbcarkit.socket.observable.StatisticsObservableMessage
import com.daimler.mbcarkit.socket.observable.TankObservableMessage
import com.daimler.mbcarkit.socket.observable.TheftObservableMessage
import com.daimler.mbcarkit.socket.observable.TiresObservableMessage
import com.daimler.mbcarkit.socket.observable.VehicleDataObservable
import com.daimler.mbcarkit.socket.observable.VehicleObservableMessage
import com.daimler.mbcarkit.socket.observable.VehicleStatusObservableMessage
import com.daimler.mbcarkit.socket.observable.WarningsObservableMessage
import com.daimler.mbcarkit.socket.observable.WindowsObservableMessage
import com.daimler.mbcarkit.socket.observable.ZevObservableMessage
import com.daimler.mbnetworkkit.socket.message.Notifyable
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class CarKitMessageNotificatorTest {

    private val notifyable = mockk<Notifyable>(relaxUnitFun = true)

    private val vehicleStatus = VehicleStatus.initialState("finOrVin")

    private lateinit var subject: CarKitMessageNotificator

    @BeforeEach
    fun setup() {
        subject = createSubject()
    }

    @AfterEach
    fun shutdown() {
        clearAllMocks()
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should notify VehicleStatus if changed`(changed: Boolean) {
        simpleNotifyTest<VehicleStatus, VehicleStatusObservableMessage>(changed) { it }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should notify Auxheat if changed`(changed: Boolean) {
        simpleNotifyTest<Auxheat, AuxheatObservableMessage>(changed) { it.auxheat }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should notify Doors if changed`(changed: Boolean) {
        simpleNotifyTest<Doors, DoorsObservableMessage>(changed) { it.doors }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should notify EcoScore if changed`(changed: Boolean) {
        simpleNotifyTest<EcoScore, EcoScoreObservableMessage>(changed) { it.ecoScore }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should notify HeadUnit if changed`(changed: Boolean) {
        simpleNotifyTest<HeadUnit, HeadUnitObservableMessage>(changed) { it.hu }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should notify Location if changed`(changed: Boolean) {
        simpleNotifyTest<Location, LocationObservableMessage>(changed) { it.location }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should notify Statistics if changed`(changed: Boolean) {
        simpleNotifyTest<Statistics, StatisticsObservableMessage>(changed) { it.statistics }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should notify Tank if changed`(changed: Boolean) {
        simpleNotifyTest<Tank, TankObservableMessage>(changed) { it.tank }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should notify Tires if changed`(changed: Boolean) {
        simpleNotifyTest<Tires, TiresObservableMessage>(changed) { it.tires }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should notify VehicleData if changed`(changed: Boolean) {
        simpleNotifyTest<VehicleData, VehicleDataObservable>(changed) { it.vehicle }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should notify Warnings if changed`(changed: Boolean) {
        simpleNotifyTest<VehicleWarnings, WarningsObservableMessage>(changed) { it.warnings }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should notify Windows if changed`(changed: Boolean) {
        simpleNotifyTest<Windows, WindowsObservableMessage>(changed) { it.windows }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should notify Theft if changed`(changed: Boolean) {
        simpleNotifyTest<Theft, TheftObservableMessage>(changed) { it.theft }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should notify Engine if changed`(changed: Boolean) {
        simpleNotifyTest<Engine, EngineObservableMessage>(changed) { it.engine }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should notify Zev if changed`(changed: Boolean) {
        simpleNotifyTest<Zev, ZevObservableMessage>(changed) { it.zev }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `should notify DrivingModes if changed`(changed: Boolean) {
        simpleNotifyTest<DrivingModes, DrivingModesObservableMessage>(changed) { it.drivingModes }
    }

    private fun createSubject() = CarKitMessageNotificator(notifyable)

    private inline fun <reified S, reified T : VehicleObservableMessage<S>> simpleNotifyTest(
        changed: Boolean,
        crossinline valueReceiver: (VehicleStatus) -> S
    ) {
        answerChange<T>(changed)
        subject.notifyVehicleRelatedObservers(vehicleStatus, vehicleStatus)
        verify(inverse = !changed) {
            notifyable.notifyChange(S::class.java, valueReceiver(vehicleStatus))
        }
    }

    private inline fun <reified T : VehicleObservableMessage<*>> answerChange(changed: Boolean) {
        mockkConstructor(T::class)
        every { anyConstructed<T>().hasChanged(any(), any()) } returns changed
    }
}
