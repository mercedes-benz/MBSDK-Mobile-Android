package com.daimler.mbprotokit.dto.service

import com.daimler.mbprotokit.generated.ServiceActivation

enum class ServiceStatus(val status: ServiceActivation.ServiceStatus) {
    UNKNOWN(ServiceActivation.ServiceStatus.SERVICE_STATUS_UNKNOWN),
    ACTIVE(ServiceActivation.ServiceStatus.SERVICE_STATUS_ACTIVE),
    INACTIVE(ServiceActivation.ServiceStatus.SERVICE_STATUS_INACTIVE),
    ACTIVATION_PENDING(ServiceActivation.ServiceStatus.SERVICE_STATUS_ACTIVATION_PENDING),
    DEACTIVATION_PENDING(ServiceActivation.ServiceStatus.SERVICE_STATUS_DEACTIVATION_PENDING);

    companion object {
        fun map(status: ServiceActivation.ServiceStatus) = values().find {
            it.status == status
        } ?: UNKNOWN
    }
}
