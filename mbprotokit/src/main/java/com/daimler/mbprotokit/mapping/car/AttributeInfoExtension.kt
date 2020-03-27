package com.daimler.mbprotokit.mapping.car

import com.daimler.mbprotokit.dto.car.AttributeInfo
import com.daimler.mbprotokit.dto.car.AttributeStatus
import com.daimler.mbprotokit.generated.VehicleEvents
import java.util.Date

/**
 * Maps status, timestamp and unit information
 * into AttributeInfo [com.daimler.mbprotokit.dto.car.AttributeInfo]
 */
internal fun VehicleEvents.VehicleAttributeStatus?.getInfo(): AttributeInfo = AttributeInfo(
    status = AttributeStatus.map(VehicleEvents.AttributeStatus.forNumber(this?.status ?: -1)),
    lastChanged = extractTimestamp(
        this?.timestampInMs,
        this?.timestamp
    ),
    unit = this.mapUnit()
)

/**
 * @return Takes the first non-nullable timestamp
 */
private fun extractTimestamp(timestampInMs: Long?, timestamp: Long?): Date? = when {
    timestampInMs != null -> Date(timestampInMs)
    timestamp != null -> Date(timestamp)
    else -> null
}
