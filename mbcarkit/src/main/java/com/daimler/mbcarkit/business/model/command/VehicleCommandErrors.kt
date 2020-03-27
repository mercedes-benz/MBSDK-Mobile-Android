// THIS FILE IS GENERATED! DO NOT EDIT!
// The generator can be found at https://git.daimler.com/RisingStars/commons-go-lib/tree/master/gen
package com.daimler.mbcarkit.business.model.command

import com.google.protobuf.Value

interface GenericVehicleCommandError {
    val genericError: GenericCommandError
}

// Error codes for the configure auxheat command version v1
sealed class AuxHeatConfigureError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : AuxHeatConfigureError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Processing of auxheat command failed
    class AuxheatCommandFailed(rawErrorCode: String) : AuxHeatConfigureError(rawErrorCode, InternalVehicleCommandError.AuxheatCommandFailed)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : AuxHeatConfigureError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Incomplete values
    class IncompleteValues(rawErrorCode: String) : AuxHeatConfigureError(rawErrorCode, InternalVehicleCommandError.IncompleteValues)

    // NULL/INF values
    class NullOrInfiniteValues(rawErrorCode: String) : AuxHeatConfigureError(rawErrorCode, InternalVehicleCommandError.NullOrInfiniteValues)

    // Service not authorized
    class ServiceNotAuthorized(rawErrorCode: String) : AuxHeatConfigureError(rawErrorCode, InternalVehicleCommandError.ServiceNotAuthorized)

    // Syntax error
    class SyntaxError(rawErrorCode: String) : AuxHeatConfigureError(rawErrorCode, InternalVehicleCommandError.SyntaxError)

    // Value out of range
    class ValueOutOfRange(rawErrorCode: String) : AuxHeatConfigureError(rawErrorCode, InternalVehicleCommandError.ValueOutOfRange)

    // Value overflow
    class ValueOverflow(rawErrorCode: String) : AuxHeatConfigureError(rawErrorCode, InternalVehicleCommandError.ValueOverflow)

    // Wrong data type
    class WrongDataType(rawErrorCode: String) : AuxHeatConfigureError(rawErrorCode, InternalVehicleCommandError.WrongDataType)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): AuxHeatConfigureError {
            return when (code) {
                "100" -> ValueOutOfRange(code)
                "105" -> WrongDataType(code)
                "110" -> ValueOverflow(code)
                "115" -> IncompleteValues(code)
                "120" -> SyntaxError(code)
                "125" -> NullOrInfiniteValues(code)
                "4061" -> AuxheatCommandFailed(code)
                "4062" -> ServiceNotAuthorized(code)
                "42" -> FastpathTimeout(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the start auxheat command version v1
sealed class AuxHeatStartError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : AuxHeatStartError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Processing of auxheat command failed
    class AuxheatCommandFailed(rawErrorCode: String) : AuxHeatStartError(rawErrorCode, InternalVehicleCommandError.AuxheatCommandFailed)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : AuxHeatStartError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Service not authorized
    class ServiceNotAuthorized(rawErrorCode: String) : AuxHeatStartError(rawErrorCode, InternalVehicleCommandError.ServiceNotAuthorized)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): AuxHeatStartError {
            return when (code) {
                "4061" -> AuxheatCommandFailed(code)
                "4062" -> ServiceNotAuthorized(code)
                "42" -> FastpathTimeout(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the stop auxheat command version v1
sealed class AuxHeatStopError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : AuxHeatStopError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Processing of auxheat command failed
    class AuxheatCommandFailed(rawErrorCode: String) : AuxHeatStopError(rawErrorCode, InternalVehicleCommandError.AuxheatCommandFailed)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : AuxHeatStopError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Service not authorized
    class ServiceNotAuthorized(rawErrorCode: String) : AuxHeatStopError(rawErrorCode, InternalVehicleCommandError.ServiceNotAuthorized)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): AuxHeatStopError {
            return when (code) {
                "4061" -> AuxheatCommandFailed(code)
                "4062" -> ServiceNotAuthorized(code)
                "42" -> FastpathTimeout(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the activate avp command version v1
sealed class AutomaticValetParkingActivateError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : AutomaticValetParkingActivateError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Failed due to another car steered by system currently
    class AnotherCarSteeredCurrently(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.AnotherCarSteeredCurrently)

    // Failed due to brake fluid lamp on during drive
    class BrakeFluid(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.BrakeFluid)

    // Failed due to not locked car
    class CarNotLocked(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.CarNotLocked)

    // Failed due to charging cable plugged
    class ChargingCablePlugged(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.ChargingCablePlugged)

    // Failed due to door is open
    class DoorOpen(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.DoorOpen)

    // The gear is not in Parking position
    class GearNotInPark(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.GearNotInPark)

    // Failed due to hood is open
    class HoodOpen(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.HoodOpen)

    // Failed due to ignition is on
    class IgnitionOn(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.IgnitionOn)

    // Failed due to key button pressed during drive
    class KeyButtonPressed(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.KeyButtonPressed)

    // Lock request not authorized
    class LockNotAuthorized(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.LockNotAuthorized)

    // Maintance planned
    class Maintenance(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.Maintenance)

    // Failed due to no user acceptence
    class NoUserAcceptance(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.NoUserAcceptance)

    // Failed due to vehicle not on drop off zone
    class NotInDropOffZone(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.NotInDropOffZone)

    // Failed due to someone detected inside vehicle
    class PersonDetected(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.PersonDetected)

    // Failed due to reservation already used
    class ReservationAlreadyUsed(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.ReservationAlreadyUsed)

    // Failed due to reservation in the past
    class ReservationInPast(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.ReservationInPast)

    // Service not authorized
    class ServiceNotAuthorized(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.ServiceNotAuthorized)

    // Service currently not available
    class ServiceUnavailable(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.ServiceUnavailable)

    // Failed due to detection of snow chains
    class SnowChainsDetected(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.SnowChainsDetected)

    // Failed due to sunroof is open
    class SunroofOpen(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.SunroofOpen)

    // Failed due to tank level too low
    class TankLevelLow(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.TankLevelLow)

    // Failed due to too low tank level during drive
    class TankLevelLowDrive(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.TankLevelLowDrive)

    // Technical error, retry possible
    class TechnicalError(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.TechnicalError)

    // Severe technical error, no retries
    class TechnicalErrorNoRetry(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.TechnicalErrorNoRetry)

    // Failed due to too low tire pressure
    class TirePressureLow(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.TirePressureLow)

    // Failed due to convertible top is open
    class TopOpen(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.TopOpen)

    // Failed due to detection of trailor
    class TrailerDetected(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.TrailerDetected)

    // Failed due to detection of trailer hitch
    class TrailerHitchDetected(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.TrailerHitchDetected)

    // Failed due to trunk lid is open
    class TrunkOpen(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.TrunkOpen)

    // Failed due to vehicle movement
    class VehicleMovement(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.VehicleMovement)

    // Failed due to window is open
    class WindowOpen(rawErrorCode: String) : AutomaticValetParkingActivateError(rawErrorCode, InternalVehicleCommandError.WindowOpen)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): AutomaticValetParkingActivateError {
            return when (code) {
                "2001" -> TechnicalError(code)
                "2002" -> TechnicalErrorNoRetry(code)
                "2004" -> ServiceNotAuthorized(code)
                "2005" -> ReservationAlreadyUsed(code)
                "2006" -> ReservationInPast(code)
                "2010" -> SunroofOpen(code)
                "2011" -> TopOpen(code)
                "2012" -> PersonDetected(code)
                "2013" -> IgnitionOn(code)
                "2014" -> GearNotInPark(code)
                "2015" -> TrailerDetected(code)
                "2016" -> DoorOpen(code)
                "2017" -> TrunkOpen(code)
                "2018" -> HoodOpen(code)
                "2019" -> WindowOpen(code)
                "2020" -> SnowChainsDetected(code)
                "2021" -> TrailerHitchDetected(code)
                "2022" -> TirePressureLow(code)
                "2023" -> VehicleMovement(code)
                "2024" -> CarNotLocked(code)
                "2025" -> ChargingCablePlugged(code)
                "2026" -> TankLevelLow(code)
                "3001" -> NotInDropOffZone(code)
                "3002" -> AnotherCarSteeredCurrently(code)
                "3003" -> Maintenance(code)
                "3004" -> ServiceUnavailable(code)
                "3051" -> PersonDetected(code)
                "3052" -> BrakeFluid(code)
                "3053" -> KeyButtonPressed(code)
                "3054" -> LockNotAuthorized(code)
                "3055" -> TankLevelLowDrive(code)
                "3056" -> NoUserAcceptance(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the maxsoc battery command version v1
sealed class BatteryMaxStateOfChargeConfigureError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : BatteryMaxStateOfChargeConfigureError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Charge Configuration failed
    class ChargeConfigurationFailed(rawErrorCode: String) : BatteryMaxStateOfChargeConfigureError(rawErrorCode, InternalVehicleCommandError.ChargeConfigurationFailed)

    // Charge Configuration failed because passed max soc value is below vehicle threshold
    class ChargeConfigurationFailedSocBelowTreshold(rawErrorCode: String) : BatteryMaxStateOfChargeConfigureError(rawErrorCode, InternalVehicleCommandError.ChargeConfigurationFailedSocBelowTreshold)

    // Charge Configuration not authorized
    class ChargeConfigurationNotAuthorized(rawErrorCode: String) : BatteryMaxStateOfChargeConfigureError(rawErrorCode, InternalVehicleCommandError.ChargeConfigurationNotAuthorized)

    // Charge Configuration not possible since INSTANT CHARGING is already activated
    class ChargeConfigurationNotPossibleSinceInstantChargingIsActive(rawErrorCode: String) : BatteryMaxStateOfChargeConfigureError(rawErrorCode, InternalVehicleCommandError.ChargeConfigurationNotPossibleSinceInstantChargingIsActive)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : BatteryMaxStateOfChargeConfigureError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): BatteryMaxStateOfChargeConfigureError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "7401" -> ChargeConfigurationFailed(code)
                "7402" -> ChargeConfigurationFailedSocBelowTreshold(code)
                "7403" -> ChargeConfigurationNotAuthorized(code)
                "7404" -> ChargeConfigurationNotPossibleSinceInstantChargingIsActive(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the configure chargecontrol command version v1
sealed class ChargeControlConfigureError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : ChargeControlConfigureError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Charge Control failed
    class ChargeControlConfigFaild(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.ChargeControlConfigFaild)

    // Charge Control not authorized
    class ChargeControlNotAuthorized(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.ChargeControlNotAuthorized)

    // Charge Control failed due to external charging problem 1
    class ExternalChargingProblem1(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.ExternalChargingProblem1)

    // Charge Control failed due to external charging problem 10
    class ExternalChargingProblem10(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.ExternalChargingProblem10)

    // Charge Control failed due to external charging problem 11
    class ExternalChargingProblem11(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.ExternalChargingProblem11)

    // Charge Control failed due to external charging problem 12
    class ExternalChargingProblem12(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.ExternalChargingProblem12)

    // Charge Control failed due to external charging problem 13
    class ExternalChargingProblem13(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.ExternalChargingProblem13)

    // Charge Control failed due to external charging problem 14
    class ExternalChargingProblem14(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.ExternalChargingProblem14)

    // Charge Control failed due to external charging problem 2
    class ExternalChargingProblem2(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.ExternalChargingProblem2)

    // Charge Control failed due to external charging problem 3
    class ExternalChargingProblem3(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.ExternalChargingProblem3)

    // Charge Control failed due to external charging problem 4
    class ExternalChargingProblem4(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.ExternalChargingProblem4)

    // Charge Control failed due to external charging problem 5
    class ExternalChargingProblem5(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.ExternalChargingProblem5)

    // Charge Control failed due to external charging problem 6
    class ExternalChargingProblem6(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.ExternalChargingProblem6)

    // Charge Control failed due to external charging problem 7
    class ExternalChargingProblem7(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.ExternalChargingProblem7)

    // Charge Control failed due to external charging problem 8
    class ExternalChargingProblem8(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.ExternalChargingProblem8)

    // Charge Control failed due to external charging problem 9
    class ExternalChargingProblem9(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.ExternalChargingProblem9)

    // not possible since either INSTANT CHARGING is already activated or INSTANT CHARGING command is currently in progress
    class InstantChargingActiveOrInProgress(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.InstantChargingActiveOrInProgress)

    // Min. SOC not possible since VVR value of either minSocLowerLimit or minSocUpperLimit is missing
    class MinSocLimitMissing(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.MinSocLimitMissing)

    // Min. SOC setting not possible since minSOC value is not in range of minSocLowerLimit & minSocUpperLimit
    class MinSocNotInRange(rawErrorCode: String) : ChargeControlConfigureError(rawErrorCode, InternalVehicleCommandError.MinSocNotInRange)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): ChargeControlConfigureError {
            return when (code) {
                "8501" -> ChargeControlConfigFaild(code)
                "8502" -> ChargeControlNotAuthorized(code)
                "8504" -> InstantChargingActiveOrInProgress(code)
                "8505" -> MinSocLimitMissing(code)
                "8506" -> MinSocNotInRange(code)
                "8511" -> ExternalChargingProblem1(code)
                "8512" -> ExternalChargingProblem2(code)
                "8513" -> ExternalChargingProblem3(code)
                "8514" -> ExternalChargingProblem4(code)
                "8515" -> ExternalChargingProblem5(code)
                "8516" -> ExternalChargingProblem6(code)
                "8517" -> ExternalChargingProblem7(code)
                "8518" -> ExternalChargingProblem8(code)
                "8519" -> ExternalChargingProblem9(code)
                "8520" -> ExternalChargingProblem10(code)
                "8521" -> ExternalChargingProblem11(code)
                "8522" -> ExternalChargingProblem12(code)
                "8523" -> ExternalChargingProblem13(code)
                "8524" -> ExternalChargingProblem14(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the unlock chargecoupler command version v1
sealed class ChargeCouplerUnlockError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : ChargeCouplerUnlockError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Failed due to charge coupler still locked
    class ChargeCouplerStillLocked(rawErrorCode: String) : ChargeCouplerUnlockError(rawErrorCode, InternalVehicleCommandError.ChargeCouplerStillLocked)

    // Failed due to charging system not awake
    class ChargingSystemNotAwake(rawErrorCode: String) : ChargeCouplerUnlockError(rawErrorCode, InternalVehicleCommandError.ChargingSystemNotAwake)

    // Failed
    class Failed(rawErrorCode: String) : ChargeCouplerUnlockError(rawErrorCode, InternalVehicleCommandError.Failed)

    // Failed due to general error in charge coupler system
    class GeneralErrorChargeCoupler(rawErrorCode: String) : ChargeCouplerUnlockError(rawErrorCode, InternalVehicleCommandError.GeneralErrorChargeCoupler)

    // Failed due to ignition is on
    class IgnitionOn(rawErrorCode: String) : ChargeCouplerUnlockError(rawErrorCode, InternalVehicleCommandError.IgnitionOn)

    // Request is not authorized
    class NotAuthorized(rawErrorCode: String) : ChargeCouplerUnlockError(rawErrorCode, InternalVehicleCommandError.NotAuthorized)

    // Service not authorized
    class ServiceNotAuthorized(rawErrorCode: String) : ChargeCouplerUnlockError(rawErrorCode, InternalVehicleCommandError.ServiceNotAuthorized)

    // Failed due to timeout
    class Timeout(rawErrorCode: String) : ChargeCouplerUnlockError(rawErrorCode, InternalVehicleCommandError.Timeout)

    // Failed due to unknown state of charging system
    class UnknownChargingSystemState(rawErrorCode: String) : ChargeCouplerUnlockError(rawErrorCode, InternalVehicleCommandError.UnknownChargingSystemState)

    // Failed due to unlock error in charge coupler system
    class UnlockErrorChargeCoupler(rawErrorCode: String) : ChargeCouplerUnlockError(rawErrorCode, InternalVehicleCommandError.UnlockErrorChargeCoupler)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): ChargeCouplerUnlockError {
            return when (code) {
                "4100" -> ServiceNotAuthorized(code)
                "4101" -> IgnitionOn(code)
                "4126" -> UnlockErrorChargeCoupler(code)
                "4127" -> GeneralErrorChargeCoupler(code)
                "4410" -> Failed(code)
                "4411" -> IgnitionOn(code)
                "4412" -> UnknownChargingSystemState(code)
                "4413" -> ChargeCouplerStillLocked(code)
                "4414" -> ChargingSystemNotAwake(code)
                "4415" -> UnknownChargingSystemState(code)
                "4416" -> Timeout(code)
                "4417" -> NotAuthorized(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the unlock chargeflap command version v1
sealed class ChargeFlapUnlockError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : ChargeFlapUnlockError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Failed due to charging system not awake
    class ChargingSystemNotAwake(rawErrorCode: String) : ChargeFlapUnlockError(rawErrorCode, InternalVehicleCommandError.ChargingSystemNotAwake)

    // Failed
    class Failed(rawErrorCode: String) : ChargeFlapUnlockError(rawErrorCode, InternalVehicleCommandError.Failed)

    // Failed due to general error in charge flap system
    class GeneralErrorChargeFlap(rawErrorCode: String) : ChargeFlapUnlockError(rawErrorCode, InternalVehicleCommandError.GeneralErrorChargeFlap)

    // Failed due to ignition is on
    class IgnitionOn(rawErrorCode: String) : ChargeFlapUnlockError(rawErrorCode, InternalVehicleCommandError.IgnitionOn)

    // Request is not authorized
    class NotAuthorized(rawErrorCode: String) : ChargeFlapUnlockError(rawErrorCode, InternalVehicleCommandError.NotAuthorized)

    // Failed due to vehicle not in parking gear selection
    class NotInPark(rawErrorCode: String) : ChargeFlapUnlockError(rawErrorCode, InternalVehicleCommandError.NotInPark)

    // Failed due to vehicle not in parking gear selection
    class NotInParkingGear(rawErrorCode: String) : ChargeFlapUnlockError(rawErrorCode, InternalVehicleCommandError.NotInParkingGear)

    // Service not authorized
    class ServiceNotAuthorized(rawErrorCode: String) : ChargeFlapUnlockError(rawErrorCode, InternalVehicleCommandError.ServiceNotAuthorized)

    // Failed due to timeout
    class Timeout(rawErrorCode: String) : ChargeFlapUnlockError(rawErrorCode, InternalVehicleCommandError.Timeout)

    // Failed due to unknown state of charging system
    class UnknownChargingSystemState(rawErrorCode: String) : ChargeFlapUnlockError(rawErrorCode, InternalVehicleCommandError.UnknownChargingSystemState)

    // Failed due to vehicle in ready state
    class VehicleInReadyState(rawErrorCode: String) : ChargeFlapUnlockError(rawErrorCode, InternalVehicleCommandError.VehicleInReadyState)

    // Failed due to vehicle in ready state
    class VehicleReady(rawErrorCode: String) : ChargeFlapUnlockError(rawErrorCode, InternalVehicleCommandError.VehicleReady)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): ChargeFlapUnlockError {
            return when (code) {
                "4100" -> ServiceNotAuthorized(code)
                "4101" -> IgnitionOn(code)
                "4123" -> NotInPark(code)
                "4124" -> VehicleReady(code)
                "4125" -> GeneralErrorChargeFlap(code)
                "4400" -> Failed(code)
                "4401" -> IgnitionOn(code)
                "4402" -> NotInParkingGear(code)
                "4403" -> VehicleInReadyState(code)
                "4404" -> ChargingSystemNotAwake(code)
                "4405" -> UnknownChargingSystemState(code)
                "4406" -> Timeout(code)
                "4407" -> NotAuthorized(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the configure chargeopt command version v1
sealed class ChargeOptimizationConfigureError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : ChargeOptimizationConfigureError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Charge optimization failed
    class ChargeOptimizationFailed(rawErrorCode: String) : ChargeOptimizationConfigureError(rawErrorCode, InternalVehicleCommandError.ChargeOptimizationFailed)

    // Charge optimization not authorized
    class ChargeOptimizationNotAuthorized(rawErrorCode: String) : ChargeOptimizationConfigureError(rawErrorCode, InternalVehicleCommandError.ChargeOptimizationNotAuthorized)

    // Charge optimization not possible since either INSTANT CHARGING is already activated or INSTANT CHARGING ACP command is currently in progress
    class ChargeOptimizationNotPossible(rawErrorCode: String) : ChargeOptimizationConfigureError(rawErrorCode, InternalVehicleCommandError.ChargeOptimizationNotPossible)

    // Charge optimization overwritten
    class ChargeOptimizationOverwritten(rawErrorCode: String) : ChargeOptimizationConfigureError(rawErrorCode, InternalVehicleCommandError.ChargeOptimizationOverwritten)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : ChargeOptimizationConfigureError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): ChargeOptimizationConfigureError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "5015" -> ChargeOptimizationFailed(code)
                "5016" -> ChargeOptimizationOverwritten(code)
                "5017" -> ChargeOptimizationNotAuthorized(code)
                "5018" -> ChargeOptimizationNotPossible(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the start chargeopt command version v1
sealed class ChargeOptimizationStartError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : ChargeOptimizationStartError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : ChargeOptimizationStartError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): ChargeOptimizationStartError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the stop chargeopt command version v1
sealed class ChargeOptimizationStopError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : ChargeOptimizationStopError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : ChargeOptimizationStopError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): ChargeOptimizationStopError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the configure chargeprogram command version v1
sealed class ChargeProgramConfigureError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : ChargeProgramConfigureError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Charge Configuration failed
    class ChargeConfigurationFailed(rawErrorCode: String) : ChargeProgramConfigureError(rawErrorCode, InternalVehicleCommandError.ChargeConfigurationFailed)

    // Charge Configuration failed because passed max soc value is below vehicle threshold
    class ChargeConfigurationFailedSocBelowTreshold(rawErrorCode: String) : ChargeProgramConfigureError(rawErrorCode, InternalVehicleCommandError.ChargeConfigurationFailedSocBelowTreshold)

    // Charge Configuration not authorized
    class ChargeConfigurationNotAuthorized(rawErrorCode: String) : ChargeProgramConfigureError(rawErrorCode, InternalVehicleCommandError.ChargeConfigurationNotAuthorized)

    // Charge Configuration not possible since INSTANT CHARGING is already activated
    class ChargeConfigurationNotPossibleSinceInstantChargingIsActive(rawErrorCode: String) : ChargeProgramConfigureError(rawErrorCode, InternalVehicleCommandError.ChargeConfigurationNotPossibleSinceInstantChargingIsActive)

    // Charge programs not supported by vehicle
    class ChargeProgramsNotSupportedByVehicle(rawErrorCode: String) : ChargeProgramConfigureError(rawErrorCode, InternalVehicleCommandError.ChargeProgramsNotSupportedByVehicle)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : ChargeProgramConfigureError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Max. SOC setting not possible since VVR value of either maxSocLowerLimit or maxSocUpperLimit is missing
    class MaxSocLimitMissing(rawErrorCode: String) : ChargeProgramConfigureError(rawErrorCode, InternalVehicleCommandError.MaxSocLimitMissing)

    // Max. SOC setting not possible since maxSOC value is not in range of maxSocLowerLimit & maxSocUpperLimit
    class MaxSocNotInRange(rawErrorCode: String) : ChargeProgramConfigureError(rawErrorCode, InternalVehicleCommandError.MaxSocNotInRange)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): ChargeProgramConfigureError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "7401" -> ChargeConfigurationFailed(code)
                "7402" -> ChargeConfigurationFailedSocBelowTreshold(code)
                "7403" -> ChargeConfigurationNotAuthorized(code)
                "7404" -> ChargeConfigurationNotPossibleSinceInstantChargingIsActive(code)
                "7405" -> ChargeProgramsNotSupportedByVehicle(code)
                "7406" -> MaxSocLimitMissing(code)
                "7407" -> MaxSocNotInRange(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the lock doors command version v1
sealed class DoorsLockError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : DoorsLockError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Failed due to central locking disabled
    class CentralLockingDisabled(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.CentralLockingDisabled)

    // Failed due to decklid not closed
    class DecklidNotClosed(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.DecklidNotClosed)

    // Failed due to decklid not locked
    class DecklidNotLocked(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.DecklidNotLocked)

    // Failed due to front left door not closed
    class DoorFrontLeftNotClosed(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.DoorFrontLeftNotClosed)

    // Failed due to front left door not locked
    class DoorFrontLeftNotLocked(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.DoorFrontLeftNotLocked)

    // Failed due to front right door not closed
    class DoorFrontRightNotClosed(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.DoorFrontRightNotClosed)

    // Failed due to front right door not locked
    class DoorFrontRightNotLocked(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.DoorFrontRightNotLocked)

    // Failed due to one or more doors not closed
    class DoorNotClosed(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.DoorNotClosed)

    // Failed due to door is open
    class DoorOpen(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.DoorOpen)

    // Failed due to rear left door not closed
    class DoorRearLeftNotClosed(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.DoorRearLeftNotClosed)

    // Failed due to rear left door not locked
    class DoorRearLeftNotLocked(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.DoorRearLeftNotLocked)

    // Failed due to rear right door not closed
    class DoorRearRightNotClosed(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.DoorRearRightNotClosed)

    // Failed due to rear right door not locked
    class DoorRearRightNotLocked(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.DoorRearRightNotLocked)

    // Failed due to one or more doors not locked
    class DoorsNotLocked(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.DoorsNotLocked)

    // Failed due to driver door open
    class DriverDoorOpen(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.DriverDoorOpen)

    // Failed due to driver in vehicle
    class DriverIsInVehicle(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.DriverIsInVehicle)

    // Failed due to vehicle already external locked
    class ExternallyLocked(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.ExternallyLocked)

    // Failed
    class Failed(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.Failed)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Failed due to flip window not closed
    class FlipWindowNotClosed(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.FlipWindowNotClosed)

    // Failed due to flip window not locked
    class FlipWindowNotLocked(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.FlipWindowNotLocked)

    // Failed due to fuel flap not closed
    class FuelFlapNotClosed(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.FuelFlapNotClosed)

    // Failed due to fuel flap not locked
    class FuelFlapNotLocked(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.FuelFlapNotLocked)

    // Failed due to gas alarm active
    class GasAlarmActive(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.GasAlarmActive)

    // Failed due to general error in charge coupler system
    class GeneralErrorChargeCoupler(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.GeneralErrorChargeCoupler)

    // Failed due to general error in charge flap system
    class GeneralErrorChargeFlap(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.GeneralErrorChargeFlap)

    // Failed due to general error in locking system
    class GeneralErrorLocking(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.GeneralErrorLocking)

    // Failed due to HOLD-function active
    class HoldActive(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.HoldActive)

    // Failed due to ignition state active
    class IgnitionActive(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.IgnitionActive)

    // Failed due to ignition is on
    class IgnitionOn(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.IgnitionOn)

    // Lock request not authorized
    class LockNotAuthorized(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.LockNotAuthorized)

    // Failed due to request to central locking system cancelled
    class LockingRequestCancelled(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.LockingRequestCancelled)

    // Energy level in Battery is too low
    class LowBatteryLevel(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel)

    // Failed due to low battery level 1
    class LowBatteryLevel1(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel1)

    // Failed due to low battery level 2
    class LowBatteryLevel2(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel2)

    // Failed due to vehicle not external locked
    class NotExternallyLocked(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.NotExternallyLocked)

    // Failed due to vehicle not in parking gear selection
    class NotInPark(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.NotInPark)

    // Failed due to parallel request to central locking system
    class ParallelRequestToLocking(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.ParallelRequestToLocking)

    // Failed due to parameter not allowed
    class ParameterNotAllowed(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.ParameterNotAllowed)

    // Failed due to RDL inactive
    class RdlInactive(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.RdlInactive)

    // Failed due to RDU decklid inactive
    class RduDecklidInactive(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.RduDecklidInactive)

    // Failed due to RDU fuel flap inactive
    class RduFuelFlapInactive(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.RduFuelFlapInactive)

    // Failed due to RDU global inactive
    class RduGlobalInactive(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.RduGlobalInactive)

    // Failed due to RDU selective inactive
    class RduSelectionInactive(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.RduSelectionInactive)

    // Failed due to rear charge flap not closed
    class RearChargeFlapNotClosed(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.RearChargeFlapNotClosed)

    // Failed due to remote engine start is active
    class RemoteEngineStartIsActive(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.RemoteEngineStartIsActive)

    // Failed due to request not allowed
    class RequestNotAllowed(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.RequestNotAllowed)

    // Failed due to restricted info parameter
    class RestrictedInfoParameter(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.RestrictedInfoParameter)

    // Service not authorized
    class ServiceNotAuthorized(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.ServiceNotAuthorized)

    // Failed due to side charge flap not closed
    class SideChargeFlapNotClosed(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.SideChargeFlapNotClosed)

    // Failed due to too many requests to central locking system
    class TooManyRequestsToLocking(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.TooManyRequestsToLocking)

    // Failed due to transport mode active
    class TransportModeActive(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.TransportModeActive)

    // Failed due to unknown reason
    class UnknownReason(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.UnknownReason)

    // Failed due to unlock error in charge coupler system
    class UnlockErrorChargeCoupler(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.UnlockErrorChargeCoupler)

    // Failed due to valet parking active
    class ValetParkingActive(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.ValetParkingActive)

    // Failed due to vehicle in ready state
    class VehicleReady(rawErrorCode: String) : DoorsLockError(rawErrorCode, InternalVehicleCommandError.VehicleReady)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): DoorsLockError {
            return when (code) {
                "21" -> LowBatteryLevel(code)
                "4001" -> Failed(code)
                "4002" -> DoorOpen(code)
                "4003" -> IgnitionOn(code)
                "4004" -> LockNotAuthorized(code)
                "4100" -> ServiceNotAuthorized(code)
                "4101" -> IgnitionOn(code)
                "4102" -> RemoteEngineStartIsActive(code)
                "4103" -> DriverIsInVehicle(code)
                "4104" -> NotExternallyLocked(code)
                "4105" -> LowBatteryLevel1(code)
                "4106" -> LowBatteryLevel2(code)
                "4107" -> DoorsNotLocked(code)
                "4108" -> DoorFrontLeftNotLocked(code)
                "4109" -> DoorFrontRightNotLocked(code)
                "4110" -> DoorRearLeftNotLocked(code)
                "4111" -> DoorRearRightNotLocked(code)
                "4112" -> DecklidNotLocked(code)
                "4113" -> FlipWindowNotLocked(code)
                "4114" -> FuelFlapNotLocked(code)
                "4115" -> DriverDoorOpen(code)
                "4116" -> IgnitionActive(code)
                "4117" -> ParallelRequestToLocking(code)
                "4118" -> TooManyRequestsToLocking(code)
                "4119" -> HoldActive(code)
                "4120" -> ExternallyLocked(code)
                "4121" -> ValetParkingActive(code)
                "4122" -> GeneralErrorLocking(code)
                "4123" -> NotInPark(code)
                "4124" -> VehicleReady(code)
                "4125" -> GeneralErrorChargeFlap(code)
                "4126" -> UnlockErrorChargeCoupler(code)
                "4127" -> GeneralErrorChargeCoupler(code)
                "4128" -> DoorNotClosed(code)
                "4129" -> DoorFrontLeftNotClosed(code)
                "4130" -> DoorFrontRightNotClosed(code)
                "4131" -> DoorRearLeftNotClosed(code)
                "4132" -> DoorRearRightNotClosed(code)
                "4133" -> DecklidNotClosed(code)
                "4134" -> FlipWindowNotClosed(code)
                "4135" -> SideChargeFlapNotClosed(code)
                "4136" -> RearChargeFlapNotClosed(code)
                "4137" -> FuelFlapNotClosed(code)
                "4138" -> LockingRequestCancelled(code)
                "4139" -> RduGlobalInactive(code)
                "4140" -> RduSelectionInactive(code)
                "4141" -> RdlInactive(code)
                "4142" -> RduDecklidInactive(code)
                "4143" -> RduFuelFlapInactive(code)
                "4144" -> RequestNotAllowed(code)
                "4145" -> ParameterNotAllowed(code)
                "4146" -> RestrictedInfoParameter(code)
                "4147" -> TransportModeActive(code)
                "4148" -> CentralLockingDisabled(code)
                "4149" -> GasAlarmActive(code)
                "4150" -> UnknownReason(code)
                "42" -> FastpathTimeout(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the unlock doors command version v1
sealed class DoorsUnlockError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : DoorsUnlockError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Failed due to central locking disabled
    class CentralLockingDisabled(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.CentralLockingDisabled)

    // Failed due to decklid not closed
    class DecklidNotClosed(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.DecklidNotClosed)

    // Failed due to decklid not locked
    class DecklidNotLocked(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.DecklidNotLocked)

    // Failed due to front left door not closed
    class DoorFrontLeftNotClosed(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.DoorFrontLeftNotClosed)

    // Failed due to front left door not locked
    class DoorFrontLeftNotLocked(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.DoorFrontLeftNotLocked)

    // Failed due to front right door not closed
    class DoorFrontRightNotClosed(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.DoorFrontRightNotClosed)

    // Failed due to front right door not locked
    class DoorFrontRightNotLocked(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.DoorFrontRightNotLocked)

    // Failed due to one or more doors not closed
    class DoorNotClosed(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.DoorNotClosed)

    // Failed due to rear left door not closed
    class DoorRearLeftNotClosed(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.DoorRearLeftNotClosed)

    // Failed due to rear left door not locked
    class DoorRearLeftNotLocked(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.DoorRearLeftNotLocked)

    // Failed due to rear right door not closed
    class DoorRearRightNotClosed(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.DoorRearRightNotClosed)

    // Failed due to rear right door not locked
    class DoorRearRightNotLocked(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.DoorRearRightNotLocked)

    // Failed due to one or more doors not locked
    class DoorsNotLocked(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.DoorsNotLocked)

    // Failed due to driver door open
    class DriverDoorOpen(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.DriverDoorOpen)

    // Failed due to driver in vehicle
    class DriverIsInVehicle(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.DriverIsInVehicle)

    // Failed due to vehicle already external locked
    class ExternallyLocked(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.ExternallyLocked)

    // Failed
    class Failed(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.Failed)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Failed due to flip window not closed
    class FlipWindowNotClosed(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.FlipWindowNotClosed)

    // Failed due to flip window not locked
    class FlipWindowNotLocked(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.FlipWindowNotLocked)

    // Failed due to fuel flap not closed
    class FuelFlapNotClosed(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.FuelFlapNotClosed)

    // Failed due to fuel flap not locked
    class FuelFlapNotLocked(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.FuelFlapNotLocked)

    // Failed due to gas alarm active
    class GasAlarmActive(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.GasAlarmActive)

    // Failed due to general error in charge coupler system
    class GeneralErrorChargeCoupler(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.GeneralErrorChargeCoupler)

    // Failed due to general error in charge flap system
    class GeneralErrorChargeFlap(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.GeneralErrorChargeFlap)

    // Failed due to general error in locking system
    class GeneralErrorLocking(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.GeneralErrorLocking)

    // Failed due to HOLD-function active
    class HoldActive(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.HoldActive)

    // Failed due to ignition state active
    class IgnitionActive(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.IgnitionActive)

    // Failed due to ignition transition
    class IgnitionInTransition(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.IgnitionInTransition)

    // Failed due to ignition is on
    class IgnitionOn(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.IgnitionOn)

    // Failed due to invalid SMS time
    class InvalidSmsTime(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.InvalidSmsTime)

    // Failed due to request to central locking system cancelled
    class LockingRequestCancelled(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.LockingRequestCancelled)

    // Energy level in Battery is too low
    class LowBatteryLevel(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel)

    // Failed due to low battery level 1
    class LowBatteryLevel1(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel1)

    // Failed due to low battery level 2
    class LowBatteryLevel2(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel2)

    // Failed due to vehicle not external locked
    class NotExternallyLocked(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.NotExternallyLocked)

    // Failed due to vehicle not in parking gear selection
    class NotInPark(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.NotInPark)

    // Failed due to parallel request to central locking system
    class ParallelRequestToLocking(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.ParallelRequestToLocking)

    // Failed due to parameter not allowed
    class ParameterNotAllowed(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.ParameterNotAllowed)

    // Failed due to RDL inactive
    class RdlInactive(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.RdlInactive)

    // Failed due to RDU decklid inactive
    class RduDecklidInactive(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.RduDecklidInactive)

    // Failed due to RDU fuel flap inactive
    class RduFuelFlapInactive(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.RduFuelFlapInactive)

    // Failed due to RDU global inactive
    class RduGlobalInactive(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.RduGlobalInactive)

    // Failed due to RDU selective inactive
    class RduSelectionInactive(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.RduSelectionInactive)

    // Failed due to rear charge flap not closed
    class RearChargeFlapNotClosed(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.RearChargeFlapNotClosed)

    // Failed due to remote engine start is active
    class RemoteEngineStartIsActive(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.RemoteEngineStartIsActive)

    // Failed due to request not allowed
    class RequestNotAllowed(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.RequestNotAllowed)

    // Failed due to restricted info parameter
    class RestrictedInfoParameter(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.RestrictedInfoParameter)

    // Service not authorized
    class ServiceNotAuthorized(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.ServiceNotAuthorized)

    // Failed due to side charge flap not closed
    class SideChargeFlapNotClosed(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.SideChargeFlapNotClosed)

    // Failed due to timeout
    class Timeout(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.Timeout)

    // Failed due to too many requests to central locking system
    class TooManyRequestsToLocking(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.TooManyRequestsToLocking)

    // Failed due to transport mode active
    class TransportModeActive(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.TransportModeActive)

    // Failed due to unknown reason
    class UnknownReason(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.UnknownReason)

    // Failed due to unlock error in charge coupler system
    class UnlockErrorChargeCoupler(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.UnlockErrorChargeCoupler)

    // Unlock request not authorized
    class UnlockNotAuthorized(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.UnlockNotAuthorized)

    // Failed due to valet parking active
    class ValetParkingActive(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.ValetParkingActive)

    // Failed because vehicle is in motion
    class VehicleInMotion(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.VehicleInMotion)

    // Failed due to vehicle in ready state
    class VehicleReady(rawErrorCode: String) : DoorsUnlockError(rawErrorCode, InternalVehicleCommandError.VehicleReady)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): DoorsUnlockError {
            return when (code) {
                "21" -> LowBatteryLevel(code)
                "4011" -> Failed(code)
                "4012" -> Timeout(code)
                "4013" -> InvalidSmsTime(code)
                "4014" -> VehicleInMotion(code)
                "4015" -> IgnitionInTransition(code)
                "4016" -> UnlockNotAuthorized(code)
                "4100" -> ServiceNotAuthorized(code)
                "4101" -> IgnitionOn(code)
                "4102" -> RemoteEngineStartIsActive(code)
                "4103" -> DriverIsInVehicle(code)
                "4104" -> NotExternallyLocked(code)
                "4105" -> LowBatteryLevel1(code)
                "4106" -> LowBatteryLevel2(code)
                "4107" -> DoorsNotLocked(code)
                "4108" -> DoorFrontLeftNotLocked(code)
                "4109" -> DoorFrontRightNotLocked(code)
                "4110" -> DoorRearLeftNotLocked(code)
                "4111" -> DoorRearRightNotLocked(code)
                "4112" -> DecklidNotLocked(code)
                "4113" -> FlipWindowNotLocked(code)
                "4114" -> FuelFlapNotLocked(code)
                "4115" -> DriverDoorOpen(code)
                "4116" -> IgnitionActive(code)
                "4117" -> ParallelRequestToLocking(code)
                "4118" -> TooManyRequestsToLocking(code)
                "4119" -> HoldActive(code)
                "4120" -> ExternallyLocked(code)
                "4121" -> ValetParkingActive(code)
                "4122" -> GeneralErrorLocking(code)
                "4123" -> NotInPark(code)
                "4124" -> VehicleReady(code)
                "4125" -> GeneralErrorChargeFlap(code)
                "4126" -> UnlockErrorChargeCoupler(code)
                "4127" -> GeneralErrorChargeCoupler(code)
                "4128" -> DoorNotClosed(code)
                "4129" -> DoorFrontLeftNotClosed(code)
                "4130" -> DoorFrontRightNotClosed(code)
                "4131" -> DoorRearLeftNotClosed(code)
                "4132" -> DoorRearRightNotClosed(code)
                "4133" -> DecklidNotClosed(code)
                "4134" -> FlipWindowNotClosed(code)
                "4135" -> SideChargeFlapNotClosed(code)
                "4136" -> RearChargeFlapNotClosed(code)
                "4137" -> FuelFlapNotClosed(code)
                "4138" -> LockingRequestCancelled(code)
                "4139" -> RduGlobalInactive(code)
                "4140" -> RduSelectionInactive(code)
                "4141" -> RdlInactive(code)
                "4142" -> RduDecklidInactive(code)
                "4143" -> RduFuelFlapInactive(code)
                "4144" -> RequestNotAllowed(code)
                "4145" -> ParameterNotAllowed(code)
                "4146" -> RestrictedInfoParameter(code)
                "4147" -> TransportModeActive(code)
                "4148" -> CentralLockingDisabled(code)
                "4149" -> GasAlarmActive(code)
                "4150" -> UnknownReason(code)
                "42" -> FastpathTimeout(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the start engine command version v1
sealed class EngineStartError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : EngineStartError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Accelerator pressed
    class AcceleratorPressed(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.AcceleratorPressed)

    // Alarm, panic alarm and/or warning blinker active
    class AlarmActive(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.AlarmActive)

    // theft alarm /panic alarm / emergency flashers got triggered
    class AlarmTriggered(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.AlarmTriggered)

    // Charge cable is plugged
    class ChargeCablePlugged(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.ChargeCablePlugged)

    // Check engine light is on
    class CheckEngineLightOn(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.CheckEngineLightOn)

    // cryptologic error
    class CryptoError(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.CryptoError)

    // Failed due to one or more doors not locked
    class DoorsNotLocked(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.DoorsNotLocked)

    // Doors open
    class DoorsOpen(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.DoorsOpen)

    // doors were opened
    class DoorsOpened(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.DoorsOpened)

    // Engine control module unexpecedely shuts off
    class EngineControlShutsOff(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.EngineControlShutsOff)

    // Engine Hood open
    class EngineHoodOpen(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.EngineHoodOpen)

    // engine hood was opened
    class EngineHoodOpened(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.EngineHoodOpened)

    // engine unexpected shut off
    class EngineShutOff(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.EngineShutOff)

    // engine shut off - doors became unlocked
    class EngineShutOffByDoorsUnlocked(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.EngineShutOffByDoorsUnlocked)

    // engine shut off - either by timeout or by user request
    class EngineShutOffByTimeoutOrUser(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.EngineShutOffByTimeoutOrUser)

    // engine successfully started
    class EngineSuccessfullyStarted(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.EngineSuccessfullyStarted)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // FBS general error for challengeResponse generation
    class FsbChallengeResponseError(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.FsbChallengeResponseError)

    // FBS is not able to create a valid challengeResponse for the given VIN
    class FsbUnableToCreateChallengeResponse(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.FsbUnableToCreateChallengeResponse)

    // FBS is not reachable due to maintenance
    class FsbUnreachable(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.FsbUnreachable)

    // fuel got low
    class FuelLow(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.FuelLow)

    // Fuel tank too low (less than 25% volume)
    class FuelTankTooLow(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.FuelTankTooLow)

    // gas pedal was pressed
    class GasPedalPressed(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.GasPedalPressed)

    // The gear is not in Parking position
    class GearNotInPark(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.GearNotInPark)

    // vehicle key plugged in the ignition mechanism
    class KeyPluggedIn(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.KeyPluggedIn)

    // Vehicle key plugged in while engine is running
    class KeyPluggedInWhileEngineIsRunning(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.KeyPluggedInWhileEngineIsRunning)

    // new RS requested within operational timewindow (default 15 min.)
    class NewRsRequested(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.NewRsRequested)

    // DaiVB does not receive asynchronous callback within MAX_RES_CALLBACK_TIME
    class NoCallbackReceived(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.NoCallbackReceived)

    // Remote start is blocked due to parallel FBS workflow
    class RemoteStartBlocked(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.RemoteStartBlocked)

    // request received and processed twice by EIS, within the same IGN cycle rsAbortedRequestRefus
    class RequestReceivedTwice(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.RequestReceivedTwice)

    // TCU exhausted all retries on CAN and did not get a valid response from EIS
    class TcuCanError(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.TcuCanError)

    // TCU has remote start service deauthorized
    class TcuNoRemoteService(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.TcuNoRemoteService)

    // Windows and/or roof open
    class WindowsOrRoofOpen(rawErrorCode: String) : EngineStartError(rawErrorCode, InternalVehicleCommandError.WindowsOrRoofOpen)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): EngineStartError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "6801" -> EngineSuccessfullyStarted(code)
                "6802" -> EngineShutOffByTimeoutOrUser(code)
                "6803" -> EngineShutOffByDoorsUnlocked(code)
                "6804" -> DoorsOpened(code)
                "6805" -> EngineHoodOpened(code)
                "6806" -> AlarmTriggered(code)
                "6807" -> FuelLow(code)
                "6808" -> GasPedalPressed(code)
                "6809" -> KeyPluggedInWhileEngineIsRunning(code)
                "6810" -> EngineControlShutsOff(code)
                "6811" -> KeyPluggedIn(code)
                "6812" -> GearNotInPark(code)
                "6813" -> DoorsNotLocked(code)
                "6814" -> DoorsOpen(code)
                "6815" -> WindowsOrRoofOpen(code)
                "6816" -> EngineHoodOpen(code)
                "6817" -> AlarmActive(code)
                "6818" -> FuelTankTooLow(code)
                "6819" -> AcceleratorPressed(code)
                "6820" -> NewRsRequested(code)
                "6821" -> CryptoError(code)
                "6822" -> RequestReceivedTwice(code)
                "6823" -> EngineShutOff(code)
                "6824" -> TcuCanError(code)
                "6825" -> TcuNoRemoteService(code)
                "6826" -> ChargeCablePlugged(code)
                "6827" -> FsbUnableToCreateChallengeResponse(code)
                "6828" -> FsbUnreachable(code)
                "6829" -> NoCallbackReceived(code)
                "6830" -> FsbChallengeResponseError(code)
                "6831" -> RemoteStartBlocked(code)
                "6832" -> CheckEngineLightOn(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the stop engine command version v1
sealed class EngineStopError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : EngineStopError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Accelerator pressed
    class AcceleratorPressed(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.AcceleratorPressed)

    // Alarm, panic alarm and/or warning blinker active
    class AlarmActive(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.AlarmActive)

    // theft alarm /panic alarm / emergency flashers got triggered
    class AlarmTriggered(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.AlarmTriggered)

    // Charge cable is plugged
    class ChargeCablePlugged(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.ChargeCablePlugged)

    // Check engine light is on
    class CheckEngineLightOn(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.CheckEngineLightOn)

    // cryptologic error
    class CryptoError(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.CryptoError)

    // Failed due to one or more doors not locked
    class DoorsNotLocked(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.DoorsNotLocked)

    // Doors open
    class DoorsOpen(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.DoorsOpen)

    // doors were opened
    class DoorsOpened(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.DoorsOpened)

    // Engine control module unexpecedely shuts off
    class EngineControlShutsOff(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.EngineControlShutsOff)

    // Engine Hood open
    class EngineHoodOpen(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.EngineHoodOpen)

    // engine hood was opened
    class EngineHoodOpened(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.EngineHoodOpened)

    // engine unexpected shut off
    class EngineShutOff(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.EngineShutOff)

    // engine shut off - doors became unlocked
    class EngineShutOffByDoorsUnlocked(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.EngineShutOffByDoorsUnlocked)

    // engine shut off - either by timeout or by user request
    class EngineShutOffByTimeoutOrUser(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.EngineShutOffByTimeoutOrUser)

    // engine successfully started
    class EngineSuccessfullyStarted(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.EngineSuccessfullyStarted)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // FBS general error for challengeResponse generation
    class FsbChallengeResponseError(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.FsbChallengeResponseError)

    // FBS is not able to create a valid challengeResponse for the given VIN
    class FsbUnableToCreateChallengeResponse(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.FsbUnableToCreateChallengeResponse)

    // FBS is not reachable due to maintenance
    class FsbUnreachable(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.FsbUnreachable)

    // fuel got low
    class FuelLow(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.FuelLow)

    // Fuel tank too low (less than 25% volume)
    class FuelTankTooLow(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.FuelTankTooLow)

    // gas pedal was pressed
    class GasPedalPressed(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.GasPedalPressed)

    // The gear is not in Parking position
    class GearNotInPark(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.GearNotInPark)

    // vehicle key plugged in the ignition mechanism
    class KeyPluggedIn(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.KeyPluggedIn)

    // Vehicle key plugged in while engine is running
    class KeyPluggedInWhileEngineIsRunning(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.KeyPluggedInWhileEngineIsRunning)

    // new RS requested within operational timewindow (default 15 min.)
    class NewRsRequested(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.NewRsRequested)

    // DaiVB does not receive asynchronous callback within MAX_RES_CALLBACK_TIME
    class NoCallbackReceived(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.NoCallbackReceived)

    // Remote start is blocked due to parallel FBS workflow
    class RemoteStartBlocked(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.RemoteStartBlocked)

    // request received and processed twice by EIS, within the same IGN cycle rsAbortedRequestRefus
    class RequestReceivedTwice(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.RequestReceivedTwice)

    // TCU exhausted all retries on CAN and did not get a valid response from EIS
    class TcuCanError(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.TcuCanError)

    // TCU has remote start service deauthorized
    class TcuNoRemoteService(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.TcuNoRemoteService)

    // Windows and/or roof open
    class WindowsOrRoofOpen(rawErrorCode: String) : EngineStopError(rawErrorCode, InternalVehicleCommandError.WindowsOrRoofOpen)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): EngineStopError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "6801" -> EngineSuccessfullyStarted(code)
                "6802" -> EngineShutOffByTimeoutOrUser(code)
                "6803" -> EngineShutOffByDoorsUnlocked(code)
                "6804" -> DoorsOpened(code)
                "6805" -> EngineHoodOpened(code)
                "6806" -> AlarmTriggered(code)
                "6807" -> FuelLow(code)
                "6808" -> GasPedalPressed(code)
                "6809" -> KeyPluggedInWhileEngineIsRunning(code)
                "6810" -> EngineControlShutsOff(code)
                "6811" -> KeyPluggedIn(code)
                "6812" -> GearNotInPark(code)
                "6813" -> DoorsNotLocked(code)
                "6814" -> DoorsOpen(code)
                "6815" -> WindowsOrRoofOpen(code)
                "6816" -> EngineHoodOpen(code)
                "6817" -> AlarmActive(code)
                "6818" -> FuelTankTooLow(code)
                "6819" -> AcceleratorPressed(code)
                "6820" -> NewRsRequested(code)
                "6821" -> CryptoError(code)
                "6822" -> RequestReceivedTwice(code)
                "6823" -> EngineShutOff(code)
                "6824" -> TcuCanError(code)
                "6825" -> TcuNoRemoteService(code)
                "6826" -> ChargeCablePlugged(code)
                "6827" -> FsbUnableToCreateChallengeResponse(code)
                "6828" -> FsbUnreachable(code)
                "6829" -> NoCallbackReceived(code)
                "6830" -> FsbChallengeResponseError(code)
                "6831" -> RemoteStartBlocked(code)
                "6832" -> CheckEngineLightOn(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the lockvehicle immobilizer command version v2
sealed class DeactivateVehicleKeysError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : DeactivateVehicleKeysError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // cryptologic error
    class CryptoError(rawErrorCode: String) : DeactivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.CryptoError)

    // Command failed: Doors failed
    class DoorsFailed(rawErrorCode: String) : DeactivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.DoorsFailed)

    // Command expired
    class Expired(rawErrorCode: String) : DeactivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.Expired)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : DeactivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Command failed: Function disabled.
    class FunctionDisabled(rawErrorCode: String) : DeactivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.FunctionDisabled)

    // Command failed: Function not authorized.
    class FunctionNotAuthorized(rawErrorCode: String) : DeactivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.FunctionNotAuthorized)

    // The ignition is not switched off
    class IgnitionNotSwitchedOff(rawErrorCode: String) : DeactivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.IgnitionNotSwitchedOff)

    // Failed due to ignition is on
    class IgnitionOn(rawErrorCode: String) : DeactivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.IgnitionOn)

    // Command failed: Immobilizer failed
    class ImmobilizerFailed(rawErrorCode: String) : DeactivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.ImmobilizerFailed)

    // Get Challenge Failed: General Error
    class ImmobilizerGetChallengeFailed(rawErrorCode: String) : DeactivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.ImmobilizerGetChallengeFailed)

    // Command failed: Incompatible response from vehicle
    class ImmobilizerIncompatibleResponseFromVehicle(rawErrorCode: String) : DeactivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.ImmobilizerIncompatibleResponseFromVehicle)

    // Command failed: Vehicle states
    class ImmobilizerParkingBrakeNotSet(rawErrorCode: String) : DeactivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.ImmobilizerParkingBrakeNotSet)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): DeactivateVehicleKeysError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "7307" -> ImmobilizerFailed(code)
                "7308" -> DoorsFailed(code)
                "7309" -> ImmobilizerParkingBrakeNotSet(code)
                "7310" -> IgnitionOn(code)
                "7316" -> Expired(code)
                "7317" -> ImmobilizerIncompatibleResponseFromVehicle(code)
                "7323" -> IgnitionNotSwitchedOff(code)
                "7324" -> CryptoError(code)
                "7325" -> ImmobilizerGetChallengeFailed(code)
                "7328" -> FunctionDisabled(code)
                "7329" -> FunctionNotAuthorized(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the releasevehicle immobilizer command version v2
sealed class ActivateVehicleKeysError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : ActivateVehicleKeysError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // cryptologic error
    class CryptoError(rawErrorCode: String) : ActivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.CryptoError)

    // Command expired
    class Expired(rawErrorCode: String) : ActivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.Expired)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : ActivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Command failed: Function disabled.
    class FunctionDisabled(rawErrorCode: String) : ActivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.FunctionDisabled)

    // Command failed: Function not authorized.
    class FunctionNotAuthorized(rawErrorCode: String) : ActivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.FunctionNotAuthorized)

    // Command failed: Ignition failed
    class IgnitionFailed(rawErrorCode: String) : ActivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.IgnitionFailed)

    // The ignition is not switched off
    class IgnitionNotSwitchedOff(rawErrorCode: String) : ActivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.IgnitionNotSwitchedOff)

    // Command failed
    class ImmobilizerCommandFailed(rawErrorCode: String) : ActivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.ImmobilizerCommandFailed)

    // Get Challenge Failed: General Error
    class ImmobilizerGetChallengeFailed(rawErrorCode: String) : ActivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.ImmobilizerGetChallengeFailed)

    // Command failed: Incompatible response from vehicle
    class ImmobilizerIncompatibleResponseFromVehicle(rawErrorCode: String) : ActivateVehicleKeysError(rawErrorCode, InternalVehicleCommandError.ImmobilizerIncompatibleResponseFromVehicle)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): ActivateVehicleKeysError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "7301" -> ImmobilizerCommandFailed(code)
                "7303" -> IgnitionFailed(code)
                "7316" -> Expired(code)
                "7317" -> ImmobilizerIncompatibleResponseFromVehicle(code)
                "7323" -> IgnitionNotSwitchedOff(code)
                "7324" -> CryptoError(code)
                "7325" -> ImmobilizerGetChallengeFailed(code)
                "7328" -> FunctionDisabled(code)
                "7329" -> FunctionNotAuthorized(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the start precond command version v1
sealed class ZevPreconditioningStartError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : ZevPreconditioningStartError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // PreConditioning not possible, charging not finished
    class ChargingNotFinished(rawErrorCode: String) : ZevPreconditioningStartError(rawErrorCode, InternalVehicleCommandError.ChargingNotFinished)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : ZevPreconditioningStartError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // not possible since either INSTANT CHARGING is already activated or INSTANT CHARGING command is currently in progress
    class InstantChargingActiveOrInProgress(rawErrorCode: String) : ZevPreconditioningStartError(rawErrorCode, InternalVehicleCommandError.InstantChargingActiveOrInProgress)

    // Energy level in Battery is too low
    class LowBatteryLevel(rawErrorCode: String) : ZevPreconditioningStartError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel)

    // PreConditioning not possible, General error
    class PreConditionGeneralError(rawErrorCode: String) : ZevPreconditioningStartError(rawErrorCode, InternalVehicleCommandError.PreConditionGeneralError)

    // Processing of zev command failed
    class ProcessingFailed(rawErrorCode: String) : ZevPreconditioningStartError(rawErrorCode, InternalVehicleCommandError.ProcessingFailed)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): ZevPreconditioningStartError {
            return when (code) {
                "4051" -> ProcessingFailed(code)
                "4052" -> LowBatteryLevel(code)
                "4053" -> ChargingNotFinished(code)
                "4054" -> InstantChargingActiveOrInProgress(code)
                "4055" -> PreConditionGeneralError(code)
                "42" -> FastpathTimeout(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the close roof command version v1
sealed class SunroofCloseError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : SunroofCloseError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Failed due to afterrun active
    class AfterRunActive(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.AfterRunActive)

    // Failed due to afterrun active on front roof roller blind
    class AfterRunActiveFrontRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveFrontRoofRollerBlind)

    // Failed due to afterrun active on rear roof roller blind
    class AfterRunActiveRearRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearRoofRollerBlind)

    // Failed due to afterrun active on sunroof
    class AfterRunActiveSunroof(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveSunroof)

    // Failed due to anti-trap protection active
    class AntiTrapProtectionActive(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActive)

    // Failed due to anti-trap protection active on front roof roller blind
    class AntiTrapProtectionActiveFrontRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveFrontRoofRollerBlind)

    // Failed due to anti-trap protection active on rear roof roller blind
    class AntiTrapProtectionActiveRearRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearRoofRollerBlind)

    // Failed due to anti-trap protection active on sunroof
    class AntiTrapProtectionActiveSunroof(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveSunroof)

    // Failed due to manual cancellation inside vehicle
    class CancelledManuallyInVehicle(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicle)

    // Failed due to manual cancellation inside vehicle on front roof roller blind
    class CancelledManuallyInVehicleFrontRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleFrontRoofRollerBlind)

    // Failed due to position not reached within timeout on rear roller blind
    class CancelledManuallyInVehicleRearRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearRollerBlind)

    // Failed due to manual cancellation inside vehicle on rear roof roller blind
    class CancelledManuallyInVehicleRearRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearRoofRollerBlind)

    // Failed due to drive motor overheated
    class DriveMotorOverheated(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheated)

    // Failed due to drive motor overheated on front roof roller blind
    class DriveMotorOverheatedFrontRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedFrontRoofRollerBlind)

    // Failed due to drive motor overheated on rear roof roller blind
    class DriveMotorOverheatedRearRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearRoofRollerBlind)

    // Failed due to drive motor overheated on sunroof
    class DriveMotorOverheatedSunroof(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedSunroof)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Failed due to feature not available on front roof roller blind
    class FeatureNotAvailableFrontRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableFrontRoofRollerBlind)

    // Failed due to feature not available on rear roof roller blind
    class FeatureNotAvailableRearRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearRoofRollerBlind)

    // Failed due to feature not available on sunroof
    class FeatureNotAvailableSunroof(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableSunroof)

    // Failed due to front roof roller blind in motion
    class FrontRoofRollerBlindInMotion(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.FrontRoofRollerBlindInMotion)

    // Failed due to ignition is on
    class IgnitionOn(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.IgnitionOn)

    // Failed due to internal system error
    class InternalSystemError(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.InternalSystemError)

    // Failed due to invalid ignition state
    class InvalidIgnitionState(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionState)

    // Failed due to invalid ignition state on front roof roller blind
    class InvalidIgnitionStateFrontRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateFrontRoofRollerBlind)

    // Failed due to invalid ignition state on rear roof roller blind
    class InvalidIgnitionStateRearRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearRoofRollerBlind)

    // Failed due to invalid ignition state on sunroof
    class InvalidIgnitionStateSunroof(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateSunroof)

    // Failed due to invalid number on front roof roller blind
    class InvalidNumberFrontRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.InvalidNumberFrontRoofRollerBlind)

    // Failed due to invalid number on rear roof roller blind
    class InvalidNumberRearRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearRoofRollerBlind)

    // Failed due to invalid number on sunroof
    class InvalidNumberSunroof(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.InvalidNumberSunroof)

    // Failed due to invalid position on front roof roller blind
    class InvalidPositionFrontRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPositionFrontRoofRollerBlind)

    // Failed due to invalid position on rear roof roller blind
    class InvalidPositionRearRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearRoofRollerBlind)

    // Failed due to invalid position on sunroof
    class InvalidPositionSunroof(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPositionSunroof)

    // Failed due to invalid power status
    class InvalidPowerStatus(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatus)

    // Failed due to invalid power status on front roof roller blind
    class InvalidPowerStatusFrontRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusFrontRoofRollerBlind)

    // Failed due to invalid power status on rear roof roller blind
    class InvalidPowerStatusRearRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearRoofRollerBlind)

    // Failed due to invalid power status on sunroof
    class InvalidPowerStatusSunroof(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusSunroof)

    // Energy level in Battery is too low
    class LowBatteryLevel(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel)

    // Failed due to low battery level 1
    class LowBatteryLevel1(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel1)

    // Failed due to low battery level 2
    class LowBatteryLevel2(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel2)

    // Failed due to mounted roof box
    class MountedRoofBox(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.MountedRoofBox)

    // Failed due to multiple anti-trap protection activations
    class MultiAntiTrapProtections(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtections)

    // Failed due to multiple anti-trap protection activations on front roof roller blind
    class MultiAntiTrapProtectionsFrontRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsFrontRoofRollerBlind)

    // Failed due to multiple anti-trap protection activations on rear roof roller blind
    class MultiAntiTrapProtectionsRearRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearRoofRollerBlind)

    // Failed due to multiple anti-trap protection activations on sunroof
    class MultiAntiTrapProtectionsSunroof(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsSunroof)

    // Failed due to rear roof roller blind in motion
    class RearRoofRollerBlindInMotion(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.RearRoofRollerBlindInMotion)

    // Failed due to remote engine start is active
    class RemoteEngineStartIsActive(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.RemoteEngineStartIsActive)

    // Failed due to roof in motion
    class RoofInMotion(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.RoofInMotion)

    // Failed due to roof or roller blind in motion
    class RoofOrRollerBlindInMotion(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.RoofOrRollerBlindInMotion)

    // Service not authorized
    class ServiceNotAuthorized(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.ServiceNotAuthorized)

    // Failed due to system could not be normed
    class SystemCouldNotBeNormed(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormed)

    // Failed due to system could not be normed on front roof roller blind
    class SystemCouldNotBeNormedFrontRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedFrontRoofRollerBlind)

    // Failed due to system could not be normed on rear roof roller blind
    class SystemCouldNotBeNormedRearRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearRoofRollerBlind)

    // Failed due to system could not be normed on sunroof
    class SystemCouldNotBeNormedSunroof(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedSunroof)

    // Failed due to system malfunction
    class SystemMalfunction(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.SystemMalfunction)

    // Failed due to system malfunction on front roof roller blind
    class SystemMalfunctionFrontRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionFrontRoofRollerBlind)

    // Failed due to system malfunction on rear roof roller blind
    class SystemMalfunctionRearRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearRoofRollerBlind)

    // Failed due to system malfunction on sunroof
    class SystemMalfunctionSunroof(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionSunroof)

    // Failed due to system not normed
    class SystemNotNormed(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.SystemNotNormed)

    // Failed due to system not normed on front roof roller blind
    class SystemNotNormedFrontRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedFrontRoofRollerBlind)

    // Failed due to system not normed on rear roof roller blind
    class SystemNotNormedRearRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearRoofRollerBlind)

    // Failed due to system not normed on sunroof
    class SystemNotNormedSunroof(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedSunroof)

    // Failed due to UI handler not available on front roof roller blind
    class UnavailableUiHandlerFrontRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerFrontRoofRollerBlind)

    // Failed due to UI handler not available on rear roof roller blind
    class UnavailableUiHandlerRearRoofRollerBlind(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearRoofRollerBlind)

    // Failed due to UI handler not available on sunroof
    class UnavailableUiHandlerSunroof(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerSunroof)

    // Failed because vehicle is in motion
    class VehicleInMotion(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.VehicleInMotion)

    // Remote window/roof command failed
    class WindowRoofCommandFailed(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandFailed)

    // Remote window/roof command failed (vehicle state in IGN)
    class WindowRoofCommandFailedIgnState(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandFailedIgnState)

    // Remote window/roof command failed (service not activated in HERMES)
    class WindowRoofCommandServiceNotActive(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandServiceNotActive)

    // Remote window/roof command failed (window not normed)
    class WindowRoofCommandWindowNotNormed(rawErrorCode: String) : SunroofCloseError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandWindowNotNormed)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): SunroofCloseError {
            return when (code) {
                "21" -> LowBatteryLevel(code)
                "42" -> FastpathTimeout(code)
                "4200" -> ServiceNotAuthorized(code)
                "4201" -> RemoteEngineStartIsActive(code)
                "4202" -> IgnitionOn(code)
                "4203" -> LowBatteryLevel2(code)
                "4204" -> LowBatteryLevel1(code)
                "4205" -> AntiTrapProtectionActive(code)
                "4212" -> AntiTrapProtectionActiveSunroof(code)
                "4213" -> AntiTrapProtectionActiveFrontRoofRollerBlind(code)
                "4214" -> AntiTrapProtectionActiveRearRoofRollerBlind(code)
                "4216" -> MultiAntiTrapProtections(code)
                "4223" -> MultiAntiTrapProtectionsSunroof(code)
                "4224" -> MultiAntiTrapProtectionsFrontRoofRollerBlind(code)
                "4225" -> MultiAntiTrapProtectionsRearRoofRollerBlind(code)
                "4227" -> CancelledManuallyInVehicle(code)
                "4235" -> CancelledManuallyInVehicleFrontRoofRollerBlind(code)
                "4236" -> CancelledManuallyInVehicleRearRoofRollerBlind(code)
                "4237" -> CancelledManuallyInVehicleRearRollerBlind(code)
                "4238" -> RoofOrRollerBlindInMotion(code)
                "4239" -> RoofInMotion(code)
                "4240" -> FrontRoofRollerBlindInMotion(code)
                "4241" -> RearRoofRollerBlindInMotion(code)
                "4242" -> DriveMotorOverheated(code)
                "4249" -> DriveMotorOverheatedSunroof(code)
                "4250" -> DriveMotorOverheatedFrontRoofRollerBlind(code)
                "4251" -> DriveMotorOverheatedRearRoofRollerBlind(code)
                "4253" -> SystemNotNormed(code)
                "4260" -> SystemNotNormedSunroof(code)
                "4261" -> SystemNotNormedFrontRoofRollerBlind(code)
                "4262" -> SystemNotNormedRearRoofRollerBlind(code)
                "4264" -> MountedRoofBox(code)
                "4265" -> InvalidPowerStatus(code)
                "4272" -> InvalidPowerStatusSunroof(code)
                "4273" -> InvalidPowerStatusFrontRoofRollerBlind(code)
                "4274" -> InvalidPowerStatusRearRoofRollerBlind(code)
                "4276" -> AfterRunActive(code)
                "4283" -> AfterRunActiveSunroof(code)
                "4284" -> AfterRunActiveFrontRoofRollerBlind(code)
                "4285" -> AfterRunActiveRearRoofRollerBlind(code)
                "4287" -> InvalidIgnitionState(code)
                "4294" -> InvalidIgnitionStateSunroof(code)
                "4295" -> InvalidIgnitionStateFrontRoofRollerBlind(code)
                "4296" -> InvalidIgnitionStateRearRoofRollerBlind(code)
                "4298" -> VehicleInMotion(code)
                "4299" -> VehicleInMotion(code)
                "4300" -> VehicleInMotion(code)
                "4301" -> VehicleInMotion(code)
                "4303" -> SystemCouldNotBeNormed(code)
                "4310" -> SystemCouldNotBeNormedSunroof(code)
                "4311" -> SystemCouldNotBeNormedFrontRoofRollerBlind(code)
                "4312" -> SystemCouldNotBeNormedRearRoofRollerBlind(code)
                "4314" -> SystemMalfunction(code)
                "4321" -> SystemMalfunctionSunroof(code)
                "4322" -> SystemMalfunctionFrontRoofRollerBlind(code)
                "4323" -> SystemMalfunctionRearRoofRollerBlind(code)
                "4325" -> InternalSystemError(code)
                "4334" -> InvalidNumberSunroof(code)
                "4335" -> FeatureNotAvailableSunroof(code)
                "4340" -> InvalidNumberFrontRoofRollerBlind(code)
                "4341" -> FeatureNotAvailableFrontRoofRollerBlind(code)
                "4342" -> InvalidNumberRearRoofRollerBlind(code)
                "4343" -> FeatureNotAvailableRearRoofRollerBlind(code)
                "4358" -> InvalidPositionSunroof(code)
                "4359" -> UnavailableUiHandlerSunroof(code)
                "4360" -> InvalidPositionFrontRoofRollerBlind(code)
                "4361" -> UnavailableUiHandlerFrontRoofRollerBlind(code)
                "4362" -> InvalidPositionRearRoofRollerBlind(code)
                "4363" -> UnavailableUiHandlerRearRoofRollerBlind(code)
                "6901" -> WindowRoofCommandFailed(code)
                "6902" -> WindowRoofCommandFailedIgnState(code)
                "6903" -> WindowRoofCommandWindowNotNormed(code)
                "6904" -> WindowRoofCommandServiceNotActive(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the lift roof command version v1
sealed class SunroofLiftError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : SunroofLiftError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Failed due to afterrun active
    class AfterRunActive(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.AfterRunActive)

    // Failed due to afterrun active on front roof roller blind
    class AfterRunActiveFrontRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveFrontRoofRollerBlind)

    // Failed due to afterrun active on rear roof roller blind
    class AfterRunActiveRearRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearRoofRollerBlind)

    // Failed due to afterrun active on sunroof
    class AfterRunActiveSunroof(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveSunroof)

    // Failed due to anti-trap protection active
    class AntiTrapProtectionActive(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActive)

    // Failed due to anti-trap protection active on front roof roller blind
    class AntiTrapProtectionActiveFrontRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveFrontRoofRollerBlind)

    // Failed due to anti-trap protection active on rear roof roller blind
    class AntiTrapProtectionActiveRearRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearRoofRollerBlind)

    // Failed due to anti-trap protection active on sunroof
    class AntiTrapProtectionActiveSunroof(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveSunroof)

    // Failed due to manual cancellation inside vehicle
    class CancelledManuallyInVehicle(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicle)

    // Failed due to manual cancellation inside vehicle on front roof roller blind
    class CancelledManuallyInVehicleFrontRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleFrontRoofRollerBlind)

    // Failed due to position not reached within timeout on rear roller blind
    class CancelledManuallyInVehicleRearRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearRollerBlind)

    // Failed due to manual cancellation inside vehicle on rear roof roller blind
    class CancelledManuallyInVehicleRearRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearRoofRollerBlind)

    // Failed due to drive motor overheated
    class DriveMotorOverheated(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheated)

    // Failed due to drive motor overheated on front roof roller blind
    class DriveMotorOverheatedFrontRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedFrontRoofRollerBlind)

    // Failed due to drive motor overheated on rear roof roller blind
    class DriveMotorOverheatedRearRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearRoofRollerBlind)

    // Failed due to drive motor overheated on sunroof
    class DriveMotorOverheatedSunroof(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedSunroof)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Failed due to feature not available on front roof roller blind
    class FeatureNotAvailableFrontRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableFrontRoofRollerBlind)

    // Failed due to feature not available on rear roof roller blind
    class FeatureNotAvailableRearRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearRoofRollerBlind)

    // Failed due to feature not available on sunroof
    class FeatureNotAvailableSunroof(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableSunroof)

    // Failed due to front roof roller blind in motion
    class FrontRoofRollerBlindInMotion(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.FrontRoofRollerBlindInMotion)

    // Failed due to ignition is on
    class IgnitionOn(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.IgnitionOn)

    // Failed due to internal system error
    class InternalSystemError(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.InternalSystemError)

    // Failed due to invalid ignition state
    class InvalidIgnitionState(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionState)

    // Failed due to invalid ignition state on front roof roller blind
    class InvalidIgnitionStateFrontRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateFrontRoofRollerBlind)

    // Failed due to invalid ignition state on rear roof roller blind
    class InvalidIgnitionStateRearRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearRoofRollerBlind)

    // Failed due to invalid ignition state on sunroof
    class InvalidIgnitionStateSunroof(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateSunroof)

    // Failed due to invalid number on front roof roller blind
    class InvalidNumberFrontRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.InvalidNumberFrontRoofRollerBlind)

    // Failed due to invalid number on rear roof roller blind
    class InvalidNumberRearRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearRoofRollerBlind)

    // Failed due to invalid number on sunroof
    class InvalidNumberSunroof(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.InvalidNumberSunroof)

    // Failed due to invalid position on front roof roller blind
    class InvalidPositionFrontRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.InvalidPositionFrontRoofRollerBlind)

    // Failed due to invalid position on rear roof roller blind
    class InvalidPositionRearRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearRoofRollerBlind)

    // Failed due to invalid position on sunroof
    class InvalidPositionSunroof(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.InvalidPositionSunroof)

    // Failed due to invalid power status
    class InvalidPowerStatus(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatus)

    // Failed due to invalid power status on front roof roller blind
    class InvalidPowerStatusFrontRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusFrontRoofRollerBlind)

    // Failed due to invalid power status on rear roof roller blind
    class InvalidPowerStatusRearRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearRoofRollerBlind)

    // Failed due to invalid power status on sunroof
    class InvalidPowerStatusSunroof(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusSunroof)

    // Energy level in Battery is too low
    class LowBatteryLevel(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel)

    // Failed due to low battery level 1
    class LowBatteryLevel1(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel1)

    // Failed due to low battery level 2
    class LowBatteryLevel2(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel2)

    // Failed due to mounted roof box
    class MountedRoofBox(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.MountedRoofBox)

    // Failed due to multiple anti-trap protection activations
    class MultiAntiTrapProtections(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtections)

    // Failed due to multiple anti-trap protection activations on front roof roller blind
    class MultiAntiTrapProtectionsFrontRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsFrontRoofRollerBlind)

    // Failed due to multiple anti-trap protection activations on rear roof roller blind
    class MultiAntiTrapProtectionsRearRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearRoofRollerBlind)

    // Failed due to multiple anti-trap protection activations on sunroof
    class MultiAntiTrapProtectionsSunroof(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsSunroof)

    // Failed due to rear roof roller blind in motion
    class RearRoofRollerBlindInMotion(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.RearRoofRollerBlindInMotion)

    // Failed due to remote engine start is active
    class RemoteEngineStartIsActive(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.RemoteEngineStartIsActive)

    // Failed due to roof in motion
    class RoofInMotion(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.RoofInMotion)

    // Failed due to roof or roller blind in motion
    class RoofOrRollerBlindInMotion(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.RoofOrRollerBlindInMotion)

    // Service not authorized
    class ServiceNotAuthorized(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.ServiceNotAuthorized)

    // Failed due to system could not be normed
    class SystemCouldNotBeNormed(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormed)

    // Failed due to system could not be normed on front roof roller blind
    class SystemCouldNotBeNormedFrontRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedFrontRoofRollerBlind)

    // Failed due to system could not be normed on rear roof roller blind
    class SystemCouldNotBeNormedRearRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearRoofRollerBlind)

    // Failed due to system could not be normed on sunroof
    class SystemCouldNotBeNormedSunroof(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedSunroof)

    // Failed due to system malfunction
    class SystemMalfunction(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.SystemMalfunction)

    // Failed due to system malfunction on front roof roller blind
    class SystemMalfunctionFrontRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionFrontRoofRollerBlind)

    // Failed due to system malfunction on rear roof roller blind
    class SystemMalfunctionRearRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearRoofRollerBlind)

    // Failed due to system malfunction on sunroof
    class SystemMalfunctionSunroof(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionSunroof)

    // Failed due to system not normed
    class SystemNotNormed(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.SystemNotNormed)

    // Failed due to system not normed on front roof roller blind
    class SystemNotNormedFrontRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedFrontRoofRollerBlind)

    // Failed due to system not normed on rear roof roller blind
    class SystemNotNormedRearRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearRoofRollerBlind)

    // Failed due to system not normed on sunroof
    class SystemNotNormedSunroof(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedSunroof)

    // Failed due to UI handler not available on front roof roller blind
    class UnavailableUiHandlerFrontRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerFrontRoofRollerBlind)

    // Failed due to UI handler not available on rear roof roller blind
    class UnavailableUiHandlerRearRoofRollerBlind(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearRoofRollerBlind)

    // Failed due to UI handler not available on sunroof
    class UnavailableUiHandlerSunroof(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerSunroof)

    // Failed because vehicle is in motion
    class VehicleInMotion(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.VehicleInMotion)

    // Remote window/roof command failed
    class WindowRoofCommandFailed(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandFailed)

    // Remote window/roof command failed (vehicle state in IGN)
    class WindowRoofCommandFailedIgnState(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandFailedIgnState)

    // Remote window/roof command failed (service not activated in HERMES)
    class WindowRoofCommandServiceNotActive(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandServiceNotActive)

    // Remote window/roof command failed (window not normed)
    class WindowRoofCommandWindowNotNormed(rawErrorCode: String) : SunroofLiftError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandWindowNotNormed)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): SunroofLiftError {
            return when (code) {
                "21" -> LowBatteryLevel(code)
                "42" -> FastpathTimeout(code)
                "4200" -> ServiceNotAuthorized(code)
                "4201" -> RemoteEngineStartIsActive(code)
                "4202" -> IgnitionOn(code)
                "4203" -> LowBatteryLevel2(code)
                "4204" -> LowBatteryLevel1(code)
                "4205" -> AntiTrapProtectionActive(code)
                "4212" -> AntiTrapProtectionActiveSunroof(code)
                "4213" -> AntiTrapProtectionActiveFrontRoofRollerBlind(code)
                "4214" -> AntiTrapProtectionActiveRearRoofRollerBlind(code)
                "4216" -> MultiAntiTrapProtections(code)
                "4223" -> MultiAntiTrapProtectionsSunroof(code)
                "4224" -> MultiAntiTrapProtectionsFrontRoofRollerBlind(code)
                "4225" -> MultiAntiTrapProtectionsRearRoofRollerBlind(code)
                "4227" -> CancelledManuallyInVehicle(code)
                "4235" -> CancelledManuallyInVehicleFrontRoofRollerBlind(code)
                "4236" -> CancelledManuallyInVehicleRearRoofRollerBlind(code)
                "4237" -> CancelledManuallyInVehicleRearRollerBlind(code)
                "4238" -> RoofOrRollerBlindInMotion(code)
                "4239" -> RoofInMotion(code)
                "4240" -> FrontRoofRollerBlindInMotion(code)
                "4241" -> RearRoofRollerBlindInMotion(code)
                "4242" -> DriveMotorOverheated(code)
                "4249" -> DriveMotorOverheatedSunroof(code)
                "4250" -> DriveMotorOverheatedFrontRoofRollerBlind(code)
                "4251" -> DriveMotorOverheatedRearRoofRollerBlind(code)
                "4253" -> SystemNotNormed(code)
                "4260" -> SystemNotNormedSunroof(code)
                "4261" -> SystemNotNormedFrontRoofRollerBlind(code)
                "4262" -> SystemNotNormedRearRoofRollerBlind(code)
                "4264" -> MountedRoofBox(code)
                "4265" -> InvalidPowerStatus(code)
                "4272" -> InvalidPowerStatusSunroof(code)
                "4273" -> InvalidPowerStatusFrontRoofRollerBlind(code)
                "4274" -> InvalidPowerStatusRearRoofRollerBlind(code)
                "4276" -> AfterRunActive(code)
                "4283" -> AfterRunActiveSunroof(code)
                "4284" -> AfterRunActiveFrontRoofRollerBlind(code)
                "4285" -> AfterRunActiveRearRoofRollerBlind(code)
                "4287" -> InvalidIgnitionState(code)
                "4294" -> InvalidIgnitionStateSunroof(code)
                "4295" -> InvalidIgnitionStateFrontRoofRollerBlind(code)
                "4296" -> InvalidIgnitionStateRearRoofRollerBlind(code)
                "4298" -> VehicleInMotion(code)
                "4299" -> VehicleInMotion(code)
                "4300" -> VehicleInMotion(code)
                "4301" -> VehicleInMotion(code)
                "4303" -> SystemCouldNotBeNormed(code)
                "4310" -> SystemCouldNotBeNormedSunroof(code)
                "4311" -> SystemCouldNotBeNormedFrontRoofRollerBlind(code)
                "4312" -> SystemCouldNotBeNormedRearRoofRollerBlind(code)
                "4314" -> SystemMalfunction(code)
                "4321" -> SystemMalfunctionSunroof(code)
                "4322" -> SystemMalfunctionFrontRoofRollerBlind(code)
                "4323" -> SystemMalfunctionRearRoofRollerBlind(code)
                "4325" -> InternalSystemError(code)
                "4334" -> InvalidNumberSunroof(code)
                "4335" -> FeatureNotAvailableSunroof(code)
                "4340" -> InvalidNumberFrontRoofRollerBlind(code)
                "4341" -> FeatureNotAvailableFrontRoofRollerBlind(code)
                "4342" -> InvalidNumberRearRoofRollerBlind(code)
                "4343" -> FeatureNotAvailableRearRoofRollerBlind(code)
                "4358" -> InvalidPositionSunroof(code)
                "4359" -> UnavailableUiHandlerSunroof(code)
                "4360" -> InvalidPositionFrontRoofRollerBlind(code)
                "4361" -> UnavailableUiHandlerFrontRoofRollerBlind(code)
                "4362" -> InvalidPositionRearRoofRollerBlind(code)
                "4363" -> UnavailableUiHandlerRearRoofRollerBlind(code)
                "6901" -> WindowRoofCommandFailed(code)
                "6902" -> WindowRoofCommandFailedIgnState(code)
                "6903" -> WindowRoofCommandWindowNotNormed(code)
                "6904" -> WindowRoofCommandServiceNotActive(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the move roof command version v1
sealed class SunroofMoveError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : SunroofMoveError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Failed due to afterrun active
    class AfterRunActive(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.AfterRunActive)

    // Failed due to afterrun active on front roof roller blind
    class AfterRunActiveFrontRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveFrontRoofRollerBlind)

    // Failed due to afterrun active on rear roof roller blind
    class AfterRunActiveRearRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearRoofRollerBlind)

    // Failed due to afterrun active on sunroof
    class AfterRunActiveSunroof(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveSunroof)

    // Failed due to anti-trap protection active
    class AntiTrapProtectionActive(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActive)

    // Failed due to anti-trap protection active on front roof roller blind
    class AntiTrapProtectionActiveFrontRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveFrontRoofRollerBlind)

    // Failed due to anti-trap protection active on rear roof roller blind
    class AntiTrapProtectionActiveRearRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearRoofRollerBlind)

    // Failed due to anti-trap protection active on sunroof
    class AntiTrapProtectionActiveSunroof(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveSunroof)

    // Failed due to manual cancellation inside vehicle
    class CancelledManuallyInVehicle(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicle)

    // Failed due to manual cancellation inside vehicle on front roof roller blind
    class CancelledManuallyInVehicleFrontRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleFrontRoofRollerBlind)

    // Failed due to position not reached within timeout on rear roller blind
    class CancelledManuallyInVehicleRearRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearRollerBlind)

    // Failed due to manual cancellation inside vehicle on rear roof roller blind
    class CancelledManuallyInVehicleRearRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearRoofRollerBlind)

    // Failed due to drive motor overheated
    class DriveMotorOverheated(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheated)

    // Failed due to drive motor overheated on front roof roller blind
    class DriveMotorOverheatedFrontRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedFrontRoofRollerBlind)

    // Failed due to drive motor overheated on rear roof roller blind
    class DriveMotorOverheatedRearRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearRoofRollerBlind)

    // Failed due to drive motor overheated on sunroof
    class DriveMotorOverheatedSunroof(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedSunroof)

    // Failed due to feature not available on front roof roller blind
    class FeatureNotAvailableFrontRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableFrontRoofRollerBlind)

    // Failed due to feature not available on rear roof roller blind
    class FeatureNotAvailableRearRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearRoofRollerBlind)

    // Failed due to feature not available on sunroof
    class FeatureNotAvailableSunroof(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableSunroof)

    // Failed due to front roof roller blind in motion
    class FrontRoofRollerBlindInMotion(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.FrontRoofRollerBlindInMotion)

    // Failed due to ignition is on
    class IgnitionOn(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.IgnitionOn)

    // Failed due to internal system error
    class InternalSystemError(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.InternalSystemError)

    // Failed due to invalid ignition state
    class InvalidIgnitionState(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionState)

    // Failed due to invalid ignition state on front roof roller blind
    class InvalidIgnitionStateFrontRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateFrontRoofRollerBlind)

    // Failed due to invalid ignition state on rear roof roller blind
    class InvalidIgnitionStateRearRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearRoofRollerBlind)

    // Failed due to invalid ignition state on sunroof
    class InvalidIgnitionStateSunroof(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateSunroof)

    // Failed due to invalid number on front roof roller blind
    class InvalidNumberFrontRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.InvalidNumberFrontRoofRollerBlind)

    // Failed due to invalid number on rear roof roller blind
    class InvalidNumberRearRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearRoofRollerBlind)

    // Failed due to invalid number on sunroof
    class InvalidNumberSunroof(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.InvalidNumberSunroof)

    // Failed due to invalid position on front roof roller blind
    class InvalidPositionFrontRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPositionFrontRoofRollerBlind)

    // Failed due to invalid position on rear roof roller blind
    class InvalidPositionRearRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearRoofRollerBlind)

    // Failed due to invalid position on sunroof
    class InvalidPositionSunroof(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPositionSunroof)

    // Failed due to invalid power status
    class InvalidPowerStatus(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatus)

    // Failed due to invalid power status on front roof roller blind
    class InvalidPowerStatusFrontRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusFrontRoofRollerBlind)

    // Failed due to invalid power status on rear roof roller blind
    class InvalidPowerStatusRearRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearRoofRollerBlind)

    // Failed due to invalid power status on sunroof
    class InvalidPowerStatusSunroof(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusSunroof)

    // Failed due to low battery level 1
    class LowBatteryLevel1(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel1)

    // Failed due to low battery level 2
    class LowBatteryLevel2(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel2)

    // Failed due to mounted roof box
    class MountedRoofBox(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.MountedRoofBox)

    // Failed due to multiple anti-trap protection activations
    class MultiAntiTrapProtections(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtections)

    // Failed due to multiple anti-trap protection activations on front roof roller blind
    class MultiAntiTrapProtectionsFrontRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsFrontRoofRollerBlind)

    // Failed due to multiple anti-trap protection activations on rear roof roller blind
    class MultiAntiTrapProtectionsRearRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearRoofRollerBlind)

    // Failed due to multiple anti-trap protection activations on sunroof
    class MultiAntiTrapProtectionsSunroof(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsSunroof)

    // Failed due to rear roof roller blind in motion
    class RearRoofRollerBlindInMotion(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.RearRoofRollerBlindInMotion)

    // Failed due to remote engine start is active
    class RemoteEngineStartIsActive(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.RemoteEngineStartIsActive)

    // Failed due to roof in motion
    class RoofInMotion(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.RoofInMotion)

    // Failed due to roof or roller blind in motion
    class RoofOrRollerBlindInMotion(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.RoofOrRollerBlindInMotion)

    // Service not authorized
    class ServiceNotAuthorized(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.ServiceNotAuthorized)

    // Failed due to system could not be normed
    class SystemCouldNotBeNormed(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormed)

    // Failed due to system could not be normed on front roof roller blind
    class SystemCouldNotBeNormedFrontRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedFrontRoofRollerBlind)

    // Failed due to system could not be normed on rear roof roller blind
    class SystemCouldNotBeNormedRearRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearRoofRollerBlind)

    // Failed due to system could not be normed on sunroof
    class SystemCouldNotBeNormedSunroof(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedSunroof)

    // Failed due to system malfunction
    class SystemMalfunction(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.SystemMalfunction)

    // Failed due to system malfunction on front roof roller blind
    class SystemMalfunctionFrontRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionFrontRoofRollerBlind)

    // Failed due to system malfunction on rear roof roller blind
    class SystemMalfunctionRearRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearRoofRollerBlind)

    // Failed due to system malfunction on sunroof
    class SystemMalfunctionSunroof(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionSunroof)

    // Failed due to system not normed
    class SystemNotNormed(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.SystemNotNormed)

    // Failed due to system not normed on front roof roller blind
    class SystemNotNormedFrontRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedFrontRoofRollerBlind)

    // Failed due to system not normed on rear roof roller blind
    class SystemNotNormedRearRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearRoofRollerBlind)

    // Failed due to system not normed on sunroof
    class SystemNotNormedSunroof(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedSunroof)

    // Failed due to UI handler not available on front roof roller blind
    class UnavailableUiHandlerFrontRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerFrontRoofRollerBlind)

    // Failed due to UI handler not available on rear roof roller blind
    class UnavailableUiHandlerRearRoofRollerBlind(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearRoofRollerBlind)

    // Failed due to UI handler not available on sunroof
    class UnavailableUiHandlerSunroof(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerSunroof)

    // Failed because vehicle is in motion
    class VehicleInMotion(rawErrorCode: String) : SunroofMoveError(rawErrorCode, InternalVehicleCommandError.VehicleInMotion)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): SunroofMoveError {
            return when (code) {
                "4200" -> ServiceNotAuthorized(code)
                "4201" -> RemoteEngineStartIsActive(code)
                "4202" -> IgnitionOn(code)
                "4203" -> LowBatteryLevel2(code)
                "4204" -> LowBatteryLevel1(code)
                "4205" -> AntiTrapProtectionActive(code)
                "4212" -> AntiTrapProtectionActiveSunroof(code)
                "4213" -> AntiTrapProtectionActiveFrontRoofRollerBlind(code)
                "4214" -> AntiTrapProtectionActiveRearRoofRollerBlind(code)
                "4216" -> MultiAntiTrapProtections(code)
                "4223" -> MultiAntiTrapProtectionsSunroof(code)
                "4224" -> MultiAntiTrapProtectionsFrontRoofRollerBlind(code)
                "4225" -> MultiAntiTrapProtectionsRearRoofRollerBlind(code)
                "4227" -> CancelledManuallyInVehicle(code)
                "4235" -> CancelledManuallyInVehicleFrontRoofRollerBlind(code)
                "4236" -> CancelledManuallyInVehicleRearRoofRollerBlind(code)
                "4237" -> CancelledManuallyInVehicleRearRollerBlind(code)
                "4238" -> RoofOrRollerBlindInMotion(code)
                "4239" -> RoofInMotion(code)
                "4240" -> FrontRoofRollerBlindInMotion(code)
                "4241" -> RearRoofRollerBlindInMotion(code)
                "4242" -> DriveMotorOverheated(code)
                "4249" -> DriveMotorOverheatedSunroof(code)
                "4250" -> DriveMotorOverheatedFrontRoofRollerBlind(code)
                "4251" -> DriveMotorOverheatedRearRoofRollerBlind(code)
                "4253" -> SystemNotNormed(code)
                "4260" -> SystemNotNormedSunroof(code)
                "4261" -> SystemNotNormedFrontRoofRollerBlind(code)
                "4262" -> SystemNotNormedRearRoofRollerBlind(code)
                "4264" -> MountedRoofBox(code)
                "4265" -> InvalidPowerStatus(code)
                "4272" -> InvalidPowerStatusSunroof(code)
                "4273" -> InvalidPowerStatusFrontRoofRollerBlind(code)
                "4274" -> InvalidPowerStatusRearRoofRollerBlind(code)
                "4276" -> AfterRunActive(code)
                "4283" -> AfterRunActiveSunroof(code)
                "4284" -> AfterRunActiveFrontRoofRollerBlind(code)
                "4285" -> AfterRunActiveRearRoofRollerBlind(code)
                "4287" -> InvalidIgnitionState(code)
                "4294" -> InvalidIgnitionStateSunroof(code)
                "4295" -> InvalidIgnitionStateFrontRoofRollerBlind(code)
                "4296" -> InvalidIgnitionStateRearRoofRollerBlind(code)
                "4298" -> VehicleInMotion(code)
                "4299" -> VehicleInMotion(code)
                "4300" -> VehicleInMotion(code)
                "4301" -> VehicleInMotion(code)
                "4303" -> SystemCouldNotBeNormed(code)
                "4310" -> SystemCouldNotBeNormedSunroof(code)
                "4311" -> SystemCouldNotBeNormedFrontRoofRollerBlind(code)
                "4312" -> SystemCouldNotBeNormedRearRoofRollerBlind(code)
                "4314" -> SystemMalfunction(code)
                "4321" -> SystemMalfunctionSunroof(code)
                "4322" -> SystemMalfunctionFrontRoofRollerBlind(code)
                "4323" -> SystemMalfunctionRearRoofRollerBlind(code)
                "4325" -> InternalSystemError(code)
                "4334" -> InvalidNumberSunroof(code)
                "4335" -> FeatureNotAvailableSunroof(code)
                "4340" -> InvalidNumberFrontRoofRollerBlind(code)
                "4341" -> FeatureNotAvailableFrontRoofRollerBlind(code)
                "4342" -> InvalidNumberRearRoofRollerBlind(code)
                "4343" -> FeatureNotAvailableRearRoofRollerBlind(code)
                "4358" -> InvalidPositionSunroof(code)
                "4359" -> UnavailableUiHandlerSunroof(code)
                "4360" -> InvalidPositionFrontRoofRollerBlind(code)
                "4361" -> UnavailableUiHandlerFrontRoofRollerBlind(code)
                "4362" -> InvalidPositionRearRoofRollerBlind(code)
                "4363" -> UnavailableUiHandlerRearRoofRollerBlind(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the open roof command version v1
sealed class SunroofOpenError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : SunroofOpenError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Failed due to afterrun active
    class AfterRunActive(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.AfterRunActive)

    // Failed due to afterrun active on front roof roller blind
    class AfterRunActiveFrontRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveFrontRoofRollerBlind)

    // Failed due to afterrun active on rear roof roller blind
    class AfterRunActiveRearRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearRoofRollerBlind)

    // Failed due to afterrun active on sunroof
    class AfterRunActiveSunroof(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveSunroof)

    // Failed due to anti-trap protection active
    class AntiTrapProtectionActive(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActive)

    // Failed due to anti-trap protection active on front roof roller blind
    class AntiTrapProtectionActiveFrontRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveFrontRoofRollerBlind)

    // Failed due to anti-trap protection active on rear roof roller blind
    class AntiTrapProtectionActiveRearRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearRoofRollerBlind)

    // Failed due to anti-trap protection active on sunroof
    class AntiTrapProtectionActiveSunroof(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveSunroof)

    // Failed due to manual cancellation inside vehicle
    class CancelledManuallyInVehicle(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicle)

    // Failed due to manual cancellation inside vehicle on front roof roller blind
    class CancelledManuallyInVehicleFrontRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleFrontRoofRollerBlind)

    // Failed due to position not reached within timeout on rear roller blind
    class CancelledManuallyInVehicleRearRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearRollerBlind)

    // Failed due to manual cancellation inside vehicle on rear roof roller blind
    class CancelledManuallyInVehicleRearRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearRoofRollerBlind)

    // Failed due to drive motor overheated
    class DriveMotorOverheated(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheated)

    // Failed due to drive motor overheated on front roof roller blind
    class DriveMotorOverheatedFrontRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedFrontRoofRollerBlind)

    // Failed due to drive motor overheated on rear roof roller blind
    class DriveMotorOverheatedRearRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearRoofRollerBlind)

    // Failed due to drive motor overheated on sunroof
    class DriveMotorOverheatedSunroof(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedSunroof)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Failed due to feature not available on front roof roller blind
    class FeatureNotAvailableFrontRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableFrontRoofRollerBlind)

    // Failed due to feature not available on rear roof roller blind
    class FeatureNotAvailableRearRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearRoofRollerBlind)

    // Failed due to feature not available on sunroof
    class FeatureNotAvailableSunroof(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableSunroof)

    // Failed due to front roof roller blind in motion
    class FrontRoofRollerBlindInMotion(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.FrontRoofRollerBlindInMotion)

    // Failed due to ignition is on
    class IgnitionOn(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.IgnitionOn)

    // Failed due to internal system error
    class InternalSystemError(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.InternalSystemError)

    // Failed due to invalid ignition state
    class InvalidIgnitionState(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionState)

    // Failed due to invalid ignition state on front roof roller blind
    class InvalidIgnitionStateFrontRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateFrontRoofRollerBlind)

    // Failed due to invalid ignition state on rear roof roller blind
    class InvalidIgnitionStateRearRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearRoofRollerBlind)

    // Failed due to invalid ignition state on sunroof
    class InvalidIgnitionStateSunroof(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateSunroof)

    // Failed due to invalid number on front roof roller blind
    class InvalidNumberFrontRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.InvalidNumberFrontRoofRollerBlind)

    // Failed due to invalid number on rear roof roller blind
    class InvalidNumberRearRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearRoofRollerBlind)

    // Failed due to invalid number on sunroof
    class InvalidNumberSunroof(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.InvalidNumberSunroof)

    // Failed due to invalid position on front roof roller blind
    class InvalidPositionFrontRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPositionFrontRoofRollerBlind)

    // Failed due to invalid position on rear roof roller blind
    class InvalidPositionRearRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearRoofRollerBlind)

    // Failed due to invalid position on sunroof
    class InvalidPositionSunroof(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPositionSunroof)

    // Failed due to invalid power status
    class InvalidPowerStatus(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatus)

    // Failed due to invalid power status on front roof roller blind
    class InvalidPowerStatusFrontRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusFrontRoofRollerBlind)

    // Failed due to invalid power status on rear roof roller blind
    class InvalidPowerStatusRearRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearRoofRollerBlind)

    // Failed due to invalid power status on sunroof
    class InvalidPowerStatusSunroof(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusSunroof)

    // Energy level in Battery is too low
    class LowBatteryLevel(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel)

    // Failed due to low battery level 1
    class LowBatteryLevel1(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel1)

    // Failed due to low battery level 2
    class LowBatteryLevel2(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel2)

    // Failed due to mounted roof box
    class MountedRoofBox(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.MountedRoofBox)

    // Failed due to multiple anti-trap protection activations
    class MultiAntiTrapProtections(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtections)

    // Failed due to multiple anti-trap protection activations on front roof roller blind
    class MultiAntiTrapProtectionsFrontRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsFrontRoofRollerBlind)

    // Failed due to multiple anti-trap protection activations on rear roof roller blind
    class MultiAntiTrapProtectionsRearRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearRoofRollerBlind)

    // Failed due to multiple anti-trap protection activations on sunroof
    class MultiAntiTrapProtectionsSunroof(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsSunroof)

    // Failed due to rear roof roller blind in motion
    class RearRoofRollerBlindInMotion(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.RearRoofRollerBlindInMotion)

    // Failed due to remote engine start is active
    class RemoteEngineStartIsActive(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.RemoteEngineStartIsActive)

    // Failed due to roof in motion
    class RoofInMotion(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.RoofInMotion)

    // Failed due to roof or roller blind in motion
    class RoofOrRollerBlindInMotion(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.RoofOrRollerBlindInMotion)

    // Service not authorized
    class ServiceNotAuthorized(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.ServiceNotAuthorized)

    // Failed due to system could not be normed
    class SystemCouldNotBeNormed(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormed)

    // Failed due to system could not be normed on front roof roller blind
    class SystemCouldNotBeNormedFrontRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedFrontRoofRollerBlind)

    // Failed due to system could not be normed on rear roof roller blind
    class SystemCouldNotBeNormedRearRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearRoofRollerBlind)

    // Failed due to system could not be normed on sunroof
    class SystemCouldNotBeNormedSunroof(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedSunroof)

    // Failed due to system malfunction
    class SystemMalfunction(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.SystemMalfunction)

    // Failed due to system malfunction on front roof roller blind
    class SystemMalfunctionFrontRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionFrontRoofRollerBlind)

    // Failed due to system malfunction on rear roof roller blind
    class SystemMalfunctionRearRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearRoofRollerBlind)

    // Failed due to system malfunction on sunroof
    class SystemMalfunctionSunroof(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionSunroof)

    // Failed due to system not normed
    class SystemNotNormed(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.SystemNotNormed)

    // Failed due to system not normed on front roof roller blind
    class SystemNotNormedFrontRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedFrontRoofRollerBlind)

    // Failed due to system not normed on rear roof roller blind
    class SystemNotNormedRearRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearRoofRollerBlind)

    // Failed due to system not normed on sunroof
    class SystemNotNormedSunroof(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedSunroof)

    // Failed due to UI handler not available on front roof roller blind
    class UnavailableUiHandlerFrontRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerFrontRoofRollerBlind)

    // Failed due to UI handler not available on rear roof roller blind
    class UnavailableUiHandlerRearRoofRollerBlind(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearRoofRollerBlind)

    // Failed due to UI handler not available on sunroof
    class UnavailableUiHandlerSunroof(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerSunroof)

    // Failed because vehicle is in motion
    class VehicleInMotion(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.VehicleInMotion)

    // Remote window/roof command failed
    class WindowRoofCommandFailed(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandFailed)

    // Remote window/roof command failed (vehicle state in IGN)
    class WindowRoofCommandFailedIgnState(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandFailedIgnState)

    // Remote window/roof command failed (service not activated in HERMES)
    class WindowRoofCommandServiceNotActive(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandServiceNotActive)

    // Remote window/roof command failed (window not normed)
    class WindowRoofCommandWindowNotNormed(rawErrorCode: String) : SunroofOpenError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandWindowNotNormed)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): SunroofOpenError {
            return when (code) {
                "21" -> LowBatteryLevel(code)
                "42" -> FastpathTimeout(code)
                "4200" -> ServiceNotAuthorized(code)
                "4201" -> RemoteEngineStartIsActive(code)
                "4202" -> IgnitionOn(code)
                "4203" -> LowBatteryLevel2(code)
                "4204" -> LowBatteryLevel1(code)
                "4205" -> AntiTrapProtectionActive(code)
                "4212" -> AntiTrapProtectionActiveSunroof(code)
                "4213" -> AntiTrapProtectionActiveFrontRoofRollerBlind(code)
                "4214" -> AntiTrapProtectionActiveRearRoofRollerBlind(code)
                "4216" -> MultiAntiTrapProtections(code)
                "4223" -> MultiAntiTrapProtectionsSunroof(code)
                "4224" -> MultiAntiTrapProtectionsFrontRoofRollerBlind(code)
                "4225" -> MultiAntiTrapProtectionsRearRoofRollerBlind(code)
                "4227" -> CancelledManuallyInVehicle(code)
                "4235" -> CancelledManuallyInVehicleFrontRoofRollerBlind(code)
                "4236" -> CancelledManuallyInVehicleRearRoofRollerBlind(code)
                "4237" -> CancelledManuallyInVehicleRearRollerBlind(code)
                "4238" -> RoofOrRollerBlindInMotion(code)
                "4239" -> RoofInMotion(code)
                "4240" -> FrontRoofRollerBlindInMotion(code)
                "4241" -> RearRoofRollerBlindInMotion(code)
                "4242" -> DriveMotorOverheated(code)
                "4249" -> DriveMotorOverheatedSunroof(code)
                "4250" -> DriveMotorOverheatedFrontRoofRollerBlind(code)
                "4251" -> DriveMotorOverheatedRearRoofRollerBlind(code)
                "4253" -> SystemNotNormed(code)
                "4260" -> SystemNotNormedSunroof(code)
                "4261" -> SystemNotNormedFrontRoofRollerBlind(code)
                "4262" -> SystemNotNormedRearRoofRollerBlind(code)
                "4264" -> MountedRoofBox(code)
                "4265" -> InvalidPowerStatus(code)
                "4272" -> InvalidPowerStatusSunroof(code)
                "4273" -> InvalidPowerStatusFrontRoofRollerBlind(code)
                "4274" -> InvalidPowerStatusRearRoofRollerBlind(code)
                "4276" -> AfterRunActive(code)
                "4283" -> AfterRunActiveSunroof(code)
                "4284" -> AfterRunActiveFrontRoofRollerBlind(code)
                "4285" -> AfterRunActiveRearRoofRollerBlind(code)
                "4287" -> InvalidIgnitionState(code)
                "4294" -> InvalidIgnitionStateSunroof(code)
                "4295" -> InvalidIgnitionStateFrontRoofRollerBlind(code)
                "4296" -> InvalidIgnitionStateRearRoofRollerBlind(code)
                "4298" -> VehicleInMotion(code)
                "4299" -> VehicleInMotion(code)
                "4300" -> VehicleInMotion(code)
                "4301" -> VehicleInMotion(code)
                "4303" -> SystemCouldNotBeNormed(code)
                "4310" -> SystemCouldNotBeNormedSunroof(code)
                "4311" -> SystemCouldNotBeNormedFrontRoofRollerBlind(code)
                "4312" -> SystemCouldNotBeNormedRearRoofRollerBlind(code)
                "4314" -> SystemMalfunction(code)
                "4321" -> SystemMalfunctionSunroof(code)
                "4322" -> SystemMalfunctionFrontRoofRollerBlind(code)
                "4323" -> SystemMalfunctionRearRoofRollerBlind(code)
                "4325" -> InternalSystemError(code)
                "4334" -> InvalidNumberSunroof(code)
                "4335" -> FeatureNotAvailableSunroof(code)
                "4340" -> InvalidNumberFrontRoofRollerBlind(code)
                "4341" -> FeatureNotAvailableFrontRoofRollerBlind(code)
                "4342" -> InvalidNumberRearRoofRollerBlind(code)
                "4343" -> FeatureNotAvailableRearRoofRollerBlind(code)
                "4358" -> InvalidPositionSunroof(code)
                "4359" -> UnavailableUiHandlerSunroof(code)
                "4360" -> InvalidPositionFrontRoofRollerBlind(code)
                "4361" -> UnavailableUiHandlerFrontRoofRollerBlind(code)
                "4362" -> InvalidPositionRearRoofRollerBlind(code)
                "4363" -> UnavailableUiHandlerRearRoofRollerBlind(code)
                "6901" -> WindowRoofCommandFailed(code)
                "6902" -> WindowRoofCommandFailedIgnState(code)
                "6903" -> WindowRoofCommandWindowNotNormed(code)
                "6904" -> WindowRoofCommandServiceNotActive(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the start sigpos command version v1
sealed class SignalPositionError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : SignalPositionError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Failed due to error in horn control system
    class ErrorInHornControlSystem(rawErrorCode: String) : SignalPositionError(rawErrorCode, InternalVehicleCommandError.ErrorInHornControlSystem)

    // Failed due to error in light control system
    class ErrorInLightControlSystem(rawErrorCode: String) : SignalPositionError(rawErrorCode, InternalVehicleCommandError.ErrorInLightControlSystem)

    // Failed due to error in light or horn control system
    class ErrorInLightOrHornControlSystem(rawErrorCode: String) : SignalPositionError(rawErrorCode, InternalVehicleCommandError.ErrorInLightOrHornControlSystem)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : SignalPositionError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Failed due to invalid horn repeat
    class InvalidHornRepeat(rawErrorCode: String) : SignalPositionError(rawErrorCode, InternalVehicleCommandError.InvalidHornRepeat)

    // Energy level in Battery is too low
    class LowBatteryLevel(rawErrorCode: String) : SignalPositionError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel)

    // Failed due to low battery level 1
    class LowBatteryLevel1(rawErrorCode: String) : SignalPositionError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel1)

    // Failed due to low battery level 2
    class LowBatteryLevel2(rawErrorCode: String) : SignalPositionError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel2)

    // RVF (sigpos) failed
    class RvfFailed(rawErrorCode: String) : SignalPositionError(rawErrorCode, InternalVehicleCommandError.RvfFailed)

    // RVF (sigpos) failed due to not authorized
    class RvfFailedNotAuthorized(rawErrorCode: String) : SignalPositionError(rawErrorCode, InternalVehicleCommandError.RvfFailedNotAuthorized)

    // RVF (sigpos) failed due to ignition is on
    class RvfFailedVehicleStageInIgn(rawErrorCode: String) : SignalPositionError(rawErrorCode, InternalVehicleCommandError.RvfFailedVehicleStageInIgn)

    // Failed due to too many requests to horn control system
    class TooManyRequestsToHornControlSystem(rawErrorCode: String) : SignalPositionError(rawErrorCode, InternalVehicleCommandError.TooManyRequestsToHornControlSystem)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): SignalPositionError {
            return when (code) {
                "21" -> LowBatteryLevel(code)
                "42" -> FastpathTimeout(code)
                "6401" -> RvfFailed(code)
                "6402" -> RvfFailedVehicleStageInIgn(code)
                "6403" -> RvfFailedNotAuthorized(code)
                "6404" -> LowBatteryLevel2(code)
                "6405" -> LowBatteryLevel1(code)
                "6406" -> InvalidHornRepeat(code)
                "6407" -> ErrorInLightOrHornControlSystem(code)
                "6408" -> ErrorInHornControlSystem(code)
                "6409" -> ErrorInLightControlSystem(code)
                "6410" -> TooManyRequestsToHornControlSystem(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the start speedalert command version v2
sealed class SpeedAlertStartError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : SpeedAlertStartError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : SpeedAlertStartError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Speed alert not authorized
    class SpeedAlertNotAuthorized(rawErrorCode: String) : SpeedAlertStartError(rawErrorCode, InternalVehicleCommandError.SpeedAlertNotAuthorized)

    // Unexpected respons
    class UnexpectedResponse(rawErrorCode: String) : SpeedAlertStartError(rawErrorCode, InternalVehicleCommandError.UnexpectedResponse)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): SpeedAlertStartError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "6101" -> UnexpectedResponse(code)
                "6102" -> SpeedAlertNotAuthorized(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the stop speedalert command version v2
sealed class SpeedAlertStopError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : SpeedAlertStopError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Speed alert not authorized
    class SpeedAlertNotAuthorized(rawErrorCode: String) : SpeedAlertStopError(rawErrorCode, InternalVehicleCommandError.SpeedAlertNotAuthorized)

    // Unexpected respons
    class UnexpectedResponse(rawErrorCode: String) : SpeedAlertStopError(rawErrorCode, InternalVehicleCommandError.UnexpectedResponse)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): SpeedAlertStopError {
            return when (code) {
                "6101" -> UnexpectedResponse(code)
                "6102" -> SpeedAlertNotAuthorized(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the /teenagedrivingmode/v1/activate  command version 
sealed class TeenageDrivingModeActivateError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : TeenageDrivingModeActivateError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): TeenageDrivingModeActivateError {
            return GenericError(GenericCommandError.fromErrorCode(code, attributes))
        }
    }
}

// Error codes for the /teenagedrivingmode/v1/deactivate  command version 
sealed class TeenageDrivingModeDeactivateError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : TeenageDrivingModeDeactivateError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): TeenageDrivingModeDeactivateError {
            return GenericError(GenericCommandError.fromErrorCode(code, attributes))
        }
    }
}

