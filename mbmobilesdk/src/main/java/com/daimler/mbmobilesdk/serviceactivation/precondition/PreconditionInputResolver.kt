package com.daimler.mbmobilesdk.serviceactivation.precondition

import com.daimler.mbmobilesdk.serviceactivation.views.FieldResolvable

interface PreconditionInputResolver {

    fun resolve(preconditionType: ServicePreconditionType.SignUserAgreement, resolvable: FieldResolvable)
    fun resolve(preconditionType: ServicePreconditionType.PurchaseLicense, resolvable: FieldResolvable)
    fun resolve(preconditionType: ServicePreconditionType.UpdateTrustLevel, resolvable: FieldResolvable)
    fun resolve(preconditionType: ServicePreconditionType.UserMail, resolvable: FieldResolvable)
    fun resolve(preconditionType: ServicePreconditionType.UserMailVerified, resolvable: FieldResolvable)
    fun resolve(preconditionType: ServicePreconditionType.UserPhone, resolvable: FieldResolvable)
    fun resolve(preconditionType: ServicePreconditionType.UserPhoneVerified, resolvable: FieldResolvable)
    fun resolve(preconditionType: ServicePreconditionType.LicensePlate, resolvable: FieldResolvable)
    fun resolve(preconditionType: ServicePreconditionType.VehicleServiceDealer, resolvable: FieldResolvable)
    fun resolve(preconditionType: ServicePreconditionType.UserContactMethod, resolvable: FieldResolvable)
    fun resolve(preconditionType: ServicePreconditionType.CpIncredit, resolvable: FieldResolvable)
}