package com.daimler.mbcarkit.proto.tmp.service

import com.daimler.mbcarkit.business.model.services.ServiceActivationStatusUpdate
import com.daimler.mbcarkit.business.model.services.ServiceActivationStatusUpdates
import com.daimler.mbcarkit.business.model.services.ServiceStatus
import com.daimler.mbcarkit.business.model.services.ServiceUpdate

fun com.daimler.mbprotokit.dto.service.ServiceActivationStatusUpdates.map() =
    ServiceActivationStatusUpdates(
        updatesByVin = updatesByVin.mapValues { it.value.map() },
        sequenceNumber = sequenceNumber
    )

fun com.daimler.mbprotokit.dto.service.ServiceActivationStatusUpdate.map() =
    ServiceActivationStatusUpdate(
        sequenceNumber = sequenceNumber,
        finOrVin = finOrVin,
        updates = updates.map { it.map() }
    )

fun com.daimler.mbprotokit.dto.service.ServiceUpdate.map() = ServiceUpdate(
    id = id,
    status = status.map()
)

fun com.daimler.mbprotokit.dto.service.ServiceStatus.map() = when (this) {
    com.daimler.mbprotokit.dto.service.ServiceStatus.UNKNOWN -> ServiceStatus.UNKNOWN
    com.daimler.mbprotokit.dto.service.ServiceStatus.ACTIVE -> ServiceStatus.ACTIVE
    com.daimler.mbprotokit.dto.service.ServiceStatus.INACTIVE -> ServiceStatus.INACTIVE
    com.daimler.mbprotokit.dto.service.ServiceStatus.ACTIVATION_PENDING -> ServiceStatus.ACTIVATION_PENDING
    com.daimler.mbprotokit.dto.service.ServiceStatus.DEACTIVATION_PENDING -> ServiceStatus.DEACTIVATION_PENDING
}
