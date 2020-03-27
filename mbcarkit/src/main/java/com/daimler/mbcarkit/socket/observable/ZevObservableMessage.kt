package com.daimler.mbcarkit.socket.observable

import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbcarkit.business.model.vehicle.Zev
import com.daimler.mbcarkit.business.model.vehicle.ZevTariff
import com.daimler.mbcarkit.business.model.vehicle.ZevTemperature

class ZevObservableMessage(zev: Zev) : VehicleObservableMessage<Zev>(zev) {

    override fun hasChanged(oldVehicleStatus: VehicleStatus?, updatedVehicleStatus: VehicleStatus): Boolean {
        return oldVehicleStatus == null || zevChanged(oldVehicleStatus.zev, updatedVehicleStatus.zev)
    }

    private fun zevChanged(oldZev: Zev, updatedZev: Zev): Boolean {
        return (oldZev.chargingActive != updatedZev.chargingActive)
            .or(oldZev.chargingError != updatedZev.chargingError)
            .or(oldZev.chargingMode != updatedZev.chargingMode)
            .or(oldZev.chargingStatus != updatedZev.chargingStatus)
            .or(oldZev.hybridWarnings != updatedZev.hybridWarnings)
            .or(oldZev.activeState != updatedZev.activeState)
            .or(oldZev.maxSoc != updatedZev.maxSoc)
            .or(oldZev.maxSocLowerLimit != updatedZev.maxSocLowerLimit)
            .or(oldZev.selectedChargeProgram != updatedZev.selectedChargeProgram)
            .or(oldZev.smartCharging != updatedZev.smartCharging)
            .or(oldZev.smartChargingAtDeparture != updatedZev.smartChargingAtDeparture)
            .or(oldZev.smartChargingAtDeparture2 != updatedZev.smartChargingAtDeparture2)
            .or(temperatureChanged(oldZev.temperature, updatedZev.temperature))
            .or(tariffChanged(oldZev.weekdayTariff.value, updatedZev.weekdayTariff.value))
            .or(tariffChanged(oldZev.weekendTariff.value, updatedZev.weekendTariff.value))
            .or(oldZev.chargingPower != updatedZev.chargingPower)
            .or(oldZev.departureTime != updatedZev.departureTime)
            .or(oldZev.departureTimeMode != updatedZev.departureTimeMode)
            .or(oldZev.departureTimeSoc != updatedZev.departureTimeSoc)
            .or(oldZev.departureTimeWeekday != updatedZev.departureTimeWeekday)
            .or(oldZev.endOfChargeTime != updatedZev.endOfChargeTime)
            .or(oldZev.endOfChargeTimeRelative != updatedZev.endOfChargeTimeRelative)
            .or(oldZev.endOfChargeTimeWeekday != updatedZev.endOfChargeTimeWeekday)
            .or(oldZev.maxRange != updatedZev.maxRange)
            .or(oldZev.precondActive != updatedZev.precondActive)
            .or(oldZev.precondAtDeparture != updatedZev.precondAtDeparture)
            .or(oldZev.precondAtDepartureDisable != updatedZev.precondAtDepartureDisable)
            .or(oldZev.precondDuration != updatedZev.precondDuration)
            .or(oldZev.precondError != updatedZev.precondError)
            .or(oldZev.precondNow != updatedZev.precondNow)
            .or(oldZev.precondNowError != updatedZev.precondNowError)
            .or(oldZev.precondSeatFrontRight != updatedZev.precondSeatFrontRight)
            .or(oldZev.precondSeatFrontLeft != updatedZev.precondSeatFrontLeft)
            .or(oldZev.precondSeatRearRight != updatedZev.precondSeatRearRight)
            .or(oldZev.precondSeatRearLeft != updatedZev.precondSeatRearLeft)
            .or(oldZev.socprofile != updatedZev.socprofile)
            .or(oldZev.chargingPrograms != updatedZev.chargingPrograms)
            .or(oldZev.bidirectionalChargingActive != updatedZev.bidirectionalChargingActive)
            .or(oldZev.minSoc != updatedZev.minSoc)
            .or(oldZev.acChargingCurrentLimitation != updatedZev.acChargingCurrentLimitation)
            .or(oldZev.chargingErrorInfrastructure != updatedZev.chargingErrorInfrastructure)
            .or(oldZev.chargingTimeType != updatedZev.chargingTimeType)
            .or(oldZev.minSocLowerLimit != updatedZev.minSocLowerLimit)
            .or(oldZev.nextDepartureTime != updatedZev.nextDepartureTime)
            .or(oldZev.nextDepartureTimeWeekday != updatedZev.nextDepartureTimeWeekday)
            .or(oldZev.departureTimeIcon != updatedZev.departureTimeIcon)
            .or(oldZev.chargingErrorWim != updatedZev.chargingErrorWim)
            .or(oldZev.maxSocUpperLimit != updatedZev.maxSocUpperLimit)
            .or(oldZev.minSocUpperLimit != updatedZev.minSocUpperLimit)
            .or(oldZev.chargingPowerEcoLimit != updatedZev.chargingPowerEcoLimit)
            .or(oldZev.chargeFlapDCStatus != updatedZev.chargeFlapDCStatus)
            .or(oldZev.chargeFlapACStatus != updatedZev.chargeFlapACStatus)
            .or(oldZev.chargeCouplerDCLockStatus != updatedZev.chargeCouplerDCLockStatus)
            .or(oldZev.chargeCouplerACLockStatus != updatedZev.chargeCouplerACLockStatus)
            .or(oldZev.chargeCouplerDCStatus != updatedZev.chargeCouplerDCStatus)
            .or(oldZev.chargeCouplerACStatus != updatedZev.chargeCouplerACStatus)
            .or(oldZev.evRangeAssistDriveOnTime != updatedZev.evRangeAssistDriveOnTime)
            .or(oldZev.evRangeAssistDriveOnSOC != updatedZev.evRangeAssistDriveOnSOC)
            .or(oldZev.vehicleChargingPowerControl != updatedZev.vehicleChargingPowerControl)
            .or(oldZev.vehicleChargingBreakClockTimers != updatedZev.vehicleChargingBreakClockTimers)
    }

    private fun tariffChanged(oldWeekdayTariff: List<ZevTariff>?, updatedWeekdayTariff: List<ZevTariff>?): Boolean {
        return if (oldWeekdayTariff != null && updatedWeekdayTariff != null) {
            oldWeekdayTariff.toTypedArray().contentDeepEquals(updatedWeekdayTariff.toTypedArray())
        } else {
            false
        }
    }

    private fun temperatureChanged(oldTemperature: ZevTemperature, updatedTemperature: ZevTemperature): Boolean {
        return (oldTemperature.frontCenter != updatedTemperature.frontCenter)
            .or(oldTemperature.frontLeft != updatedTemperature.frontLeft)
            .or(oldTemperature.frontRight != updatedTemperature.frontRight)
            .or(oldTemperature.rearCenter != updatedTemperature.rearCenter)
            .or(oldTemperature.rearCenter2 != updatedTemperature.rearCenter2)
            .or(oldTemperature.rearLeft != updatedTemperature.rearLeft)
            .or(oldTemperature.rearRight != updatedTemperature.rearRight)
    }
}
