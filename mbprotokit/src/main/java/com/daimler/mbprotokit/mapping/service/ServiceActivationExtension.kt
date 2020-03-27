package com.daimler.mbprotokit.mapping.service

import com.daimler.mbprotokit.dto.service.ServiceActivationStatusUpdate
import com.daimler.mbprotokit.dto.service.ServiceActivationStatusUpdates
import com.daimler.mbprotokit.dto.service.ServiceStatus
import com.daimler.mbprotokit.dto.service.ServiceUpdate
import com.daimler.mbprotokit.generated.ServiceActivation

internal fun ServiceActivation.ServiceStatusUpdatesByVIN.map() = ServiceActivationStatusUpdates(
    updatesByVin = updatesMap.mapValues { it.value.map() },
    sequenceNumber = sequenceNumber
)

internal fun ServiceActivation.ServiceStatusUpdate.map() = ServiceActivationStatusUpdate(
    sequenceNumber,
    vin,
    updatesMap.map()
)

internal fun Map<Int, ServiceActivation.ServiceStatus>.map() =
    map { ServiceUpdate(it.key, ServiceStatus.map(it.value)) }
