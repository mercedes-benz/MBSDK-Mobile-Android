package com.daimler.mbcarkit.persistance.model

import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

internal open class RealmVehicle : RealmObject() {

    @PrimaryKey
    var finOrVin: String = ""

    var vin: String? = null
    var fin: String? = null
    var licensePlate: String? = null
    var model: String? = null
    var assignmentState: Int? = null
    var trustLevel: Int? = null
    var baumuster: String? = null
    var tirePressureMonitoringState: Int? = null
    var dealers: RealmList<RealmVehicleDealer>? = null
    var carline: String? = null
    var dataCollectionVersion: Int? = null
    var doorsCount: Int? = null
    var fuelType: Int? = null
    var handDrive: Int? = null
    var hasAuxHeat: Boolean? = null
    var mopf: Boolean? = null
    var roofType: Int? = null
    var starArchitecture: Int? = null
    var tcuHardwareVersion: Int? = null
    var windowsLiftCount: Int? = null
    var vehicleConnectivity: Int? = null
    var tcuSoftwareVersion: Int? = null
    var vehicleSegment: Int? = null
    var isOwner: Boolean? = null
    var indicatorImageUrl: String? = null
    var temporarilyAccessible: Boolean? = null
    var accessibleUntil: Long? = null
    var profileSyncSupport: Int? = null
    var normalizedProfileControlSupport: Int? = null

    var paintCode: String? = null
    var paintDescription: String? = null
    var upholsteryCode: String? = null
    var upholsteryDescription: String? = null
    var lineCode: String? = null
    var lineDescription: String? = null
}

internal fun RealmVehicle.cascadeDeleteFromRealm() {
    dealers?.toList()?.forEach { it.cascadeDeleteFromRealm() }
    dealers?.deleteAllFromRealm()
    deleteFromRealm()
}

/**
 * Mapper from VehicleInfo to realm model
 * ATTENTION: dealers are handled separetly
 */
internal fun RealmVehicle.fromVehicleInfoNoDealers(vehicle: VehicleInfo) {
    vin = vehicle.vin
    fin = vehicle.fin
    licensePlate = vehicle.licensePlate
    model = vehicle.model
    assignmentState = vehicle.assignmentState?.ordinal
    trustLevel = vehicle.trustLevel
    baumuster = vehicle.baumuster
    tirePressureMonitoringState = vehicle.tirePressureMonitoringState?.ordinal
    carline = vehicle.carline
    dataCollectionVersion = vehicle.dataCollectorVersion?.ordinal
    doorsCount = vehicle.doorsCount?.ordinal
    fuelType = vehicle.fuelType?.ordinal
    handDrive = vehicle.handDrive?.ordinal
    hasAuxHeat = vehicle.hasAuxHeat
    mopf = vehicle.hasFaceLift
    roofType = vehicle.roofType?.ordinal
    starArchitecture = vehicle.starArchitecture?.ordinal
    tcuHardwareVersion = vehicle.tcuHardwareVersion?.ordinal
    windowsLiftCount = vehicle.windowsLiftCount?.ordinal
    vehicleConnectivity = vehicle.vehicleConnectivity?.ordinal
    tcuSoftwareVersion = vehicle.tcuSoftwareVersion?.ordinal
    vehicleSegment = vehicle.vehicleSegment?.ordinal
    isOwner = vehicle.isOwner
    indicatorImageUrl = vehicle.indicatorImageUrl
    temporarilyAccessible = vehicle.temporarilyAccessible
    accessibleUntil = vehicle.accessibleUntil?.time
    profileSyncSupport = vehicle.profileSyncSupport?.ordinal
    normalizedProfileControlSupport = vehicle.normalizedProfileControlSupport?.ordinal
    paintCode = vehicle.paint?.code
    paintDescription = vehicle.paint?.description
    upholsteryCode = vehicle.upholstery?.code
    upholsteryDescription = vehicle.upholstery?.description
    lineCode = vehicle.line?.code
    lineDescription = vehicle.line?.description
}
