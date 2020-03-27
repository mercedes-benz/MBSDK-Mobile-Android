package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.services.ServiceActivationStatusUpdateWrapper
import com.daimler.mbnetworkkit.socket.message.ObservableMessage

internal class ServiceActivationObservableMessage(
    update: ServiceActivationStatusUpdateWrapper
) : ObservableMessage<ServiceActivationStatusUpdateWrapper>(update)
