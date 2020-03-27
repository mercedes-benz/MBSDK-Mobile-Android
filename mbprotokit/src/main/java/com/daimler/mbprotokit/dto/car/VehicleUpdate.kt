package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.dto.car.auxheat.Auxheat
import com.daimler.mbprotokit.dto.car.doors.DoorLockState
import com.daimler.mbprotokit.dto.car.doors.DoorOverallStatus
import com.daimler.mbprotokit.dto.car.doors.Doors
import com.daimler.mbprotokit.dto.car.drivingmodes.DrivingModes
import com.daimler.mbprotokit.dto.car.engine.Engine
import com.daimler.mbprotokit.dto.car.headunit.Headunit
import com.daimler.mbprotokit.dto.car.position.Position
import com.daimler.mbprotokit.dto.car.sunroof.Sunroof
import com.daimler.mbprotokit.dto.car.theft.Theft
import com.daimler.mbprotokit.dto.car.tires.Tires
import com.daimler.mbprotokit.dto.car.vehicledata.VehicleData
import com.daimler.mbprotokit.dto.car.windows.Windows
import com.daimler.mbprotokit.dto.car.zev.Zev
import com.daimler.mbprotokit.generated.VehicleEvents
import com.daimler.mbprotokit.mapping.car.ApiVehicleKey
import com.daimler.mbprotokit.mapping.car.proto

data class VehicleUpdate(
    val fullUpdate: Boolean,
    val finOrVin: String,
    val eventTimeStamp: Long,
    val sequenceNumber: Int,
    private val attributes: Map<String, VehicleEvents.VehicleAttributeStatus>
) {
    val auxheat: Auxheat = Auxheat(attributes)
    val speed: Speed = Speed(attributes)
    val decklid: Decklid = Decklid(attributes)
    val distance: Distance = Distance(attributes)
    val door: Doors = Doors(attributes)
    val drivenTime: DrivenTime = DrivenTime(attributes)
    val ecoScore: EcoScore = EcoScore(attributes)
    val consumption: Consumption = Consumption(attributes)
    val headunit: Headunit = Headunit(attributes)
    val position: Position = Position(attributes)
    val engine: Engine = Engine(attributes)
    val sunroof: Sunroof = Sunroof(attributes)
    val tank: Tank = Tank(attributes)
    val theft: Theft = Theft(attributes)
    val tire: Tires = Tires(attributes)
    val vehicleData: VehicleData = VehicleData(attributes)
    val windows: Windows = Windows(attributes)
    val warnings: Warning = Warning(attributes)
    val zev: Zev = Zev(attributes)
    val drivingModes: DrivingModes = DrivingModes(attributes)
    val healthStatus: Pair<Int?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.VEHICLE_HEALTH_STATUS
    )
    val stateOverall: Pair<DoorOverallStatus?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DOOR_STATE_OVERALL,
        DoorOverallStatus.map()
    )

    /**
     * Overall door lock state for all doors combined
     * 0: Vehicle unlocked
     * 1: Vehicle internal locked
     * 2: Vehicle external locked
     * 3: Vehicle selective unlocked
     */
    val vehicleLockState: Pair<DoorLockState?, AttributeInfo> by proto(
        attributes,
        ApiVehicleKey.DOOR_LOCK_STATUS_VEHICLE,
        DoorLockState.map()
    )
}