// Error codes for the configure temperature command version v1
sealed class TemperatureConfigureError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : TemperatureConfigureError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Failed
    class Failed(rawErrorCode: String) : TemperatureConfigureError(rawErrorCode, InternalVehicleCommandError.Failed)

    // failedCANCom
    class FailedCanCom(rawErrorCode: String) : TemperatureConfigureError(rawErrorCode, InternalVehicleCommandError.FailedCanCom)

    // failedIgnOn
    class FailedIgnOn(rawErrorCode: String) : TemperatureConfigureError(rawErrorCode, InternalVehicleCommandError.FailedIgnOn)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : TemperatureConfigureError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Request is not authorized
    class NotAuthorized(rawErrorCode: String) : TemperatureConfigureError(rawErrorCode, InternalVehicleCommandError.NotAuthorized)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): TemperatureConfigureError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "6501" -> Failed(code)
                "6502" -> FailedCanCom(code)
                "6503" -> FailedIgnOn(code)
                "6504" -> NotAuthorized(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the confirmdamagedetection theftalarm command version v3
sealed class TheftAlarmConfirmDamageDetectionError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : TheftAlarmConfirmDamageDetectionError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : TheftAlarmConfirmDamageDetectionError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Remote VTA failed
    class RemoteVtaFailed(rawErrorCode: String) : TheftAlarmConfirmDamageDetectionError(rawErrorCode, InternalVehicleCommandError.RemoteVtaFailed)

    // Remote VTA ignition not locked
    class RemoteVtaIgnitionLocked(rawErrorCode: String) : TheftAlarmConfirmDamageDetectionError(rawErrorCode, InternalVehicleCommandError.RemoteVtaIgnitionLocked)

    // Remote VTA VVR not allowed
    class RemoteVtaNotAllowed(rawErrorCode: String) : TheftAlarmConfirmDamageDetectionError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAllowed)

    // Remote VTA service not authorized
    class RemoteVtaNotAuthorized(rawErrorCode: String) : TheftAlarmConfirmDamageDetectionError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAuthorized)

    // Remote VTA VVR value not set
    class RemoteVtaValueNotSet(rawErrorCode: String) : TheftAlarmConfirmDamageDetectionError(rawErrorCode, InternalVehicleCommandError.RemoteVtaValueNotSet)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): TheftAlarmConfirmDamageDetectionError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "5301" -> RemoteVtaFailed(code)
                "5302" -> RemoteVtaNotAuthorized(code)
                "5303" -> RemoteVtaIgnitionLocked(code)
                "5304" -> RemoteVtaValueNotSet(code)
                "5305" -> RemoteVtaNotAllowed(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the deselectdamagedetection theftalarm command version v3
sealed class TheftAlarmDeselectDamageDetectionError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : TheftAlarmDeselectDamageDetectionError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : TheftAlarmDeselectDamageDetectionError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Remote VTA failed
    class RemoteVtaFailed(rawErrorCode: String) : TheftAlarmDeselectDamageDetectionError(rawErrorCode, InternalVehicleCommandError.RemoteVtaFailed)

    // Remote VTA ignition not locked
    class RemoteVtaIgnitionLocked(rawErrorCode: String) : TheftAlarmDeselectDamageDetectionError(rawErrorCode, InternalVehicleCommandError.RemoteVtaIgnitionLocked)

    // Remote VTA VVR not allowed
    class RemoteVtaNotAllowed(rawErrorCode: String) : TheftAlarmDeselectDamageDetectionError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAllowed)

    // Remote VTA service not authorized
    class RemoteVtaNotAuthorized(rawErrorCode: String) : TheftAlarmDeselectDamageDetectionError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAuthorized)

    // Remote VTA VVR value not set
    class RemoteVtaValueNotSet(rawErrorCode: String) : TheftAlarmDeselectDamageDetectionError(rawErrorCode, InternalVehicleCommandError.RemoteVtaValueNotSet)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): TheftAlarmDeselectDamageDetectionError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "5301" -> RemoteVtaFailed(code)
                "5302" -> RemoteVtaNotAuthorized(code)
                "5303" -> RemoteVtaIgnitionLocked(code)
                "5304" -> RemoteVtaValueNotSet(code)
                "5305" -> RemoteVtaNotAllowed(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the deselectinterior theftalarm command version v3
sealed class TheftAlarmDeselectInteriorError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : TheftAlarmDeselectInteriorError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : TheftAlarmDeselectInteriorError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Remote VTA failed
    class RemoteVtaFailed(rawErrorCode: String) : TheftAlarmDeselectInteriorError(rawErrorCode, InternalVehicleCommandError.RemoteVtaFailed)

    // Remote VTA ignition not locked
    class RemoteVtaIgnitionLocked(rawErrorCode: String) : TheftAlarmDeselectInteriorError(rawErrorCode, InternalVehicleCommandError.RemoteVtaIgnitionLocked)

    // Remote VTA VVR not allowed
    class RemoteVtaNotAllowed(rawErrorCode: String) : TheftAlarmDeselectInteriorError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAllowed)

    // Remote VTA service not authorized
    class RemoteVtaNotAuthorized(rawErrorCode: String) : TheftAlarmDeselectInteriorError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAuthorized)

    // Remote VTA VVR value not set
    class RemoteVtaValueNotSet(rawErrorCode: String) : TheftAlarmDeselectInteriorError(rawErrorCode, InternalVehicleCommandError.RemoteVtaValueNotSet)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): TheftAlarmDeselectInteriorError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "5301" -> RemoteVtaFailed(code)
                "5302" -> RemoteVtaNotAuthorized(code)
                "5303" -> RemoteVtaIgnitionLocked(code)
                "5304" -> RemoteVtaValueNotSet(code)
                "5305" -> RemoteVtaNotAllowed(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the deselecttow theftalarm command version v3
sealed class TheftAlarmDeselectTowError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : TheftAlarmDeselectTowError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : TheftAlarmDeselectTowError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Remote VTA failed
    class RemoteVtaFailed(rawErrorCode: String) : TheftAlarmDeselectTowError(rawErrorCode, InternalVehicleCommandError.RemoteVtaFailed)

    // Remote VTA ignition not locked
    class RemoteVtaIgnitionLocked(rawErrorCode: String) : TheftAlarmDeselectTowError(rawErrorCode, InternalVehicleCommandError.RemoteVtaIgnitionLocked)

    // Remote VTA VVR not allowed
    class RemoteVtaNotAllowed(rawErrorCode: String) : TheftAlarmDeselectTowError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAllowed)

    // Remote VTA service not authorized
    class RemoteVtaNotAuthorized(rawErrorCode: String) : TheftAlarmDeselectTowError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAuthorized)

    // Remote VTA VVR value not set
    class RemoteVtaValueNotSet(rawErrorCode: String) : TheftAlarmDeselectTowError(rawErrorCode, InternalVehicleCommandError.RemoteVtaValueNotSet)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): TheftAlarmDeselectTowError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "5301" -> RemoteVtaFailed(code)
                "5302" -> RemoteVtaNotAuthorized(code)
                "5303" -> RemoteVtaIgnitionLocked(code)
                "5304" -> RemoteVtaValueNotSet(code)
                "5305" -> RemoteVtaNotAllowed(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the selectdamagedetection theftalarm command version v3
sealed class TheftAlarmSelectDamageDetectionError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : TheftAlarmSelectDamageDetectionError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : TheftAlarmSelectDamageDetectionError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Remote VTA failed
    class RemoteVtaFailed(rawErrorCode: String) : TheftAlarmSelectDamageDetectionError(rawErrorCode, InternalVehicleCommandError.RemoteVtaFailed)

    // Remote VTA ignition not locked
    class RemoteVtaIgnitionLocked(rawErrorCode: String) : TheftAlarmSelectDamageDetectionError(rawErrorCode, InternalVehicleCommandError.RemoteVtaIgnitionLocked)

    // Remote VTA VVR not allowed
    class RemoteVtaNotAllowed(rawErrorCode: String) : TheftAlarmSelectDamageDetectionError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAllowed)

    // Remote VTA service not authorized
    class RemoteVtaNotAuthorized(rawErrorCode: String) : TheftAlarmSelectDamageDetectionError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAuthorized)

    // Remote VTA VVR value not set
    class RemoteVtaValueNotSet(rawErrorCode: String) : TheftAlarmSelectDamageDetectionError(rawErrorCode, InternalVehicleCommandError.RemoteVtaValueNotSet)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): TheftAlarmSelectDamageDetectionError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "5301" -> RemoteVtaFailed(code)
                "5302" -> RemoteVtaNotAuthorized(code)
                "5303" -> RemoteVtaIgnitionLocked(code)
                "5304" -> RemoteVtaValueNotSet(code)
                "5305" -> RemoteVtaNotAllowed(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the selectinterior theftalarm command version v3
sealed class TheftAlarmSelectInteriorError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : TheftAlarmSelectInteriorError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : TheftAlarmSelectInteriorError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Remote VTA failed
    class RemoteVtaFailed(rawErrorCode: String) : TheftAlarmSelectInteriorError(rawErrorCode, InternalVehicleCommandError.RemoteVtaFailed)

    // Remote VTA ignition not locked
    class RemoteVtaIgnitionLocked(rawErrorCode: String) : TheftAlarmSelectInteriorError(rawErrorCode, InternalVehicleCommandError.RemoteVtaIgnitionLocked)

    // Remote VTA VVR not allowed
    class RemoteVtaNotAllowed(rawErrorCode: String) : TheftAlarmSelectInteriorError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAllowed)

    // Remote VTA service not authorized
    class RemoteVtaNotAuthorized(rawErrorCode: String) : TheftAlarmSelectInteriorError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAuthorized)

    // Remote VTA VVR value not set
    class RemoteVtaValueNotSet(rawErrorCode: String) : TheftAlarmSelectInteriorError(rawErrorCode, InternalVehicleCommandError.RemoteVtaValueNotSet)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): TheftAlarmSelectInteriorError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "5301" -> RemoteVtaFailed(code)
                "5302" -> RemoteVtaNotAuthorized(code)
                "5303" -> RemoteVtaIgnitionLocked(code)
                "5304" -> RemoteVtaValueNotSet(code)
                "5305" -> RemoteVtaNotAllowed(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the selecttow theftalarm command version v3
sealed class TheftAlarmSelectTowError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : TheftAlarmSelectTowError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : TheftAlarmSelectTowError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Remote VTA failed
    class RemoteVtaFailed(rawErrorCode: String) : TheftAlarmSelectTowError(rawErrorCode, InternalVehicleCommandError.RemoteVtaFailed)

    // Remote VTA ignition not locked
    class RemoteVtaIgnitionLocked(rawErrorCode: String) : TheftAlarmSelectTowError(rawErrorCode, InternalVehicleCommandError.RemoteVtaIgnitionLocked)

    // Remote VTA VVR not allowed
    class RemoteVtaNotAllowed(rawErrorCode: String) : TheftAlarmSelectTowError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAllowed)

    // Remote VTA service not authorized
    class RemoteVtaNotAuthorized(rawErrorCode: String) : TheftAlarmSelectTowError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAuthorized)

    // Remote VTA VVR value not set
    class RemoteVtaValueNotSet(rawErrorCode: String) : TheftAlarmSelectTowError(rawErrorCode, InternalVehicleCommandError.RemoteVtaValueNotSet)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): TheftAlarmSelectTowError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "5301" -> RemoteVtaFailed(code)
                "5302" -> RemoteVtaNotAuthorized(code)
                "5303" -> RemoteVtaIgnitionLocked(code)
                "5304" -> RemoteVtaValueNotSet(code)
                "5305" -> RemoteVtaNotAllowed(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the start theftalarm command version v3
sealed class TheftAlarmStartError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : TheftAlarmStartError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : TheftAlarmStartError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Remote VTA failed
    class RemoteVtaFailed(rawErrorCode: String) : TheftAlarmStartError(rawErrorCode, InternalVehicleCommandError.RemoteVtaFailed)

    // Remote VTA ignition not locked
    class RemoteVtaIgnitionLocked(rawErrorCode: String) : TheftAlarmStartError(rawErrorCode, InternalVehicleCommandError.RemoteVtaIgnitionLocked)

    // Remote VTA VVR not allowed
    class RemoteVtaNotAllowed(rawErrorCode: String) : TheftAlarmStartError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAllowed)

    // Remote VTA service not authorized
    class RemoteVtaNotAuthorized(rawErrorCode: String) : TheftAlarmStartError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAuthorized)

    // Remote VTA VVR value not set
    class RemoteVtaValueNotSet(rawErrorCode: String) : TheftAlarmStartError(rawErrorCode, InternalVehicleCommandError.RemoteVtaValueNotSet)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): TheftAlarmStartError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "5301" -> RemoteVtaFailed(code)
                "5302" -> RemoteVtaNotAuthorized(code)
                "5303" -> RemoteVtaIgnitionLocked(code)
                "5304" -> RemoteVtaValueNotSet(code)
                "5305" -> RemoteVtaNotAllowed(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the stop theftalarm command version v3
sealed class TheftAlarmStopError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : TheftAlarmStopError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : TheftAlarmStopError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Remote VTA failed
    class RemoteVtaFailed(rawErrorCode: String) : TheftAlarmStopError(rawErrorCode, InternalVehicleCommandError.RemoteVtaFailed)

    // Remote VTA ignition not locked
    class RemoteVtaIgnitionLocked(rawErrorCode: String) : TheftAlarmStopError(rawErrorCode, InternalVehicleCommandError.RemoteVtaIgnitionLocked)

    // Remote VTA VVR not allowed
    class RemoteVtaNotAllowed(rawErrorCode: String) : TheftAlarmStopError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAllowed)

    // Remote VTA service not authorized
    class RemoteVtaNotAuthorized(rawErrorCode: String) : TheftAlarmStopError(rawErrorCode, InternalVehicleCommandError.RemoteVtaNotAuthorized)

    // Remote VTA VVR value not set
    class RemoteVtaValueNotSet(rawErrorCode: String) : TheftAlarmStopError(rawErrorCode, InternalVehicleCommandError.RemoteVtaValueNotSet)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): TheftAlarmStopError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "5301" -> RemoteVtaFailed(code)
                "5302" -> RemoteVtaNotAuthorized(code)
                "5303" -> RemoteVtaIgnitionLocked(code)
                "5304" -> RemoteVtaValueNotSet(code)
                "5305" -> RemoteVtaNotAllowed(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the /valetdrivingmode/v1/activate  command version 
sealed class ValetDrivingModeActivateError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : ValetDrivingModeActivateError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): ValetDrivingModeActivateError {
            return GenericError(GenericCommandError.fromErrorCode(code, attributes))
        }
    }
}

