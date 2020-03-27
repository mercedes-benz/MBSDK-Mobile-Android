package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.AssignmentPendingState
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.google.gson.annotations.SerializedName
import java.util.Date

internal data class ApiVehicle(
    @SerializedName("fin") val fin: String,
    @SerializedName("vin") val vin: String?,
    @SerializedName("licensePlate") val licensePlate: String?,
    @SerializedName("technicalInformation") val technicalInformation: ApiTechnicalVehicleInfo?,
    @SerializedName("salesRelatedInformation") val salesRelatedInformation: ApiSalesRelatedVehicleInfo?,
    @SerializedName("trustLevel") val trustLevel: Int,
    @SerializedName("tirePressureMonitoringType") val tirePressureMonitoringState: ApiTirePressureMonitoringState?,
    @SerializedName("dealers") val dealers: ApiVehicleDealersResponse?,
    @SerializedName("carline") val carline: String?,
    @SerializedName("dataCollectorVersion") val dataCollectorVersion: ApiDataCollectorVersion?,
    @SerializedName("doorsCount") val doorsCount: ApiDoorsCount?,
    @SerializedName("fuelType") val fuelType: ApiFuelType?,
    @SerializedName("handDrive") val handDrive: ApiHandDrive?,
    @SerializedName("hasAuxHeat") val hasAuxHeat: Boolean?,
    @SerializedName("mopf") val mopf: Boolean?,
    @SerializedName("roofType") val roofType: ApiRoofType?,
    @SerializedName("starArchitecture") val starArchitecture: ApiStarArchitecture?,
    @SerializedName("tcuHardwareVersion") val tcuHardwareVersion: ApiTcuHardwareVersion?,
    @SerializedName("windowsLiftCount") val windowsLiftCount: ApiWindowsLiftCount?,
    @SerializedName("vehicleConnectivity") val vehicleConnectivity: ApiVehicleConnectivity?,
    @SerializedName("tcuSoftwareVersion") val tcuSoftwareVersion: ApiTcuSoftwareVersion?,
    @SerializedName("vehicleSegment") val vehicleSegment: ApiVehicleSegment,
    @SerializedName("isOwner") val isOwner: Boolean?,
    @SerializedName("indicatorImageUrl") val indicatorImageUrl: String?,
    @SerializedName("isTemporarilyAccessible") val temporarilyAccessible: Boolean?,
    @SerializedName("accessibleUntil") val accessibleUntil: Date?,
    @SerializedName("profileSyncSupport") val profileSyncSupport: ApiProfileSyncSupport?,
    @SerializedName("normalizedProfileControlSupport") val normalizedProfileControlSupport: ApiNormalizedProfileControlSupport?
)

internal fun ApiVehicle.toVehicleInfo() =
    VehicleInfo(
        vin,
        fin,
        licensePlate.orEmpty(),
        getModelFromApiVehicle(this),
        AssignmentPendingState.NONE,
        trustLevel,
        salesRelatedInformation?.baumuster?.value ?: technicalInformation?.baumuster.orEmpty(),
        tirePressureMonitoringState.toTirePressureMonitoringState(),
        dealers?.items?.map { apiDealer ->
            apiDealer.toVehicleDealer()
        } ?: emptyList(),
        carline,
        dataCollectorVersion.toDataCollectorVersion(),
        doorsCount.toDoorsCount(),
        fuelType.toFuelType(),
        handDrive.toHandDrive(),
        hasAuxHeat ?: false,
        mopf ?: false,
        roofType.toRoofType(),
        starArchitecture.toStarArchitecture(),
        tcuHardwareVersion.toTcuHardwareVersion(),
        windowsLiftCount.toWindowsLiftCount(),
        vehicleConnectivity.toVehicleConnectivity(),
        tcuSoftwareVersion.toTcuSoftwareVersion(),
        vehicleSegment.toVehicleSegment(),
        isOwner ?: false,
        indicatorImageUrl,
        temporarilyAccessible ?: false,
        accessibleUntil,
        profileSyncSupport.toProfileSyncSupport(),
        normalizedProfileControlSupport.toNormalizedProfileControlSupport(),
        salesRelatedInformation?.paint.toVehicleAmenity(),
        salesRelatedInformation?.upholstery.toVehicleAmenity(),
        salesRelatedInformation?.line.toVehicleAmenity()
    )

private fun getModelFromApiVehicle(vehicle: ApiVehicle): String {
    val salesRelatedInformationDescription = vehicle.salesRelatedInformation?.baumuster?.description
    if (!salesRelatedInformationDescription.isNullOrBlank()) return salesRelatedInformationDescription
    return vehicle.technicalInformation?.salesDesignation.orEmpty()
}
