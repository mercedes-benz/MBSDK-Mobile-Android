package com.daimler.mbcarkit.business.model.vehicle

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class VehicleInfo(
    /**
     * Vehicle identification number
     */
    val vin: String?,

    /**
     * Fahrzeug Identifizierungs Nummer
     */
    val fin: String?,

    /**
     * License Plate
     */
    val licensePlate: String,

    /**
     * Model series, e.g. 'CLC 180 KOMPRESSOR'
     */
    val model: String,

    /**
     * Current assign-action state, e.g. 'deleting' or 'assigning'.
     * If vehicle is successfully assigned, value will be 'none'
     */
    val assignmentState: AssignmentPendingState?,

    /**
     * Trustlevel:
     * + 1: No trust
     * + 2: Vehicle access granted
     * + 3: Vehicle access granted + Successful personal identification
     */
    val trustLevel: Int,

    /**
     * Baumuster, e.g. ''
     */
    val baumuster: String,

    /**
     * Tire pressure sensor type, e.g. 'flat runner' or 'tire pressure monitor'
     */
    val tirePressureMonitoringState: TirePressureMonitoringState?,

    /**
     * List of vehicle dealers
     */
    val dealers: List<VehicleDealer>,

    /**
     * Vehicle's car line, e.g. '203'
     */
    val carline: String?,

    /**
     * Telematic Control Unit Data Collector Version
     */
    val dataCollectorVersion: DataCollectorVersion?,

    /**
     * Number of doors, e.g. '2 doors' or '3 doors'
     */
    val doorsCount: DoorsCount?,

    /**
     * Fuel type, e.g. 'GAS' for LPG/H2-vehicles or 'Plugin' for hybrid cars with rechargeable battery
     */
    val fuelType: FuelType?,

    /**
     * Steering wheel position (left/right)
     */
    val handDrive: HandDrive?,

    /**
     * Indicates if vehicle supports Auxiliary heating
     */
    val hasAuxHeat: Boolean,

    /**
     * Indicates if vehicle has facelift (Modellpflege)
     */
    val hasFaceLift: Boolean,

    /**
     * Sunroof type, e.g. 'convertible', 'sunroof' or 'no sunroof'
     */
    val roofType: RoofType?,

    /**
     * HeadUnit architecture type
     */
    val starArchitecture: StarArchitecture?,

    /**
     * HeadUnit architecture version
     */
    val tcuHardwareVersion: TcuHardwareVersion?,

    /**
     * Number of windows, that can be lifted, e.g. 'zero' or 'two windows'
     */
    val windowsLiftCount: WindowsLiftCount?,

    /**
     * Indicates if vehicle supports connectivity, one out of 'No connectivity',
     * 'ODB-adapter connectivity' or 'Built in connectivity'
     */
    val vehicleConnectivity: VehicleConnectivity?,

    /**
     * Telematic Control Unit's software version
     */
    val tcuSoftwareVersion: TcuSoftwareVersion?,

    /**
     * Indicates vehicle segment
     */
    val vehicleSegment: VehicleSegment?,

    /**
     * Indicates if the current user is owner of the vehicle
     */
    val isOwner: Boolean,

    /**
     * Contains the URL to an indicator image for the master user of the vehicle.
     * Only available if the requesting user is a sub user.
     */
    val indicatorImageUrl: String?,

    /**
     * True if the vehicle is only temporarily accessible.
     */
    val temporarilyAccessible: Boolean,

    /**
     * The date until when this vehicle is accessible. This value is only set if [temporarilyAccessible]
     * is true.
     */
    val accessibleUntil: Date?,

    /**
     * Indicates the support state for automatic profile sync.
     */
    val profileSyncSupport: ProfileSyncSupport?,

    /**
     * Indicates whether the vehicle does support normalized profile control
     */
    val normalizedProfileControlSupport: NormalizedProfileControlSupport?,

    /**
     * Paint information of the car like code="144", description="digitalweiss metallic"
     */
    val paint: VehicleAmenity?,

    /**
     * Information about the upholstery, like code="651", description="Ledernachbildung ARTICO"
     */
    val upholstery: VehicleAmenity?,

    /**
     * Line of the vehicle, like code="956", description="Sport-Paket AMG Plus"
     */
    val line: VehicleAmenity?
) : Parcelable {

    /**
     * Returns either the VIN or the FIN of this vehicle.
     * It returns the VIN, if it is not null, returns the FIN otherwise. One of both fields
     * is always non-null.
     */
    val finOrVin: String get() = vin ?: fin!!

    // NOTE: If you change this method, you also have to change the cache-update logic
    // in the CachedVehicleService.
    override fun equals(other: Any?): Boolean {
        return (other is VehicleInfo) && other.finOrVin == finOrVin
    }

    override fun hashCode() = finOrVin.hashCode()

    companion object {

        fun createPendingVehicle(
            vin: String?,
            fin: String?,
            assignmentState: AssignmentPendingState?,
            tirePressureMonitoringState: TirePressureMonitoringState? = TirePressureMonitoringState.NO_TIRE_PRESSURE,
            dealers: List<VehicleDealer> = emptyList()
        ) =
            VehicleInfo(
                vin = vin,
                fin = fin,
                licensePlate = "",
                model = "",
                assignmentState = assignmentState,
                trustLevel = 0,
                baumuster = "",
                tirePressureMonitoringState = tirePressureMonitoringState,
                dealers = dealers,
                carline = "",
                dataCollectorVersion = null,
                doorsCount = null,
                fuelType = null,
                handDrive = null,
                hasAuxHeat = false,
                hasFaceLift = false,
                roofType = null,
                starArchitecture = null,
                tcuHardwareVersion = null,
                windowsLiftCount = null,
                vehicleConnectivity = null,
                tcuSoftwareVersion = null,
                vehicleSegment = null,
                isOwner = false,
                indicatorImageUrl = null,
                temporarilyAccessible = false,
                accessibleUntil = null,
                profileSyncSupport = null,
                normalizedProfileControlSupport = null,
                paint = null,
                upholstery = null,
                line = null
            )
    }
}
