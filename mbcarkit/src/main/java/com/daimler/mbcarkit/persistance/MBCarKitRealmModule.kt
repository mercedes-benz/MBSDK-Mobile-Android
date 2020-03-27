package com.daimler.mbcarkit.persistance

import com.daimler.mbcarkit.persistance.model.RealmChargingProgram
import com.daimler.mbcarkit.persistance.model.RealmCommand
import com.daimler.mbcarkit.persistance.model.RealmCommandParameter
import com.daimler.mbcarkit.persistance.model.RealmDayPeriod
import com.daimler.mbcarkit.persistance.model.RealmMerchant
import com.daimler.mbcarkit.persistance.model.RealmMerchantAddress
import com.daimler.mbcarkit.persistance.model.RealmMerchantOpeningHours
import com.daimler.mbcarkit.persistance.model.RealmMissingAccountLinkage
import com.daimler.mbcarkit.persistance.model.RealmMissingServiceData
import com.daimler.mbcarkit.persistance.model.RealmOpeningDay
import com.daimler.mbcarkit.persistance.model.RealmPrerequisiteCheck
import com.daimler.mbcarkit.persistance.model.RealmSendToCarCapability
import com.daimler.mbcarkit.persistance.model.RealmService
import com.daimler.mbcarkit.persistance.model.RealmTimeProfile
import com.daimler.mbcarkit.persistance.model.RealmVehicle
import com.daimler.mbcarkit.persistance.model.RealmVehicleAssignedUser
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeChargingBreakClockTimer
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeChargingPowerControl
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeChargingProgram
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeDouble
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeInt
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeLong
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeSocProfile
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeSpeedAlertConfiguration
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeTariff
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeWeeklyProfile
import com.daimler.mbcarkit.persistance.model.RealmVehicleAttributeWeeklySetHU
import com.daimler.mbcarkit.persistance.model.RealmVehicleChargingBreakClockTimer
import com.daimler.mbcarkit.persistance.model.RealmVehicleDealer
import com.daimler.mbcarkit.persistance.model.RealmVehicleImage
import com.daimler.mbcarkit.persistance.model.RealmVehicleLocalProfiles
import com.daimler.mbcarkit.persistance.model.RealmVehicleState
import com.daimler.mbcarkit.persistance.model.RealmVehicleUserManagement
import io.realm.annotations.RealmModule

@RealmModule(
    library = true,
    classes = [
        RealmVehicle::class,
        RealmVehicleAttributeDouble::class,
        RealmVehicleAttributeInt::class,
        RealmVehicleAttributeLong::class,
        RealmVehicleAttributeTariff::class,
        RealmVehicleAttributeWeeklySetHU::class,
        RealmVehicleAttributeSpeedAlertConfiguration::class,
        RealmVehicleAttributeSocProfile::class,
        RealmVehicleImage::class,
        RealmVehicleState::class,
        RealmVehicleDealer::class,
        RealmDayPeriod::class,
        RealmMerchant::class,
        RealmMerchantAddress::class,
        RealmMerchantOpeningHours::class,
        RealmOpeningDay::class,
        RealmService::class,
        RealmPrerequisiteCheck::class,
        RealmMissingAccountLinkage::class,
        RealmMissingServiceData::class,
        RealmVehicleUserManagement::class,
        RealmVehicleAssignedUser::class,
        RealmVehicleLocalProfiles::class,
        RealmCommandParameter::class,
        RealmCommand::class,
        RealmSendToCarCapability::class,
        RealmTimeProfile::class,
        RealmVehicleAttributeWeeklyProfile::class,
        RealmChargingProgram::class,
        RealmVehicleAttributeChargingProgram::class,
        RealmVehicleChargingBreakClockTimer::class,
        RealmVehicleAttributeChargingBreakClockTimer::class,
        RealmVehicleAttributeChargingPowerControl::class
    ]
)
class MBCarKitRealmModule
