package com.daimler.mbcarkit.network

import com.daimler.mbcarkit.business.AccountLinkageService
import com.daimler.mbcarkit.business.AssignmentService
import com.daimler.mbcarkit.business.GeofencingService
import com.daimler.mbcarkit.business.OnboardGeofencingService
import com.daimler.mbcarkit.business.OutletsService
import com.daimler.mbcarkit.business.SendToCarProvider
import com.daimler.mbcarkit.business.ServiceService
import com.daimler.mbcarkit.business.SpeedAlertService
import com.daimler.mbcarkit.business.SpeedfenceService
import com.daimler.mbcarkit.business.UserManagementService
import com.daimler.mbcarkit.business.ValetProtectService
import com.daimler.mbcarkit.business.VehicleImageService
import com.daimler.mbcarkit.business.VehicleService

internal interface RetrofitServiceProvider {

    fun createAccountLinkageService(): AccountLinkageService

    fun createAssignmentService(): AssignmentService

    fun createGeofencingService(): GeofencingService

    fun createOutletsService(): OutletsService

    fun createSendToCarProvider(): SendToCarProvider

    fun createServiceService(): ServiceService

    fun createSpeedAlertService(): SpeedAlertService

    fun createOnboardGeofencingService(): OnboardGeofencingService

    fun createSpeedfenceService(): SpeedfenceService

    fun createUserManagementService(): UserManagementService

    fun createValetProtectService(): ValetProtectService

    fun createVehicleImageService(): VehicleImageService

    fun createVehicleService(): VehicleService
}
