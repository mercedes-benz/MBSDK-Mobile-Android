package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.services.ServiceActivationStatusUpdate
import com.daimler.mbcarkit.business.model.services.ServiceActivationStatusUpdateWrapper
import com.daimler.mbnetworkkit.socket.message.ObservableMessage
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ServiceActivationObserverTest {

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `ServiceUpdate without FIN should only notify if update for selected vehicle`(input: Boolean) {
        var update: ServiceActivationStatusUpdate? = null
        val observer = ServiceActivationObserver.ServiceStatus { update = it }
        val statusUpdate = ServiceActivationStatusUpdateWrapper(
            input,
            ServiceActivationStatusUpdate(0, SELECTED_FIN, emptyList())
        )
        observer.onUpdate(ObservableMessage(statusUpdate))
        Assertions.assertEquals(input, update != null)
    }

    @ParameterizedTest
    @ValueSource(strings = [FIN_1, FIN_2, FIN_3])
    fun `ServiceUpdate with FIN should only notify for equal FIN`(input: String) {
        var update: ServiceActivationStatusUpdate? = null
        val observedFinOrVin = FIN_1
        val observer = ServiceActivationObserver.ServiceStatus(observedFinOrVin) { update = it }
        val statusUpdate = ServiceActivationStatusUpdateWrapper(
            false,
            ServiceActivationStatusUpdate(0, input, emptyList())
        )
        observer.onUpdate(ObservableMessage(statusUpdate))
        Assertions.assertEquals(input == observedFinOrVin, update != null)
    }

    private companion object {

        private const val SELECTED_FIN = "fin.selected"
        private const val FIN_1 = "1"
        private const val FIN_2 = "2"
        private const val FIN_3 = "3"
    }
}
