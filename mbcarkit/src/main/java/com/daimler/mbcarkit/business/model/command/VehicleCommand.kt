// THIS FILE IS GENERATED! DO NOT EDIT!
// The generator can be found at https://git.daimler.com/RisingStars/commons-go-lib/tree/master/gen
package com.daimler.mbcarkit.business.model.command

import com.daimler.mbcarkit.business.PinProvider
import com.daimler.mbcarkit.business.model.vehicle.AuxheatTimeSelectionState
import com.daimler.mbcarkit.business.model.vehicle.ChargingProgram
import com.daimler.mbcarkit.business.model.vehicle.DayTime
import com.daimler.mbcarkit.business.model.vehicle.DepartureTimeMode
import com.daimler.mbcarkit.business.model.vehicle.HornType
import com.daimler.mbcarkit.business.model.vehicle.LightType
import com.daimler.mbcarkit.business.model.vehicle.PreconditioningType
import com.daimler.mbcarkit.business.model.vehicle.SigposType
import com.daimler.mbcarkit.business.model.vehicle.TemperaturePoint
import com.daimler.mbcarkit.business.model.vehicle.WeeklyProfile
import com.daimler.mbcarkit.business.model.vehicle.ZevTariff
import java.util.Date
import java.util.UUID

sealed class VehicleCommand<T>(val vin: String, var pinProvider: PinProvider? = null, val requiresPin: Boolean = false) {

    fun convertErrors(update: CommandVehicleApiStatus): List<T> {
        return update.errors.map { convertToSpecificError(it) }
    }

    abstract fun convertToSpecificError(error: CommandVehicleApiError): T

    abstract fun createGenericError(error: GenericCommandError): T

    val id = UUID.randomUUID().toString()

