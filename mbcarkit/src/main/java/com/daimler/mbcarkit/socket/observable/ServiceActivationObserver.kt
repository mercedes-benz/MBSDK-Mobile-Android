package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.services.ServiceActivationStatusUpdate
import com.daimler.mbcarkit.business.model.services.ServiceActivationStatusUpdateWrapper
import com.daimler.mbnetworkkit.socket.message.MessageObserver
import com.daimler.mbnetworkkit.socket.message.ObservableMessage

sealed class ServiceActivationObserver<T>(
    private val action: (T) -> Unit
) : MessageObserver<T> {

    override fun onUpdate(observableMessage: ObservableMessage<T>) {
        action.invoke(observableMessage.data)
    }

    /**
     * Observes for [ServiceActivationStatusUpdate].
     *
     * @param finOrVin the FIN/ VIN of the vehicle of which the services should be observed;
     * null to observe the services of the currently selected vehicle
     * @param action the action to execute with the new [ServiceActivationStatusUpdate]
     */
    class ServiceStatus(
        private val finOrVin: String? = null,
        action: (ServiceActivationStatusUpdate) -> Unit
    ) : ServiceActivationObserver<ServiceActivationStatusUpdateWrapper>({ action.invoke(it.statusUpdate) }) {

        override fun onUpdate(observableMessage: ObservableMessage<ServiceActivationStatusUpdateWrapper>) {
            if (shouldNotify(observableMessage)) {
                super.onUpdate(observableMessage)
            }
        }

        private fun shouldNotify(observableMessage: ObservableMessage<ServiceActivationStatusUpdateWrapper>) =
            (finOrVin == null && observableMessage.data.isSelectedVehicle) ||
                (finOrVin == observableMessage.data.statusUpdate.finOrVin)
    }
}
