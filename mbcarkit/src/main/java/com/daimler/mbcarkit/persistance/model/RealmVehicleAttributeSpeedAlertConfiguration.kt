package com.daimler.mbcarkit.persistance.model

import com.daimler.mbcarkit.business.model.vehicle.SpeedAlertConfiguration
import com.daimler.mbcarkit.business.model.vehicle.StatusEnum
import com.daimler.mbcarkit.business.model.vehicle.VehicleAttribute
import com.daimler.mbcarkit.business.model.vehicle.unit.DisplayUnitCase
import io.realm.RealmList
import io.realm.RealmObject
import java.util.Date

internal open class RealmVehicleAttributeSpeedAlertConfiguration(
    var status: Int = StatusEnum.INVALID.value,
    override var timestamp: Long? = null,
    var endTimestampsInSeconds: RealmList<Long> = RealmList(),
    var thresholdsInKilometersPerHour: RealmList<Int> = RealmList(),
    var thresholdDisplayValue: RealmList<String> = RealmList(),
    var displayValue: String? = null,
    var displayUnitCaseOrdinal: Int = DisplayUnitCase.DISPLAYUNIT_NOT_SET.ordinal,
    var displayUnitOrdinal: Int = 0
) : RealmObject(), RealmTimeObject

internal fun RealmVehicleAttributeSpeedAlertConfiguration.fromVehicleAttribute(value: VehicleAttribute<List<SpeedAlertConfiguration>, *>): RealmVehicleAttributeSpeedAlertConfiguration {
    status = value.status.value
    timestamp = value.lastChanged?.time
    endTimestampsInSeconds.clear()
    endTimestampsInSeconds.addAll(value.value?.map { it.endTimestampInSeconds } ?: emptyList())
    thresholdsInKilometersPerHour.clear()
    thresholdsInKilometersPerHour.addAll(value.value?.map { it.thresholdInKilometersPerHour } ?: emptyList())
    thresholdDisplayValue.clear()
    thresholdDisplayValue.addAll(value.value?.map { it.thresholdDisplayValue } ?: emptyList())
    value.unit?.displayUnit?.let {
        displayValue = value.unit.displayValue
        displayUnitCaseOrdinal = value.unit.displayUnitCase.ordinal
        displayUnitOrdinal = value.unit.displayUnit.ordinal
    }
    return this
}

internal fun RealmVehicleAttributeSpeedAlertConfiguration.toVehicleAttribute(unit: VehicleAttribute.Unit<*>?): VehicleAttribute<List<SpeedAlertConfiguration>, *> {
    val status = StatusEnum.from(status)
    val lastChanged = timestamp?.let { ts -> Date(ts) }
    val speedAlertConfigurations = endTimestampsInSeconds.mapIndexed { index, endTimestamp ->
        SpeedAlertConfiguration(
            endTimestamp,
            thresholdsInKilometersPerHour.getOrNull(index) ?: 0,
            thresholdDisplayValue.getOrNull(index).orEmpty()
        )
    }
    return VehicleAttribute(status, lastChanged, speedAlertConfigurations, unit)
}
