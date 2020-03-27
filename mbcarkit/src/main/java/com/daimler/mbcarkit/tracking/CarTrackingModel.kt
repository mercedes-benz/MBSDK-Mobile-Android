package com.daimler.mbcarkit.tracking

import com.daimler.mbcarkit.business.model.command.CommandCondition
import com.daimler.mbcarkit.business.model.command.VehicleCommandStatus

data class CarTrackingModel(
    var id: String?,
    var carSeries: String?,
    var timestamp: Long?,
    var state: VehicleCommandStatus?,
    var condition: CommandCondition?,
    var responseCode: String?,
    var sdkVersion: String?,
    var intValue: Int?,
    var boolValue: Boolean?,
    var commandDuration: Long?,
    var stateType: VehicleCommandStatus? = null
) {

    fun toTrackingMap(): Map<String, String> = mapOf(
        CarTrackingModel::id.name to id.toString(),
        CarTrackingModel::carSeries.name to carSeries.toString(),
        CarTrackingModel::timestamp.name to timestamp.toString(),
        CarTrackingModel::state.name to state.toString(),
        CarTrackingModel::condition.name to condition.toString(),
        CarTrackingModel::responseCode.name to responseCode.toString(),
        CarTrackingModel::sdkVersion.name to sdkVersion.toString(),
        CarTrackingModel::intValue.name to intValue.toString(),
        CarTrackingModel::boolValue.name to boolValue.toString(),
        CarTrackingModel::commandDuration.name to commandDuration.toString(),
        CarTrackingModel::stateType.name to stateType.toString()
    )
}
