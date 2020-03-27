package com.daimler.mbcarkit.business.model.command

import com.daimler.mbcarkit.business.model.vehicle.APPLICATION_IDENTIFIER_FOR_NEW_ENTRY
import com.daimler.mbcarkit.business.model.vehicle.ChargingProgram
import com.daimler.mbcarkit.implementation.exceptions.MissingPinException
import com.daimler.mbnetworkkit.socket.message.DataSocketMessage
import com.daimler.mbnetworkkit.socket.message.SendableMessage
import com.daimler.mbprotokit.generated.Client
import com.daimler.mbprotokit.generated.VehicleCommands
import com.google.protobuf.BoolValue
import com.google.protobuf.Int32Value

class CarVehicleApiCommand<T> internal constructor(private val commandRequest: VehicleCommand<T>) : SendableMessage {

    internal var pin: String? = null

    internal var pId: String? = null

    private var state: CommandVehicleApiStatus? = null

    internal fun update(commandStatus: CommandVehicleApiStatus) {
        state = commandStatus
    }

    internal fun clearPinProvider() {
        if (commandRequest.pinProvider != null) {
            commandRequest.pinProvider = null
        }
    }

    fun requestId(): String = commandRequest.id

    internal fun pinProvider() = commandRequest.pinProvider

    fun requiresPin(): Boolean {
        return commandRequest.requiresPin
    }

    fun vin(): String = commandRequest.vin

    fun commandRequest(): VehicleCommand<T> = commandRequest

    fun state(): VehicleCommandStatus = state?.commandState ?: VehicleCommandStatus.UNKNOWN

    fun errors(): List<CommandVehicleApiError> = state?.errors ?: emptyList()

    fun processId() = pId

    /**
     * Returns the timestamp of last update in MS
     */
    fun updatedTimestamp() = state?.timestamp

    internal fun type() = state?.type ?: 0