// Error codes for the /valetdrivingmode/v1/deactivate  command version 
sealed class ValetDrivingModeDeactivateError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : ValetDrivingModeDeactivateError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): ValetDrivingModeDeactivateError {
            return GenericError(GenericCommandError.fromErrorCode(code, attributes))
        }
    }
}

// Error codes for the configure weekprofile command version v1
sealed class WeekProfileConfigureError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : WeekProfileConfigureError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // ZEV WeekDeptSet processing failed as incorrect AppId passed
    class AppIdIncorrect(rawErrorCode: String) : WeekProfileConfigureError(rawErrorCode, InternalVehicleCommandError.AppIdIncorrect)

    // ZEV WeekDeptSet processing failed as AsppId is not present
    class AppIdMissing(rawErrorCode: String) : WeekProfileConfigureError(rawErrorCode, InternalVehicleCommandError.AppIdMissing)

    // ZEV WeekDeptSet processing failed as AppId not matching
    class AppIdNotMatching(rawErrorCode: String) : WeekProfileConfigureError(rawErrorCode, InternalVehicleCommandError.AppIdNotMatching)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : WeekProfileConfigureError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // ZEV WeekDeptSet not authorized
    class ZevWeekDeptSetNotAuthorized(rawErrorCode: String) : WeekProfileConfigureError(rawErrorCode, InternalVehicleCommandError.ZevWeekDeptSetNotAuthorized)

    // ZEV WeekDeptSet not possible since either INSTANT CHARGING is already activated or INSTANT CHARGING ACP command is currently in progress
    class ZevWeekDeptSetProcessingDeptSetNotPossible(rawErrorCode: String) : WeekProfileConfigureError(rawErrorCode, InternalVehicleCommandError.ZevWeekDeptSetProcessingDeptSetNotPossible)

    // ZEV WeekDeptSet processing failed
    class ZevWeekDeptSetProcessingFailed(rawErrorCode: String) : WeekProfileConfigureError(rawErrorCode, InternalVehicleCommandError.ZevWeekDeptSetProcessingFailed)

    // ZEV WeekDeptSet processing overwritten
    class ZevWeekDeptSetProcessingOverwritten(rawErrorCode: String) : WeekProfileConfigureError(rawErrorCode, InternalVehicleCommandError.ZevWeekDeptSetProcessingOverwritten)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): WeekProfileConfigureError {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "6601" -> ZevWeekDeptSetProcessingFailed(code)
                "6602" -> ZevWeekDeptSetNotAuthorized(code)
                "6603" -> ZevWeekDeptSetProcessingOverwritten(code)
                "6604" -> ZevWeekDeptSetProcessingDeptSetNotPossible(code)
                "6611" -> AppIdMissing(code)
                "6612" -> AppIdIncorrect(code)
                "6613" -> AppIdNotMatching(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the configure weekprofile command version v2
sealed class WeekProfileConfigureV2Error(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : WeekProfileConfigureV2Error(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // ZEV WeekDeptSet processing failed as incorrect AppId passed
    class AppIdIncorrect(rawErrorCode: String) : WeekProfileConfigureV2Error(rawErrorCode, InternalVehicleCommandError.AppIdIncorrect)

    // ZEV WeekDeptSet processing failed as AsppId is not present
    class AppIdMissing(rawErrorCode: String) : WeekProfileConfigureV2Error(rawErrorCode, InternalVehicleCommandError.AppIdMissing)

    // ZEV WeekDeptSet processing failed as AppId not matching
    class AppIdNotMatching(rawErrorCode: String) : WeekProfileConfigureV2Error(rawErrorCode, InternalVehicleCommandError.AppIdNotMatching)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : WeekProfileConfigureV2Error(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // ZEV WeekDeptSet not authorized
    class ZevWeekDeptSetNotAuthorized(rawErrorCode: String) : WeekProfileConfigureV2Error(rawErrorCode, InternalVehicleCommandError.ZevWeekDeptSetNotAuthorized)

    // ZEV WeekDeptSet not possible since either INSTANT CHARGING is already activated or INSTANT CHARGING ACP command is currently in progress
    class ZevWeekDeptSetProcessingDeptSetNotPossible(rawErrorCode: String) : WeekProfileConfigureV2Error(rawErrorCode, InternalVehicleCommandError.ZevWeekDeptSetProcessingDeptSetNotPossible)

    // ZEV WeekDeptSet processing failed
    class ZevWeekDeptSetProcessingFailed(rawErrorCode: String) : WeekProfileConfigureV2Error(rawErrorCode, InternalVehicleCommandError.ZevWeekDeptSetProcessingFailed)

    // ZEV WeekDeptSet processing overwritten
    class ZevWeekDeptSetProcessingOverwritten(rawErrorCode: String) : WeekProfileConfigureV2Error(rawErrorCode, InternalVehicleCommandError.ZevWeekDeptSetProcessingOverwritten)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): WeekProfileConfigureV2Error {
            return when (code) {
                "42" -> FastpathTimeout(code)
                "6601" -> ZevWeekDeptSetProcessingFailed(code)
                "6602" -> ZevWeekDeptSetNotAuthorized(code)
                "6603" -> ZevWeekDeptSetProcessingOverwritten(code)
                "6604" -> ZevWeekDeptSetProcessingDeptSetNotPossible(code)
                "6611" -> AppIdMissing(code)
                "6612" -> AppIdIncorrect(code)
                "6613" -> AppIdNotMatching(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the close windows command version v1
sealed class WindowsCloseError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : WindowsCloseError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Failed due to afterrun active
    class AfterRunActive(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.AfterRunActive)

    // Failed due to afterrun active on front left window
    class AfterRunActiveFrontLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveFrontLeftWindow)

    // Failed due to afterrun active on front right window
    class AfterRunActiveFrontRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveFrontRightWindow)

    // Failed due to afterrun active on rear left roller blind
    class AfterRunActiveRearLeftRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearLeftRollerBlind)

    // Failed due to afterrun active on rear left window
    class AfterRunActiveRearLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearLeftWindow)

    // Failed due to afterrun active on rear right roller blind
    class AfterRunActiveRearRightRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearRightRollerBlind)

    // Failed due to afterrun active on rear right window
    class AfterRunActiveRearRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearRightWindow)

    // Failed due to anti-trap protection active
    class AntiTrapProtectionActive(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActive)

    // Failed due to anti-trap protection active on front left window
    class AntiTrapProtectionActiveFrontLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveFrontLeftWindow)

    // Failed due to anti-trap protection active on front right window
    class AntiTrapProtectionActiveFrontRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveFrontRightWindow)

    // Failed due to anti-trap protection active on rear left roller blind
    class AntiTrapProtectionActiveRearLeftRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearLeftRollerBlind)

    // Failed due to anti-trap protection active on rear left window
    class AntiTrapProtectionActiveRearLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearLeftWindow)

    // Failed due to anti-trap protection active on rear right roller blind
    class AntiTrapProtectionActiveRearRightRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearRightRollerBlind)

    // Failed due to anti-trap protection active on rear right window
    class AntiTrapProtectionActiveRearRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearRightWindow)

    // Failed due to manual cancellation inside vehicle
    class CancelledManuallyInVehicle(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicle)

    // Failed due to manual cancellation inside vehicle on front left window
    class CancelledManuallyInVehicleFrontLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleFrontLeftWindow)

    // Failed due to manual cancellation inside vehicle on front right window
    class CancelledManuallyInVehicleFrontRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleFrontRightWindow)

    // Failed due to manual cancellation inside vehicle on rear left roller blind
    class CancelledManuallyInVehicleRearLeftRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearLeftRollerBlind)

    // Failed due to manual cancellation inside vehicle on rear left window
    class CancelledManuallyInVehicleRearLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearLeftWindow)

    // Failed due to manual cancellation inside vehicle on rear right roller blind
    class CancelledManuallyInVehicleRearRightRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearRightRollerBlind)

    // Failed due to manual cancellation inside vehicle on rear right window
    class CancelledManuallyInVehicleRearRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearRightWindow)

    // Failed due to manual cancellation inside vehicle on sunroof
    class CancelledManuallyInVehicleSunroof(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleSunroof)

    // Failed due to drive motor overheated
    class DriveMotorOverheated(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheated)

    // Failed due to drive motor overheated on front left window
    class DriveMotorOverheatedFrontLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedFrontLeftWindow)

    // Failed due to drive motor overheated on front right window
    class DriveMotorOverheatedFrontRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedFrontRightWindow)

    // Failed due to drive motor overheated on rear left roller blind
    class DriveMotorOverheatedRearLeftRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearLeftRollerBlind)

    // Failed due to drive motor overheated on rear left window
    class DriveMotorOverheatedRearLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearLeftWindow)

    // Failed due to drive motor overheated on rear right roller blind
    class DriveMotorOverheatedRearRightRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearRightRollerBlind)

    // Failed due to drive motor overheated on rear right window
    class DriveMotorOverheatedRearRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearRightWindow)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Failed due to feature not available on front left window
    class FeatureNotAvailableFrontLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableFrontLeftWindow)

    // Failed due to feature not available on front right window
    class FeatureNotAvailableFrontRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableFrontRightWindow)

    // Failed due to feature not available on rear left roller blind
    class FeatureNotAvailableRearLeftRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearLeftRollerBlind)

    // Failed due to feature not available on rear left window
    class FeatureNotAvailableRearLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearLeftWindow)

    // Failed due to feature not available on rear right roller blind
    class FeatureNotAvailableRearRightRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearRightRollerBlind)

    // Failed due to feature not available on rear right window
    class FeatureNotAvailableRearRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearRightWindow)

    // Failed due to feature not available on rear roller blind
    class FeatureNotAvailableRearRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearRollerBlind)

    // Failed due to ignition is on
    class IgnitionOn(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.IgnitionOn)

    // Failed due to internal system error
    class InternalSystemError(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InternalSystemError)

    // Failed due to invalid ignition state
    class InvalidIgnitionState(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionState)

    // Failed due to invalid ignition state on front left window
    class InvalidIgnitionStateFrontLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateFrontLeftWindow)

    // Failed due to invalid ignition state on front right window
    class InvalidIgnitionStateFrontRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateFrontRightWindow)

    // Failed due to invalid ignition state on rear left roller blind
    class InvalidIgnitionStateRearLeftRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearLeftRollerBlind)

    // Failed due to invalid ignition state on rear left window
    class InvalidIgnitionStateRearLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearLeftWindow)

    // Failed due to invalid ignition state on rear right roller blind
    class InvalidIgnitionStateRearRightRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearRightRollerBlind)

    // Failed due to invalid ignition state on rear right window
    class InvalidIgnitionStateRearRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearRightWindow)

    // Failed due to invalid number on front left window
    class InvalidNumberFrontLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidNumberFrontLeftWindow)

    // Failed due to invalid number on front right window
    class InvalidNumberFrontRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidNumberFrontRightWindow)

    // Failed due to invalid number on rear left roller blind
    class InvalidNumberRearLeftRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearLeftRollerBlind)

    // Failed due to invalid number on rear left window
    class InvalidNumberRearLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearLeftWindow)

    // Failed due to invalid number on rear right roller blind
    class InvalidNumberRearRightRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearRightRollerBlind)

    // Failed due to invalid number on rear right window
    class InvalidNumberRearRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearRightWindow)

    // Failed due to invalid position on front left window
    class InvalidPositionFrontLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPositionFrontLeftWindow)

    // Failed due to invalid position on front right window
    class InvalidPositionFrontRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPositionFrontRightWindow)

    // Failed due to invalid position on rear left roller blind
    class InvalidPositionRearLeftRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearLeftRollerBlind)

    // Failed due to invalid position on rear left window
    class InvalidPositionRearLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearLeftWindow)

    // Failed due to invalid position on rear right roller blind
    class InvalidPositionRearRightRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearRightRollerBlind)

    // Failed due to invalid position on rear right window
    class InvalidPositionRearRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearRightWindow)

    // Failed due to invalid position on rear roller blind
    class InvalidPositionRearRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearRollerBlind)

    // Failed due to invalid power status
    class InvalidPowerStatus(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatus)

    // Failed due to invalid power status on front left window
    class InvalidPowerStatusFrontLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusFrontLeftWindow)

    // Failed due to invalid power status on front right window
    class InvalidPowerStatusFrontRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusFrontRightWindow)

    // Failed due to invalid power status on rear left roller blind
    class InvalidPowerStatusRearLeftRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearLeftRollerBlind)

    // Failed due to invalid power status on rear left window
    class InvalidPowerStatusRearLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearLeftWindow)

    // Failed due to invalid power status on rear right roller blind
    class InvalidPowerStatusRearRightRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearRightRollerBlind)

    // Failed due to invalid power status on rear right window
    class InvalidPowerStatusRearRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearRightWindow)

    // Failed due to low or high voltage on rear roller blind
    class InvalidPowerStatusRearRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearRollerBlind)

    // Energy level in Battery is too low
    class LowBatteryLevel(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel)

    // Failed due to low battery level 1
    class LowBatteryLevel1(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel1)

    // Failed due to low battery level 2
    class LowBatteryLevel2(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel2)

    // Failed due to mechanical problem on rear roller blind
    class MechanicalProblemRearRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.MechanicalProblemRearRollerBlind)

    // Failed due to multiple anti-trap protection activations
    class MultiAntiTrapProtections(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtections)

    // Failed due to multiple anti-trap protection activations on front left window
    class MultiAntiTrapProtectionsFrontLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsFrontLeftWindow)

    // Failed due to multiple anti-trap protection activations on front right window
    class MultiAntiTrapProtectionsFrontRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsFrontRightWindow)

    // Failed due to multiple anti-trap protection activations on rear left roller blind
    class MultiAntiTrapProtectionsRearLeftRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearLeftRollerBlind)

    // Failed due to multiple anti-trap protection activations on rear left window
    class MultiAntiTrapProtectionsRearLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearLeftWindow)

    // Failed due to multiple anti-trap protection activations on rear right roller blind
    class MultiAntiTrapProtectionsRearRightRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearRightRollerBlind)

    // Failed due to multiple anti-trap protection activations on rear right window
    class MultiAntiTrapProtectionsRearRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearRightWindow)

    // Failed due to open load on rear roller blind
    class OpenLoadRearRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.OpenLoadRearRollerBlind)

    // Failed due to remote engine start is active
    class RemoteEngineStartIsActive(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.RemoteEngineStartIsActive)

    // Failed due to hall sensor signal problem on rear roller blind
    class SensorProblemRearRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SensorProblemRearRollerBlind)

    // Service not authorized
    class ServiceNotAuthorized(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.ServiceNotAuthorized)

    // Failed due to system is blocked on rear roller blind
    class SystemBlockedRearRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemBlockedRearRollerBlind)

    // Failed due to system could not be normed
    class SystemCouldNotBeNormed(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormed)

    // Failed due to system could not be normed on front left window
    class SystemCouldNotBeNormedFrontLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedFrontLeftWindow)

    // Failed due to system could not be normed on front right window
    class SystemCouldNotBeNormedFrontRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedFrontRightWindow)

    // Failed due to system could not be normed on rear left roller blind
    class SystemCouldNotBeNormedRearLeftRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearLeftRollerBlind)

    // Failed due to system could not be normed on rear left window
    class SystemCouldNotBeNormedRearLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearLeftWindow)

    // Failed due to system could not be normed on rear right roller blind
    class SystemCouldNotBeNormedRearRightRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearRightRollerBlind)

    // Failed due to system could not be normed on rear right window
    class SystemCouldNotBeNormedRearRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearRightWindow)

    // Failed due to system malfunction
    class SystemMalfunction(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemMalfunction)

    // Failed due to system malfunction on front left window
    class SystemMalfunctionFrontLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionFrontLeftWindow)

    // Failed due to system malfunction on front right window
    class SystemMalfunctionFrontRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionFrontRightWindow)

    // Failed due to system malfunction on rear left roller blind
    class SystemMalfunctionRearLeftRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearLeftRollerBlind)

    // Failed due to system malfunction on rear left window
    class SystemMalfunctionRearLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearLeftWindow)

    // Failed due to system malfunction on rear right roller blind
    class SystemMalfunctionRearRightRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearRightRollerBlind)

    // Failed due to system malfunction on rear right window
    class SystemMalfunctionRearRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearRightWindow)

    // Failed due to system malfunction on rear roller blind
    class SystemMalfunctionRearRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearRollerBlind)

    // Failed due to system not normed
    class SystemNotNormed(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemNotNormed)

    // Failed due to system not normed  on front left window
    class SystemNotNormedFrontLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedFrontLeftWindow)

    // Failed due to system not normed  on front right window
    class SystemNotNormedFrontRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedFrontRightWindow)

    // Failed due to system not normed on rear left roller blind
    class SystemNotNormedRearLeftRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearLeftRollerBlind)

    // Failed due to system not normed  on rear left window
    class SystemNotNormedRearLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearLeftWindow)

    // Failed due to system not normed on rear right roller blind
    class SystemNotNormedRearRightRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearRightRollerBlind)

    // Failed due to system not normed  on rear right window
    class SystemNotNormedRearRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearRightWindow)

    // Failed due to temperature too low on rear roller blind
    class TemperatureTooLowRearRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.TemperatureTooLowRearRollerBlind)

    // Failed due to thermal protection active on rear roller blind
    class ThermalProtectionActiveRearRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.ThermalProtectionActiveRearRollerBlind)

    // Failed due to UI handler not available on front left window
    class UnavailableUiHandlerFrontLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerFrontLeftWindow)

    // Failed due to UI handler not available on front right window
    class UnavailableUiHandlerFrontRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerFrontRightWindow)

    // Failed due to UI handler not available on rear left roller blind
    class UnavailableUiHandlerRearLeftRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearLeftRollerBlind)

    // Failed due to UI handler not available on rear left window
    class UnavailableUiHandlerRearLeftWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearLeftWindow)

    // Failed due to UI handler not available on rear right roller blind
    class UnavailableUiHandlerRearRightRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearRightRollerBlind)

    // Failed due to UI handler not available on rear right window
    class UnavailableUiHandlerRearRightWindow(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearRightWindow)

    // Failed due to UI handler not available on rear roller blind
    class UnavailableUiHandlerRearRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearRollerBlind)

    // Failed due to unknown error on rear roller blind
    class UnknownErrorRearRollerBlind(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.UnknownErrorRearRollerBlind)

    // Failed because vehicle is in motion
    class VehicleInMotion(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.VehicleInMotion)

    // Remote window/roof command failed
    class WindowRoofCommandFailed(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandFailed)

    // Remote window/roof command failed (vehicle state in IGN)
    class WindowRoofCommandFailedIgnState(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandFailedIgnState)

    // Remote window/roof command failed (service not activated in HERMES)
    class WindowRoofCommandServiceNotActive(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandServiceNotActive)

    // Remote window/roof command failed (window not normed)
    class WindowRoofCommandWindowNotNormed(rawErrorCode: String) : WindowsCloseError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandWindowNotNormed)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): WindowsCloseError {
            return when (code) {
                "21" -> LowBatteryLevel(code)
                "42" -> FastpathTimeout(code)
                "4200" -> ServiceNotAuthorized(code)
                "4201" -> RemoteEngineStartIsActive(code)
                "4202" -> IgnitionOn(code)
                "4203" -> LowBatteryLevel2(code)
                "4204" -> LowBatteryLevel1(code)
                "4205" -> AntiTrapProtectionActive(code)
                "4206" -> AntiTrapProtectionActiveFrontLeftWindow(code)
                "4207" -> AntiTrapProtectionActiveFrontRightWindow(code)
                "4208" -> AntiTrapProtectionActiveRearLeftWindow(code)
                "4209" -> AntiTrapProtectionActiveRearRightWindow(code)
                "4210" -> AntiTrapProtectionActiveRearLeftRollerBlind(code)
                "4211" -> AntiTrapProtectionActiveRearRightRollerBlind(code)
                "4215" -> SystemBlockedRearRollerBlind(code)
                "4216" -> MultiAntiTrapProtections(code)
                "4217" -> MultiAntiTrapProtectionsFrontLeftWindow(code)
                "4218" -> MultiAntiTrapProtectionsFrontRightWindow(code)
                "4219" -> MultiAntiTrapProtectionsRearLeftWindow(code)
                "4220" -> MultiAntiTrapProtectionsRearRightWindow(code)
                "4221" -> MultiAntiTrapProtectionsRearLeftRollerBlind(code)
                "4222" -> MultiAntiTrapProtectionsRearRightRollerBlind(code)
                "4226" -> SensorProblemRearRollerBlind(code)
                "4227" -> CancelledManuallyInVehicle(code)
                "4228" -> CancelledManuallyInVehicleFrontLeftWindow(code)
                "4229" -> CancelledManuallyInVehicleFrontRightWindow(code)
                "4230" -> CancelledManuallyInVehicleRearLeftWindow(code)
                "4231" -> CancelledManuallyInVehicleRearRightWindow(code)
                "4232" -> CancelledManuallyInVehicleRearLeftRollerBlind(code)
                "4233" -> CancelledManuallyInVehicleRearRightRollerBlind(code)
                "4234" -> CancelledManuallyInVehicleSunroof(code)
                "4242" -> DriveMotorOverheated(code)
                "4243" -> DriveMotorOverheatedFrontLeftWindow(code)
                "4244" -> DriveMotorOverheatedFrontRightWindow(code)
                "4245" -> DriveMotorOverheatedRearLeftWindow(code)
                "4246" -> DriveMotorOverheatedRearRightWindow(code)
                "4247" -> DriveMotorOverheatedRearLeftRollerBlind(code)
                "4248" -> DriveMotorOverheatedRearRightRollerBlind(code)
                "4253" -> SystemNotNormed(code)
                "4254" -> SystemNotNormedFrontLeftWindow(code)
                "4255" -> SystemNotNormedFrontRightWindow(code)
                "4256" -> SystemNotNormedRearLeftWindow(code)
                "4257" -> SystemNotNormedRearRightWindow(code)
                "4258" -> SystemNotNormedRearLeftRollerBlind(code)
                "4259" -> SystemNotNormedRearRightRollerBlind(code)
                "4263" -> FeatureNotAvailableRearRollerBlind(code)
                "4265" -> InvalidPowerStatus(code)
                "4266" -> InvalidPowerStatusFrontLeftWindow(code)
                "4267" -> InvalidPowerStatusFrontRightWindow(code)
                "4268" -> InvalidPowerStatusRearLeftWindow(code)
                "4269" -> InvalidPowerStatusRearRightWindow(code)
                "4270" -> InvalidPowerStatusRearLeftRollerBlind(code)
                "4271" -> InvalidPowerStatusRearRightRollerBlind(code)
                "4275" -> InvalidPowerStatusRearRollerBlind(code)
                "4276" -> AfterRunActive(code)
                "4277" -> AfterRunActiveFrontLeftWindow(code)
                "4278" -> AfterRunActiveFrontRightWindow(code)
                "4279" -> AfterRunActiveRearLeftWindow(code)
                "4280" -> AfterRunActiveRearRightWindow(code)
                "4281" -> AfterRunActiveRearLeftRollerBlind(code)
                "4282" -> AfterRunActiveRearRightRollerBlind(code)
                "4286" -> MechanicalProblemRearRollerBlind(code)
                "4287" -> InvalidIgnitionState(code)
                "4288" -> InvalidIgnitionStateFrontLeftWindow(code)
                "4289" -> InvalidIgnitionStateFrontRightWindow(code)
                "4290" -> InvalidIgnitionStateRearLeftWindow(code)
                "4291" -> InvalidIgnitionStateRearRightWindow(code)
                "4292" -> InvalidIgnitionStateRearLeftRollerBlind(code)
                "4293" -> InvalidIgnitionStateRearRightRollerBlind(code)
                "4297" -> ThermalProtectionActiveRearRollerBlind(code)
                "4298" -> VehicleInMotion(code)
                "4302" -> OpenLoadRearRollerBlind(code)
                "4303" -> SystemCouldNotBeNormed(code)
                "4304" -> SystemCouldNotBeNormedFrontLeftWindow(code)
                "4305" -> SystemCouldNotBeNormedFrontRightWindow(code)
                "4306" -> SystemCouldNotBeNormedRearLeftWindow(code)
                "4307" -> SystemCouldNotBeNormedRearRightWindow(code)
                "4308" -> SystemCouldNotBeNormedRearLeftRollerBlind(code)
                "4309" -> SystemCouldNotBeNormedRearRightRollerBlind(code)
                "4313" -> TemperatureTooLowRearRollerBlind(code)
                "4314" -> SystemMalfunction(code)
                "4315" -> SystemMalfunctionFrontLeftWindow(code)
                "4316" -> SystemMalfunctionFrontRightWindow(code)
                "4317" -> SystemMalfunctionRearLeftWindow(code)
                "4318" -> SystemMalfunctionRearRightWindow(code)
                "4319" -> SystemMalfunctionRearLeftRollerBlind(code)
                "4320" -> SystemMalfunctionRearRightRollerBlind(code)
                "4324" -> SystemMalfunctionRearRollerBlind(code)
                "4325" -> InternalSystemError(code)
                "4326" -> InvalidNumberFrontLeftWindow(code)
                "4327" -> FeatureNotAvailableFrontLeftWindow(code)
                "4328" -> InvalidNumberFrontRightWindow(code)
                "4329" -> FeatureNotAvailableFrontRightWindow(code)
                "4330" -> InvalidNumberRearLeftWindow(code)
                "4331" -> FeatureNotAvailableRearLeftWindow(code)
                "4332" -> InvalidNumberRearRightWindow(code)
                "4333" -> FeatureNotAvailableRearRightWindow(code)
                "4336" -> InvalidNumberRearLeftRollerBlind(code)
                "4337" -> FeatureNotAvailableRearLeftRollerBlind(code)
                "4338" -> InvalidNumberRearRightRollerBlind(code)
                "4339" -> FeatureNotAvailableRearRightRollerBlind(code)
                "4344" -> UnknownErrorRearRollerBlind(code)
                "4346" -> InvalidPositionFrontLeftWindow(code)
                "4347" -> UnavailableUiHandlerFrontLeftWindow(code)
                "4348" -> InvalidPositionFrontRightWindow(code)
                "4349" -> UnavailableUiHandlerFrontRightWindow(code)
                "4350" -> InvalidPositionRearLeftWindow(code)
                "4351" -> UnavailableUiHandlerRearLeftWindow(code)
                "4352" -> InvalidPositionRearRightWindow(code)
                "4353" -> UnavailableUiHandlerRearRightWindow(code)
                "4354" -> InvalidPositionRearLeftRollerBlind(code)
                "4355" -> UnavailableUiHandlerRearLeftRollerBlind(code)
                "4356" -> InvalidPositionRearRightRollerBlind(code)
                "4357" -> UnavailableUiHandlerRearRightRollerBlind(code)
                "4364" -> InvalidPositionRearRollerBlind(code)
                "4365" -> UnavailableUiHandlerRearRollerBlind(code)
                "6901" -> WindowRoofCommandFailed(code)
                "6902" -> WindowRoofCommandFailedIgnState(code)
                "6903" -> WindowRoofCommandWindowNotNormed(code)
                "6904" -> WindowRoofCommandServiceNotActive(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the move windows command version v1
sealed class WindowsMoveError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : WindowsMoveError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Failed due to afterrun active
    class AfterRunActive(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.AfterRunActive)

    // Failed due to afterrun active on front left window
    class AfterRunActiveFrontLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveFrontLeftWindow)

    // Failed due to afterrun active on front right window
    class AfterRunActiveFrontRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveFrontRightWindow)

    // Failed due to afterrun active on rear left roller blind
    class AfterRunActiveRearLeftRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearLeftRollerBlind)

    // Failed due to afterrun active on rear left window
    class AfterRunActiveRearLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearLeftWindow)

    // Failed due to afterrun active on rear right roller blind
    class AfterRunActiveRearRightRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearRightRollerBlind)

    // Failed due to afterrun active on rear right window
    class AfterRunActiveRearRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearRightWindow)

    // Failed due to anti-trap protection active
    class AntiTrapProtectionActive(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActive)

    // Failed due to anti-trap protection active on front left window
    class AntiTrapProtectionActiveFrontLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveFrontLeftWindow)

    // Failed due to anti-trap protection active on front right window
    class AntiTrapProtectionActiveFrontRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveFrontRightWindow)

    // Failed due to anti-trap protection active on rear left roller blind
    class AntiTrapProtectionActiveRearLeftRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearLeftRollerBlind)

    // Failed due to anti-trap protection active on rear left window
    class AntiTrapProtectionActiveRearLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearLeftWindow)

    // Failed due to anti-trap protection active on rear right roller blind
    class AntiTrapProtectionActiveRearRightRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearRightRollerBlind)

    // Failed due to anti-trap protection active on rear right window
    class AntiTrapProtectionActiveRearRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearRightWindow)

    // Failed due to manual cancellation inside vehicle
    class CancelledManuallyInVehicle(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicle)

    // Failed due to manual cancellation inside vehicle on front left window
    class CancelledManuallyInVehicleFrontLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleFrontLeftWindow)

    // Failed due to manual cancellation inside vehicle on front right window
    class CancelledManuallyInVehicleFrontRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleFrontRightWindow)

    // Failed due to manual cancellation inside vehicle on rear left roller blind
    class CancelledManuallyInVehicleRearLeftRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearLeftRollerBlind)

    // Failed due to manual cancellation inside vehicle on rear left window
    class CancelledManuallyInVehicleRearLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearLeftWindow)

    // Failed due to manual cancellation inside vehicle on rear right roller blind
    class CancelledManuallyInVehicleRearRightRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearRightRollerBlind)

    // Failed due to manual cancellation inside vehicle on rear right window
    class CancelledManuallyInVehicleRearRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearRightWindow)

    // Failed due to manual cancellation inside vehicle on sunroof
    class CancelledManuallyInVehicleSunroof(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleSunroof)

    // Failed due to drive motor overheated
    class DriveMotorOverheated(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheated)

    // Failed due to drive motor overheated on front left window
    class DriveMotorOverheatedFrontLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedFrontLeftWindow)

    // Failed due to drive motor overheated on front right window
    class DriveMotorOverheatedFrontRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedFrontRightWindow)

    // Failed due to drive motor overheated on rear left roller blind
    class DriveMotorOverheatedRearLeftRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearLeftRollerBlind)

    // Failed due to drive motor overheated on rear left window
    class DriveMotorOverheatedRearLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearLeftWindow)

    // Failed due to drive motor overheated on rear right roller blind
    class DriveMotorOverheatedRearRightRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearRightRollerBlind)

    // Failed due to drive motor overheated on rear right window
    class DriveMotorOverheatedRearRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearRightWindow)

    // Failed due to feature not available on front left window
    class FeatureNotAvailableFrontLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableFrontLeftWindow)

    // Failed due to feature not available on front right window
    class FeatureNotAvailableFrontRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableFrontRightWindow)

    // Failed due to feature not available on rear left roller blind
    class FeatureNotAvailableRearLeftRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearLeftRollerBlind)

    // Failed due to feature not available on rear left window
    class FeatureNotAvailableRearLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearLeftWindow)

    // Failed due to feature not available on rear right roller blind
    class FeatureNotAvailableRearRightRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearRightRollerBlind)

    // Failed due to feature not available on rear right window
    class FeatureNotAvailableRearRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearRightWindow)

    // Failed due to feature not available on rear roller blind
    class FeatureNotAvailableRearRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearRollerBlind)

    // Failed due to ignition is on
    class IgnitionOn(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.IgnitionOn)

    // Failed due to internal system error
    class InternalSystemError(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InternalSystemError)

    // Failed due to invalid ignition state
    class InvalidIgnitionState(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionState)

    // Failed due to invalid ignition state on front left window
    class InvalidIgnitionStateFrontLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateFrontLeftWindow)

    // Failed due to invalid ignition state on front right window
    class InvalidIgnitionStateFrontRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateFrontRightWindow)

    // Failed due to invalid ignition state on rear left roller blind
    class InvalidIgnitionStateRearLeftRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearLeftRollerBlind)

    // Failed due to invalid ignition state on rear left window
    class InvalidIgnitionStateRearLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearLeftWindow)

    // Failed due to invalid ignition state on rear right roller blind
    class InvalidIgnitionStateRearRightRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearRightRollerBlind)

    // Failed due to invalid ignition state on rear right window
    class InvalidIgnitionStateRearRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearRightWindow)

    // Failed due to invalid number on front left window
    class InvalidNumberFrontLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidNumberFrontLeftWindow)

    // Failed due to invalid number on front right window
    class InvalidNumberFrontRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidNumberFrontRightWindow)

    // Failed due to invalid number on rear left roller blind
    class InvalidNumberRearLeftRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearLeftRollerBlind)

    // Failed due to invalid number on rear left window
    class InvalidNumberRearLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearLeftWindow)

    // Failed due to invalid number on rear right roller blind
    class InvalidNumberRearRightRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearRightRollerBlind)

    // Failed due to invalid number on rear right window
    class InvalidNumberRearRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearRightWindow)

    // Failed due to invalid position on front left window
    class InvalidPositionFrontLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPositionFrontLeftWindow)

    // Failed due to invalid position on front right window
    class InvalidPositionFrontRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPositionFrontRightWindow)

    // Failed due to invalid position on rear left roller blind
    class InvalidPositionRearLeftRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearLeftRollerBlind)

    // Failed due to invalid position on rear left window
    class InvalidPositionRearLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearLeftWindow)

    // Failed due to invalid position on rear right roller blind
    class InvalidPositionRearRightRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearRightRollerBlind)

    // Failed due to invalid position on rear right window
    class InvalidPositionRearRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearRightWindow)

    // Failed due to invalid position on rear roller blind
    class InvalidPositionRearRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearRollerBlind)

    // Failed due to invalid power status
    class InvalidPowerStatus(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatus)

    // Failed due to invalid power status on front left window
    class InvalidPowerStatusFrontLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusFrontLeftWindow)

    // Failed due to invalid power status on front right window
    class InvalidPowerStatusFrontRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusFrontRightWindow)

    // Failed due to invalid power status on rear left roller blind
    class InvalidPowerStatusRearLeftRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearLeftRollerBlind)

    // Failed due to invalid power status on rear left window
    class InvalidPowerStatusRearLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearLeftWindow)

    // Failed due to invalid power status on rear right roller blind
    class InvalidPowerStatusRearRightRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearRightRollerBlind)

    // Failed due to invalid power status on rear right window
    class InvalidPowerStatusRearRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearRightWindow)

    // Failed due to low or high voltage on rear roller blind
    class InvalidPowerStatusRearRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearRollerBlind)

    // Failed due to low battery level 1
    class LowBatteryLevel1(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel1)

    // Failed due to low battery level 2
    class LowBatteryLevel2(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel2)

    // Failed due to mechanical problem on rear roller blind
    class MechanicalProblemRearRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.MechanicalProblemRearRollerBlind)

    // Failed due to multiple anti-trap protection activations
    class MultiAntiTrapProtections(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtections)

    // Failed due to multiple anti-trap protection activations on front left window
    class MultiAntiTrapProtectionsFrontLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsFrontLeftWindow)

    // Failed due to multiple anti-trap protection activations on front right window
    class MultiAntiTrapProtectionsFrontRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsFrontRightWindow)

    // Failed due to multiple anti-trap protection activations on rear left roller blind
    class MultiAntiTrapProtectionsRearLeftRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearLeftRollerBlind)

    // Failed due to multiple anti-trap protection activations on rear left window
    class MultiAntiTrapProtectionsRearLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearLeftWindow)

    // Failed due to multiple anti-trap protection activations on rear right roller blind
    class MultiAntiTrapProtectionsRearRightRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearRightRollerBlind)

    // Failed due to multiple anti-trap protection activations on rear right window
    class MultiAntiTrapProtectionsRearRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearRightWindow)

    // Failed due to open load on rear roller blind
    class OpenLoadRearRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.OpenLoadRearRollerBlind)

    // Failed due to remote engine start is active
    class RemoteEngineStartIsActive(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.RemoteEngineStartIsActive)

    // Failed due to hall sensor signal problem on rear roller blind
    class SensorProblemRearRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SensorProblemRearRollerBlind)

    // Service not authorized
    class ServiceNotAuthorized(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.ServiceNotAuthorized)

    // Failed due to system is blocked on rear roller blind
    class SystemBlockedRearRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemBlockedRearRollerBlind)

    // Failed due to system could not be normed
    class SystemCouldNotBeNormed(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormed)

    // Failed due to system could not be normed on front left window
    class SystemCouldNotBeNormedFrontLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedFrontLeftWindow)

    // Failed due to system could not be normed on front right window
    class SystemCouldNotBeNormedFrontRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedFrontRightWindow)

    // Failed due to system could not be normed on rear left roller blind
    class SystemCouldNotBeNormedRearLeftRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearLeftRollerBlind)

    // Failed due to system could not be normed on rear left window
    class SystemCouldNotBeNormedRearLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearLeftWindow)

    // Failed due to system could not be normed on rear right roller blind
    class SystemCouldNotBeNormedRearRightRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearRightRollerBlind)

    // Failed due to system could not be normed on rear right window
    class SystemCouldNotBeNormedRearRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearRightWindow)

    // Failed due to system malfunction
    class SystemMalfunction(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemMalfunction)

    // Failed due to system malfunction on front left window
    class SystemMalfunctionFrontLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionFrontLeftWindow)

    // Failed due to system malfunction on front right window
    class SystemMalfunctionFrontRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionFrontRightWindow)

    // Failed due to system malfunction on rear left roller blind
    class SystemMalfunctionRearLeftRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearLeftRollerBlind)

    // Failed due to system malfunction on rear left window
    class SystemMalfunctionRearLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearLeftWindow)

    // Failed due to system malfunction on rear right roller blind
    class SystemMalfunctionRearRightRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearRightRollerBlind)

    // Failed due to system malfunction on rear right window
    class SystemMalfunctionRearRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearRightWindow)

    // Failed due to system malfunction on rear roller blind
    class SystemMalfunctionRearRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearRollerBlind)

    // Failed due to system not normed
    class SystemNotNormed(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemNotNormed)

    // Failed due to system not normed  on front left window
    class SystemNotNormedFrontLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedFrontLeftWindow)

    // Failed due to system not normed  on front right window
    class SystemNotNormedFrontRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedFrontRightWindow)

    // Failed due to system not normed on rear left roller blind
    class SystemNotNormedRearLeftRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearLeftRollerBlind)

    // Failed due to system not normed  on rear left window
    class SystemNotNormedRearLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearLeftWindow)

    // Failed due to system not normed on rear right roller blind
    class SystemNotNormedRearRightRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearRightRollerBlind)

    // Failed due to system not normed  on rear right window
    class SystemNotNormedRearRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearRightWindow)

    // Failed due to temperature too low on rear roller blind
    class TemperatureTooLowRearRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.TemperatureTooLowRearRollerBlind)

    // Failed due to thermal protection active on rear roller blind
    class ThermalProtectionActiveRearRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.ThermalProtectionActiveRearRollerBlind)

    // Failed due to UI handler not available on front left window
    class UnavailableUiHandlerFrontLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerFrontLeftWindow)

    // Failed due to UI handler not available on front right window
    class UnavailableUiHandlerFrontRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerFrontRightWindow)

    // Failed due to UI handler not available on rear left roller blind
    class UnavailableUiHandlerRearLeftRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearLeftRollerBlind)

    // Failed due to UI handler not available on rear left window
    class UnavailableUiHandlerRearLeftWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearLeftWindow)

    // Failed due to UI handler not available on rear right roller blind
    class UnavailableUiHandlerRearRightRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearRightRollerBlind)

    // Failed due to UI handler not available on rear right window
    class UnavailableUiHandlerRearRightWindow(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearRightWindow)

    // Failed due to UI handler not available on rear roller blind
    class UnavailableUiHandlerRearRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearRollerBlind)

    // Failed due to unknown error on rear roller blind
    class UnknownErrorRearRollerBlind(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.UnknownErrorRearRollerBlind)

    // Failed because vehicle is in motion
    class VehicleInMotion(rawErrorCode: String) : WindowsMoveError(rawErrorCode, InternalVehicleCommandError.VehicleInMotion)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): WindowsMoveError {
            return when (code) {
                "4200" -> ServiceNotAuthorized(code)
                "4201" -> RemoteEngineStartIsActive(code)
                "4202" -> IgnitionOn(code)
                "4203" -> LowBatteryLevel2(code)
                "4204" -> LowBatteryLevel1(code)
                "4205" -> AntiTrapProtectionActive(code)
                "4206" -> AntiTrapProtectionActiveFrontLeftWindow(code)
                "4207" -> AntiTrapProtectionActiveFrontRightWindow(code)
                "4208" -> AntiTrapProtectionActiveRearLeftWindow(code)
                "4209" -> AntiTrapProtectionActiveRearRightWindow(code)
                "4210" -> AntiTrapProtectionActiveRearLeftRollerBlind(code)
                "4211" -> AntiTrapProtectionActiveRearRightRollerBlind(code)
                "4215" -> SystemBlockedRearRollerBlind(code)
                "4216" -> MultiAntiTrapProtections(code)
                "4217" -> MultiAntiTrapProtectionsFrontLeftWindow(code)
                "4218" -> MultiAntiTrapProtectionsFrontRightWindow(code)
                "4219" -> MultiAntiTrapProtectionsRearLeftWindow(code)
                "4220" -> MultiAntiTrapProtectionsRearRightWindow(code)
                "4221" -> MultiAntiTrapProtectionsRearLeftRollerBlind(code)
                "4222" -> MultiAntiTrapProtectionsRearRightRollerBlind(code)
                "4226" -> SensorProblemRearRollerBlind(code)
                "4227" -> CancelledManuallyInVehicle(code)
                "4228" -> CancelledManuallyInVehicleFrontLeftWindow(code)
                "4229" -> CancelledManuallyInVehicleFrontRightWindow(code)
                "4230" -> CancelledManuallyInVehicleRearLeftWindow(code)
                "4231" -> CancelledManuallyInVehicleRearRightWindow(code)
                "4232" -> CancelledManuallyInVehicleRearLeftRollerBlind(code)
                "4233" -> CancelledManuallyInVehicleRearRightRollerBlind(code)
                "4234" -> CancelledManuallyInVehicleSunroof(code)
                "4242" -> DriveMotorOverheated(code)
                "4243" -> DriveMotorOverheatedFrontLeftWindow(code)
                "4244" -> DriveMotorOverheatedFrontRightWindow(code)
                "4245" -> DriveMotorOverheatedRearLeftWindow(code)
                "4246" -> DriveMotorOverheatedRearRightWindow(code)
                "4247" -> DriveMotorOverheatedRearLeftRollerBlind(code)
                "4248" -> DriveMotorOverheatedRearRightRollerBlind(code)
                "4253" -> SystemNotNormed(code)
                "4254" -> SystemNotNormedFrontLeftWindow(code)
                "4255" -> SystemNotNormedFrontRightWindow(code)
                "4256" -> SystemNotNormedRearLeftWindow(code)
                "4257" -> SystemNotNormedRearRightWindow(code)
                "4258" -> SystemNotNormedRearLeftRollerBlind(code)
                "4259" -> SystemNotNormedRearRightRollerBlind(code)
                "4263" -> FeatureNotAvailableRearRollerBlind(code)
                "4265" -> InvalidPowerStatus(code)
                "4266" -> InvalidPowerStatusFrontLeftWindow(code)
                "4267" -> InvalidPowerStatusFrontRightWindow(code)
                "4268" -> InvalidPowerStatusRearLeftWindow(code)
                "4269" -> InvalidPowerStatusRearRightWindow(code)
                "4270" -> InvalidPowerStatusRearLeftRollerBlind(code)
                "4271" -> InvalidPowerStatusRearRightRollerBlind(code)
                "4275" -> InvalidPowerStatusRearRollerBlind(code)
                "4276" -> AfterRunActive(code)
                "4277" -> AfterRunActiveFrontLeftWindow(code)
                "4278" -> AfterRunActiveFrontRightWindow(code)
                "4279" -> AfterRunActiveRearLeftWindow(code)
                "4280" -> AfterRunActiveRearRightWindow(code)
                "4281" -> AfterRunActiveRearLeftRollerBlind(code)
                "4282" -> AfterRunActiveRearRightRollerBlind(code)
                "4286" -> MechanicalProblemRearRollerBlind(code)
                "4287" -> InvalidIgnitionState(code)
                "4288" -> InvalidIgnitionStateFrontLeftWindow(code)
                "4289" -> InvalidIgnitionStateFrontRightWindow(code)
                "4290" -> InvalidIgnitionStateRearLeftWindow(code)
                "4291" -> InvalidIgnitionStateRearRightWindow(code)
                "4292" -> InvalidIgnitionStateRearLeftRollerBlind(code)
                "4293" -> InvalidIgnitionStateRearRightRollerBlind(code)
                "4297" -> ThermalProtectionActiveRearRollerBlind(code)
                "4298" -> VehicleInMotion(code)
                "4302" -> OpenLoadRearRollerBlind(code)
                "4303" -> SystemCouldNotBeNormed(code)
                "4304" -> SystemCouldNotBeNormedFrontLeftWindow(code)
                "4305" -> SystemCouldNotBeNormedFrontRightWindow(code)
                "4306" -> SystemCouldNotBeNormedRearLeftWindow(code)
                "4307" -> SystemCouldNotBeNormedRearRightWindow(code)
                "4308" -> SystemCouldNotBeNormedRearLeftRollerBlind(code)
                "4309" -> SystemCouldNotBeNormedRearRightRollerBlind(code)
                "4313" -> TemperatureTooLowRearRollerBlind(code)
                "4314" -> SystemMalfunction(code)
                "4315" -> SystemMalfunctionFrontLeftWindow(code)
                "4316" -> SystemMalfunctionFrontRightWindow(code)
                "4317" -> SystemMalfunctionRearLeftWindow(code)
                "4318" -> SystemMalfunctionRearRightWindow(code)
                "4319" -> SystemMalfunctionRearLeftRollerBlind(code)
                "4320" -> SystemMalfunctionRearRightRollerBlind(code)
                "4324" -> SystemMalfunctionRearRollerBlind(code)
                "4325" -> InternalSystemError(code)
                "4326" -> InvalidNumberFrontLeftWindow(code)
                "4327" -> FeatureNotAvailableFrontLeftWindow(code)
                "4328" -> InvalidNumberFrontRightWindow(code)
                "4329" -> FeatureNotAvailableFrontRightWindow(code)
                "4330" -> InvalidNumberRearLeftWindow(code)
                "4331" -> FeatureNotAvailableRearLeftWindow(code)
                "4332" -> InvalidNumberRearRightWindow(code)
                "4333" -> FeatureNotAvailableRearRightWindow(code)
                "4336" -> InvalidNumberRearLeftRollerBlind(code)
                "4337" -> FeatureNotAvailableRearLeftRollerBlind(code)
                "4338" -> InvalidNumberRearRightRollerBlind(code)
                "4339" -> FeatureNotAvailableRearRightRollerBlind(code)
                "4344" -> UnknownErrorRearRollerBlind(code)
                "4346" -> InvalidPositionFrontLeftWindow(code)
                "4347" -> UnavailableUiHandlerFrontLeftWindow(code)
                "4348" -> InvalidPositionFrontRightWindow(code)
                "4349" -> UnavailableUiHandlerFrontRightWindow(code)
                "4350" -> InvalidPositionRearLeftWindow(code)
                "4351" -> UnavailableUiHandlerRearLeftWindow(code)
                "4352" -> InvalidPositionRearRightWindow(code)
                "4353" -> UnavailableUiHandlerRearRightWindow(code)
                "4354" -> InvalidPositionRearLeftRollerBlind(code)
                "4355" -> UnavailableUiHandlerRearLeftRollerBlind(code)
                "4356" -> InvalidPositionRearRightRollerBlind(code)
                "4357" -> UnavailableUiHandlerRearRightRollerBlind(code)
                "4364" -> InvalidPositionRearRollerBlind(code)
                "4365" -> UnavailableUiHandlerRearRollerBlind(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the open windows command version v1
sealed class WindowsOpenError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : WindowsOpenError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Failed due to afterrun active
    class AfterRunActive(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.AfterRunActive)

    // Failed due to afterrun active on front left window
    class AfterRunActiveFrontLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveFrontLeftWindow)

    // Failed due to afterrun active on front right window
    class AfterRunActiveFrontRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveFrontRightWindow)

    // Failed due to afterrun active on rear left roller blind
    class AfterRunActiveRearLeftRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearLeftRollerBlind)

    // Failed due to afterrun active on rear left window
    class AfterRunActiveRearLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearLeftWindow)

    // Failed due to afterrun active on rear right roller blind
    class AfterRunActiveRearRightRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearRightRollerBlind)

    // Failed due to afterrun active on rear right window
    class AfterRunActiveRearRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearRightWindow)

    // Failed due to anti-trap protection active
    class AntiTrapProtectionActive(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActive)

    // Failed due to anti-trap protection active on front left window
    class AntiTrapProtectionActiveFrontLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveFrontLeftWindow)

    // Failed due to anti-trap protection active on front right window
    class AntiTrapProtectionActiveFrontRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveFrontRightWindow)

    // Failed due to anti-trap protection active on rear left roller blind
    class AntiTrapProtectionActiveRearLeftRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearLeftRollerBlind)

    // Failed due to anti-trap protection active on rear left window
    class AntiTrapProtectionActiveRearLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearLeftWindow)

    // Failed due to anti-trap protection active on rear right roller blind
    class AntiTrapProtectionActiveRearRightRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearRightRollerBlind)

    // Failed due to anti-trap protection active on rear right window
    class AntiTrapProtectionActiveRearRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearRightWindow)

    // Failed due to manual cancellation inside vehicle
    class CancelledManuallyInVehicle(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicle)

    // Failed due to manual cancellation inside vehicle on front left window
    class CancelledManuallyInVehicleFrontLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleFrontLeftWindow)

    // Failed due to manual cancellation inside vehicle on front right window
    class CancelledManuallyInVehicleFrontRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleFrontRightWindow)

    // Failed due to manual cancellation inside vehicle on rear left roller blind
    class CancelledManuallyInVehicleRearLeftRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearLeftRollerBlind)

    // Failed due to manual cancellation inside vehicle on rear left window
    class CancelledManuallyInVehicleRearLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearLeftWindow)

    // Failed due to manual cancellation inside vehicle on rear right roller blind
    class CancelledManuallyInVehicleRearRightRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearRightRollerBlind)

    // Failed due to manual cancellation inside vehicle on rear right window
    class CancelledManuallyInVehicleRearRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearRightWindow)

    // Failed due to manual cancellation inside vehicle on sunroof
    class CancelledManuallyInVehicleSunroof(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleSunroof)

    // Failed due to drive motor overheated
    class DriveMotorOverheated(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheated)

    // Failed due to drive motor overheated on front left window
    class DriveMotorOverheatedFrontLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedFrontLeftWindow)

    // Failed due to drive motor overheated on front right window
    class DriveMotorOverheatedFrontRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedFrontRightWindow)

    // Failed due to drive motor overheated on rear left roller blind
    class DriveMotorOverheatedRearLeftRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearLeftRollerBlind)

    // Failed due to drive motor overheated on rear left window
    class DriveMotorOverheatedRearLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearLeftWindow)

    // Failed due to drive motor overheated on rear right roller blind
    class DriveMotorOverheatedRearRightRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearRightRollerBlind)

    // Failed due to drive motor overheated on rear right window
    class DriveMotorOverheatedRearRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearRightWindow)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Failed due to feature not available on front left window
    class FeatureNotAvailableFrontLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableFrontLeftWindow)

    // Failed due to feature not available on front right window
    class FeatureNotAvailableFrontRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableFrontRightWindow)

    // Failed due to feature not available on rear left roller blind
    class FeatureNotAvailableRearLeftRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearLeftRollerBlind)

    // Failed due to feature not available on rear left window
    class FeatureNotAvailableRearLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearLeftWindow)

    // Failed due to feature not available on rear right roller blind
    class FeatureNotAvailableRearRightRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearRightRollerBlind)

    // Failed due to feature not available on rear right window
    class FeatureNotAvailableRearRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearRightWindow)

    // Failed due to feature not available on rear roller blind
    class FeatureNotAvailableRearRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearRollerBlind)

    // Failed due to ignition is on
    class IgnitionOn(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.IgnitionOn)

    // Failed due to internal system error
    class InternalSystemError(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InternalSystemError)

    // Failed due to invalid ignition state
    class InvalidIgnitionState(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionState)

    // Failed due to invalid ignition state on front left window
    class InvalidIgnitionStateFrontLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateFrontLeftWindow)

    // Failed due to invalid ignition state on front right window
    class InvalidIgnitionStateFrontRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateFrontRightWindow)

    // Failed due to invalid ignition state on rear left roller blind
    class InvalidIgnitionStateRearLeftRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearLeftRollerBlind)

    // Failed due to invalid ignition state on rear left window
    class InvalidIgnitionStateRearLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearLeftWindow)

    // Failed due to invalid ignition state on rear right roller blind
    class InvalidIgnitionStateRearRightRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearRightRollerBlind)

    // Failed due to invalid ignition state on rear right window
    class InvalidIgnitionStateRearRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearRightWindow)

    // Failed due to invalid number on front left window
    class InvalidNumberFrontLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidNumberFrontLeftWindow)

    // Failed due to invalid number on front right window
    class InvalidNumberFrontRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidNumberFrontRightWindow)

    // Failed due to invalid number on rear left roller blind
    class InvalidNumberRearLeftRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearLeftRollerBlind)

    // Failed due to invalid number on rear left window
    class InvalidNumberRearLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearLeftWindow)

    // Failed due to invalid number on rear right roller blind
    class InvalidNumberRearRightRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearRightRollerBlind)

    // Failed due to invalid number on rear right window
    class InvalidNumberRearRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearRightWindow)

    // Failed due to invalid position on front left window
    class InvalidPositionFrontLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPositionFrontLeftWindow)

    // Failed due to invalid position on front right window
    class InvalidPositionFrontRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPositionFrontRightWindow)

    // Failed due to invalid position on rear left roller blind
    class InvalidPositionRearLeftRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearLeftRollerBlind)

    // Failed due to invalid position on rear left window
    class InvalidPositionRearLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearLeftWindow)

    // Failed due to invalid position on rear right roller blind
    class InvalidPositionRearRightRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearRightRollerBlind)

    // Failed due to invalid position on rear right window
    class InvalidPositionRearRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearRightWindow)

    // Failed due to invalid position on rear roller blind
    class InvalidPositionRearRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearRollerBlind)

    // Failed due to invalid power status
    class InvalidPowerStatus(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatus)

    // Failed due to invalid power status on front left window
    class InvalidPowerStatusFrontLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusFrontLeftWindow)

    // Failed due to invalid power status on front right window
    class InvalidPowerStatusFrontRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusFrontRightWindow)

    // Failed due to invalid power status on rear left roller blind
    class InvalidPowerStatusRearLeftRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearLeftRollerBlind)

    // Failed due to invalid power status on rear left window
    class InvalidPowerStatusRearLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearLeftWindow)

    // Failed due to invalid power status on rear right roller blind
    class InvalidPowerStatusRearRightRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearRightRollerBlind)

    // Failed due to invalid power status on rear right window
    class InvalidPowerStatusRearRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearRightWindow)

    // Failed due to low or high voltage on rear roller blind
    class InvalidPowerStatusRearRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearRollerBlind)

    // Energy level in Battery is too low
    class LowBatteryLevel(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel)

    // Failed due to low battery level 1
    class LowBatteryLevel1(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel1)

    // Failed due to low battery level 2
    class LowBatteryLevel2(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel2)

    // Failed due to mechanical problem on rear roller blind
    class MechanicalProblemRearRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.MechanicalProblemRearRollerBlind)

    // Failed due to multiple anti-trap protection activations
    class MultiAntiTrapProtections(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtections)

    // Failed due to multiple anti-trap protection activations on front left window
    class MultiAntiTrapProtectionsFrontLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsFrontLeftWindow)

    // Failed due to multiple anti-trap protection activations on front right window
    class MultiAntiTrapProtectionsFrontRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsFrontRightWindow)

    // Failed due to multiple anti-trap protection activations on rear left roller blind
    class MultiAntiTrapProtectionsRearLeftRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearLeftRollerBlind)

    // Failed due to multiple anti-trap protection activations on rear left window
    class MultiAntiTrapProtectionsRearLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearLeftWindow)

    // Failed due to multiple anti-trap protection activations on rear right roller blind
    class MultiAntiTrapProtectionsRearRightRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearRightRollerBlind)

    // Failed due to multiple anti-trap protection activations on rear right window
    class MultiAntiTrapProtectionsRearRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearRightWindow)

    // Failed due to open load on rear roller blind
    class OpenLoadRearRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.OpenLoadRearRollerBlind)

    // Failed due to remote engine start is active
    class RemoteEngineStartIsActive(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.RemoteEngineStartIsActive)

    // Failed due to hall sensor signal problem on rear roller blind
    class SensorProblemRearRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SensorProblemRearRollerBlind)

    // Service not authorized
    class ServiceNotAuthorized(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.ServiceNotAuthorized)

    // Failed due to system is blocked on rear roller blind
    class SystemBlockedRearRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemBlockedRearRollerBlind)

    // Failed due to system could not be normed
    class SystemCouldNotBeNormed(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormed)

    // Failed due to system could not be normed on front left window
    class SystemCouldNotBeNormedFrontLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedFrontLeftWindow)

    // Failed due to system could not be normed on front right window
    class SystemCouldNotBeNormedFrontRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedFrontRightWindow)

    // Failed due to system could not be normed on rear left roller blind
    class SystemCouldNotBeNormedRearLeftRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearLeftRollerBlind)

    // Failed due to system could not be normed on rear left window
    class SystemCouldNotBeNormedRearLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearLeftWindow)

    // Failed due to system could not be normed on rear right roller blind
    class SystemCouldNotBeNormedRearRightRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearRightRollerBlind)

    // Failed due to system could not be normed on rear right window
    class SystemCouldNotBeNormedRearRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearRightWindow)

    // Failed due to system malfunction
    class SystemMalfunction(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemMalfunction)

    // Failed due to system malfunction on front left window
    class SystemMalfunctionFrontLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionFrontLeftWindow)

    // Failed due to system malfunction on front right window
    class SystemMalfunctionFrontRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionFrontRightWindow)

    // Failed due to system malfunction on rear left roller blind
    class SystemMalfunctionRearLeftRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearLeftRollerBlind)

    // Failed due to system malfunction on rear left window
    class SystemMalfunctionRearLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearLeftWindow)

    // Failed due to system malfunction on rear right roller blind
    class SystemMalfunctionRearRightRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearRightRollerBlind)

    // Failed due to system malfunction on rear right window
    class SystemMalfunctionRearRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearRightWindow)

    // Failed due to system malfunction on rear roller blind
    class SystemMalfunctionRearRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearRollerBlind)

    // Failed due to system not normed
    class SystemNotNormed(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemNotNormed)

    // Failed due to system not normed  on front left window
    class SystemNotNormedFrontLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedFrontLeftWindow)

    // Failed due to system not normed  on front right window
    class SystemNotNormedFrontRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedFrontRightWindow)

    // Failed due to system not normed on rear left roller blind
    class SystemNotNormedRearLeftRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearLeftRollerBlind)

    // Failed due to system not normed  on rear left window
    class SystemNotNormedRearLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearLeftWindow)

    // Failed due to system not normed on rear right roller blind
    class SystemNotNormedRearRightRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearRightRollerBlind)

    // Failed due to system not normed  on rear right window
    class SystemNotNormedRearRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearRightWindow)

    // Failed due to temperature too low on rear roller blind
    class TemperatureTooLowRearRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.TemperatureTooLowRearRollerBlind)

    // Failed due to thermal protection active on rear roller blind
    class ThermalProtectionActiveRearRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.ThermalProtectionActiveRearRollerBlind)

    // Failed due to UI handler not available on front left window
    class UnavailableUiHandlerFrontLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerFrontLeftWindow)

    // Failed due to UI handler not available on front right window
    class UnavailableUiHandlerFrontRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerFrontRightWindow)

    // Failed due to UI handler not available on rear left roller blind
    class UnavailableUiHandlerRearLeftRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearLeftRollerBlind)

    // Failed due to UI handler not available on rear left window
    class UnavailableUiHandlerRearLeftWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearLeftWindow)

    // Failed due to UI handler not available on rear right roller blind
    class UnavailableUiHandlerRearRightRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearRightRollerBlind)

    // Failed due to UI handler not available on rear right window
    class UnavailableUiHandlerRearRightWindow(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearRightWindow)

    // Failed due to UI handler not available on rear roller blind
    class UnavailableUiHandlerRearRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearRollerBlind)

    // Failed due to unknown error on rear roller blind
    class UnknownErrorRearRollerBlind(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.UnknownErrorRearRollerBlind)

    // Failed because vehicle is in motion
    class VehicleInMotion(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.VehicleInMotion)

    // Remote window/roof command failed
    class WindowRoofCommandFailed(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandFailed)

    // Remote window/roof command failed (vehicle state in IGN)
    class WindowRoofCommandFailedIgnState(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandFailedIgnState)

    // Remote window/roof command failed (service not activated in HERMES)
    class WindowRoofCommandServiceNotActive(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandServiceNotActive)

    // Remote window/roof command failed (window not normed)
    class WindowRoofCommandWindowNotNormed(rawErrorCode: String) : WindowsOpenError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandWindowNotNormed)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): WindowsOpenError {
            return when (code) {
                "21" -> LowBatteryLevel(code)
                "42" -> FastpathTimeout(code)
                "4200" -> ServiceNotAuthorized(code)
                "4201" -> RemoteEngineStartIsActive(code)
                "4202" -> IgnitionOn(code)
                "4203" -> LowBatteryLevel2(code)
                "4204" -> LowBatteryLevel1(code)
                "4205" -> AntiTrapProtectionActive(code)
                "4206" -> AntiTrapProtectionActiveFrontLeftWindow(code)
                "4207" -> AntiTrapProtectionActiveFrontRightWindow(code)
                "4208" -> AntiTrapProtectionActiveRearLeftWindow(code)
                "4209" -> AntiTrapProtectionActiveRearRightWindow(code)
                "4210" -> AntiTrapProtectionActiveRearLeftRollerBlind(code)
                "4211" -> AntiTrapProtectionActiveRearRightRollerBlind(code)
                "4215" -> SystemBlockedRearRollerBlind(code)
                "4216" -> MultiAntiTrapProtections(code)
                "4217" -> MultiAntiTrapProtectionsFrontLeftWindow(code)
                "4218" -> MultiAntiTrapProtectionsFrontRightWindow(code)
                "4219" -> MultiAntiTrapProtectionsRearLeftWindow(code)
                "4220" -> MultiAntiTrapProtectionsRearRightWindow(code)
                "4221" -> MultiAntiTrapProtectionsRearLeftRollerBlind(code)
                "4222" -> MultiAntiTrapProtectionsRearRightRollerBlind(code)
                "4226" -> SensorProblemRearRollerBlind(code)
                "4227" -> CancelledManuallyInVehicle(code)
                "4228" -> CancelledManuallyInVehicleFrontLeftWindow(code)
                "4229" -> CancelledManuallyInVehicleFrontRightWindow(code)
                "4230" -> CancelledManuallyInVehicleRearLeftWindow(code)
                "4231" -> CancelledManuallyInVehicleRearRightWindow(code)
                "4232" -> CancelledManuallyInVehicleRearLeftRollerBlind(code)
                "4233" -> CancelledManuallyInVehicleRearRightRollerBlind(code)
                "4234" -> CancelledManuallyInVehicleSunroof(code)
                "4242" -> DriveMotorOverheated(code)
                "4243" -> DriveMotorOverheatedFrontLeftWindow(code)
                "4244" -> DriveMotorOverheatedFrontRightWindow(code)
                "4245" -> DriveMotorOverheatedRearLeftWindow(code)
                "4246" -> DriveMotorOverheatedRearRightWindow(code)
                "4247" -> DriveMotorOverheatedRearLeftRollerBlind(code)
                "4248" -> DriveMotorOverheatedRearRightRollerBlind(code)
                "4253" -> SystemNotNormed(code)
                "4254" -> SystemNotNormedFrontLeftWindow(code)
                "4255" -> SystemNotNormedFrontRightWindow(code)
                "4256" -> SystemNotNormedRearLeftWindow(code)
                "4257" -> SystemNotNormedRearRightWindow(code)
                "4258" -> SystemNotNormedRearLeftRollerBlind(code)
                "4259" -> SystemNotNormedRearRightRollerBlind(code)
                "4263" -> FeatureNotAvailableRearRollerBlind(code)
                "4265" -> InvalidPowerStatus(code)
                "4266" -> InvalidPowerStatusFrontLeftWindow(code)
                "4267" -> InvalidPowerStatusFrontRightWindow(code)
                "4268" -> InvalidPowerStatusRearLeftWindow(code)
                "4269" -> InvalidPowerStatusRearRightWindow(code)
                "4270" -> InvalidPowerStatusRearLeftRollerBlind(code)
                "4271" -> InvalidPowerStatusRearRightRollerBlind(code)
                "4275" -> InvalidPowerStatusRearRollerBlind(code)
                "4276" -> AfterRunActive(code)
                "4277" -> AfterRunActiveFrontLeftWindow(code)
                "4278" -> AfterRunActiveFrontRightWindow(code)
                "4279" -> AfterRunActiveRearLeftWindow(code)
                "4280" -> AfterRunActiveRearRightWindow(code)
                "4281" -> AfterRunActiveRearLeftRollerBlind(code)
                "4282" -> AfterRunActiveRearRightRollerBlind(code)
                "4286" -> MechanicalProblemRearRollerBlind(code)
                "4287" -> InvalidIgnitionState(code)
                "4288" -> InvalidIgnitionStateFrontLeftWindow(code)
                "4289" -> InvalidIgnitionStateFrontRightWindow(code)
                "4290" -> InvalidIgnitionStateRearLeftWindow(code)
                "4291" -> InvalidIgnitionStateRearRightWindow(code)
                "4292" -> InvalidIgnitionStateRearLeftRollerBlind(code)
                "4293" -> InvalidIgnitionStateRearRightRollerBlind(code)
                "4297" -> ThermalProtectionActiveRearRollerBlind(code)
                "4298" -> VehicleInMotion(code)
                "4302" -> OpenLoadRearRollerBlind(code)
                "4303" -> SystemCouldNotBeNormed(code)
                "4304" -> SystemCouldNotBeNormedFrontLeftWindow(code)
                "4305" -> SystemCouldNotBeNormedFrontRightWindow(code)
                "4306" -> SystemCouldNotBeNormedRearLeftWindow(code)
                "4307" -> SystemCouldNotBeNormedRearRightWindow(code)
                "4308" -> SystemCouldNotBeNormedRearLeftRollerBlind(code)
                "4309" -> SystemCouldNotBeNormedRearRightRollerBlind(code)
                "4313" -> TemperatureTooLowRearRollerBlind(code)
                "4314" -> SystemMalfunction(code)
                "4315" -> SystemMalfunctionFrontLeftWindow(code)
                "4316" -> SystemMalfunctionFrontRightWindow(code)
                "4317" -> SystemMalfunctionRearLeftWindow(code)
                "4318" -> SystemMalfunctionRearRightWindow(code)
                "4319" -> SystemMalfunctionRearLeftRollerBlind(code)
                "4320" -> SystemMalfunctionRearRightRollerBlind(code)
                "4324" -> SystemMalfunctionRearRollerBlind(code)
                "4325" -> InternalSystemError(code)
                "4326" -> InvalidNumberFrontLeftWindow(code)
                "4327" -> FeatureNotAvailableFrontLeftWindow(code)
                "4328" -> InvalidNumberFrontRightWindow(code)
                "4329" -> FeatureNotAvailableFrontRightWindow(code)
                "4330" -> InvalidNumberRearLeftWindow(code)
                "4331" -> FeatureNotAvailableRearLeftWindow(code)
                "4332" -> InvalidNumberRearRightWindow(code)
                "4333" -> FeatureNotAvailableRearRightWindow(code)
                "4336" -> InvalidNumberRearLeftRollerBlind(code)
                "4337" -> FeatureNotAvailableRearLeftRollerBlind(code)
                "4338" -> InvalidNumberRearRightRollerBlind(code)
                "4339" -> FeatureNotAvailableRearRightRollerBlind(code)
                "4344" -> UnknownErrorRearRollerBlind(code)
                "4346" -> InvalidPositionFrontLeftWindow(code)
                "4347" -> UnavailableUiHandlerFrontLeftWindow(code)
                "4348" -> InvalidPositionFrontRightWindow(code)
                "4349" -> UnavailableUiHandlerFrontRightWindow(code)
                "4350" -> InvalidPositionRearLeftWindow(code)
                "4351" -> UnavailableUiHandlerRearLeftWindow(code)
                "4352" -> InvalidPositionRearRightWindow(code)
                "4353" -> UnavailableUiHandlerRearRightWindow(code)
                "4354" -> InvalidPositionRearLeftRollerBlind(code)
                "4355" -> UnavailableUiHandlerRearLeftRollerBlind(code)
                "4356" -> InvalidPositionRearRightRollerBlind(code)
                "4357" -> UnavailableUiHandlerRearRightRollerBlind(code)
                "4364" -> InvalidPositionRearRollerBlind(code)
                "4365" -> UnavailableUiHandlerRearRollerBlind(code)
                "6901" -> WindowRoofCommandFailed(code)
                "6902" -> WindowRoofCommandFailedIgnState(code)
                "6903" -> WindowRoofCommandWindowNotNormed(code)
                "6904" -> WindowRoofCommandServiceNotActive(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the ventilate windows command version v1
sealed class WindowsVentilateError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : WindowsVentilateError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    // Failed due to afterrun active
    class AfterRunActive(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.AfterRunActive)

    // Failed due to afterrun active on front left window
    class AfterRunActiveFrontLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveFrontLeftWindow)

    // Failed due to afterrun active on front right window
    class AfterRunActiveFrontRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveFrontRightWindow)

    // Failed due to afterrun active on rear left roller blind
    class AfterRunActiveRearLeftRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearLeftRollerBlind)

    // Failed due to afterrun active on rear left window
    class AfterRunActiveRearLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearLeftWindow)

    // Failed due to afterrun active on rear right roller blind
    class AfterRunActiveRearRightRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearRightRollerBlind)

    // Failed due to afterrun active on rear right window
    class AfterRunActiveRearRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.AfterRunActiveRearRightWindow)

    // Failed due to anti-trap protection active
    class AntiTrapProtectionActive(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActive)

    // Failed due to anti-trap protection active on front left window
    class AntiTrapProtectionActiveFrontLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveFrontLeftWindow)

    // Failed due to anti-trap protection active on front right window
    class AntiTrapProtectionActiveFrontRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveFrontRightWindow)

    // Failed due to anti-trap protection active on rear left roller blind
    class AntiTrapProtectionActiveRearLeftRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearLeftRollerBlind)

    // Failed due to anti-trap protection active on rear left window
    class AntiTrapProtectionActiveRearLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearLeftWindow)

    // Failed due to anti-trap protection active on rear right roller blind
    class AntiTrapProtectionActiveRearRightRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearRightRollerBlind)

    // Failed due to anti-trap protection active on rear right window
    class AntiTrapProtectionActiveRearRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.AntiTrapProtectionActiveRearRightWindow)

    // Failed due to manual cancellation inside vehicle
    class CancelledManuallyInVehicle(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicle)

    // Failed due to manual cancellation inside vehicle on front left window
    class CancelledManuallyInVehicleFrontLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleFrontLeftWindow)

    // Failed due to manual cancellation inside vehicle on front right window
    class CancelledManuallyInVehicleFrontRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleFrontRightWindow)

    // Failed due to manual cancellation inside vehicle on rear left roller blind
    class CancelledManuallyInVehicleRearLeftRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearLeftRollerBlind)

    // Failed due to manual cancellation inside vehicle on rear left window
    class CancelledManuallyInVehicleRearLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearLeftWindow)

    // Failed due to manual cancellation inside vehicle on rear right roller blind
    class CancelledManuallyInVehicleRearRightRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearRightRollerBlind)

    // Failed due to manual cancellation inside vehicle on rear right window
    class CancelledManuallyInVehicleRearRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleRearRightWindow)

    // Failed due to manual cancellation inside vehicle on sunroof
    class CancelledManuallyInVehicleSunroof(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.CancelledManuallyInVehicleSunroof)

    // Failed due to drive motor overheated
    class DriveMotorOverheated(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheated)

    // Failed due to drive motor overheated on front left window
    class DriveMotorOverheatedFrontLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedFrontLeftWindow)

    // Failed due to drive motor overheated on front right window
    class DriveMotorOverheatedFrontRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedFrontRightWindow)

    // Failed due to drive motor overheated on rear left roller blind
    class DriveMotorOverheatedRearLeftRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearLeftRollerBlind)

    // Failed due to drive motor overheated on rear left window
    class DriveMotorOverheatedRearLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearLeftWindow)

    // Failed due to drive motor overheated on rear right roller blind
    class DriveMotorOverheatedRearRightRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearRightRollerBlind)

    // Failed due to drive motor overheated on rear right window
    class DriveMotorOverheatedRearRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.DriveMotorOverheatedRearRightWindow)

    // Fastpath timeout
    class FastpathTimeout(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.FastpathTimeout)

    // Failed due to feature not available on front left window
    class FeatureNotAvailableFrontLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableFrontLeftWindow)

    // Failed due to feature not available on front right window
    class FeatureNotAvailableFrontRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableFrontRightWindow)

    // Failed due to feature not available on rear left roller blind
    class FeatureNotAvailableRearLeftRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearLeftRollerBlind)

    // Failed due to feature not available on rear left window
    class FeatureNotAvailableRearLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearLeftWindow)

    // Failed due to feature not available on rear right roller blind
    class FeatureNotAvailableRearRightRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearRightRollerBlind)

    // Failed due to feature not available on rear right window
    class FeatureNotAvailableRearRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearRightWindow)

    // Failed due to feature not available on rear roller blind
    class FeatureNotAvailableRearRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.FeatureNotAvailableRearRollerBlind)

    // Failed due to ignition is on
    class IgnitionOn(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.IgnitionOn)

    // Failed due to internal system error
    class InternalSystemError(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InternalSystemError)

    // Failed due to invalid ignition state
    class InvalidIgnitionState(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionState)

    // Failed due to invalid ignition state on front left window
    class InvalidIgnitionStateFrontLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateFrontLeftWindow)

    // Failed due to invalid ignition state on front right window
    class InvalidIgnitionStateFrontRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateFrontRightWindow)

    // Failed due to invalid ignition state on rear left roller blind
    class InvalidIgnitionStateRearLeftRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearLeftRollerBlind)

    // Failed due to invalid ignition state on rear left window
    class InvalidIgnitionStateRearLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearLeftWindow)

    // Failed due to invalid ignition state on rear right roller blind
    class InvalidIgnitionStateRearRightRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearRightRollerBlind)

    // Failed due to invalid ignition state on rear right window
    class InvalidIgnitionStateRearRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidIgnitionStateRearRightWindow)

    // Failed due to invalid number on front left window
    class InvalidNumberFrontLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidNumberFrontLeftWindow)

    // Failed due to invalid number on front right window
    class InvalidNumberFrontRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidNumberFrontRightWindow)

    // Failed due to invalid number on rear left roller blind
    class InvalidNumberRearLeftRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearLeftRollerBlind)

    // Failed due to invalid number on rear left window
    class InvalidNumberRearLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearLeftWindow)

    // Failed due to invalid number on rear right roller blind
    class InvalidNumberRearRightRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearRightRollerBlind)

    // Failed due to invalid number on rear right window
    class InvalidNumberRearRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidNumberRearRightWindow)

    // Failed due to invalid position on front left window
    class InvalidPositionFrontLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidPositionFrontLeftWindow)

    // Failed due to invalid position on front right window
    class InvalidPositionFrontRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidPositionFrontRightWindow)

    // Failed due to invalid position on rear left roller blind
    class InvalidPositionRearLeftRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearLeftRollerBlind)

    // Failed due to invalid position on rear left window
    class InvalidPositionRearLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearLeftWindow)

    // Failed due to invalid position on rear right roller blind
    class InvalidPositionRearRightRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearRightRollerBlind)

    // Failed due to invalid position on rear right window
    class InvalidPositionRearRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearRightWindow)

    // Failed due to invalid position on rear roller blind
    class InvalidPositionRearRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidPositionRearRollerBlind)

    // Failed due to invalid power status
    class InvalidPowerStatus(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatus)

    // Failed due to invalid power status on front left window
    class InvalidPowerStatusFrontLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusFrontLeftWindow)

    // Failed due to invalid power status on front right window
    class InvalidPowerStatusFrontRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusFrontRightWindow)

    // Failed due to invalid power status on rear left roller blind
    class InvalidPowerStatusRearLeftRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearLeftRollerBlind)

    // Failed due to invalid power status on rear left window
    class InvalidPowerStatusRearLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearLeftWindow)

    // Failed due to invalid power status on rear right roller blind
    class InvalidPowerStatusRearRightRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearRightRollerBlind)

    // Failed due to invalid power status on rear right window
    class InvalidPowerStatusRearRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearRightWindow)

    // Failed due to low or high voltage on rear roller blind
    class InvalidPowerStatusRearRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.InvalidPowerStatusRearRollerBlind)

    // Energy level in Battery is too low
    class LowBatteryLevel(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel)

    // Failed due to low battery level 1
    class LowBatteryLevel1(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel1)

    // Failed due to low battery level 2
    class LowBatteryLevel2(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.LowBatteryLevel2)

    // Failed due to mechanical problem on rear roller blind
    class MechanicalProblemRearRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.MechanicalProblemRearRollerBlind)

    // Failed due to multiple anti-trap protection activations
    class MultiAntiTrapProtections(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtections)

    // Failed due to multiple anti-trap protection activations on front left window
    class MultiAntiTrapProtectionsFrontLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsFrontLeftWindow)

    // Failed due to multiple anti-trap protection activations on front right window
    class MultiAntiTrapProtectionsFrontRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsFrontRightWindow)

    // Failed due to multiple anti-trap protection activations on rear left roller blind
    class MultiAntiTrapProtectionsRearLeftRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearLeftRollerBlind)

    // Failed due to multiple anti-trap protection activations on rear left window
    class MultiAntiTrapProtectionsRearLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearLeftWindow)

    // Failed due to multiple anti-trap protection activations on rear right roller blind
    class MultiAntiTrapProtectionsRearRightRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearRightRollerBlind)

    // Failed due to multiple anti-trap protection activations on rear right window
    class MultiAntiTrapProtectionsRearRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.MultiAntiTrapProtectionsRearRightWindow)

    // Failed due to open load on rear roller blind
    class OpenLoadRearRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.OpenLoadRearRollerBlind)

    // Failed due to remote engine start is active
    class RemoteEngineStartIsActive(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.RemoteEngineStartIsActive)

    // Failed due to hall sensor signal problem on rear roller blind
    class SensorProblemRearRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SensorProblemRearRollerBlind)

    // Service not authorized
    class ServiceNotAuthorized(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.ServiceNotAuthorized)

    // Failed due to system is blocked on rear roller blind
    class SystemBlockedRearRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemBlockedRearRollerBlind)

    // Failed due to system could not be normed
    class SystemCouldNotBeNormed(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormed)

    // Failed due to system could not be normed on front left window
    class SystemCouldNotBeNormedFrontLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedFrontLeftWindow)

    // Failed due to system could not be normed on front right window
    class SystemCouldNotBeNormedFrontRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedFrontRightWindow)

    // Failed due to system could not be normed on rear left roller blind
    class SystemCouldNotBeNormedRearLeftRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearLeftRollerBlind)

    // Failed due to system could not be normed on rear left window
    class SystemCouldNotBeNormedRearLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearLeftWindow)

    // Failed due to system could not be normed on rear right roller blind
    class SystemCouldNotBeNormedRearRightRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearRightRollerBlind)

    // Failed due to system could not be normed on rear right window
    class SystemCouldNotBeNormedRearRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemCouldNotBeNormedRearRightWindow)

    // Failed due to system malfunction
    class SystemMalfunction(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemMalfunction)

    // Failed due to system malfunction on front left window
    class SystemMalfunctionFrontLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionFrontLeftWindow)

    // Failed due to system malfunction on front right window
    class SystemMalfunctionFrontRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionFrontRightWindow)

    // Failed due to system malfunction on rear left roller blind
    class SystemMalfunctionRearLeftRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearLeftRollerBlind)

    // Failed due to system malfunction on rear left window
    class SystemMalfunctionRearLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearLeftWindow)

    // Failed due to system malfunction on rear right roller blind
    class SystemMalfunctionRearRightRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearRightRollerBlind)

    // Failed due to system malfunction on rear right window
    class SystemMalfunctionRearRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearRightWindow)

    // Failed due to system malfunction on rear roller blind
    class SystemMalfunctionRearRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemMalfunctionRearRollerBlind)

    // Failed due to system not normed
    class SystemNotNormed(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemNotNormed)

    // Failed due to system not normed  on front left window
    class SystemNotNormedFrontLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedFrontLeftWindow)

    // Failed due to system not normed  on front right window
    class SystemNotNormedFrontRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedFrontRightWindow)

    // Failed due to system not normed on rear left roller blind
    class SystemNotNormedRearLeftRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearLeftRollerBlind)

    // Failed due to system not normed  on rear left window
    class SystemNotNormedRearLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearLeftWindow)

    // Failed due to system not normed on rear right roller blind
    class SystemNotNormedRearRightRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearRightRollerBlind)

    // Failed due to system not normed  on rear right window
    class SystemNotNormedRearRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.SystemNotNormedRearRightWindow)

    // Failed due to temperature too low on rear roller blind
    class TemperatureTooLowRearRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.TemperatureTooLowRearRollerBlind)

    // Failed due to thermal protection active on rear roller blind
    class ThermalProtectionActiveRearRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.ThermalProtectionActiveRearRollerBlind)

    // Failed due to UI handler not available on front left window
    class UnavailableUiHandlerFrontLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerFrontLeftWindow)

    // Failed due to UI handler not available on front right window
    class UnavailableUiHandlerFrontRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerFrontRightWindow)

    // Failed due to UI handler not available on rear left roller blind
    class UnavailableUiHandlerRearLeftRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearLeftRollerBlind)

    // Failed due to UI handler not available on rear left window
    class UnavailableUiHandlerRearLeftWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearLeftWindow)

    // Failed due to UI handler not available on rear right roller blind
    class UnavailableUiHandlerRearRightRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearRightRollerBlind)

    // Failed due to UI handler not available on rear right window
    class UnavailableUiHandlerRearRightWindow(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearRightWindow)

    // Failed due to UI handler not available on rear roller blind
    class UnavailableUiHandlerRearRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.UnavailableUiHandlerRearRollerBlind)

    // Failed due to unknown error on rear roller blind
    class UnknownErrorRearRollerBlind(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.UnknownErrorRearRollerBlind)

    // Failed because vehicle is in motion
    class VehicleInMotion(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.VehicleInMotion)

    // Remote window/roof command failed
    class WindowRoofCommandFailed(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandFailed)

    // Remote window/roof command failed (vehicle state in IGN)
    class WindowRoofCommandFailedIgnState(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandFailedIgnState)

    // Remote window/roof command failed (service not activated in HERMES)
    class WindowRoofCommandServiceNotActive(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandServiceNotActive)

    // Remote window/roof command failed (window not normed)
    class WindowRoofCommandWindowNotNormed(rawErrorCode: String) : WindowsVentilateError(rawErrorCode, InternalVehicleCommandError.WindowRoofCommandWindowNotNormed)

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): WindowsVentilateError {
            return when (code) {
                "21" -> LowBatteryLevel(code)
                "42" -> FastpathTimeout(code)
                "4200" -> ServiceNotAuthorized(code)
                "4201" -> RemoteEngineStartIsActive(code)
                "4202" -> IgnitionOn(code)
                "4203" -> LowBatteryLevel2(code)
                "4204" -> LowBatteryLevel1(code)
                "4205" -> AntiTrapProtectionActive(code)
                "4206" -> AntiTrapProtectionActiveFrontLeftWindow(code)
                "4207" -> AntiTrapProtectionActiveFrontRightWindow(code)
                "4208" -> AntiTrapProtectionActiveRearLeftWindow(code)
                "4209" -> AntiTrapProtectionActiveRearRightWindow(code)
                "4210" -> AntiTrapProtectionActiveRearLeftRollerBlind(code)
                "4211" -> AntiTrapProtectionActiveRearRightRollerBlind(code)
                "4215" -> SystemBlockedRearRollerBlind(code)
                "4216" -> MultiAntiTrapProtections(code)
                "4217" -> MultiAntiTrapProtectionsFrontLeftWindow(code)
                "4218" -> MultiAntiTrapProtectionsFrontRightWindow(code)
                "4219" -> MultiAntiTrapProtectionsRearLeftWindow(code)
                "4220" -> MultiAntiTrapProtectionsRearRightWindow(code)
                "4221" -> MultiAntiTrapProtectionsRearLeftRollerBlind(code)
                "4222" -> MultiAntiTrapProtectionsRearRightRollerBlind(code)
                "4226" -> SensorProblemRearRollerBlind(code)
                "4227" -> CancelledManuallyInVehicle(code)
                "4228" -> CancelledManuallyInVehicleFrontLeftWindow(code)
                "4229" -> CancelledManuallyInVehicleFrontRightWindow(code)
                "4230" -> CancelledManuallyInVehicleRearLeftWindow(code)
                "4231" -> CancelledManuallyInVehicleRearRightWindow(code)
                "4232" -> CancelledManuallyInVehicleRearLeftRollerBlind(code)
                "4233" -> CancelledManuallyInVehicleRearRightRollerBlind(code)
                "4234" -> CancelledManuallyInVehicleSunroof(code)
                "4242" -> DriveMotorOverheated(code)
                "4243" -> DriveMotorOverheatedFrontLeftWindow(code)
                "4244" -> DriveMotorOverheatedFrontRightWindow(code)
                "4245" -> DriveMotorOverheatedRearLeftWindow(code)
                "4246" -> DriveMotorOverheatedRearRightWindow(code)
                "4247" -> DriveMotorOverheatedRearLeftRollerBlind(code)
                "4248" -> DriveMotorOverheatedRearRightRollerBlind(code)
                "4253" -> SystemNotNormed(code)
                "4254" -> SystemNotNormedFrontLeftWindow(code)
                "4255" -> SystemNotNormedFrontRightWindow(code)
                "4256" -> SystemNotNormedRearLeftWindow(code)
                "4257" -> SystemNotNormedRearRightWindow(code)
                "4258" -> SystemNotNormedRearLeftRollerBlind(code)
                "4259" -> SystemNotNormedRearRightRollerBlind(code)
                "4263" -> FeatureNotAvailableRearRollerBlind(code)
                "4265" -> InvalidPowerStatus(code)
                "4266" -> InvalidPowerStatusFrontLeftWindow(code)
                "4267" -> InvalidPowerStatusFrontRightWindow(code)
                "4268" -> InvalidPowerStatusRearLeftWindow(code)
                "4269" -> InvalidPowerStatusRearRightWindow(code)
                "4270" -> InvalidPowerStatusRearLeftRollerBlind(code)
                "4271" -> InvalidPowerStatusRearRightRollerBlind(code)
                "4275" -> InvalidPowerStatusRearRollerBlind(code)
                "4276" -> AfterRunActive(code)
                "4277" -> AfterRunActiveFrontLeftWindow(code)
                "4278" -> AfterRunActiveFrontRightWindow(code)
                "4279" -> AfterRunActiveRearLeftWindow(code)
                "4280" -> AfterRunActiveRearRightWindow(code)
                "4281" -> AfterRunActiveRearLeftRollerBlind(code)
                "4282" -> AfterRunActiveRearRightRollerBlind(code)
                "4286" -> MechanicalProblemRearRollerBlind(code)
                "4287" -> InvalidIgnitionState(code)
                "4288" -> InvalidIgnitionStateFrontLeftWindow(code)
                "4289" -> InvalidIgnitionStateFrontRightWindow(code)
                "4290" -> InvalidIgnitionStateRearLeftWindow(code)
                "4291" -> InvalidIgnitionStateRearRightWindow(code)
                "4292" -> InvalidIgnitionStateRearLeftRollerBlind(code)
                "4293" -> InvalidIgnitionStateRearRightRollerBlind(code)
                "4297" -> ThermalProtectionActiveRearRollerBlind(code)
                "4298" -> VehicleInMotion(code)
                "4302" -> OpenLoadRearRollerBlind(code)
                "4303" -> SystemCouldNotBeNormed(code)
                "4304" -> SystemCouldNotBeNormedFrontLeftWindow(code)
                "4305" -> SystemCouldNotBeNormedFrontRightWindow(code)
                "4306" -> SystemCouldNotBeNormedRearLeftWindow(code)
                "4307" -> SystemCouldNotBeNormedRearRightWindow(code)
                "4308" -> SystemCouldNotBeNormedRearLeftRollerBlind(code)
                "4309" -> SystemCouldNotBeNormedRearRightRollerBlind(code)
                "4313" -> TemperatureTooLowRearRollerBlind(code)
                "4314" -> SystemMalfunction(code)
                "4315" -> SystemMalfunctionFrontLeftWindow(code)
                "4316" -> SystemMalfunctionFrontRightWindow(code)
                "4317" -> SystemMalfunctionRearLeftWindow(code)
                "4318" -> SystemMalfunctionRearRightWindow(code)
                "4319" -> SystemMalfunctionRearLeftRollerBlind(code)
                "4320" -> SystemMalfunctionRearRightRollerBlind(code)
                "4324" -> SystemMalfunctionRearRollerBlind(code)
                "4325" -> InternalSystemError(code)
                "4326" -> InvalidNumberFrontLeftWindow(code)
                "4327" -> FeatureNotAvailableFrontLeftWindow(code)
                "4328" -> InvalidNumberFrontRightWindow(code)
                "4329" -> FeatureNotAvailableFrontRightWindow(code)
                "4330" -> InvalidNumberRearLeftWindow(code)
                "4331" -> FeatureNotAvailableRearLeftWindow(code)
                "4332" -> InvalidNumberRearRightWindow(code)
                "4333" -> FeatureNotAvailableRearRightWindow(code)
                "4336" -> InvalidNumberRearLeftRollerBlind(code)
                "4337" -> FeatureNotAvailableRearLeftRollerBlind(code)
                "4338" -> InvalidNumberRearRightRollerBlind(code)
                "4339" -> FeatureNotAvailableRearRightRollerBlind(code)
                "4344" -> UnknownErrorRearRollerBlind(code)
                "4346" -> InvalidPositionFrontLeftWindow(code)
                "4347" -> UnavailableUiHandlerFrontLeftWindow(code)
                "4348" -> InvalidPositionFrontRightWindow(code)
                "4349" -> UnavailableUiHandlerFrontRightWindow(code)
                "4350" -> InvalidPositionRearLeftWindow(code)
                "4351" -> UnavailableUiHandlerRearLeftWindow(code)
                "4352" -> InvalidPositionRearRightWindow(code)
                "4353" -> UnavailableUiHandlerRearRightWindow(code)
                "4354" -> InvalidPositionRearLeftRollerBlind(code)
                "4355" -> UnavailableUiHandlerRearLeftRollerBlind(code)
                "4356" -> InvalidPositionRearRightRollerBlind(code)
                "4357" -> UnavailableUiHandlerRearRightRollerBlind(code)
                "4364" -> InvalidPositionRearRollerBlind(code)
                "4365" -> UnavailableUiHandlerRearRollerBlind(code)
                "6901" -> WindowRoofCommandFailed(code)
                "6902" -> WindowRoofCommandFailedIgnState(code)
                "6903" -> WindowRoofCommandWindowNotNormed(code)
                "6904" -> WindowRoofCommandServiceNotActive(code)
                else -> GenericError(GenericCommandError.fromErrorCode(code, attributes))
            }
        }
    }
}

// Error codes for the configure zev command version v1
sealed class ZevPreconditioningConfigureError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : ZevPreconditioningConfigureError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): ZevPreconditioningConfigureError {
            return GenericError(GenericCommandError.fromErrorCode(code, attributes))
        }
    }
}

// Error codes for the configureseats zev command version v1
sealed class ZevPreconditioningConfigureSeatsError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : ZevPreconditioningConfigureSeatsError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): ZevPreconditioningConfigureSeatsError {
            return GenericError(GenericCommandError.fromErrorCode(code, attributes))
        }
    }
}

// Error codes for the stop zev command version v1
sealed class ZevPreconditioningStopError(rawErrorCode: String, val error: InternalVehicleCommandError) : VehicleCommandError(rawErrorCode) {

    override fun getErrorCode(): InternalVehicleCommandError {
        return error
    }

    class GenericError(override val genericError: GenericCommandError) : ZevPreconditioningStopError(genericError.rawErrorCode, genericError.getErrorCode()), GenericVehicleCommandError

    companion object {
        fun fromErrorCode(code: String, attributes: Map<String, Value>): ZevPreconditioningStopError {
            return GenericError(GenericCommandError.fromErrorCode(code, attributes))
        }
    }
}
