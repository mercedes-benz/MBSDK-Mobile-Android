package com.daimler.mbmobilesdk.serviceactivation.precondition

internal class MockServicePreparation : ServicePreconditionPreparation {

    override fun preconditions(): List<ServicePreconditionType> {
        val list = mutableListOf<ServicePreconditionType>()
        list.add(ServicePreconditionType.SignUserAgreement())
        list.add(ServicePreconditionType.PurchaseLicense(0))
        list.add(ServicePreconditionType.UpdateTrustLevel())
        list.add(ServicePreconditionType.UserMail())
        list.add(ServicePreconditionType.UserMailVerified())
        list.add(ServicePreconditionType.UserPhone())
        list.add(ServicePreconditionType.UserPhoneVerified())
        list.add(ServicePreconditionType.UserContactMethod())
        list.add(ServicePreconditionType.LicensePlate())
        list.add(ServicePreconditionType.VehicleServiceDealer())
        return list
    }
}