    override fun parse(): DataSocketMessage {
        val clientMessageBuilder = Client.ClientMessage.newBuilder()
        val protoCommandRequestBuilder = VehicleCommands.CommandRequest.newBuilder()
            .setVin(commandRequest.vin)
        protoCommandRequestBuilder.requestId = commandRequest.id
        protoCommandRequestBuilder.backend = VehicleCommands.CommandRequest.Backend.VehicleAPI
        when (commandRequest) {
            is VehicleCommand.ActivateVehicleKeys -> protoCommandRequestBuilder.activateVehicleKeys = VehicleCommands.ActivateVehicleKeys.newBuilder().apply {
                pin = this@CarVehicleApiCommand.pinOrFail()
                expirationUnix = (commandRequest.expirationDate?.time ?: 0) / 1000
            }.build()
            is VehicleCommand.DeactivateVehicleKeys -> protoCommandRequestBuilder.deactivateVehicleKeys = VehicleCommands.DeactivateVehicleKeys.newBuilder().apply {
                pin = this@CarVehicleApiCommand.pinOrFail()
                expirationUnix = (commandRequest.expirationDate?.time ?: 0) / 1000
            }.build()
            is VehicleCommand.AutomaticValetParkingActivate -> protoCommandRequestBuilder.automaticValetParkingActivate = VehicleCommands.AutomaticValetParkingActivate.newBuilder().apply {
                driveType = VehicleCommands.DriveType.values().getOrElse(commandRequest.driveType.id) { VehicleCommands.DriveType.UNKNOWN_DRIVE_TYPE }
                bookingId = commandRequest.bookingId
            }.build()
            is VehicleCommand.AuxHeatConfigure -> protoCommandRequestBuilder.auxheatConfigure = VehicleCommands.AuxheatConfigure.newBuilder().apply {
                time1 = commandRequest.time1
                time2 = commandRequest.time2
                time3 = commandRequest.time3
                timeSelection = VehicleCommands.AuxheatConfigure.Selection.forNumber(commandRequest.timeSelection.ordinal)
            }.build()
            is VehicleCommand.AuxHeatStart -> protoCommandRequestBuilder.auxheatStart = VehicleCommands.AuxheatStart.newBuilder().build()
            is VehicleCommand.AuxHeatStop -> protoCommandRequestBuilder.auxheatStop = VehicleCommands.AuxheatStop.newBuilder().build()
            is VehicleCommand.BatteryMaxStateOfChargeConfigure -> protoCommandRequestBuilder.batteryMaxSoc = VehicleCommands.BatteryMaxSocConfigure.newBuilder().apply {
                maxSoc = commandRequest.maxStateOfCharge
            }.build()
            is VehicleCommand.ChargeOptimizationConfigure -> protoCommandRequestBuilder.chargeOptConfigure = VehicleCommands.ChargeOptConfigure.newBuilder().apply {
                addAllWeekdayTariff(
                    commandRequest.weekdays.map {
                        VehicleCommands.ChargeOptConfigure.Tariff.newBuilder().apply {
                            rateValue = it.rate.ordinal
                            time = it.time
                        }.build()
                    }
                )
                addAllWeekendTariff(
                    commandRequest.weekends.map {
                        VehicleCommands.ChargeOptConfigure.Tariff.newBuilder().apply {
                            rateValue = it.rate.ordinal
                            time = it.time
                        }.build()
                    }
                )
            }.build()
            is VehicleCommand.ChargeCouplerUnlock -> protoCommandRequestBuilder.chargeCouplerUnlock = VehicleCommands.ChargeCouplerUnlock.newBuilder().build()
            is VehicleCommand.ChargeFlapUnlock -> protoCommandRequestBuilder.chargeFlapUnlock = VehicleCommands.ChargeFlapUnlock.newBuilder().build()
            is VehicleCommand.ChargeOptimizationStart -> protoCommandRequestBuilder.chargeOptStart = VehicleCommands.ChargeOptStart.newBuilder().build()
            is VehicleCommand.ChargeOptimizationStop -> protoCommandRequestBuilder.chargeOptStop = VehicleCommands.ChargeOptStop.newBuilder().build()
            is VehicleCommand.DoorsLock -> protoCommandRequestBuilder.doorsLock = VehicleCommands.DoorsLock.newBuilder().build()
            is VehicleCommand.DoorsUnlock -> protoCommandRequestBuilder.doorsUnlock = VehicleCommands.DoorsUnlock.newBuilder().apply {
                pin = this@CarVehicleApiCommand.pinOrFail()
            }.build()
            is VehicleCommand.EngineStart -> protoCommandRequestBuilder.engineStart = VehicleCommands.EngineStart.newBuilder().apply {
                pin = this@CarVehicleApiCommand.pinOrFail()
            }.build()
            is VehicleCommand.EngineStop -> protoCommandRequestBuilder.engineStop = VehicleCommands.EngineStop.newBuilder().build()
            is VehicleCommand.SpeedAlertStart -> protoCommandRequestBuilder.speedalertStart = VehicleCommands.SpeedalertStart.newBuilder().apply {
                alertEndTime = commandRequest.alertEndTime
                threshold = commandRequest.threshold
            }.build()
            is VehicleCommand.SignalPosition -> protoCommandRequestBuilder.sigposStart = VehicleCommands.SigPosStart.newBuilder().apply {
                hornRepeat = commandRequest.hornRepeat
                hornType = VehicleCommands.SigPosStart.HornType.forNumber(commandRequest.hornType.ordinal)
                lightType = VehicleCommands.SigPosStart.LightType.forNumber(commandRequest.lightType.ordinal)
                sigposDuration = commandRequest.durationInSeconds
                sigposType = VehicleCommands.SigPosStart.SigposType.forNumber(commandRequest.sigPosType.ordinal)
            }.build()
            is VehicleCommand.SpeedAlertStop -> protoCommandRequestBuilder.speedalertStop = VehicleCommands.SpeedalertStop.newBuilder().build()
            is VehicleCommand.SunroofOpen -> protoCommandRequestBuilder.sunroofOpen = VehicleCommands.SunroofOpen.newBuilder().apply {
                pin = this@CarVehicleApiCommand.pinOrFail()
            }.build()
            is VehicleCommand.SunroofClose -> protoCommandRequestBuilder.sunroofClose = VehicleCommands.SunroofClose.newBuilder().build()
            is VehicleCommand.SunroofLift -> protoCommandRequestBuilder.sunroofLift = VehicleCommands.SunroofLift.newBuilder().apply {
                pin = this@CarVehicleApiCommand.pinOrFail()
            }.build()
            is VehicleCommand.SunroofMove -> protoCommandRequestBuilder.sunroofMove = VehicleCommands.SunroofMove.newBuilder().apply {
                commandRequest.blindFrontPosition?.let {
                    sunroofBlindFront = Int32Value.newBuilder().setValue(it).build()
                }
                commandRequest.blindRearPosition?.let {
                    sunroofBlindRear = Int32Value.newBuilder().setValue(it).build()
                }
                commandRequest.sunroofPosition?.let {
                    sunroof = Int32Value.newBuilder().setValue(it).build()
                }
                pin = this@CarVehicleApiCommand.pinOrFail()
            }.build()
            is VehicleCommand.TeenageDrivingModeActivate -> protoCommandRequestBuilder.teenageDrivingModeActivate = VehicleCommands.TeenageDrivingModeActivate.newBuilder().build()
            is VehicleCommand.TeenageDrivingModeDeactivate -> protoCommandRequestBuilder.teenageDrivingModeDeactivate = VehicleCommands.TeenageDrivingModeDeactivate.newBuilder().build()
            is VehicleCommand.TemperatureConfigure -> protoCommandRequestBuilder.temperatureConfigure = VehicleCommands.TemperatureConfigure.newBuilder().apply {
                addAllTemperaturePoints(
                    commandRequest.temperaturePoints.map {
                        VehicleCommands.TemperatureConfigure.TemperaturePoint.newBuilder().apply {
                            temperatureInCelsius = it.temperatureInCelsius
                            zoneValue = it.zone.ordinal
                        }.build()
                    }
                )
            }.build()
            is VehicleCommand.TheftAlarmConfirmDamageDetection -> protoCommandRequestBuilder.theftalarmConfirmDamagedetection = VehicleCommands.TheftalarmConfirmDamagedetection.newBuilder().build()
            is VehicleCommand.TheftAlarmDeselectDamageDetection -> protoCommandRequestBuilder.theftalarmDeselectDamagedetection = VehicleCommands.TheftalarmDeselectDamagedetection.newBuilder().build()
            is VehicleCommand.TheftAlarmDeselectInterior -> protoCommandRequestBuilder.theftalarmDeselectInterior = VehicleCommands.TheftalarmDeselectInterior.newBuilder().build()
            is VehicleCommand.TheftAlarmDeselectTow -> protoCommandRequestBuilder.theftalarmDeselectTow = VehicleCommands.TheftalarmDeselectTow.newBuilder().build()
            is VehicleCommand.TheftAlarmSelectDamageDetection -> protoCommandRequestBuilder.theftalarmSelectDamagedetection = VehicleCommands.TheftalarmSelectDamagedetection.newBuilder().build()
            is VehicleCommand.TheftAlarmSelectInterior -> protoCommandRequestBuilder.theftalarmSelectInterior = VehicleCommands.TheftalarmSelectInterior.newBuilder().build()
            is VehicleCommand.TheftAlarmSelectTow -> protoCommandRequestBuilder.theftalarmSelectTow = VehicleCommands.TheftalarmSelectTow.newBuilder().build()
            is VehicleCommand.TheftAlarmStart -> protoCommandRequestBuilder.theftalarmStart = VehicleCommands.TheftalarmStart.newBuilder().apply {
                alarmDurationInSeconds = commandRequest.durationInSeconds
            }.build()
            is VehicleCommand.TheftAlarmStop -> protoCommandRequestBuilder.theftalarmStop = VehicleCommands.TheftalarmStop.newBuilder().build()
            is VehicleCommand.ValetDrivingModeActivate -> protoCommandRequestBuilder.valetDrivingModeActivate = VehicleCommands.ValetDrivingModeActivate.newBuilder().build()
            is VehicleCommand.ValetDrivingModeDeactivate -> protoCommandRequestBuilder.valetDrivingModeDeactivate = VehicleCommands.ValetDrivingModeDeactivate.newBuilder().build()
            is VehicleCommand.WeekProfileConfigure -> protoCommandRequestBuilder.weekProfileConfigure = VehicleCommands.WeekProfileConfigure.newBuilder().apply {
                addAllWeeklySetHu(
                    commandRequest.dayTimes.map {
                        VehicleCommands.WeekProfileConfigure.WeeklySetHU.newBuilder().apply {
                            dayValue = it.day.ordinal
                            time = it.time
                        }.build()
                    }
                )
            }.build()
            is VehicleCommand.WindowsOpen -> protoCommandRequestBuilder.windowsOpen = VehicleCommands.WindowsOpen.newBuilder().apply {
                pin = this@CarVehicleApiCommand.pinOrFail()
            }.build()
            is VehicleCommand.WindowsClose -> protoCommandRequestBuilder.windowsClose = VehicleCommands.WindowsClose.newBuilder().build()
            is VehicleCommand.WindowsMove -> protoCommandRequestBuilder.windowsMove = VehicleCommands.WindowsMove.newBuilder().apply {
                commandRequest.frontLeftPosition?.let {
                    frontLeft = Int32Value.newBuilder().setValue(it).build()
                }
                commandRequest.frontRightPosition?.let {
                    frontRight = Int32Value.newBuilder().setValue(it).build()
                }
                commandRequest.rearBlindPosition?.let {
                    rearBlind = Int32Value.newBuilder().setValue(it).build()
                }
                commandRequest.rearLeftBlindPosition?.let {
                    rearLeftBlind = Int32Value.newBuilder().setValue(it).build()
                }
                commandRequest.rearRightBlindPosition?.let {
                    rearRightBlind = Int32Value.newBuilder().setValue(it).build()
                }
                commandRequest.rearLeftPosition?.let {
                    rearLeft = Int32Value.newBuilder().setValue(it).build()
                }
                commandRequest.rearRightPosition?.let {
                    rearRight = Int32Value.newBuilder().setValue(it).build()
                }
                pin = this@CarVehicleApiCommand.pinOrFail()
            }.build()
            is VehicleCommand.WindowsVentilate -> protoCommandRequestBuilder.windowsVentilate = VehicleCommands.WindowsVentilate.newBuilder().apply {
                pin = this@CarVehicleApiCommand.pinOrFail()
            }.build()
            is VehicleCommand.ZevPreconditioningConfigure -> protoCommandRequestBuilder.zevPreconditionConfigure = VehicleCommands.ZEVPreconditioningConfigure.newBuilder().apply {
                departureTime = commandRequest.departureTime
                departureTimeModeValue = commandRequest.departureTimeMode.ordinal
            }.build()
            is VehicleCommand.ZevPreconditioningConfigureSeats -> protoCommandRequestBuilder.zevPreconditionConfigureSeats = VehicleCommands.ZEVPreconditioningConfigureSeats.newBuilder().apply {
                frontLeft = commandRequest.frontLeft
                frontRight = commandRequest.frontRight
                rearLeft = commandRequest.rearLeft
                rearRight = commandRequest.rearRight
            }.build()
            is VehicleCommand.ZevPreconditioningStart -> protoCommandRequestBuilder.zevPreconditioningStart = VehicleCommands.ZEVPreconditioningStart.newBuilder().apply {
                departureTime = commandRequest.departureTime
                typeValue = commandRequest.type.ordinal
            }.build()
            is VehicleCommand.ZevPreconditioningStop -> protoCommandRequestBuilder.zevPreconditioningStop = VehicleCommands.ZEVPreconditioningStop.newBuilder().apply {
                typeValue = commandRequest.type.ordinal
            }.build()
            is VehicleCommand.ChargeControlConfigure -> protoCommandRequestBuilder.chargeControlConfigure = VehicleCommands.ChargeControlConfigure.newBuilder().apply {
                commandRequest.enableBidirectionalCharging?.let {
                    biChargingEnabled = BoolValue.newBuilder().setValue(it).build()
                }
                commandRequest.minStateOfCharge?.let {
                    minSoc = Int32Value.newBuilder().setValue(it).build()
                }
            }.build()
            is VehicleCommand.ChargeProgramConfigure -> protoCommandRequestBuilder.chargeProgramConfigure = VehicleCommands.ChargeProgramConfigure.newBuilder().apply {
                if (commandRequest.chargeProgram == ChargingProgram.INSTANT) {
                    throw IllegalArgumentException("ChargingProgram.INSTANT is not allowed")
                }
                this.chargeProgramValue = commandRequest.chargeProgram.ordinal
                commandRequest.enableAutoUnlock?.let {
                    autoUnlock = BoolValue.newBuilder().setValue(it).build()
                }
                commandRequest.enableLocationBasedCharging?.let {
                    locationBasedCharging = BoolValue.newBuilder().setValue(it).build()
                }
                commandRequest.maxStateOfCharge?.let {
                    maxSoc = Int32Value.newBuilder().setValue(it).build()
                }
                commandRequest.enableEcoCharging?.let {
                    ecoCharging = BoolValue.newBuilder().setValue(it).build()
                }
            }.build()
            is VehicleCommand.WeekProfileConfigureV2 -> protoCommandRequestBuilder.weekProfileConfigureV2 = VehicleCommands.WeekProfileConfigureV2.newBuilder().apply {
                this.addAllTimeProfiles(
                    commandRequest.weeklyProfileModel.allTimeProfiles.map {
                        VehicleCommands.TimeProfile.newBuilder().apply {
                            it.identifier?.let {
                                identifier = Int32Value.newBuilder().setValue(it).build()
                            }
                            if (!it.toBeRemoved) {
                                hour = Int32Value.newBuilder().setValue(it.hour).build()
                                active = BoolValue.newBuilder().setValue(it.active).build()
                                minute = Int32Value.newBuilder().setValue(it.minute).build()
                                addAllDays(it.days.map { VehicleCommands.TimeProfileDay.forNumber(it.ordinal) })
                            }
                            // The value of -1 is later replaced by the apptwin with the correct value. It just serves as a marker that the entry has changed.
                            applicationIdentifier = if (commandRequest.weeklyProfileModel.backupProfiles[it.identifier] != it) APPLICATION_IDENTIFIER_FOR_NEW_ENTRY else it.applicationIdentifier
                        }.build()
                    }
                ).build()
            }.build()
        }
        clientMessageBuilder.setCommandRequest(protoCommandRequestBuilder)
        return DataSocketMessage.ByteSocketMessage(System.currentTimeMillis(), clientMessageBuilder.build().toByteArray())
    }

    private fun pinOrFail(): String {
        return pin
            ?: throw MissingPinException()
    }

    fun convertToSpecificErrors(update: CommandVehicleApiStatus): List<T> {
        return commandRequest.convertErrors(update)
    }

    fun createGenericError(error: GenericCommandError): T {
        return commandRequest.createGenericError(error)
    }
}