    /**
     * Command for configuring the auxiliary heating. It is possible to define three daytimes and select one active time.
     *
     * @property time1 Daytime in minutes after midnight. E.g. valid value for 8 am would be 480. Value range is 0 to 1439.
     * @property time2 Daytime in minutes after midnight. E.g. valid value for 8 am would be 480. Value range is 0 to 1439.
     * @property time3 Daytime in minutes after midnight. E.g. valid value for 8 am would be 480. Value range is 0 to 1439.
     * @property timeSelection The activated auxiliary heating preset time
     */
    class AuxHeatConfigure(vin: String, val time1: Int, val time2: Int, val time3: Int, val timeSelection: AuxheatTimeSelectionState) : VehicleCommand<AuxHeatConfigureError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): AuxHeatConfigureError {
            return AuxHeatConfigureError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = AuxHeatConfigureError.GenericError(error)
    }

    /**
     * Command for starting the auxiliary heating.
     */
    class AuxHeatStart(vin: String) : VehicleCommand<AuxHeatStartError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): AuxHeatStartError {
            return AuxHeatStartError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = AuxHeatStartError.GenericError(error)
    }

    /**
     * Command for stopping the auxiliary heating.
     */
    class AuxHeatStop(vin: String) : VehicleCommand<AuxHeatStopError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): AuxHeatStopError {
            return AuxHeatStopError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = AuxHeatStopError.GenericError(error)
    }

    /**
     * EXPERIMENTAL: Command for activating the automatic valet parking
     *
     * @property driveType Indicates whether this should be a pick up or drop off valet parking action.
     * @property bookingId The bookingId is coming from Parking (Offstreet)
     */
    class AutomaticValetParkingActivate(vin: String, val driveType: AutomaticValetParkingDriveType, val bookingId: String) : VehicleCommand<AutomaticValetParkingActivateError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): AutomaticValetParkingActivateError {
            return AutomaticValetParkingActivateError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = AutomaticValetParkingActivateError.GenericError(error)
    }

    /**
     * Command for setting the charging limit to potentially protect the battery by lowering the maximum state of charge
     *
     * @property maxStateOfCharge To protect the battery a maximum state of charge can be configured. Valid values 50, 60, 70, 80, 90 or 100
     */
    class BatteryMaxStateOfChargeConfigure(vin: String, val maxStateOfCharge: Int) : VehicleCommand<BatteryMaxStateOfChargeConfigureError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): BatteryMaxStateOfChargeConfigureError {
            return BatteryMaxStateOfChargeConfigureError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = BatteryMaxStateOfChargeConfigureError.GenericError(error)
    }

    /**
     * EXPERIMENTAL: Command for configuring the bidrectional charging. API is subject to change
     *
     * @property minStateOfCharge The minimum state of charge until the vehicle can charge
     * @property enableBidirectionalCharging Activate or deactivate bidrectional charging
     */
    class ChargeControlConfigure(vin: String, val minStateOfCharge: Int?, val enableBidirectionalCharging: Boolean?) : VehicleCommand<ChargeControlConfigureError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): ChargeControlConfigureError {
            return ChargeControlConfigureError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = ChargeControlConfigureError.GenericError(error)
    }

    /**
     * EXPERIMENTAL: Command for unlocking the charge coupler of an electric vehicle.
     */
    class ChargeCouplerUnlock(vin: String) : VehicleCommand<ChargeCouplerUnlockError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): ChargeCouplerUnlockError {
            return ChargeCouplerUnlockError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = ChargeCouplerUnlockError.GenericError(error)
    }

    /**
     * EXPERIMENTAL: Command for unlocking the charge flap of an electric vehicle.
     */
    class ChargeFlapUnlock(vin: String) : VehicleCommand<ChargeFlapUnlockError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): ChargeFlapUnlockError {
            return ChargeFlapUnlockError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = ChargeFlapUnlockError.GenericError(error)
    }

    /**
     * Command for configuring the charge optimization settings.
     * The vehicle will preferably charge in cheap tariff times. Only supported for vehicles produced until beginning 2018.
     * NOT YET SUPPORTED ON PRODUCTION!
     *
     * @property weekdays List of tariff tuples indicating cheap/expensive time periods for weekdays
     * @property weekends List of tariff tuples indicating cheap/expensive time periods for weekends
     */
    class ChargeOptimizationConfigure(vin: String, val weekdays: List<ZevTariff>, val weekends: List<ZevTariff>) : VehicleCommand<ChargeOptimizationConfigureError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): ChargeOptimizationConfigureError {
            return ChargeOptimizationConfigureError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = ChargeOptimizationConfigureError.GenericError(error)
    }

    /**
     * Command for configuring the charge optimization settings.
     * The vehicle will preferably charge in cheap tariff times. Only supported for vehicles produced until beginning 2018.
     * NOT YET SUPPORTED ON PRODUCTION!
     */
    class ChargeOptimizationStart(vin: String) : VehicleCommand<ChargeOptimizationStartError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): ChargeOptimizationStartError {
            return ChargeOptimizationStartError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = ChargeOptimizationStartError.GenericError(error)
    }

    /**
     * Command for configuring the charge optimization settings.
     * The vehicle will preferably charge in cheap tariff times. Only supported for vehicles produced until beginning 2018.
     * NOT YET SUPPORTED ON PRODUCTION!
     */
    class ChargeOptimizationStop(vin: String) : VehicleCommand<ChargeOptimizationStopError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): ChargeOptimizationStopError {
            return ChargeOptimizationStopError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = ChargeOptimizationStopError.GenericError(error)
    }

    /**
     * Command for configuring the charge program of the battery
     *
     * @property chargeProgram The charge program according to which the HV battery of the vehicle should be charged. Valid values are Default, Home and Work
     * @property maxStateOfCharge To protect the battery a maximum state of charge can be configured. Valid values 50, 60, 70, 80, 90 or 100
     * @property enableLocationBasedCharging Denote whether to automatically switch between home and work program, based on the location of the car. Parameter will be ignored, if  chargeProgram is set to Default.
     * @property enableAutoUnlock Denotes whether the charge cable should be unlocked automatically if the HV battery is fully charged resp. charged til Max. SoC value. Parameter will be ignored, if  chargeProgram is set to Default.
     * @property enableEcoCharging Denotes whether eco charging should be enabled or disabled
     */
    class ChargeProgramConfigure(vin: String, val chargeProgram: ChargingProgram, val maxStateOfCharge: Int?, val enableLocationBasedCharging: Boolean?, val enableAutoUnlock: Boolean?, val enableEcoCharging: Boolean?) : VehicleCommand<ChargeProgramConfigureError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): ChargeProgramConfigureError {
            return ChargeProgramConfigureError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = ChargeProgramConfigureError.GenericError(error)
    }

    /**
     * Command for locking all doors of the vehicle.
     *
     * Preconditions:
     * * The doors of the vehicle must be closed.
     */
    class DoorsLock(vin: String) : VehicleCommand<DoorsLockError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): DoorsLockError {
            return DoorsLockError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = DoorsLockError.GenericError(error)
    }

    /**
     * Command for unlocking all doors of the vehicle. The user is required to enter his/her PIN for this command
     */
    class DoorsUnlock(vin: String) : VehicleCommand<DoorsUnlockError>(vin, requiresPin = true) {
        override fun convertToSpecificError(error: CommandVehicleApiError): DoorsUnlockError {
            return DoorsUnlockError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = DoorsUnlockError.GenericError(error)
    }

    /**
     * Command for starting the engine of the vehicle.
     * Beware that there are regional restrictions for this command. E.g. this command won't work in Europe but would work in other countries like UAE.
     *
     * Preconditions:
     * * Vehicle must be parked
     */
    class EngineStart(vin: String) : VehicleCommand<EngineStartError>(vin, requiresPin = true) {
        override fun convertToSpecificError(error: CommandVehicleApiError): EngineStartError {
            return EngineStartError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = EngineStartError.GenericError(error)
    }

    /**
     * Command for stopping the engine of the vehicle. Beware that there are regional restrictions for this command. E.g. this command won't work in Europe but would in UAE.
     */
    class EngineStop(vin: String) : VehicleCommand<EngineStopError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): EngineStopError {
            return EngineStopError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = EngineStopError.GenericError(error)
    }

    /**
     * Command to deactivate all keys to your vehicle in case you have lost the keys.
     *
     * Preconditions:
     * * Ignition has to be deactivated
     * * Parking brake has to be set
     * NOT YET SUPPORTED ON PRODUCTION!
     *
     * @property expirationDate Timestamp until the command is valid. If the command has not reached the vehicle until this timestamp, the command will be discarded. This value is optional.
     */
    class DeactivateVehicleKeys(vin: String, val expirationDate: Date?) : VehicleCommand<DeactivateVehicleKeysError>(vin, requiresPin = true) {
        override fun convertToSpecificError(error: CommandVehicleApiError): DeactivateVehicleKeysError {
            return DeactivateVehicleKeysError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = DeactivateVehicleKeysError.GenericError(error)
    }

    /**
     * Command to activate all keys to your vehicle. This can be used when you have previously lost your keys and therefore disabled them.
     *
     * Preconditions:
     * * Ignition has to be deactivated
     * * Parking brake has to be set
     * NOT YET SUPPORTED ON PRODUCTION!
     *
     * @property expirationDate Timestamp until the command is valid. If the command has not reached the vehicle until this timestamp, the command will be discarded. This value is optional.
     */
    class ActivateVehicleKeys(vin: String, val expirationDate: Date?) : VehicleCommand<ActivateVehicleKeysError>(vin, requiresPin = true) {
        override fun convertToSpecificError(error: CommandVehicleApiError): ActivateVehicleKeysError {
            return ActivateVehicleKeysError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = ActivateVehicleKeysError.GenericError(error)
    }

    /**
     * Command for starting the preconditioning of an zev (=zero emission vehicle)
     *
     * @property departureTime Daytime in minutes after midnight. E.g. valid value for 8 am would be 480. Value range is 0 to 1439.
     * @property type The type of preconditioning to start
     */
    class ZevPreconditioningStart(vin: String, val departureTime: Int, val type: PreconditioningType) : VehicleCommand<ZevPreconditioningStartError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): ZevPreconditioningStartError {
            return ZevPreconditioningStartError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = ZevPreconditioningStartError.GenericError(error)
    }

    /**
     * Command for closing the sunroof of the vehicle.
     *
     * Preconditions:
     * * Vehicle must be parked
     * * doors must be locked and closed
     * * ignition off.
     */
    class SunroofClose(vin: String) : VehicleCommand<SunroofCloseError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): SunroofCloseError {
            return SunroofCloseError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = SunroofCloseError.GenericError(error)
    }

    /**
     * Command for lifting the sunroof of the vehicle.
     *
     * Preconditions:
     * * Vehicle must be parked
     * * doors must be locked and closed
     * * ignition off.
     */
    class SunroofLift(vin: String) : VehicleCommand<SunroofLiftError>(vin, requiresPin = true) {
        override fun convertToSpecificError(error: CommandVehicleApiError): SunroofLiftError {
            return SunroofLiftError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = SunroofLiftError.GenericError(error)
    }

    /**
     * EXPERIMENTAL: Command for moving the sunroof and blinds to specific positions
     *
     * @property blindFrontPosition Position of the rear blind in range [0, 100] while 0 means completely closed and 100 completely open
     * @property blindRearPosition Position of the front blind in range [0, 100] while 0 means completely closed and 100 completely open
     * @property sunroofPosition Position of the sunroof. Valid values in range [0, 200] with step size 1. 0: lift position, 100: closed, 200: open.
     */
    class SunroofMove(vin: String, val blindFrontPosition: Int?, val blindRearPosition: Int?, val sunroofPosition: Int?) : VehicleCommand<SunroofMoveError>(vin, requiresPin = true) {
        override fun convertToSpecificError(error: CommandVehicleApiError): SunroofMoveError {
            return SunroofMoveError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = SunroofMoveError.GenericError(error)
    }

    /**
     * Command for opening the sunroof of the vehicle. The user is required to enter his/her PIN for this command.
     * Preconditions:
     *
     * * Vehicle must be parked
     * * doors must be locked and closed
     * * ignition off.
     */
    class SunroofOpen(vin: String) : VehicleCommand<SunroofOpenError>(vin, requiresPin = true) {
        override fun convertToSpecificError(error: CommandVehicleApiError): SunroofOpenError {
            return SunroofOpenError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = SunroofOpenError.GenericError(error)
    }

    /**
     * Command for signaling the position of the vehicle by either horning or flashing lights or both for a defined duration. Beware horn is not allowed in Europe.
     *
     * @property hornRepeat Amount for how often horning should be repeated
     * @property hornType The horn type to be used for signaling the position
     * @property lightType The light type to be used for signaling the position
     * @property durationInSeconds The duration for how long the position should be signaled in seconds
     * @property sigPosType Type of how to signal the position
     */
    class SignalPosition(vin: String, val hornRepeat: Int, val hornType: HornType, val lightType: LightType, val durationInSeconds: Int, val sigPosType: SigposType) : VehicleCommand<SignalPositionError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): SignalPositionError {
            return SignalPositionError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = SignalPositionError.GenericError(error)
    }

    /**
     * Command for setting and starting the speed alert configuration.
     *
     * @property alertEndTime The unix timestamp when the speed alert configuration should end
     * @property threshold Speed threshold in kilometers per hour. Valid values are 30 to 280 in multiples of 10.
     */
    class SpeedAlertStart(vin: String, val alertEndTime: Long, val threshold: Int) : VehicleCommand<SpeedAlertStartError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): SpeedAlertStartError {
            return SpeedAlertStartError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = SpeedAlertStartError.GenericError(error)
    }

    /**
     * Command for disabling the speed alert.
     */
    class SpeedAlertStop(vin: String) : VehicleCommand<SpeedAlertStopError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): SpeedAlertStopError {
            return SpeedAlertStopError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = SpeedAlertStopError.GenericError(error)
    }

    /**
     * Command for activating Teenage Driving Mode
     */
    class TeenageDrivingModeActivate(vin: String) : VehicleCommand<TeenageDrivingModeActivateError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): TeenageDrivingModeActivateError {
            return TeenageDrivingModeActivateError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = TeenageDrivingModeActivateError.GenericError(error)
    }

    /**
     * Command for deactivating Teenage Driving Mode
     */
    class TeenageDrivingModeDeactivate(vin: String) : VehicleCommand<TeenageDrivingModeDeactivateError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): TeenageDrivingModeDeactivateError {
            return TeenageDrivingModeDeactivateError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = TeenageDrivingModeDeactivateError.GenericError(error)
    }

    /**
     * Command for configuring the temperature zones of the vehicle.
     * NOT YET SUPPORTED ON PRODUCTION!
     *
     * @property temperaturePoints A list of temperature points consisting of the zone and desired temperature. Beware: all available zones must be set.
     */
    class TemperatureConfigure(vin: String, val temperaturePoints: List<TemperaturePoint>) : VehicleCommand<TemperatureConfigureError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): TemperatureConfigureError {
            return TemperatureConfigureError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = TemperatureConfigureError.GenericError(error)
    }

    /**
     * Command for confirming damage detection.
     */
    class TheftAlarmConfirmDamageDetection(vin: String) : VehicleCommand<TheftAlarmConfirmDamageDetectionError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): TheftAlarmConfirmDamageDetectionError {
            return TheftAlarmConfirmDamageDetectionError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = TheftAlarmConfirmDamageDetectionError.GenericError(error)
    }

    /**
     * Command for deselecting damage detection.
     */
    class TheftAlarmDeselectDamageDetection(vin: String) : VehicleCommand<TheftAlarmDeselectDamageDetectionError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): TheftAlarmDeselectDamageDetectionError {
            return TheftAlarmDeselectDamageDetectionError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = TheftAlarmDeselectDamageDetectionError.GenericError(error)
    }

    /**
     * Command for deselecting interior.
     */
    class TheftAlarmDeselectInterior(vin: String) : VehicleCommand<TheftAlarmDeselectInteriorError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): TheftAlarmDeselectInteriorError {
            return TheftAlarmDeselectInteriorError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = TheftAlarmDeselectInteriorError.GenericError(error)
    }

    /**
     * Command for deselecting tow for theft alarm.
     */
    class TheftAlarmDeselectTow(vin: String) : VehicleCommand<TheftAlarmDeselectTowError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): TheftAlarmDeselectTowError {
            return TheftAlarmDeselectTowError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = TheftAlarmDeselectTowError.GenericError(error)
    }

    /**
     * Command for selecting damage detection.
     */
    class TheftAlarmSelectDamageDetection(vin: String) : VehicleCommand<TheftAlarmSelectDamageDetectionError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): TheftAlarmSelectDamageDetectionError {
            return TheftAlarmSelectDamageDetectionError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = TheftAlarmSelectDamageDetectionError.GenericError(error)
    }

    /**
     * Command for selecting interior for theft alarm.
     */
    class TheftAlarmSelectInterior(vin: String) : VehicleCommand<TheftAlarmSelectInteriorError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): TheftAlarmSelectInteriorError {
            return TheftAlarmSelectInteriorError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = TheftAlarmSelectInteriorError.GenericError(error)
    }

    /**
     * Command to select tow for theft alarm.
     */
    class TheftAlarmSelectTow(vin: String) : VehicleCommand<TheftAlarmSelectTowError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): TheftAlarmSelectTowError {
            return TheftAlarmSelectTowError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = TheftAlarmSelectTowError.GenericError(error)
    }

    /**
     * Command for starting the theft alarm.
     *
     * @property durationInSeconds The duration for how long the theftalarm should be activated
     */
    class TheftAlarmStart(vin: String, val durationInSeconds: Int) : VehicleCommand<TheftAlarmStartError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): TheftAlarmStartError {
            return TheftAlarmStartError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = TheftAlarmStartError.GenericError(error)
    }

    /**
     * Command for stopping the theft alarm.
     */
    class TheftAlarmStop(vin: String) : VehicleCommand<TheftAlarmStopError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): TheftAlarmStopError {
            return TheftAlarmStopError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = TheftAlarmStopError.GenericError(error)
    }

    /**
     * Command for activating Valet Driving Mode
     */
    class ValetDrivingModeActivate(vin: String) : VehicleCommand<ValetDrivingModeActivateError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): ValetDrivingModeActivateError {
            return ValetDrivingModeActivateError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = ValetDrivingModeActivateError.GenericError(error)
    }

    /**
     * Command for deactivating Valet Driving Mode
     */
    class ValetDrivingModeDeactivate(vin: String) : VehicleCommand<ValetDrivingModeDeactivateError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): ValetDrivingModeDeactivateError {
            return ValetDrivingModeDeactivateError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = ValetDrivingModeDeactivateError.GenericError(error)
    }

    /**
     * Command for configuring the departure times for a whole week to precondition the car upfront. Sending this command will replace the week profile settings and won't update/patch it.
     *
     * @property dayTimes A list of departure times that consist of the day and time in minutes since midnight (value range 0 to 1439)
     */
    class WeekProfileConfigure(vin: String, val dayTimes: List<DayTime>) : VehicleCommand<WeekProfileConfigureError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): WeekProfileConfigureError {
            return WeekProfileConfigureError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = WeekProfileConfigureError.GenericError(error)
    }

    /**
     * Command for configuring the departure times for a whole week to precondition the car upfront. Sending this command will replace the week profile settings and won't update/patch it.
     *
     * @property weeklyProfileModel A struct with all time profile configurations. This struct must be built with the existing config to ensure that the existing profiles are not overwritten accidentally.
     */
    class WeekProfileConfigureV2(vin: String, val weeklyProfileModel: WeeklyProfile) : VehicleCommand<WeekProfileConfigureV2Error>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): WeekProfileConfigureV2Error {
            return WeekProfileConfigureV2Error.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = WeekProfileConfigureV2Error.GenericError(error)
    }

    /**
     * Command for closing all windows of the vehicle.
     *
     * Preconditions:
     * * ignition off
     * * doors closed & locked
     */
    class WindowsClose(vin: String) : VehicleCommand<WindowsCloseError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): WindowsCloseError {
            return WindowsCloseError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = WindowsCloseError.GenericError(error)
    }

    /**
     * EXPERIMENTAL: Command for moving the specified windows to a particular position.
     *
     * @property frontLeftPosition Position of the front left window in range [0, 100] while 0 means completely closed and 100 completely open
     * @property frontRightPosition Position of the front right window in range [0, 100] while 0 means completely closed and 100 completely open
     * @property rearBlindPosition Position of the rear blind in range [0, 100] while 0 means completely closed and 100 completely open. Often just the values 0 and 100 are supported.
     * @property rearLeftPosition Position of the rear left window in range [0, 100] while 0 means completely closed and 100 completely open
     * @property rearLeftBlindPosition Position of the rear left blind in range [0, 100] while 0 means completely closed and 100 completely open. Often just the values 0 and 100 are supported.
     * @property rearRightPosition Position of the rear right window in range [0, 100] while 0 means completely closed and 100 completely open
     * @property rearRightBlindPosition Position of the rear right blind in range [0, 100] while 0 means completely closed and 100 completely open. Often just the values 0 and 100 are supported.
     */
    class WindowsMove(vin: String, val frontLeftPosition: Int?, val frontRightPosition: Int?, val rearBlindPosition: Int?, val rearLeftPosition: Int?, val rearLeftBlindPosition: Int?, val rearRightPosition: Int?, val rearRightBlindPosition: Int?) : VehicleCommand<WindowsMoveError>(vin, requiresPin = true) {
        override fun convertToSpecificError(error: CommandVehicleApiError): WindowsMoveError {
            return WindowsMoveError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = WindowsMoveError.GenericError(error)
    }

    /**
     * Command for opening all windows of the vehicle.
     * The user is required to enter his/her PIN.
     *
     * Preconditions:
     * * ignition off
     * * doors closed & locked
     */
    class WindowsOpen(vin: String) : VehicleCommand<WindowsOpenError>(vin, requiresPin = true) {
        override fun convertToSpecificError(error: CommandVehicleApiError): WindowsOpenError {
            return WindowsOpenError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = WindowsOpenError.GenericError(error)
    }

    /**
     * EXPERIMENTAL: Command for opening all windows just by just a narrow gap, so that hot air can escape.
     */
    class WindowsVentilate(vin: String) : VehicleCommand<WindowsVentilateError>(vin, requiresPin = true) {
        override fun convertToSpecificError(error: CommandVehicleApiError): WindowsVentilateError {
            return WindowsVentilateError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = WindowsVentilateError.GenericError(error)
    }

    /**
     * Command for disabling automatic preconditioning on departure time, activating a single time or activating the departure time week profile.
     *
     * @property departureTime Daytime in minutes after midnight. E.g. valid value for 8 am would be 480. Value range is 0 to 1439. Won't have an effect if mode is set to `weekly`
     * @property departureTimeMode The departure time setting. Either disabled, once or weekly (uses the configured week profile). If weekly is set the departure time value won't have an effect.
     */
    class ZevPreconditioningConfigure(vin: String, val departureTime: Int, val departureTimeMode: DepartureTimeMode) : VehicleCommand<ZevPreconditioningConfigureError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): ZevPreconditioningConfigureError {
            return ZevPreconditioningConfigureError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = ZevPreconditioningConfigureError.GenericError(error)
    }

    /**
     * Command for configuring the precondition either the driver seat only or all seats must be set to true. All other configurations are not supported.
     *
     * @property frontLeft Indicates whether the front left seat should be preconditioned. If this is the driver seat and set to false, all other seats must be false as well
     * @property frontRight Indicates whether the front right seat should be preconditioned. If this is the driver seat and set to false, all other seats must be false as well.
     * @property rearLeft Indicates whether the rear left seat should be preconditioned. If true, all other seats must be true as well
     * @property rearRight Indicates whether the rear right seat should be preconditioned. If true, all other seats must be true as well
     */
    class ZevPreconditioningConfigureSeats(vin: String, val frontLeft: Boolean, val frontRight: Boolean, val rearLeft: Boolean, val rearRight: Boolean) : VehicleCommand<ZevPreconditioningConfigureSeatsError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): ZevPreconditioningConfigureSeatsError {
            return ZevPreconditioningConfigureSeatsError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = ZevPreconditioningConfigureSeatsError.GenericError(error)
    }

    /**
     * Command for stopping the preconditioning of an zev (=zero emission vehicle)
     *
     * @property type The type of preconditioning to stop
     */
    class ZevPreconditioningStop(vin: String, val type: PreconditioningType) : VehicleCommand<ZevPreconditioningStopError>(vin) {
        override fun convertToSpecificError(error: CommandVehicleApiError): ZevPreconditioningStopError {
            return ZevPreconditioningStopError.fromErrorCode(error.code, error.attributes)
        }

        override fun createGenericError(error: GenericCommandError) = ZevPreconditioningStopError.GenericError(error)
    }
}
