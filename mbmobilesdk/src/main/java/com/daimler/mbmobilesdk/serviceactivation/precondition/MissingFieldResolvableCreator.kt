package com.daimler.mbmobilesdk.serviceactivation.precondition

interface MissingFieldResolvableCreator {

    fun createResolvable(preconditionType: ServicePreconditionType.SignUserAgreement)
    fun createResolvable(preconditionType: ServicePreconditionType.PurchaseLicense)
    fun createResolvable(preconditionType: ServicePreconditionType.UpdateTrustLevel)
    fun createResolvable(preconditionType: ServicePreconditionType.UserMail)
    fun createResolvable(preconditionType: ServicePreconditionType.UserMailVerified)
    fun createResolvable(preconditionType: ServicePreconditionType.UserPhone)
    fun createResolvable(preconditionType: ServicePreconditionType.UserPhoneVerified)
    fun createResolvable(preconditionType: ServicePreconditionType.LicensePlate)
    fun createResolvable(preconditionType: ServicePreconditionType.VehicleServiceDealer)
    fun createResolvable(preconditionType: ServicePreconditionType.UserContactMethod)
    fun createResolvable(preconditionType: ServicePreconditionType.CpIncredit)
}